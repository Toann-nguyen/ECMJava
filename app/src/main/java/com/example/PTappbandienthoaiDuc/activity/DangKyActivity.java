package com.example.PTappbandienthoaiDuc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.PTappbandienthoaiDuc.R;
import com.example.PTappbandienthoaiDuc.ultil.Server;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class DangKyActivity extends AppCompatActivity {
    Button btnDangky;
    EditText edtTenKhachHang, edtEmailDK, edtMatKhau, edtNhapLaiMatKhau;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        AnhXa();
        Dangky();
    }

    private void Dangky() {
        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTen = edtTenKhachHang.getText().toString().trim();
                String email = edtEmailDK.getText().toString().trim();
                String matkhau = edtMatKhau.getText().toString().trim();
                String nhaplaimatkhau = edtNhapLaiMatKhau.getText().toString().trim();
                if (!matkhau.equals(nhaplaimatkhau)) {
                    Toast.makeText(DangKyActivity.this, "Mật khẩu nhập lại không trùng nhau.", Toast.LENGTH_LONG).show();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(DangKyActivity.this,"Không đúng định dạng email",Toast.LENGTH_LONG).show();
                } else if (!hoTen.equals("") && !email.equals("") && !matkhau.equals("")) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanregister, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("success")) {
                                Toast.makeText(DangKyActivity.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(DangKyActivity.this, DangNhapActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(DangKyActivity.this, "Đăng ký thất bại", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                            , new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(DangKyActivity.this, error.toString().trim(), Toast.LENGTH_LONG).show();
                        }
                    }
                    ) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> data = new HashMap<>();
                            data.put("name", hoTen);
                            data.put("email", email);
                            data.put("password", matkhau);
                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }else {
                    Toast.makeText(DangKyActivity.this,"Mời bạn nhập đầy đủ thông tin.",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void AnhXa() {
        btnDangky = findViewById(R.id.btn_register);
        edtTenKhachHang = findViewById(R.id.edt_tenkhachhangdangky);
        edtEmailDK = findViewById(R.id.edt_emaildangky);
        edtMatKhau = findViewById(R.id.edt_matkhaudangky);
        edtNhapLaiMatKhau = findViewById(R.id.edt_nhaplaimatkhau);
    }

}