package io.hypertrack.sendeta.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hypertrack.lib.HyperTrack;
import com.hypertrack.lib.internal.consumer.view.Placeline.PlacelineFragment;

import io.hypertrack.sendeta.R;
import io.hypertrack.sendeta.store.SharedPreferenceManager;

/**
 * Created by Aman Jain on 24/05/17.
 */

public class Placeline extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private PlacelineFragment placelineFragment;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeline);

        initUI();

        // Start Tracking, Only first time
        if (SharedPreferenceManager.isTrackingON(this) == null ||
                (SharedPreferenceManager.isTrackingON(this) && !HyperTrack.isTracking())) {
            startHyperTrackTracking();
        }
    }

    private void initUI() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (HyperTrack.isTracking()) {
            navigationView.getMenu().findItem(R.id.start_tracking_toggle).setTitle(R.string.stop_tracking);
        }
        navigationView.setNavigationItemSelectedListener(this);
        placelineFragment = (PlacelineFragment) getSupportFragmentManager().findFragmentById(R.id.placeline_fragment);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Placeline.this, Home.class);
                intent.putExtra("class_from", Placeline.class.getSimpleName());
                startActivity(intent);
            }
        });
        placelineFragment.setToolbarIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawers();
        if (item.getItemId() == R.id.edit_profile)
            startActivity(new Intent(this, Profile.class));

        else if (item.getItemId() == R.id.start_tracking_toggle) {
            startHyperTrackTracking();
        }
        return true;
    }

    private void startHyperTrackTracking() {
        if (!HyperTrack.isTracking()) {
            HyperTrack.startTracking();
            SharedPreferenceManager.setTrackingON(this);
            navigationView.getMenu().findItem(R.id.start_tracking_toggle).setTitle(R.string.stop_tracking);
            Toast.makeText(this, "Tracking started successfully.", Toast.LENGTH_SHORT).show();

        } else {
            HyperTrack.stopTracking();
            SharedPreferenceManager.setTrackingOFF(this);
            navigationView.getMenu().findItem(R.id.start_tracking_toggle).setTitle(R.string.start_tracking);
            Toast.makeText(this, "Tracking stopped successfully.", Toast.LENGTH_SHORT).show();
        }

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawers();
            return;
        }

        if (placelineFragment.onBackPressed())
            return;
        super.onBackPressed();
    }
}