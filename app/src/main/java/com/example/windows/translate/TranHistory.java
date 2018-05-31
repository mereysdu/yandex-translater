package com.example.windows.translate;

public class TranHistory {
    int id;
    String text;
    String favorite;
    public TranHistory() {}

    public int getId() {
        return this.id;
    }
    public String getText() {
        return this.text;
    }
    public String getFavorite() {
        return this.favorite;
    }
    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

}
