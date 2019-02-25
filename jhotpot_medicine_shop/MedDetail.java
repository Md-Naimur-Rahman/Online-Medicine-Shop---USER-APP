package com.sdmgapl1a0501.naimur.jhotpot_medicine_shop;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Database.Database;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Model.Medicine;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Model.Order;
import com.squareup.picasso.Picasso;

public class MedDetail extends AppCompatActivity {

    TextView med_name,med_des,med_price;
    ImageView med_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;
    String medId="";

    FirebaseDatabase database;
    DatabaseReference medicine;
    Medicine currentMedicine;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_detail);

        // fiebase
        database = FirebaseDatabase.getInstance();
        medicine = database.getReference("Medicine");

        med_name = findViewById(R.id.med_namei);
        med_image = findViewById(R.id.img_medicine);
        med_des= findViewById(R.id.med_description);
        med_price= findViewById(R.id.med_price);

        btnCart = findViewById(R.id.btncart);
        numberButton = findViewById(R.id.number_button);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database(getBaseContext()).addToCart(new Order(
                        medId,currentMedicine.getName(),numberButton.getNumber(),currentMedicine.getPrice(),currentMedicine.getDiscount()
                ));

                Toast.makeText(MedDetail.this,"Added to Cart",Toast.LENGTH_LONG).show();
            }
        });


        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpendedAppbar);


            if (getIntent() !=null){

                medId = getIntent().getStringExtra("MedicineId");
            }
             if (!medId.isEmpty()){
                mediDetail(medId);

             }

    }

    private void mediDetail(String medId) {

        medicine.child(medId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentMedicine = dataSnapshot.getValue(Medicine.class);
                Picasso.get().load(currentMedicine.getImage()).into(med_image);
                collapsingToolbarLayout.setTitle(currentMedicine.getName());
                med_price.setText(currentMedicine.getPrice());
                med_name.setText(currentMedicine.getName());
                med_des.setText(currentMedicine.getDescription());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
