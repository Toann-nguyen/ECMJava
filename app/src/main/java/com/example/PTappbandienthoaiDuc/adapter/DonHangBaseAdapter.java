package com.example.PTappbandienthoaiDuc.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.PTappbandienthoaiDuc.activity.ChiTietActivity;
import com.example.PTappbandienthoaiDuc.R;
import com.example.PTappbandienthoaiDuc.activity.HoadonActivity;
import com.example.PTappbandienthoaiDuc.model.DonHang;

import java.util.ArrayList;

public class DonHangBaseAdapter extends BaseAdapter {
    HoadonActivity context;
    ArrayList<DonHang> donHangArrayList;

    public DonHangBaseAdapter(HoadonActivity context, ArrayList<DonHang> donHangArrayList) {
        this.context = context;
        this.donHangArrayList = donHangArrayList;
    }

    @Override
    public int getCount() {
        return donHangArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return donHangArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class ViewHolder{
        TextView tvTenKhach,tvSDT,tvEmail,tvDiaChi;
        ImageView imageView,imageView2;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder viewHolder = null;
         if (convertView == null){
             viewHolder = new ViewHolder();
             LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             convertView=layoutInflater.inflate(R.layout.donhang_item,null);
             viewHolder.tvTenKhach = convertView.findViewById(R.id.tv_tenkhach);
             viewHolder.tvSDT = convertView.findViewById(R.id.tv_sdtdh);
             viewHolder.tvEmail = convertView.findViewById(R.id.tv_emaildh);
             viewHolder.tvDiaChi = convertView.findViewById(R.id.tv_diachidh);
             viewHolder.imageView = convertView.findViewById(R.id.img_deletedh);
             viewHolder.imageView2 = convertView.findViewById(R.id.img_thanhtoan);
             convertView.setTag(viewHolder);
         }else {
             viewHolder = (ViewHolder) convertView.getTag();
         }
        DonHang donHang = (DonHang) getItem(position);
        viewHolder.tvTenKhach.setText("Tên khách hàng: "+donHang.getTenKhach());
        viewHolder.tvSDT.setText("Số Điện thoại: "+"0"+String.valueOf(donHang.getSoDT()));
        viewHolder.tvEmail.setText("Email: "+donHang.getEmail());
        viewHolder.tvDiaChi.setText("Địa chỉ: "+donHang.getDiaChi());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietActivity.class);
                intent.putExtra("iddonhang",donHang.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
       viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               context.deleteDL(donHang.getId(),donHang.getTenKhach());
           }
       });
        ViewHolder finalViewHolder = viewHolder;
        viewHolder.imageView2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             finalViewHolder.imageView2.setVisibility(View.INVISIBLE);
           }
       });
        return convertView;
    }
}
