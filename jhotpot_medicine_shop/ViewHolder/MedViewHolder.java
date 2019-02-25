package com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Interface.ItemClickListener;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.R;

public class MedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView med_name;
    public ImageView med_image;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public MedViewHolder(@NonNull View itemView) {
        super(itemView);

        med_name = itemView.findViewById(R.id.med_name);
        med_image=itemView.findViewById(R.id.med_image);
        itemView.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
