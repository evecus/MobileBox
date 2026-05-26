package com.github.tvbox.mobile.bean;

import java.util.ArrayList;
import java.util.List;

public class LiveChannelGroup {
    private String groupName;
    private List<LiveChannelItem> channelList = new ArrayList<>();
    private boolean passwordProtected;
    private String password;

    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
    public List<LiveChannelItem> getChannelList() { return channelList; }
    public void setChannelList(List<LiveChannelItem> channelList) { this.channelList = channelList; }
    public boolean isPasswordProtected() { return passwordProtected; }
    public void setPasswordProtected(boolean passwordProtected) { this.passwordProtected = passwordProtected; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
