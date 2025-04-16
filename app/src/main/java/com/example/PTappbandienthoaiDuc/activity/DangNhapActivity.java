package com.example.PTappbandienthoaiDuc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class DangNhapActivity extends AppCompatActivity {
    Button btnDangNhap, btnDangKy;
    EditText edtEmail, edtMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        AnhXa();
        DangKy();
        DangNhap();
        KiemTraEditText();
    }

    private void KiemTraEditText() {
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    edtEmail.setError("Bạn phải nhập Email");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtMatKhau.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    edtMatKhau.setError("Bạn phải nhập mật khẩu");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void DangNhap() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtMatKhau.getText().toString().trim();
                if (email.equals("druc662@gmail.com") && password.equals("123")) {
                    Intent intent = new Intent(DangNhapActivity.this, HoadonActivity.class);
                    startActivity(intent);
                } else if (!email.equals("") && !password.equals("")) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanTaikhoan, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("1")) {
                                Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(DangNhapActivity.this, "Thông tin tài khoản không chính xác.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                            , new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(DangNhapActivity.this, error.toString().trim(), Toast.LENGTH_LONG).show();
                        }
                    }
                    ) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> data = new HashMap<>();
                            data.put("email", email);
                            data.put("password", password);
                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(DangNhapActivity.this, "Mời bạn nhập thông tin", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void DangKy() {
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhapActivity.this, DangKyActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        btnDangNhap = findViewById(R.id.btn_dangnhap);
        btnDangKy = findViewById(R.id.btn_dangkymdn);
        edtEmail = findViewById(R.id.edt_emaildangnhap);
        edtMatKhau = findViewById(R.id.edt_matkhau);

    }
}