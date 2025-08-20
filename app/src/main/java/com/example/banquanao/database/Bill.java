package com.example.banquanao.database;

public class Bill {
    private int id;
    private String name;
    private int quantity;
    private double price;
    private double total;
    private int state;
    private byte[] img;

    public Bill(){}

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", total=" + total +
                ", state=" + state +
                ", img=" + img +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public  byte[] getImg() {
        return img;
    }

    public void setImg( byte[] img) {
        this.img = img;
    }
}
