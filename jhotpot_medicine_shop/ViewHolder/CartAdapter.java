package com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.ViewHolder;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Interface.ItemClickListener;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Model.Order;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


 public TextView txt_cart,txt_price;
    public ImageView img_cart_count;
    private ItemClickListener itemClickListener;

    public void setTxt_cart(TextView txt_cart) {
        this.txt_cart = txt_cart;
    }

    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_cart = itemView.findViewById(R.id.cart_itemInameid);
        txt_price= itemView.findViewById(R.id.cart_itempriceid);
        img_cart_count = itemView.findViewById(R.id.cart_item_count);
    }

    @Override
    public void onClick(View view) {

    }
}




public class CartAdapter extends RecyclerView.Adapter<CardViewHolder>{

    private List<Order> listData = new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout,viewGroup,false);
        return new CardViewHolder(itemView);



    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, int i) {

        TextDrawable drawable = TextDrawable.builder().buildRound(""+listData.get(i).getQuantity(), Color.RED);
        cardViewHolder.img_cart_count.setImageDrawable(drawable);
        Locale locale = new Locale("bn", "bd");
        NumberFormat form = NumberFormat.getCurrencyInstance(locale);

        int price = (Integer.parseInt(listData.get(i).getPrice()))* (Integer.parseInt(listData.get(i).getQuantity())) ;

        cardViewHolder.txt_price.setText(form.format(price));
        cardViewHolder.txt_cart.setText(listData.get(i).getProductName() );

    }

    @Override
    public int getItemCount() {
        return listData.size()  ;
    }
}
