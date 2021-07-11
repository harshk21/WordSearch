package com.hk210.wordssearch.Model;

public class DataModel {
    public String word;
    public String score;

    public DataModel(String word, String score) {
        this.word = word;
        this.score = score;
    }

    public DataModel() {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }


}
