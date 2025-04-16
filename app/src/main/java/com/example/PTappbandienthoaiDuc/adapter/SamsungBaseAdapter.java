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

public class SamsungBaseAdapter extends BaseAdapter{
    Context context ;
    ArrayList <Sanpham> listSanPham;

    public SamsungBaseAdapter(Context context, ArrayList<Sanpham> listSanPham) {
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
        ImageView ingSamsung;
        TextView tvTenSS,tvGiaSS,tvMoTaSS;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view==null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.samsung_item,null);
            viewHolder.ingSamsung = view.findViewById(R.id.img_samsung);
            viewHolder.tvTenSS = view.findViewById(R.id.tv_tenspsamsung);
            viewHolder.tvGiaSS = view.findViewById(R.id.tv_giaspsamsung);
            viewHolder.tvMoTaSS = view.findViewById(R.id.tv_motaspsamsung);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.tvTenSS.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvGiaSS.setText("Giá: "+decimalFormat.format(sanpham.getGiasp())+" vnđ");
        Glide.with(context).load(sanpham.getHinhanhsp()).placeholder(R.drawable.ic_launcher_background).into(viewHolder.ingSamsung);
        viewHolder.tvMoTaSS.setMaxLines(3);
        viewHolder.tvMoTaSS.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.tvMoTaSS.setText(sanpham.getMotasp());
        return view;
    }
}
