package com.sdmgapl1a0501.naimur.jhotpot_medicine_shop;

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
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Model.User;

public class SignUp extends AppCompatActivity {
    EditText etname,etphn,etpswd,etadrs;
    Button btnsubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etphn=findViewById(R.id.entcntctid);
        etpswd=findViewById(R.id.entpasswrdid);
        etname=findViewById(R.id.nameid);
        etadrs=findViewById(R.id.addressid);
        btnsubmit= findViewById(R.id.btnsubmitid);


//firebase
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference tb_user = db.getReference("User");



        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tb_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(etphn.getText().toString()).exists()){
                            Toast.makeText(SignUp.this, "Already Registered", Toast.LENGTH_SHORT).show();
                        }
                        else {
                         User user = new User(etname.getText().toString(),etadrs.getText().toString(),etpswd.getText().toString());
                        tb_user.child(etphn.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "SignUp done", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });







    }
}
