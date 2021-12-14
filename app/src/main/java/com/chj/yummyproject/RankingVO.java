package com.chj.yummyproject;

public class RankingVO {
    private int rank_num;
    private String rank_img;
    private String rank_name;
    private double rank_score;

    public RankingVO(int rank_num, String rank_img, String rank_name, double rank_score) {
        this.rank_num = rank_num;
        this.rank_img = rank_img;
        this.rank_name = rank_name;
        this.rank_score = rank_score;
    }

    public int getRank_num() {
        return rank_num;
    }

    public void setRank_num(int rank_num) {
        this.rank_num = rank_num;
    }

    public String getRank_img() {
        return rank_img;
    }

    public void setRank_img(String rank_img) {
        this.rank_img = rank_img;
    }

    public String getRank_name() {
        return rank_name;
    }

    public void setRank_name(String rank_name) {
        this.rank_name = rank_name;
    }

    public double getRank_score() {
        return rank_score;
    }

    public void setRank_score(double rank_score) {
        this.rank_score = rank_score;
    }
}
