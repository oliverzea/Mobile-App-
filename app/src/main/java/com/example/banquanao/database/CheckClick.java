package com.example.banquanao.database;

public class CheckClick {
    private boolean check ;
    private int id ;

    public CheckClick(boolean check, int id) {
        this.check = check;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
