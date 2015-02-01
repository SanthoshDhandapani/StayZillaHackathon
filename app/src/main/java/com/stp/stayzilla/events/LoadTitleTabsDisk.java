package com.stp.stayzilla.events;

import java.util.List;

public class LoadTitleTabsDisk {
    private List<String> listTitleTabs;

    public LoadTitleTabsDisk(List<String> listTitleTabs) {
        this.listTitleTabs = listTitleTabs;
    }

    public List<String> getListTitleTabs() {
        return listTitleTabs;
    }

    public void setListTitleTabs(List<String> listTitleTabs) {
        this.listTitleTabs = listTitleTabs;
    }
}
