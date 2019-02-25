package com.sdmgapl1a0501.naimur.jhotpot_medicine_shop;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Common.Common;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Database.Database;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Model.Order;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.Model.Request;
import com.sdmgapl1a0501.naimur.jhotpot_medicine_shop.ViewHolder.CartAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class Cart extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener {
RecyclerView recyclerView;
RecyclerView.LayoutManager layoutManager;
FirebaseDatabase database;
DatabaseReference requests;
TextView txtTotalPrice;
Button submitOrderbtn;

List<Order> cart = new ArrayList<>();
CartAdapter adapter;


LocationRequest mlocationRequest;
GoogleApiClient mgoogleApiClient;
Location mLastLocation;

private static final int UPDATE_INTERVAL = 5000;
    private static final int FASTEST_INTERVAL = 3000;
    private static final int DISPLACEMENT = 10;
    private static final int LOCATION_REQUEST_CODE = 9999;
    private static final int   PLAY_SERVICES_REQUEST = 9997;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                )
        {
            ActivityCompat.requestPermissions(this,new String[]
                    {
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },LOCATION_REQUEST_CODE
            );
        }
else {
            if (checlPlayServices())
            {
                buildGoogleApiClient();
                createLocationRequest();
            }

        }

        //
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = findViewById(R.id.listCartid);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        txtTotalPrice = findViewById(R.id.totalid);
        submitOrderbtn = findViewById(R.id.btnplaceprderid);


        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        load();


        submitOrderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

    }

    private void createLocationRequest() {

        mlocationRequest = new LocationRequest();
        mlocationRequest.setInterval(UPDATE_INTERVAL);
        mlocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mlocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mlocationRequest.setSmallestDisplacement(DISPLACEMENT);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case  LOCATION_REQUEST_CODE:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    if (checlPlayServices())
                    {
                        buildGoogleApiClient();
                        createLocationRequest();
                    }
                }
        }
    }

    private synchronized void buildGoogleApiClient() {

        mgoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mgoogleApiClient.connect();

    }

    private boolean checlPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS)
        {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))
            {
                GooglePlayServicesUtil.getErrorDialog(resultCode,this,PLAY_SERVICES_REQUEST).show();
            }
            else {

                Toast.makeText(this, "This device is not supported", Toast.LENGTH_SHORT).show();
                finish();
            }
            return  false;
        }
        return true;
    }

    private void showAlertDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("One more step!");
        alertDialog.setMessage("Enter your address: ");


        final EditText edtAddress = new EditText(Cart.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        edtAddress.setLayoutParams(lp);


        alertDialog.setView(edtAddress);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);


        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Request request = new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        edtAddress.getText().toString(),
                        txtTotalPrice.getText().toString(),
                        cart
                );

                requests.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);

                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this, " Thank you, Order Place", Toast.LENGTH_SHORT).show();
               finish();
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }


    private void load() {
cart = new Database(this).getCarts();
adapter = new CartAdapter(cart,this);
recyclerView.setAdapter(adapter);

int total= 0;
for(Order order:cart)
    total+=(Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("bn", "bd");
        NumberFormat form = NumberFormat.getCurrencyInstance(locale);
txtTotalPrice.setText(form.format(total));


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                )
        {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mgoogleApiClient,mlocationRequest,this);
    }

    private void displayLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                )
        {
            return;
        }
        mLastLocation= LocationServices.FusedLocationApi.getLastLocation(mgoogleApiClient);
        if (mLastLocation !=null)
        {
            Log.d("LOCATION","Your Location : "+mLastLocation.getLatitude()+","+mLastLocation.getLongitude());
        }
        else {
            Log.d("LOCATION","Could not get your location");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
mgoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
