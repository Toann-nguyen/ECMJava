package com.example.PTappbandienthoaiDuc.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.PTappbandienthoaiDuc.R;
import com.example.PTappbandienthoaiDuc.ultil.CheckConnection;
import com.example.PTappbandienthoaiDuc.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChiTietActivity extends AppCompatActivity {
    TextView tvMaDonHang,tvMaSP,tvTenSP,tvGiaSP,tvSL;
    int iddonhang;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        GetIdDonHang();
        anhxa();
        GetDataCT();
    }

    private void anhxa() {
        tvMaDonHang = findViewById(R.id.tv_madonhang);
        tvMaSP = findViewById(R.id.tv_masanphamct);
        tvTenSP = findViewById(R.id.tv_tensanphamct);
        imageView = findViewById(R.id.img_back);
        tvGiaSP = findViewById(R.id.tv_giasanphamct);
        tvSL = findViewById(R.id.tv_soluongsanphamct);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetDataCT() {
        if (iddonhang == -1) {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Không tìm thấy thông tin đơn hàng");
            return;
        }
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanchitiet, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                int madonhang = 0;
                int masanpham = 0;
                String tensanpham = "";
                int giasanpham = 0;
                int soluongsanpham = 0;
                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            madonhang = jsonObject.getInt("madonhang");
                            masanpham = jsonObject.getInt("masanpham");
                            tensanpham = jsonObject.getString("tensanpham");
                            giasanpham = jsonObject.getInt("giasanpham");
                            soluongsanpham = jsonObject.getInt("soluongsanpham");
                            if (madonhang==iddonhang){
                                tvMaDonHang.setText("Mã đơn hàng: "+String.valueOf(madonhang));
                                tvMaSP.setText("Mã sản phẩm: "+String.valueOf(masanpham));
                                tvTenSP.setText("Tên sản phẩm: "+tensanpham);
                                tvGiaSP.setText("Gía sản phẩm: "+String.valueOf(giasanpham)+" vnđ");
                                tvSL.setText("Số lượng: "+String.valueOf(soluongsanpham));
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"khong co du lieu");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
              //  HashMap<String, String> param = new HashMap<String, String>();
             //   param.put("madonhang", String.valueOf(iddonhang));
             //   return param;
                HashMap<String, String> param = new HashMap<String, String>();
                // Kiểm tra trước khi sử dụng iddonhang
                String madonhangValue = (iddonhang != -1) ? String.valueOf(iddonhang) : "";
                param.put("madonhang", madonhangValue);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetIdDonHang() {
        iddonhang = getIntent().getIntExtra("iddonhang", -1);
        if (iddonhang == -1) {
            // Log lỗi hoặc hiển thị thông báo
            Log.e("ChiTietActivity", "Không tìm thấy mã đơn hàng hợp lệ");
        }
    }
}