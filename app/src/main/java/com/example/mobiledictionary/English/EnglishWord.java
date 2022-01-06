package com.example.mobiledictionary.English;

public class EnglishWord {
    private int id;
    private String word;
    private String meaning;
    private int highlight;
    private String note;

    public EnglishWord (int id, String word, String meaning, int highlight, String note)
    {
        this.id = id;
        this.word = word;
        this.meaning = meaning;
        this.highlight = highlight;
        this.note = note;
    }

    public int getId () {
        return id;
    }
    public String getWord () {
        return word;
    }
    public String getMeaning () {
        return meaning;
    }
    public int getHighlight() {
        return highlight;
    }
    public String getNote () {
        return note;
    }
    public void setId (int id) {
        this.id = id;
    }

    public void setWord (String word) {
        this.word = word;
    }
    public void setMeaning (String meaning) {
        this.meaning = meaning;
    }
    public void setNote (String note) {
        this.note = note;
    }
    public void setHighlight (int highlight) {
        this.highlight = highlight;
    }
}
