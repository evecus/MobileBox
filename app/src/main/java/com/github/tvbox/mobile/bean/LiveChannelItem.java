package com.github.tvbox.mobile.bean;

import java.util.ArrayList;
import java.util.List;

public class LiveChannelItem {
    private String channelName;
    private int channelNum;
    private String channelLogo;
    private List<String> urls = new ArrayList<>();
    private int currentUrlIndex = 0;

    public String getChannelName() { return channelName; }
    public void setChannelName(String channelName) { this.channelName = channelName; }
    public int getChannelNum() { return channelNum; }
    public void setChannelNum(int channelNum) { this.channelNum = channelNum; }
    public String getChannelLogo() { return channelLogo; }
    public void setChannelLogo(String channelLogo) { this.channelLogo = channelLogo; }
    public List<String> getUrls() { return urls; }
    public void setUrls(List<String> urls) { this.urls = urls; }
    public int getCurrentUrlIndex() { return currentUrlIndex; }
    public void setCurrentUrlIndex(int idx) { this.currentUrlIndex = idx; }

    public String getCurrentUrl() {
        if (urls.isEmpty()) return "";
        return urls.get(Math.min(currentUrlIndex, urls.size() - 1));
    }
}
