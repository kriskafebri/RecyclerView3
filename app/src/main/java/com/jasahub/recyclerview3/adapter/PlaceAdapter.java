package com.jasahub.recyclerview3.adapter;
import com.jasahub.recyclerview3.R;
import com.jasahub.recyclerview3.model.DataHotel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.Uri;

import com.jasahub.recyclerview3.MainActivity;

import java.util.ArrayList;

/**
 * Created by SE on 30/08/2017.
 */

/*class yang mengatur item-item pada ListView*/
public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {
    ArrayList<DataHotel> hotelList;
    IPlaceAdapter mIPlaceAdapter;

    public PlaceAdapter(Context context, ArrayList<DataHotel> hotelList) {
        this.hotelList = hotelList;
        mIPlaceAdapter = (IPlaceAdapter) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataHotel datahotel = hotelList.get(position);
        holder.tvJudul.setText(datahotel.judul);
        holder.tvDeskripsi.setText(datahotel.deskripsi);
        holder.ivFoto.setImageURI(Uri.parse(datahotel.foto));

    }

    @Override
    public int getItemCount() {
        if (hotelList != null)
            return hotelList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvJudul;
        TextView tvDeskripsi;
        Button bEdit;
        Button bDelete;
        ImageButton ibFav;
        ImageButton ibShare;

        public ViewHolder(View itemView)
        {
            super(itemView);
            ivFoto = (ImageView) itemView.findViewById(R.id.imageView);
            tvJudul = (TextView) itemView.findViewById(R.id.textViewJudul);
            tvDeskripsi = (TextView) itemView.findViewById(R.id.textViewDeskripsi);
            bEdit = (Button) itemView.findViewById(R.id.buttonEdit);
            bDelete = (Button) itemView.findViewById(R.id.buttonDelete);
            ibFav = (ImageButton) itemView.findViewById(R.id.buttonFavorite);
            ibShare = (ImageButton) itemView.findViewById(R.id.buttonShare);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mIPlaceAdapter.doClick(getAdapterPosition());
                }
            });
            bEdit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mIPlaceAdapter.doEdit(getAdapterPosition());
                }
            });
            bDelete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mIPlaceAdapter.doDelete(getAdapterPosition());
                }
            });
            ibFav.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mIPlaceAdapter.doFav(getAdapterPosition());
                }
            });
            ibShare.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mIPlaceAdapter.doShare(getAdapterPosition());
                }
            });
        }
    }

    /*interface. kumpulan method tanpa tubuh*/
    public interface IPlaceAdapter
    {
        void doClick(int pos);

        void doEdit(int pos);

        void doDelete(int pos);

        void doFav(int pos);

        void doShare(int pos);
    }
}
