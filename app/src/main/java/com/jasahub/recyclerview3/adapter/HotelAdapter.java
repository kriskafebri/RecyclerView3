package com.jasahub.recyclerview3.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasahub.recyclerview3.R;
import com.jasahub.recyclerview3.model.Hotel;

import java.util.ArrayList;

/**
 * Created by SE on 23/08/2017.
 */

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {

    IHotelAdapter mIHotelAdapter;
    ArrayList<Hotel> hotelList;


    public HotelAdapter(Context context, ArrayList<Hotel> hotelList) {
        this.hotelList = hotelList;
        mIHotelAdapter = (IHotelAdapter) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Hotel hotel = hotelList.get(position);
        holder.tvJudul.setText(hotel.judul);
        holder.tvDeskripsi.setText(hotel.deskripsi);
        holder.ivFoto.setImageURI(Uri.parse(hotel.foto));


    }

    @Override
    public int getItemCount() {
        if (hotelList != null)
            return hotelList.size();
        return 0;

    }

    /*membuat interface untuk implementasi class mainactivity*/
    public interface IHotelAdapter {
        void doClick(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView ivFoto;
        TextView tvJudul;
        TextView tvDeskripsi;

        public ViewHolder(View itemView) {
            super(itemView);

            ivFoto = (ImageView) itemView.findViewById(R.id.imageView);
            tvJudul = (TextView) itemView.findViewById(R.id.textViewJudul);
            tvDeskripsi = (TextView) itemView.findViewById(R.id.textViewDeskripsi);

            /*kode untuk membuat tiap item pada RecyclerView dapat diklik*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIHotelAdapter.doClick(getAdapterPosition());
                }
            });


        }


    }

}
