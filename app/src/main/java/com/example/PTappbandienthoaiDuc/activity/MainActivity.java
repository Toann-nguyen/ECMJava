package com.example.PTappbandienthoaiDuc.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.example.PTappbandienthoaiDuc.R;
import com.example.PTappbandienthoaiDuc.adapter.SanphamAdapter;
import com.example.PTappbandienthoaiDuc.model.GioHang;
import com.example.PTappbandienthoaiDuc.model.Sanpham;
import com.example.PTappbandienthoaiDuc.ultil.CheckConnection;
import com.example.PTappbandienthoaiDuc.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewMain;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ArrayList<Sanpham> listSP;
    SanphamAdapter sanphamAdapter;
    public static ArrayList<GioHang> mangGioHang;

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbar_main);
        viewFlipper = findViewById(R.id.vfip_main);
        recyclerViewMain = findViewById(R.id.rv_main);
        navigationView = findViewById(R.id.nav_main);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = findViewById(R.id.drawer_main);
        listSP = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(listSP,getApplicationContext());
        recyclerViewMain.setHasFixedSize(true);
        recyclerViewMain.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewMain.setAdapter(sanphamAdapter);

        if (mangGioHang != null){

        }else {
            mangGioHang = new ArrayList<>();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        Checkadmin();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            AcBar();
            AcViewParger();
            GetDuLieuSPMoiNhat();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(),
                    "Ban kiem tra lai ket noi");
            finish();
        }

    }

    private void Checkadmin() {

//        Toast.makeText(getApplicationContext(),pass.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart,menu);
        getMenuInflater().inflate(R.menu.admin,menu);
        String a = getIntent().getStringExtra("a");
        String a1 = "abc";
        MenuItem register = menu.findItem(R.id.menu_admin);
        if(a1.equals(a))
        {
            register.setVisible(true);
        }else {
            register.setVisible(false);
        }
        this.invalidateOptionsMenu();
        return true;
    }

    private void GetDuLieuSPMoiNhat() {
         RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDansanpham, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int ID = 0;
                    String Tensp = "";
                    Integer Giasp=0;
                    String Hinhanhsp = "";
                    String Motasp = "";
                    int IdSP = 0;
                    for (int i =0 ; i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            Tensp = jsonObject.getString("tensanpham");
                            Giasp = jsonObject.getInt("giasanpham");
                            Hinhanhsp = jsonObject.getString("hinhanhsanpham");
                            Motasp = jsonObject.getString("motasanpham");
                            IdSP = jsonObject.getInt("idsanpham");
                            listSP.add(new Sanpham(ID,Tensp,Giasp,Hinhanhsp,Motasp,IdSP));
                            sanphamAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void AcViewParger() {
        ArrayList<String> listquangcao = new ArrayList<>();
        listquangcao.add("https://cdn.tgdd.vn/2021/09/banner/800-200-800x200-112.png");
        listquangcao.add("https://cdn.tgdd.vn/2021/09/banner/A52s-800-200-800x200-1.png");
        listquangcao.add("https://cdn.tgdd.vn/2021/08/banner/Oppo-A74-800-200-800x200.png");
        listquangcao.add("https://cdn.tgdd.vn/2021/09/banner/800-200-800x200-124.png");
        listquangcao.add("https://cdn.tgdd.vn/2021/09/banner/redmi-9t-800-200-800x200-5.png");
        listquangcao.add("https://cdn.tgdd.vn/2021/09/banner/800-200-800x200-90.png");
        listquangcao.add("https://cdn.tgdd.vn/2021/08/banner/800-200-800x200-156.png");
        listquangcao.add("https://cdn.tgdd.vn/2021/08/banner/800-200(1)-800x200-1.png");
        for (int i = 0; i < listquangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext())
                    .load(listquangcao.get(i))
                    .into(imageView);
            imageView.setScaleType(ImageView
                    .ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(7000);
        viewFlipper.setAutoStart(true);
        Animation animation_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_outquangcao);

        Animation animation_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_quangcao);
        viewFlipper.setOutAnimation(animation_out);
        viewFlipper.setInAnimation(animation_in);

    }

    private void AcBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.menu_main);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_cart) {
            Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.menu_admin) {
            Intent intent1 = new Intent(getApplicationContext(), HoadonActivity.class);
            startActivity(intent1);
            return true;
        }
         if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_trangchu) {
            finish();
        } else if (itemId == R.id.menu_sanpham) {
            Intent intent = new Intent(MainActivity.this, LoaispActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.menu_facebook) {
            String url = "https://www.facebook.com/druc.66";
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(myIntent);
        } else if (itemId == R.id.menu_dangxuat) {
            Intent intent9 = new Intent(MainActivity.this, DangNhapActivity.class);
            startActivity(intent9);
            finish();
        }

        return false;
    }
}

