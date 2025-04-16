package com.example.PTappbandienthoaiDuc.model;

public class DonHang {
    private int id;
    private String tenKhach;
    private int soDT;
    private String email;
    private String diaChi;

    public DonHang() {
    }

    public DonHang(int id, String tenKhach, int soDT, String email, String diaChi) {
        this.id = id;
        this.tenKhach = tenKhach;
        this.soDT = soDT;
        this.email = email;
        this.diaChi = diaChi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenKhach() {
        return tenKhach;
    }

    public void setTenKhach(String tenKhach) {
        this.tenKhach = tenKhach;
    }

    public int getSoDT() {
        return soDT;
    }

    public void setSoDT(int soDT) {
        this.soDT = soDT;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
