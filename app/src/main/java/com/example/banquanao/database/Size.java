package com.example.banquanao.database;

import java.io.Serializable;

public class Size  implements Serializable {
    private int idSize;
    private double weight;
    private double height;
    private String sex;
    private String type;

    public Size(int idSize, double weight, double height, String sex, String type) {
        this.idSize = idSize;
        this.weight = weight;
        this.height = height;
        this.sex = sex;
        this.type = type;
    }

    public int getIdSize() {
        return idSize;
    }

    public void setIdSize(int idSize) {
        this.idSize = idSize;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
