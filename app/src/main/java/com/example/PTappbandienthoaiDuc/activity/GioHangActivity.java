package com.example.PTappbandienthoaiDuc.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.PTappbandienthoaiDuc.R;
import com.example.PTappbandienthoaiDuc.adapter.GioHangAdapter;
import com.example.PTappbandienthoaiDuc.ultil.CheckConnection;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
    ListView listViewGH;
    TextView tvThongBao;
    static TextView tvTongTien;
    Button btnThanhToan, btnTiepTucMua;
    Toolbar toolbarGH;
    GioHangAdapter gioHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        AnhXa();
        ToolbarGh();
        EvenUtils();
        EvenButton();
        CactOnItemListView();
        CheckData();
    }

    private void EvenButton() {
        btnTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.mangGioHang.size()>0){
                    Intent intent = new Intent(getApplicationContext(), ThongtinkhachActivity.class);
                    startActivity(intent);
                }else {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Mời bạn thêm sản phẩm vào giỏ hàng để thanh toán");
                }
            }
        });
    }

    private void CactOnItemListView() {
        listViewGH.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn muốn xóa sản phẩm này");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (MainActivity.mangGioHang.size() <= 0) {
                            tvThongBao.setVisibility(View.VISIBLE);
                        } else {
                            MainActivity.mangGioHang.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            EvenUtils();
                            if (MainActivity.mangGioHang.size() <= 0) {
                                tvThongBao.setVisibility(View.VISIBLE);
                            } else {
                                tvThongBao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                EvenUtils();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gioHangAdapter.notifyDataSetChanged();
                        EvenUtils();
                    }
                });
                builder.show();
                return true;
            }
        });
    }


    public static void EvenUtils() {
        long tongtien = 0;
        for (int i = 0; i < MainActivity.mangGioHang.size(); i++) {
            tongtien += MainActivity.mangGioHang.get(i).getGiaSP();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvTongTien.setText(decimalFormat.format(tongtien) + " vnđ");
    }

    private void CheckData() {
        if (MainActivity.mangGioHang.size() <= 0) {
            gioHangAdapter.notifyDataSetChanged();
            tvThongBao.setVisibility(View.VISIBLE);
            listViewGH.setVisibility(View.INVISIBLE);
        } else {
            gioHangAdapter.notifyDataSetChanged();
            tvThongBao.setVisibility(View.INVISIBLE);
            listViewGH.setVisibility(View.VISIBLE);
        }
    }

    private void ToolbarGh() {
        setSupportActionBar(toolbarGH);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGH.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        listViewGH = findViewById(R.id.lv_giohang);
        tvThongBao = findViewById(R.id.tv_thongbao);
        tvTongTien = findViewById(R.id.tv_tongtien);
        btnThanhToan = findViewById(R.id.btn_thanhtoangiohang);
        btnTiepTucMua = findViewById(R.id.btn_tieptucmuahang);
        toolbarGH = findViewById(R.id.toolbar_gioihang);
        gioHangAdapter = new GioHangAdapter(GioHangActivity.this, MainActivity.mangGioHang);
        listViewGH.setAdapter(gioHangAdapter);
    }
}