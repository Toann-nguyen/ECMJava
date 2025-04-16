package com.example.PTappbandienthoaiDuc.model;

public class Loaisp {
    int Id;
    String Tensanpham;
    String Hinhanhloaisp;

    public Loaisp() {
    }

    public Loaisp(int id, String tensanpham, String hinhanhloaisp) {
        Id = id;
        Tensanpham = tensanpham;
        Hinhanhloaisp = hinhanhloaisp;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTensanpham() {
        return Tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        Tensanpham = tensanpham;
    }

    public String getHinhanhloaisp() {
        return Hinhanhloaisp;
    }

    public void setHinhanhloaisp(String hinhanhloaisp) {
        Hinhanhloaisp = hinhanhloaisp;
    }
}
