package com.example.PTappbandienthoaiDuc.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.PTappbandienthoaiDuc.R;
import com.example.PTappbandienthoaiDuc.model.Sanpham;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class IphoneBaseAdapter extends BaseAdapter {
    Context context ;
    ArrayList<Sanpham> listSanPham;

    public IphoneBaseAdapter(Context context, ArrayList<Sanpham> listSanPham) {
        this.context = context;
        this.listSanPham = listSanPham;
    }

    @Override
    public int getCount() {
        return listSanPham.size();
    }

    @Override
    public Object getItem(int i) {
        return listSanPham.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    class ViewHolder{
        ImageView ingIphone;
        TextView tvTenL,tvGiaDTL,tvMoTaDL;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        IphoneBaseAdapter.ViewHolder viewHolder = null;
        if (view==null){
            viewHolder = new IphoneBaseAdapter.ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.iphone_item,null);
            viewHolder.ingIphone = view.findViewById(R.id.img_iphone);
            viewHolder.tvTenL = view.findViewById(R.id.tv_tenspiphone);
            viewHolder.tvGiaDTL = view.findViewById(R.id.tv_giaspiphone);
            viewHolder.tvMoTaDL = view.findViewById(R.id.tv_motaspiphone);
            view.setTag(viewHolder);
        }else {
            viewHolder = (IphoneBaseAdapter.ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.tvTenL.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvGiaDTL.setText("Giá: "+decimalFormat.format(sanpham.getGiasp())+" vnđ");
        Glide.with(context).load(sanpham.getHinhanhsp()).placeholder(R.drawable.ic_launcher_background).into(viewHolder.ingIphone);
        viewHolder.tvMoTaDL.setMaxLines(3);
        viewHolder.tvMoTaDL.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.tvMoTaDL.setText(sanpham.getMotasp());
        return view;
    }
}
