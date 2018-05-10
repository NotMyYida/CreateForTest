package com.example.hp.verifyimagination.popmenu;

/**
 * 菜单项.
 */
public class PopMenuMoreItem {
    public int id;
    public int resId;
    public String text;

    public PopMenuMoreItem(int id, String text) {
        this.id = id;
        this.resId = 0;
        this.text = text;
    }

    public PopMenuMoreItem(int id, int resId, String text) {
        this.id = id;
        this.resId = resId;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}