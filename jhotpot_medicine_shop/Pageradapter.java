package com.sdmgapl1a0501.naimur.jhotpot_medicine_shop;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class Pageradapter extends PagerAdapter {
    private List<viewmodel> content;
    private Context con;

    public Pageradapter(List<viewmodel> content, Context con) {
        this.content = content;
        this.con = con;
    }

    @Override
    public int getCount() {
        return content.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==(LinearLayout)o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inf=(LayoutInflater)con.getSystemService(con.LAYOUT_INFLATER_SERVICE);
        View view=inf.inflate(R.layout.view_page,container,false);
        container.addView(view);

        ImageView im=(ImageView) view.findViewById(R.id.imgpager);
        im.setImageResource(content.get(position).getImages());

        TextView name,work;
        name=(TextView) view.findViewById(R.id.name);
        name.setText(content.get(position).getNames());

        work=(TextView) view.findViewById(R.id.work);
        work.setText(content.get(position).getWork());

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
