package com.github.tvbox.mobile.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.tvbox.mobile.R;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.chip.Chip;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText etSearch;
    private ImageView ivClearInput;
    private FlexboxLayout flexboxHistory;
    private FlexboxLayout flexboxHot;

    private static final String KEY_SEARCH_HISTORY = "search_history";
    private static final int MAX_HISTORY = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Light status bar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_bg_primary));

        setContentView(R.layout.activity_search);

        etSearch = findViewById(R.id.et_search);
        ivClearInput = findViewById(R.id.iv_clear_input);
        flexboxHistory = findViewById(R.id.flexbox_history);
        flexboxHot = findViewById(R.id.flexbox_hot);

        // Back
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());

        // Clear history
        findViewById(R.id.iv_clear_history).setOnClickListener(v -> {
            Hawk.delete(KEY_SEARCH_HISTORY);
            flexboxHistory.removeAllViews();
            findViewById(R.id.ll_history_section).setVisibility(View.GONE);
        });

        // Clear input
        ivClearInput.setOnClickListener(v -> etSearch.setText(""));

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                ivClearInput.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                doSearch(etSearch.getText().toString().trim());
                return true;
            }
            return false;
        });

        // Search button
        findViewById(R.id.iv_search_btn).setOnClickListener(v ->
                doSearch(etSearch.getText().toString().trim()));

        loadHistory();
        loadHotSearch();

        // Auto focus
        etSearch.requestFocus();
    }

    private void doSearch(String keyword) {
        if (keyword.isEmpty()) return;
        saveHistory(keyword);
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("keyword", keyword);
        startActivity(intent);
    }

    private void loadHistory() {
        List<String> history = Hawk.get(KEY_SEARCH_HISTORY, new ArrayList<>());
        if (history.isEmpty()) {
            findViewById(R.id.ll_history_section).setVisibility(View.GONE);
            return;
        }
        flexboxHistory.removeAllViews();
        for (String kw : history) {
            addChip(flexboxHistory, kw, true);
        }
    }

    private void loadHotSearch() {
        // Static hot keywords (can be fetched from API later)
        String[] hotKeywords = {"古装剧", "犯罪片", "科幻", "爱情片", "综艺", "动漫"};
        flexboxHot.removeAllViews();
        for (String kw : hotKeywords) {
            addChip(flexboxHot, kw, false);
        }
    }

    private void addChip(FlexboxLayout parent, String text, boolean isHistory) {
        View chipView = getLayoutInflater().inflate(R.layout.item_search_chip, parent, false);
        if (chipView instanceof android.widget.TextView) {
            ((android.widget.TextView) chipView).setText(text);
        }
        chipView.setOnClickListener(v -> doSearch(text));
        parent.addView(chipView);
    }

    private void saveHistory(String keyword) {
        List<String> history = Hawk.get(KEY_SEARCH_HISTORY, new ArrayList<>());
        history.remove(keyword); // remove duplicate
        history.add(0, keyword);
        if (history.size() > MAX_HISTORY) {
            history = history.subList(0, MAX_HISTORY);
        }
        Hawk.put(KEY_SEARCH_HISTORY, history);
    }
}
