package com.example.banquanao.database;

public class Util {
    private int id_pro;
    private int quantity;

    public Util(int id_pro, int quantity) {

        this.id_pro = id_pro;
        this.quantity = quantity;
    }

    public int getId_pro() {
        return id_pro;
    }

    public void setId_pro(int id_pro) {

        this.id_pro = id_pro;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
