package com.sdmgapl1a0501.naimur.jhotpot_medicine_shop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Common.Common;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Model.User;

public class MainActivity extends AppCompatActivity {
    private EditText etnumber,etpassword;
    Button btnsignin,btnsignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etnumber=findViewById(R.id.numberid);
        etpassword=findViewById(R.id.passwrdid);
        btnsignin=findViewById(R.id.btnsignid);
        btnsignup = findViewById(R.id.btnsignupid);


//firebase

        FirebaseDatabase  db = FirebaseDatabase.getInstance();

        final DatabaseReference tb_user = db.getReference("User");


        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tb_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(etnumber.getText().toString()).exists()) {

                            User user = dataSnapshot.child(etnumber.getText().toString()).getValue(User.class);
                            user.setPhone(etnumber.getText().toString());

                            if (user.getPassword().equals(etpassword.getText().toString())) {
                           //  Toast.makeText(MainActivity.this, "log in successfull", Toast.LENGTH_SHORT).show();
                                Common.currentUser= user;
                             Intent i = new Intent(MainActivity.this,Buy.class);

                              startActivity(i);
                              finish();


                            } else {

                                Toast.makeText(MainActivity.this, "log in faild", Toast.LENGTH_SHORT).show();
                            }
                        }

                        else {

                            Toast.makeText(MainActivity.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });


            }
        });

btnsignup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i = new Intent(MainActivity.this,SignUp.class);
        startActivity(i);
    }
});


    }
}
