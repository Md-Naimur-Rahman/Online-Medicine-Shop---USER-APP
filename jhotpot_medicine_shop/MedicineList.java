package com.sdmgapl1a0501.naimur.jhotpot_medicine_shop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

public class MedicineList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Medicine,MedViewHolder>  adapter;
    FirebaseDatabase database;
    DatabaseReference mediList;
    FirebaseRecyclerAdapter<Medicine, MedViewHolder> searchAdapter = null;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    String categoryId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        //firebase

        database = FirebaseDatabase.getInstance();
        mediList = database.getReference("Medicine");

        recyclerView = findViewById(R.id.recycler_medid);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent() != null){
            categoryId = getIntent().getStringExtra("CategoryId");
            if (!categoryId.isEmpty() && categoryId !=null){
                loadlist(categoryId);
            }


            if (!categoryId.isEmpty() && categoryId != null) {
                loadlist(categoryId);
            }

materialSearchBar= findViewById(R.id.searchBarid);
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

        }


    }

    private void startSearch(CharSequence text) {

        searchAdapter = new FirebaseRecyclerAdapter<Medicine, MedViewHolder>(Medicine.class,R.layout.medicine_item,MedViewHolder.class,
                mediList.orderByChild("name").equalTo(text.toString()) ) {
            @Override
            protected void populateViewHolder(MedViewHolder viewHolder, Medicine model, int position) {
                viewHolder.med_name.setText(model.getName());
                Picasso.get().load(model.getImage()).into(viewHolder.med_image);

                final Medicine local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(MedicineList.this,MedDetail.class);
                        intent.putExtra("MedicineId",searchAdapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(searchAdapter);
    }

    private void loadSuggest() {
        mediList.orderByChild("menuId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
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

    private void loadlist(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Medicine, MedViewHolder>(Medicine.class,R.layout.medicine_item,MedViewHolder.class,
                mediList.orderByChild("menuId").equalTo(categoryId)
                ) {
            @Override
            protected void populateViewHolder(MedViewHolder viewHolder, Medicine model, int position) {
                viewHolder.med_name.setText(model.getName());
                Picasso.get().load(model.getImage()).into(viewHolder.med_image);

                final Medicine local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(MedicineList.this,MedDetail.class);
                        intent.putExtra("MedicineId",adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });

            }
        };
        recyclerView.setAdapter(adapter);
    }
}
