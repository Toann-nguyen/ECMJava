package com.example.PTappbandienthoaiDuc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.PTappbandienthoaiDuc.activity.GioHangActivity;
import com.example.PTappbandienthoaiDuc.R;
import com.example.PTappbandienthoaiDuc.activity.MainActivity;
import com.example.PTappbandienthoaiDuc.model.GioHang;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> listGioHang;

    public GioHangAdapter(Context context, ArrayList<GioHang> listGioHang) {
        this.context = context;
        this.listGioHang = listGioHang;
    }

    @Override
    public int getCount() {
        return listGioHang.size();
    }

    @Override
    public Object getItem(int i) {
        return listGioHang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewHolder {
        public TextView tvTenGioiH, tvGiaGioH;
        public ImageView imgHinhAnhGioH;
        public Button btnPlus, btnValues, btnMinnus;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_giohang, null);
            viewHolder.tvTenGioiH = view.findViewById(R.id.tv_tengioihang);
            viewHolder.tvGiaGioH = view.findViewById(R.id.tv_giatrimonhang);
            viewHolder.imgHinhAnhGioH = view.findViewById(R.id.img_giohangitem);
            viewHolder.btnMinnus = view.findViewById(R.id.btn_minnus);
            viewHolder.btnValues = view.findViewById(R.id.btn_values);
            viewHolder.btnPlus = view.findViewById(R.id.btn_plus);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        GioHang gioHang = listGioHang.get(i);
        viewHolder.tvTenGioiH.setText(gioHang.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvGiaGioH.setText("Giá: " + decimalFormat.format(gioHang.getGiaSP()) + "vnđ");
        Glide.with(context).load(gioHang.getHinhsp()).into(viewHolder.imgHinhAnhGioH);
        viewHolder.btnValues.setText(gioHang.getSoLuongsp() + "");
        int sl = Integer.parseInt(viewHolder.btnValues.getText().toString());
        if (sl >= 10) {
            viewHolder.btnPlus.setVisibility(View.INVISIBLE);
            viewHolder.btnMinnus.setVisibility(View.VISIBLE);
        } else if (sl <= 1) {
            viewHolder.btnPlus.setVisibility(View.VISIBLE);
            viewHolder.btnMinnus.setVisibility(View.INVISIBLE);
        } else if (sl >= 1) {
            viewHolder.btnPlus.setVisibility(View.VISIBLE);
            viewHolder.btnMinnus.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoi = Integer.parseInt(finalViewHolder.btnValues.getText().toString())+1;
                int slhient = MainActivity.mangGioHang.get(i).getSoLuongsp();
                long giaht = MainActivity.mangGioHang.get(i).getGiaSP();
                MainActivity.mangGioHang.get(i).setSoLuongsp(slmoi);
                long giamoinhat = (giaht * slmoi) / slhient;
                MainActivity.mangGioHang.get(i).setGiaSP(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.tvGiaGioH.setText("Giá: " + decimalFormat.format(giamoinhat) + "vnđ");
                GioHangActivity.EvenUtils();
                if (slmoi>9){
                    finalViewHolder.btnPlus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnMinnus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValues.setText(String.valueOf(slmoi));
                }else {
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnMinnus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValues.setText(String.valueOf(slmoi));
                }
            }
        });
        viewHolder.btnMinnus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoi = Integer.parseInt(finalViewHolder.btnValues.getText().toString())-1;
                int slhient = MainActivity.mangGioHang.get(i).getSoLuongsp();
                long giaht = MainActivity.mangGioHang.get(i).getGiaSP();
                MainActivity.mangGioHang.get(i).setSoLuongsp(slmoi);
                long giamoinhat = (giaht * slmoi) / slhient;
                MainActivity.mangGioHang.get(i).setGiaSP(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.tvGiaGioH.setText("Giá: " + decimalFormat.format(giamoinhat) + "vnđ");
                GioHangActivity.EvenUtils();
                if (slmoi<2){
                    finalViewHolder.btnMinnus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValues.setText(String.valueOf(slmoi));
                }else {
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnMinnus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValues.setText(String.valueOf(slmoi));
                }
            }
        });
        return view;
    }
}
