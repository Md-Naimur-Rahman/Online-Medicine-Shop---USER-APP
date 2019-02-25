package com.sdmgapl1a0501.naimur.jhotpot_medicine_shop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Interface.ItemClickListener;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Model.Medicine;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.ViewHolder.MedViewHolder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {
    FirebaseRecyclerAdapter<Medicine, MedViewHolder> searchAdapter = null;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<Medicine,MedViewHolder>  adapter;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference mediList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        database = FirebaseDatabase.getInstance();
        mediList = database.getReference("Medicine");





        recyclerView = findViewById(R.id.recycler_searchid);
    //    recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);






        materialSearchBar= findViewById(R.id.searchid);
        materialSearchBar.setHint("Enter your Medicine");
        loadSuggest();

        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String> suggest = new ArrayList<>();
                for (String search : suggestList) {
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())) {
                        suggest.add(search);
                    }
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled) {
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        load();

    }

    private void startSearch(CharSequence text) {
        searchAdapter = new FirebaseRecyclerAdapter<Medicine, MedViewHolder>(Medicine.class,R.layout.medicine_item,MedViewHolder.class,
                mediList.orderByChild("Name").equalTo(text.toString()) ) {
            @Override
            protected void populateViewHolder(MedViewHolder viewHolder, Medicine model, int position) {
                viewHolder.med_name.setText(model.getName());
                Picasso.get().load(model.getImage()).into(viewHolder.med_image);

                final Medicine local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(Search.this,MedDetail.class);
                        intent.putExtra("MedicineId",searchAdapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(searchAdapter);

        load();
    }

    private void load() {
        adapter = new FirebaseRecyclerAdapter<Medicine, MedViewHolder>(Medicine.class,R.layout.medicine_item,MedViewHolder.class,
                mediList
        ) {
            @Override
            protected void populateViewHolder(MedViewHolder viewHolder, Medicine model, int position) {
                viewHolder.med_name.setText(model.getName());
                Picasso.get().load(model.getImage()).into(viewHolder.med_image);

                final Medicine local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(Search.this,MedDetail.class);
                        intent.putExtra("MedicineId",adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });

            }
        };
        recyclerView.setAdapter(adapter);



    }

    private void loadSuggest() {

        mediList.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // get food item
                        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Medicine item = postSnapshot.getValue(Medicine.class);
                            suggestList.add(item.getName());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }


}
