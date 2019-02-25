package com.sdmgapl1a0501.naimur.jhotpot_medicine_shop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Common.Common;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Interface.ItemClickListener;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Model.Request;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.ViewHolder.OrderViewHolder;

import static com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Common.Common.convertCodeToStatus;

public class OrderStatus extends AppCompatActivity {

  RecyclerView.LayoutManager layoutManager ;
  RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference requests;

    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);


        // Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");


//        recyclerView.setHasFixedSize(true);
        recyclerView =findViewById(R.id.listOrders);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent() == null)
            loadOrders(Common.currentUser.getPhone());
        else
            loadOrders(getIntent().getStringExtra("userPhone"));


    }


    private void loadOrders(String phone) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("phone")
                        .equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
                // get key from DatabaseReference
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                // set text for order status, address and phone
                viewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrAddress.setText(model.getAddress());
                viewHolder.txtOrderPhone.setText(model.getPhone());
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(OrderStatus.this, "" + position, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
    }



}
