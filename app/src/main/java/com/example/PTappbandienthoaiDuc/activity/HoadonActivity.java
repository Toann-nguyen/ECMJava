package com.example.PTappbandienthoaiDuc.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.PTappbandienthoaiDuc.R;
import com.example.PTappbandienthoaiDuc.adapter.DonHangBaseAdapter;
import com.example.PTappbandienthoaiDuc.model.DonHang;
import com.example.PTappbandienthoaiDuc.ultil.CheckConnection;
import com.example.PTappbandienthoaiDuc.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HoadonActivity extends AppCompatActivity {
int id;
String tenkhachhang;
int sodienthoai;
Toolbar toolbar;
String email;
String diachi;
ListView listView;
ArrayList<DonHang> donHangArrayList;
DonHangBaseAdapter donHangBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);
        toolbar  = findViewById(R.id.toolbar_hoadon);
        listView = findViewById(R.id.lv_hoadon);
        donHangArrayList = new ArrayList<>();
        donHangBaseAdapter = new DonHangBaseAdapter(this,donHangArrayList);
        listView.setAdapter(donHangBaseAdapter);
        toolbarH();
        getDulieuLoaisp();
    }

    public void deleteDL(int id123,String tenkhachhang) {
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle("bạn có muốn xóa đơn hàng "+String.valueOf(id123) + " của " + tenkhachhang.toString());
      builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {

          }
      });
      builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
              StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanxoa, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            Toast.makeText(getApplicationContext(), " Xóa thành công ", Toast.LENGTH_LONG).show();
                            getDulieuLoaisp();
                        } else {
                            Toast.makeText(getApplicationContext(), "Xóa thất bại ", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_LONG).show();
                    }
                }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();
                        data.put("id", String.valueOf(id123));
                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
          }
      });
      builder.show();
    }

    private void toolbarH() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void getDulieuLoaisp() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanhoadon,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        donHangArrayList.clear();
                        if (response != null) {
                            for (int i = 0 ;i<response.length();i++){
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    id = jsonObject.getInt("id");
                                    tenkhachhang = jsonObject.getString("tenkhachhang");
                                    sodienthoai = jsonObject.getInt("sodienthoai");
                                    email = jsonObject.getString("email");
                                    diachi = jsonObject.getString("diachi");
                                    donHangArrayList.add(new DonHang(id,tenkhachhang,sodienthoai,email,diachi));
                                    donHangBaseAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), error.toString());
                Log.e("ERROR",error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_admin){
            Intent intent = new Intent(HoadonActivity.this,MainActivity.class);
            intent.putExtra("a","abc");
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}