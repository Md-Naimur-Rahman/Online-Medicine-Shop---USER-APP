package com.sdmgapl1a0501.naimur.jhotpot_medicine_shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Common.Common;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Interface.ItemClickListener;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Model.Category;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Service.ListenOrder;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.ViewHolder.MenuViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class Buy extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
     FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;
FirebaseDatabase  database;
   // FirebaseRecyclerAdapter adapter;
DatabaseReference category;
TextView txtName,txtAdrs;
RecyclerView rc_menu;
RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        //firebase
        database = FirebaseDatabase.getInstance() ;
        category = database.getReference("Category");



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent i = new Intent(Buy.this, Cart.class);
              startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //set name for user

        View headerview = navigationView.getHeaderView(0);
       txtName = headerview.findViewById(R.id.nametxtid);
       txtAdrs =headerview.findViewById(R.id.adrstxtid);
        txtName.setText(Common.currentUser.getName());
        txtAdrs.setText(Common.currentUser.getAddress());

        //load menu
           rc_menu = findViewById(R.id.recycler_menu);
         //  rc_menu.setHasFixedSize(true);
          // layoutManager = new LinearLayoutManager(this);
      //  rc_menu.setLayoutManager(layoutManager);
        rc_menu.setLayoutManager(new GridLayoutManager(this,2));


           loadMenu();

Intent service = new Intent(Buy.this, ListenOrder.class);
startService(service);


    }

    private void loadMenu() {
      //  Toast.makeText(Buy.this, "Loadddd", Toast.LENGTH_SHORT).show();
 adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category) {
    @Override

    protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
       // Toast.makeText(Buy.this, "check", Toast.LENGTH_SHORT).show();
viewHolder.txtMenuName.setText(model.getName());
Picasso.get().load(model.getImage()).into(viewHolder.imageView);
final Category clickitem = model;
viewHolder.setItemClickListener(new ItemClickListener() {
    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        Intent intent = new Intent(Buy.this,MedicineList.class);
        intent.putExtra("CategoryId",adapter.getRef(position).getKey());
       startActivity(intent);
    }
});
    }
};
rc_menu.setAdapter(adapter);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.buy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if(item.getItemId()==R.id.menu_search){
            startActivity(new Intent(Buy.this,Search.class));

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            // Handle the camera action
        } else if (id == R.id.nav_cart) {
            Intent i = new Intent(Buy.this,Cart.class);
            startActivity(i);

        } else if (id == R.id.nav_order) {
            Intent i = new Intent(Buy.this,OrderStatus.class);
            startActivity(i);
        } else if (id == R.id.nav_logout) {
            Intent i = new Intent(Buy.this,MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
