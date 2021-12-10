package com.chj.yummyproject;

public class Listitem {

    private int icon;
    private String eng;
    private String kor;
    private int voice_file;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getKor() {
        return kor;
    }

    public void setKor(String kor) {
        this.kor = kor;
    }

    public void setVoice_file(int voice_file) {
        this.voice_file = voice_file;
    }

    public int getVoice_file() {
        return voice_file;
    }
}
