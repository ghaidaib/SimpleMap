package com.example.asus.simplemap;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String x,y;
    private double lat , longi ;
    private LatLng location , myLocation;
    private Context context;
    private static String URL_REQUEST="http://172.23.250.113/Maps_php/location_save.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        location = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(location).title("My Location")).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,16));



        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            Toast.makeText(MapsActivity.this,latLng.latitude+","+latLng.longitude,Toast.LENGTH_SHORT).show();

                 lat = latLng.latitude;
                 longi = latLng.longitude;

                myLocation = new LatLng(lat,longi);


                x =  Double.valueOf(lat).toString();
                y = Double.valueOf(longi).toString();



            StringRequest stringRequest= new StringRequest(Request.Method.POST,URL_REQUEST,
                    new Response.Listener<String>(){

                        @Override
                        public void onResponse(String response){
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success =jsonObject.getString("success");

                                if(success.equals("1")){
                                    Toast.makeText(MapsActivity.this,
                                            "location saved success",
                                            Toast.LENGTH_SHORT).show();

                                }

                            }catch (JSONException e){
                                e.printStackTrace();

                                Toast.makeText(MapsActivity.this,
                                        "location saved error"+e.toString(),
                                        Toast.LENGTH_SHORT).show();


                            }
                        }
                    },

                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){

                            Toast.makeText(MapsActivity.this,
                                    "location saved Error"+error.toString(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    })
            {

                protected Map<String,String> getParams() throws AuthFailureError{
                    Map<String,String> params = new HashMap<>();
                    params.put("x",x);
                    params.put("y",y);
                    return params;
                }
            };
                RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                requestQueue.add(stringRequest);

                mMap.clear();

                mMap.addMarker(new MarkerOptions().position(myLocation).title("My Location")).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,16));
        }




        }
        );
    }
}
