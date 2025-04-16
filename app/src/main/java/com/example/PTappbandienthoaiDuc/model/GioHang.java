package com.example.PTappbandienthoaiDuc.model;

public class GioHang {
    int idsp;
    String tenSP;
    long giaSP;
    String hinhsp;
    int soLuongsp;

    public GioHang() {
    }

    public GioHang(int idsp, String tenSP, long giaSP, String hinhsp, int soLuongsp) {
        this.idsp = idsp;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.hinhsp = hinhsp;
        this.soLuongsp = soLuongsp;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public long getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(long giaSP) {
        this.giaSP = giaSP;
    }

    public String getHinhsp() {
        return hinhsp;
    }

    public void setHinhsp(String hinhsp) {
        this.hinhsp = hinhsp;
    }

    public int getSoLuongsp() {
        return soLuongsp;
    }

    public void setSoLuongsp(int soLuongsp) {
        this.soLuongsp = soLuongsp;
    }
}
