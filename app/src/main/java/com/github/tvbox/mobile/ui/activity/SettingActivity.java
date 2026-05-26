package com.github.tvbox.mobile.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.tvbox.mobile.R;
import com.github.tvbox.mobile.util.HawkConfig;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.orhanobut.hawk.Hawk;

public class SettingActivity extends AppCompatActivity {

    private String page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_bg_primary));
        setContentView(R.layout.activity_setting);

        page = getIntent().getStringExtra("page");
        if (page == null) page = "api";

        setupToolbar();
        setupContent();
    }

    private void setupToolbar() {
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());
        TextView tvTitle = findViewById(R.id.tv_setting_page_title);
        switch (page) {
            case "api":    tvTitle.setText("订阅接口"); break;
            case "live":   tvTitle.setText("直播订阅"); break;
            case "player": tvTitle.setText("播放器"); break;
            case "decode": tvTitle.setText("解码方式"); break;
            case "misc":   tvTitle.setText("播放设置"); break;
            case "about":  tvTitle.setText("关于"); break;
            default:       tvTitle.setText("设置"); break;
        }
    }

    private void setupContent() {
        View contentApi   = findViewById(R.id.layout_setting_api);
        View contentLive  = findViewById(R.id.layout_setting_live);
        View contentPlayer= findViewById(R.id.layout_setting_player);
        View contentAbout = findViewById(R.id.layout_setting_about);

        // Hide all, show relevant
        contentApi.setVisibility(View.GONE);
        contentLive.setVisibility(View.GONE);
        contentPlayer.setVisibility(View.GONE);
        contentAbout.setVisibility(View.GONE);

        switch (page) {
            case "api":
                contentApi.setVisibility(View.VISIBLE);
                setupApiSection();
                break;
            case "live":
                contentLive.setVisibility(View.VISIBLE);
                setupLiveSection();
                break;
            case "player":
            case "decode":
            case "misc":
                contentPlayer.setVisibility(View.VISIBLE);
                setupPlayerSection();
                break;
            case "about":
                contentAbout.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setupApiSection() {
        TextInputEditText etUrl = findViewById(R.id.et_api_url);
        String saved = Hawk.get(HawkConfig.API_URL, "");
        etUrl.setText(saved);

        findViewById(R.id.btn_save_api).setOnClickListener(v -> {
            String url = etUrl.getText() != null ? etUrl.getText().toString().trim() : "";
            Hawk.put(HawkConfig.API_URL, url);
            Toast.makeText(this, "已保存，重启App生效", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btn_clear_api).setOnClickListener(v -> {
            etUrl.setText("");
            Hawk.delete(HawkConfig.API_URL);
            Toast.makeText(this, "已清除", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupLiveSection() {
        TextInputEditText etLiveUrl = findViewById(R.id.et_live_url);
        String saved = Hawk.get(HawkConfig.LIVE_URL, "");
        etLiveUrl.setText(saved);

        findViewById(R.id.btn_save_live).setOnClickListener(v -> {
            String url = etLiveUrl.getText() != null ? etLiveUrl.getText().toString().trim() : "";
            Hawk.put(HawkConfig.LIVE_URL, url);
            Toast.makeText(this, "已保存", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupPlayerSection() {
        // Player engine selector
        int engine = Hawk.get(HawkConfig.PLAYER_ENGINE, HawkConfig.PLAYER_IJK);
        int decode  = Hawk.get(HawkConfig.DECODE_MODE,  HawkConfig.DECODE_HARD);

        String[] engines = {"IjkPlayer", "ExoPlayer", "系统播放器"};
        String[] decodes  = {"硬解 (性能好)", "软解 (兼容好)"};

        TextView tvEngine = findViewById(R.id.tv_player_engine_value);
        TextView tvDecode = findViewById(R.id.tv_decode_value);
        tvEngine.setText(engines[Math.min(engine, engines.length-1)]);
        tvDecode.setText(decodes[Math.min(decode, decodes.length-1)]);

        findViewById(R.id.row_player_engine_pick).setOnClickListener(v ->
                showPickerDialog("播放器内核", engines, engine, idx -> {
                    Hawk.put(HawkConfig.PLAYER_ENGINE, idx);
                    tvEngine.setText(engines[idx]);
                }));

        findViewById(R.id.row_decode_pick).setOnClickListener(v ->
                showPickerDialog("解码方式", decodes, decode, idx -> {
                    Hawk.put(HawkConfig.DECODE_MODE, idx);
                    tvDecode.setText(decodes[idx]);
                }));
    }

    private void showPickerDialog(String title, String[] options, int current, PickCallback cb) {
        BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.BottomSheetDialog);
        View view = getLayoutInflater().inflate(R.layout.dialog_picker, null);
        dialog.setContentView(view);

        TextView tvDialogTitle = view.findViewById(R.id.tv_picker_title);
        tvDialogTitle.setText(title);

        android.widget.RadioGroup rg = view.findViewById(R.id.rg_options);
        for (int i = 0; i < options.length; i++) {
            android.widget.RadioButton rb = new android.widget.RadioButton(this);
            rb.setText(options[i]);
            rb.setId(i);
            rb.setPadding(48, 24, 48, 24);
            rb.setTextSize(15);
            rb.setTextColor(ContextCompat.getColor(this, R.color.color_text_primary));
            rg.addView(rb);
            if (i == current) rb.setChecked(true);
        }

        view.findViewById(R.id.btn_picker_confirm).setOnClickListener(v -> {
            int checkedId = rg.getCheckedRadioButtonId();
            if (checkedId >= 0) cb.onPick(checkedId);
            dialog.dismiss();
        });
        view.findViewById(R.id.btn_picker_cancel).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    interface PickCallback { void onPick(int index); }
}
