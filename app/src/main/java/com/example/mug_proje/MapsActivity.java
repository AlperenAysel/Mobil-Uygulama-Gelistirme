package com.example.mug_proje;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double lat;
    double lng;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", "mobil");
            jsonObject.put("password", "mobiluygulamagelistirme");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestQueue requestQueue = Volley.newRequestQueue(MapsActivity.this);
        String url = "http://yusufozgul.com:8282/login";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String check = response.getString("success");
                            if (check.equals("true")) {
                                JSONObject jObject =response.getJSONObject("data");
                                JSONObject loc = (JSONObject) jObject.get("location");
                                lat = loc.getDouble("lat");
                                lng = loc.getDouble("lon");

                                mMap = googleMap;



                                // Add a marker in Sydney and move the camera
                                LatLng sydney = new LatLng(lat, lng);
                                mMap.addMarker(new MarkerOptions().position(sydney).title("Pin Turgutlu'da"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                                
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());

            }
        }
        );
        requestQueue.add(jsonObjectRequest);

    }
}
