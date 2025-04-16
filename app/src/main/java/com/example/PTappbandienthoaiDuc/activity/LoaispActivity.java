package com.example.PTappbandienthoaiDuc.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.PTappbandienthoaiDuc.R;
import com.example.PTappbandienthoaiDuc.adapter.LoaispAdapter;
import com.example.PTappbandienthoaiDuc.model.Loaisp;
import com.example.PTappbandienthoaiDuc.ultil.CheckConnection;
import com.example.PTappbandienthoaiDuc.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoaispActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Loaisp> loaisps;
    LoaispAdapter loaispAdapter;
    Toolbar toolbarLS;

    int id = 0;
    String tenLoaisp = "";
    String hinhAnhLoaisp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaisp);
        Anhxa();
        ToolbarLS();
    }
    private void ToolbarLS() {
        setSupportActionBar(toolbarLS);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLS.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void Anhxa() {
        toolbarLS  = findViewById(R.id.toolbar_loaisp);
        recyclerView = findViewById(R.id.rv_loaisp);
        loaisps = new ArrayList<>();
        getDulieuLoaisp();
        loaispAdapter = new LoaispAdapter(getApplicationContext(), loaisps);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(loaispAdapter);
    }

    private void getDulieuLoaisp() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanLoaisp,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                       Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                        if (response != null) {
                            for (int i = 0 ;i<response.length();i++){
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    id = jsonObject.getInt("id");
                                    tenLoaisp = jsonObject.getString("tenloaisanpham");
                                    hinhAnhLoaisp = jsonObject.getString("hinhanhloaisanpham");
                                    loaisps.add(0,new Loaisp(id,tenLoaisp,hinhAnhLoaisp));
                                    loaispAdapter.notifyDataSetChanged();
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
}