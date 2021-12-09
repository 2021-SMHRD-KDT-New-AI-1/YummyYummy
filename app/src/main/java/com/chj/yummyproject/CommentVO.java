package com.chj.yummyproject;

public class CommentVO {
    private String member_id;
    private String comment_cont;
    private String comment_date;
    private int comment_num;

    public CommentVO (String member_id, String comment_cont, String comment_date, int comment_num) {
        this.member_id = member_id;
        this.comment_cont = comment_cont;
        this.comment_date = comment_date;
        this.comment_num = comment_num;
    }

    public String getMember_id() {
        return member_id;
    }

    public String getComment_cont() {
        return comment_cont;
    }

    public String getComment_date() {
        return comment_date;
    }

    public int getComment_num() {
        return comment_num;
    }
}

