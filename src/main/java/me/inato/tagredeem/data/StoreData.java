package me.inato.tagredeem.data;

import java.util.HashSet;

public class StoreData {

    private HashSet<String> tags;
    private String selected;

    public HashSet<String> getTags() {
        return tags;
    }

    public void setSelectedTag(String selected) {
         this.selected = selected;
    }

    public String getSelectedTag() {
        return selected;
    }

    public StoreData(String selected, HashSet<String> tags) {
        this.selected = selected;
        this.tags = tags;
    }

}
