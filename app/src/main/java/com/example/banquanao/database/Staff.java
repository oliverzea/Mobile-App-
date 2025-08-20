package com.example.banquanao.database;

import java.io.Serializable;

public class Staff implements Serializable {
    private int id_Staff;
    private String name;
    private String passWork;
    private int number_phone;
    private String email;
    private int idManager;
    private int idPowerStaff;
    private byte[] imageStaff;
    public Staff(){}

    public int getId_Staff() {
        return id_Staff;
    }

    public byte[] getImageStaff() {
        return imageStaff;
    }

    public void setImageStaff(byte[] imageStaff) {
        this.imageStaff = imageStaff;
    }

    public Staff(int idStaff, String nameStaff, String password, int phone, String email, byte[] imageStaff, int idManager) {
        this.id_Staff = idStaff;
        this.name = nameStaff;
        this.passWork = password;
        this.number_phone = phone;
        this.email = email;
        this.imageStaff = imageStaff;
        this.idManager = idManager;
    }
    public Staff(int maNV, String tenNV, String matKhau, int soDT, String emailNV) {
        this.id_Staff = maNV;
        this.name = tenNV;
        this.passWork = matKhau;
        this.number_phone = soDT;
        this.email = emailNV;
    }

    public void setId_Staff(int id_Staff) {
        this.id_Staff = id_Staff;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassWork() {
        return passWork;
    }

    public void setPassWork(String passWork) {
        this.passWork = passWork;
    }

    public int getNumber_phone() {
        return number_phone;
    }

    public void setNumber_phone(int number_phone) {
        this.number_phone = number_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdManager() {
        return idManager;
    }

    public void setIdManager(int idManager) {
        this.idManager = idManager;
    }

    public int getIdPowerStaff() {
        return idPowerStaff;
    }

    public void setIdPowerStaff(int idPowerStaff) {
        this.idPowerStaff = idPowerStaff;
    }
}
