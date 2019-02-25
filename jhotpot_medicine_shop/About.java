package com.sdmgapl1a0501.naimur.jhotpot_medicine_shop;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;


public class About extends AppCompatActivity {
    private ViewPager vpager;
    private Pageradapter vadapter;
    private ArrayList<viewmodel> vmodel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        vpager=(ViewPager) findViewById(R.id.vp);
        vmodel=new ArrayList<>();

        int images[]={R.drawable.dia,R.drawable.sani,R.drawable.shuvo,R.drawable.fysal};
        String names[]={"Rownak Kabir","Shaon Hossain","Naimur Rahman","Fahad Fysal"};
        String work[]={"Data Analyst","Developer","Developer","Data Analyst"};

        for(int i=0;i<images.length;i++)
        {
            viewmodel vm=new viewmodel();
            vm.images=images[i];
            vm.names=names[i];
            vm.work=work[i];
            vmodel.add(vm);
        }

        vadapter=new Pageradapter(vmodel,this);
        vpager.setPageTransformer(true,new ViewPagerStack());
        vpager.setOffscreenPageLimit(4);
        vpager.setAdapter(vadapter);



    }
    private class ViewPagerStack implements ViewPager.PageTransformer{

        @Override
        public void transformPage(@NonNull View view, float v) {
            if(v>=0)
            {   view.setScaleX(0.7f-0.5f*v);
                view.setScaleY(0.7f);
                view.setTranslationX(-view.getWidth() *v);
                view.setTranslationY(-30*v);


            }
        }
    }
}
