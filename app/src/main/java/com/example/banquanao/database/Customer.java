package com.example.banquanao.database;

import java.io.Serializable;

public class Customer implements Serializable {
    private int id_cus;
    private String name_Cus;
    private String password_Cus;
    private int numberPhone;
    private String address;
    private int id_card;
    private String email;
    private  int idPowerCus;
    private  byte[] imgCus;

    public byte[] getImgCus() {
        return imgCus;
    }

    public void setImgCus(byte[] imgCus) {
        this.imgCus = imgCus;
    }

    public int getIdPowerCus() {
        return idPowerCus;
    }

    public void setIdPowerCus(int idPowerCus) {
        this.idPowerCus = idPowerCus;
    }

    public Customer(){}

    public Customer(String name, int sdt, int id, String email) {
        this.name_Cus = name;
        this.numberPhone = sdt;
        this.id_cus =id;
        this.email = email;
    }

    public Customer(int id_cus, String name_Cus, String password_Cus, int numberPhone, String email, String address, int id_card) {
        this.id_cus = id_cus;
        this.name_Cus = name_Cus;
        this.password_Cus = password_Cus;
        this.email=email;
        this.numberPhone = numberPhone;
        this.address = address;
        this.id_card = id_card;
    }
    public Customer(int idCus, String nameCus, String email, int phoneNumber, String address, int idCard) {
        this.id_cus = idCus;
        this.name_Cus = nameCus;
        this.email = email;
        this.numberPhone = phoneNumber;
        this.address = address;
        this.id_card = idCard;
    }
    public Customer(int idCus, String nameCus, String passwork, String email, int phoneNumber, String address, int idCard) {
        this.id_cus = idCus;
        this.name_Cus = nameCus;
        this.password_Cus = passwork;
        this.email = email;
        this.numberPhone = phoneNumber;
        this.address = address;
        this.id_card = idCard;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId_cus() {
        return id_cus;
    }

    public void setId_cus(int id_cus) {
        this.id_cus = id_cus;
    }

    public String getName_Cus() {
        return name_Cus;
    }

    public void setName_Cus(String name_Cus) {
        this.name_Cus = name_Cus;
    }

    public String getPassword_Cus() {
        return password_Cus;
    }

    public void setPassword_Cus(String password_Cus) {
        this.password_Cus = password_Cus;
    }

    public int getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(int numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId_card() {
        return id_card;
    }

    public void setId_card(int id_card) {
        this.id_card = id_card;
    }
    public String getString(){
        return getId_cus()+" "+getName_Cus()+" "+getPassword_Cus()+" "+getNumberPhone()+" "+getAddress()+" "+getId_card()+" "+"\n";
    }
}
