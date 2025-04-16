package com.example.PTappbandienthoaiDuc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.PTappbandienthoaiDuc.R;
import com.example.PTappbandienthoaiDuc.adapter.vivoBaseAdapter;
import com.example.PTappbandienthoaiDuc.model.Sanpham;
import com.example.PTappbandienthoaiDuc.ultil.CheckConnection;
import com.example.PTappbandienthoaiDuc.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class vivoActivity extends AppCompatActivity {
    ListView lvvivo;
    Toolbar toolbar;
    ArrayList<Sanpham> listvivo;
    vivoBaseAdapter vivoBaseAdapter;
    int idvivo = 0;
    int page = 1;

    View footerViewLT;
    boolean  isLoading;
    boolean limitdatavivo;
    myhandlervivo myhandlervivo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vivo);
        AnhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            Getdata(page);
            Toorbar();
            LoadModeData();
            Getidsp();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"kiem tra ket noi");
        }
    }

    private void LoadModeData() {
        lvvivo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(vivoActivity.this, ChiTietSPActivity.class);
                intent.putExtra("thongtinsanpham",listvivo.get(i));
                startActivity(intent);
            }
        });
        lvvivo.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                  if (firstItem + visibleItem == totalItem && totalItem != 0 && isLoading ==false&& limitdatavivo==false){
                      isLoading = true;
                           ThreadLaptop threadLaptop = new ThreadLaptop();
                           threadLaptop.start();
                  }
            }
        });
}

public class myhandlervivo  extends Handler{
    @Override
    public void handleMessage(@NonNull Message msg) {
        switch (msg.what){
            case 0:
                lvvivo.removeFooterView(footerViewLT);
                break;
            case 1:
                Getdata(++page);
                isLoading = false;
                break;
        }
        super.handleMessage(msg);
    }
}
public class ThreadLaptop extends Thread{
    @Override
    public void run() {
        myhandlervivo.sendEmptyMessage(0);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Message message = myhandlervivo.obtainMessage(1);
        myhandlervivo.sendMessage(message);
        super.run();
    }
}


    private void AnhXa() {
        toolbar = findViewById(R.id.toolbar_mainvivo);
        lvvivo = findViewById(R.id.lv_laptop);
        listvivo = new ArrayList<>();
        vivoBaseAdapter = new vivoBaseAdapter(getApplicationContext(), listvivo);
        lvvivo.setAdapter(vivoBaseAdapter);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerViewLT = layoutInflater.inflate(R.layout.progress_vivo,null);
        myhandlervivo = new  myhandlervivo();
    }
    private void Getidsp() {
        idvivo = getIntent().getIntExtra("idloaisanpham", -1);

    }
    private void Toorbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void Getdata(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.DuongDanDienThoaiFPT + String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String tendt = "";
                int giadt = 0;
                String hinhanhdt = "";
                String motadt = "";
                int iddt1 = 0;
                if (response != null && response.length() != 2) {
                    lvvivo.removeFooterView(footerViewLT);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tendt = jsonObject.getString("tensp");
                            giadt = jsonObject.getInt("giasp");
                            hinhanhdt = jsonObject.getString("hinhanhsp");
                            motadt = jsonObject.getString("motasp");
                            iddt1 = jsonObject.getInt("idsanpham");
                            listvivo.add(new Sanpham(id, tendt, giadt, hinhanhdt, motadt, iddt1));
                            vivoBaseAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    limitdatavivo = true;
                    lvvivo.removeFooterView(footerViewLT);
                    CheckConnection.ShowToast_Short(getApplicationContext(),"het du lieu");
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
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idsanpham", String.valueOf(idvivo));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_cart:
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}