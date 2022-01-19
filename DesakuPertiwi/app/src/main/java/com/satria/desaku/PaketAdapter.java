package com.satria.desaku;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class PaketAdapter extends RecyclerView.Adapter<PaketAdapter.ViewHolder>{
    int price = 0;
    private String listBelanja_str = "";
    private WeakReference<TextView> mListofbelanja;
    private ArrayList<Paket> mPaket;
    private Context mContext;
    private WeakReference<TextView> mTotalPriceText;
    PaketAdapter(Context context, ArrayList<Paket> foodsData, TextView totalPriceText, TextView listofbelanja) {
        this.mPaket = foodsData;
        this.mContext = context;
        this.mTotalPriceText = new WeakReference<>(totalPriceText);
        this.mListofbelanja = new WeakReference<>(listofbelanja);
    }


    @Override
    public PaketAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(PaketAdapter.ViewHolder holder, int position) {
        Paket currentFood = mPaket.get(position);
        holder.bindTo(currentFood);
    }

    @Override
    public int getItemCount() {
        return mPaket.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mNameText;
        private TextView mPriceText;
        private TextView mDescText;
        private TextView mIdText;
        private ImageView mFoodImage;

        ViewHolder(View itemView) {
            super(itemView);
            mIdText = (TextView) itemView.findViewById(R.id.idsaya);
            mNameText = (TextView)itemView.findViewById(R.id.name);
            mPriceText = (TextView)itemView.findViewById(R.id.price);
            mDescText = (TextView) itemView.findViewById(R.id.deskripsi);
            mFoodImage = itemView.findViewById(R.id.foodImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Paket currentFood = mPaket.get(getAdapterPosition());
                    price += currentFood.getPrice();
                    Log.d("THEBUGX", listBelanja_str);
                    listBelanja_str += mIdText.getText()  + ",";

                    Log.d("THEBUGX", mIdText.getText().toString());
                    mListofbelanja.get().setText(listBelanja_str);
                    mTotalPriceText.get().setText("TOTAL = " + String.valueOf(price));
                }
            });

            mNameText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Paket currentFood = mPaket.get(getAdapterPosition());
                    Intent myIntent = new Intent(mContext, DetailActivity.class);

                    myIntent.putExtra("nama", currentFood.getName());
                    myIntent.putExtra("harga", currentFood.getPrice());
                    myIntent.putExtra("gambar", currentFood.getImageResource());
                    myIntent.putExtra("deskripsi", currentFood.getDeskripsi());
                    mContext.startActivity(myIntent);
                }
            });
        }

        void bindTo(Paket currentFood){
            mIdText.setText(String.valueOf(currentFood.getId()));
            mNameText.setText(currentFood.getName());
            mPriceText.setText(String.valueOf(currentFood.getPrice()));
//            mFoodImage.setImageResource(currentFood.getImageResource());
            mDescText.setText(currentFood.getDeskripsi());
            Glide.with(mContext).load(currentFood.getImageResource()).into(mFoodImage);

        }

    }
}