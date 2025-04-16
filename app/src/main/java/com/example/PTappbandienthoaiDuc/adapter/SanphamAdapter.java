package com.example.PTappbandienthoaiDuc.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.PTappbandienthoaiDuc.R;
import com.example.PTappbandienthoaiDuc.activity.ChiTietSPActivity;
import com.example.PTappbandienthoaiDuc.model.Sanpham;
import com.example.PTappbandienthoaiDuc.ultil.CheckConnection;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ViewHolder> {
    ArrayList<Sanpham> listSanPham;
    Context context;

    public SanphamAdapter(ArrayList<Sanpham> listSanPham, Context context) {
        this.listSanPham = listSanPham;
        this.context = context;
    }

    @NonNull
    @Override
    public SanphamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sanpham_list_activity,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanphamAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Sanpham sanpham = listSanPham.get(position);
        holder.tvTensanpham.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvGiasanpham.setText("Giá: "+decimalFormat.format(sanpham.getGiasp())+" vnđ");
        Glide.with(context).load(sanpham.getHinhanhsp()).placeholder(R.drawable.ic_launcher_background).into(holder.imgAnhsanpham);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChiTietSPActivity.class);
                intent.putExtra("thongtinsanpham",listSanPham.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                CheckConnection.ShowToast_Short(context,listSanPham.get(position).getTensp());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listSanPham.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAnhsanpham;
        TextView tvTensanpham,tvGiasanpham;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnhsanpham = itemView.findViewById(R.id.img_sanpham);
            tvTensanpham =  itemView.findViewById(R.id.tv_tensanpham);
            tvGiasanpham = itemView.findViewById(R.id.tv_giasanpham);

        }
    }
}
