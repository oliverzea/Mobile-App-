package com.example.banquanao.database;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private double price;
    private byte[] idResourse ;
    private int Quantity;
    private double total;
    private double width;
    private double height;
    private double weight;
    private String sex;
    private String type;
    private int size;


    public Product(){}

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    private int id_category;
    public Product(byte[] hinhsp, String tensp, double dongia, int soluong) {
        this.idResourse = hinhsp;
        this.name = tensp;
        this.price = dongia;
        this.Quantity= soluong;
        this.total = dongia*soluong;
    }
    public Product(byte[] imagePro, String namePro, double price, int size, int quantity) {
        this.idResourse = imagePro;
        this.name = namePro;
        this.price = price;
        this.size = size;
        this.Quantity = quantity;
    }

    public Product(int id, String name, double price, byte[] idResourse, int quantity, double width, double height, double weight, String sex, String type,int id_category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.idResourse = idResourse;
        Quantity = quantity;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.sex = sex;
        this.type = type;
        this.id_category = id_category;
    }
    public Product(byte[] imagePro, String namePro, int idPro, double price, int size, int idCat, int quantity) {
        this.idResourse = imagePro;
        this.name = namePro;
        this.id = idPro;
        this.price = price;
        this.size = size;
        this.id_category = idCat;
        this.Quantity = quantity;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public Product(int id, String name, double price, byte[] idResourse, int Quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.idResourse = idResourse;
        this.Quantity=Quantity;
    }

    public int getQuantity() {
        return Quantity;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }



    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public byte[] getIdResourse() {
        return idResourse;
    }

    public void setIdResourse(byte[] idResourse) {
        this.idResourse = idResourse;
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
    public String getString(){
        return getName()+" "+getId()+" "+getSex()+" "+getType()+" "+getIdResourse()+" "+getId_category()+" "+getHeight()+" "+getWeight()+" "+getWidth()+" "+getPrice()+" "+getQuantity()+"\n";
    }
}
