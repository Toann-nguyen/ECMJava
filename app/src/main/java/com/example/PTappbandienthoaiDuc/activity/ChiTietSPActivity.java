package com.example.PTappbandienthoaiDuc.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.PTappbandienthoaiDuc.R;
import com.example.PTappbandienthoaiDuc.model.GioHang;
import com.example.PTappbandienthoaiDuc.model.Sanpham;

import java.text.DecimalFormat;

public class ChiTietSPActivity extends AppCompatActivity {
    Toolbar toolbarCt;
    TextView tvTen, tvGia, tvMota;
    ImageView imgCT;
    Spinner spinnerCT;
    Button btnGioHang;
    int id = 0;
    String tenChiTiet = "";
    int giaChiTiet = 0;
    String hinhChiTiet = "";
    String moTaChiTiet = "";
    int idspChiTiet = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_s_p);
        AnhXa();
        ToobarCT();
        GetInformation();
        GioHangSP();
        SpinnerCT();
    }

    private void GioHangSP() {
        btnGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.mangGioHang.size()>0){
                    int sl = Integer.parseInt(spinnerCT.getSelectedItem().toString());
                    boolean exit = false;
                    for (int i=0;i < MainActivity.mangGioHang.size();i++){
                        if (MainActivity.mangGioHang.get(i).getIdsp()==id){
                            MainActivity.mangGioHang.get(i).setSoLuongsp(MainActivity.mangGioHang.get(i).getSoLuongsp()+sl);
                            if (MainActivity.mangGioHang.get(i).getSoLuongsp()>=10){
                                      MainActivity.mangGioHang.get(i).setSoLuongsp(10);
                            }
                            MainActivity.mangGioHang.get(i).setGiaSP(giaChiTiet*MainActivity.mangGioHang.get(i).getSoLuongsp());
                            exit = true;
                        }
                    }
                    if (exit == false){
                        int soluong = Integer.parseInt(spinnerCT.getSelectedItem().toString());
                        long giamoi = soluong * giaChiTiet;
                        MainActivity.mangGioHang.add(new GioHang(id,tenChiTiet,giamoi,hinhChiTiet,soluong));
                    }

                }else {
                    int soluong = Integer.parseInt(spinnerCT.getSelectedItem().toString());
                    long giamoi = soluong * giaChiTiet;
                    MainActivity.mangGioHang.add(new GioHang(id,tenChiTiet,giamoi,hinhChiTiet,soluong));

                }
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void SpinnerCT() {
        Integer [] soluong =  new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinnerCT.setAdapter(arrayAdapter);
    }

    private void GetInformation() {
        Sanpham sanpham  = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanpham.getId();
        tenChiTiet = sanpham.getTensp();
        giaChiTiet = sanpham.getGiasp();
        hinhChiTiet = sanpham.getHinhanhsp();
        moTaChiTiet = sanpham.getMotasp();
        idspChiTiet = sanpham.getIdsp();
        tvTen.setText(tenChiTiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvGia.setText("Giá: "+decimalFormat.format(giaChiTiet)+" vnđ");
        tvMota.setText(moTaChiTiet);
        Glide.with(getApplicationContext()).load(hinhChiTiet).into(imgCT);
    }

    private void ToobarCT() {
        setSupportActionBar(toolbarCt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbarCt = findViewById(R.id.toor_chitietsp);
        tvTen = findViewById(R.id.tv_tenspchitiet);
        tvGia = findViewById(R.id.tv_giaspchitiet);
        tvMota = findViewById(R.id.tv_motaspchitiet);
        spinnerCT = findViewById(R.id.spinner_chitiet);
        imgCT = findViewById(R.id.img_chitietsp);
        btnGioHang = findViewById(R.id.btn_themchitiet);
    }
}