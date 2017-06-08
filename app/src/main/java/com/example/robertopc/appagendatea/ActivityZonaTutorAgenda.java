package com.example.robertopc.appagendatea;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class ActivityZonaTutorAgenda extends AppCompatActivity {
    private NavigationView navView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zona_tutor_agenda);


        navView = (NavigationView)findViewById(R.id.navigation_view_agenda);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.perfilid:
                        Intent intentperfil = new Intent(ActivityZonaTutorAgenda.this, ActivityZonaTutor.class);
                        startActivity(intentperfil.putExtra("tutor", getIntent().getStringExtra("tutor")));
                        return true;
                    case R.id.galeriaid:
                        Intent intentgaleria = new Intent(ActivityZonaTutorAgenda.this, ActivityZonaTutorGaleria.class);
                        startActivity(intentgaleria.putExtra("tutor", getIntent().getStringExtra("tutor")));
                        return true;
                    case R.id.listaagendasid:
                        return true;
                }
                return true;
            }
        });

    }
}
