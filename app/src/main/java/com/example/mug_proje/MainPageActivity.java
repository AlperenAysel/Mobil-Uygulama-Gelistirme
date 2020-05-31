package com.example.mug_proje;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainPageActivity extends AppCompatActivity {

    String[] primeText = {"Pins","Photo","Accelerometer","Charging"};
    String[] subText = {"Add a pin to map","Take or pick a photo","Accelerometer measurements","Charging Operations"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


        MyAdapter adapter = new MyAdapter(this, primeText, subText);

        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0) {
                    goToMap();
                }
                if(position == 1) {
                    goToPic();
                }
                if(position == 2) {
                    goToSensor();
                }
                if(position == 3) {
                    goToBattery();
                }

                Toast.makeText(MainPageActivity.this, primeText[position], Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void goToMap() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    public void goToBattery() {
         Intent intent = new Intent(this, BatteryActivity.class);
         startActivity(intent);
    }
    public void goToSensor() {

        Intent intent = new Intent(this, SensorActivity.class);
        startActivity(intent);
    }
    public void goToPic() {
        Intent intent = new Intent(this, PhotoActivity.class);
        startActivity(intent);
    }


    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String rDescription[];


        MyAdapter (Context c, String title[], String description[]) {
            super(c, R.layout.custom, R.id.prime, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;


        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.custom, parent, false);
            TextView myTitle = row.findViewById(R.id.prime);
            TextView myDescription = row.findViewById(R.id.sub);



            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);




            return row;
        }
    }
}
