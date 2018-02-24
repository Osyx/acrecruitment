package io.osyx.acrecuritment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import io.osyx.acrecuritment.helpers.HostnameDialog;

public class MainActivity extends AppCompatActivity implements HostnameDialog.DialogListener {
    private String HOST_URL = "http://192.168.10.218:8080";
    ListJobAppsFragment listJobAppsFragment;
    CreateJobAppFragment createJobAppFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, listJobAppsFragment)
                            .commit();
                    return true;
                case R.id.navigation_dashboard:
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, createJobAppFragment)
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DialogFragment dialogFragment = new HostnameDialog();
        dialogFragment.show(getFragmentManager(), "");

        listJobAppsFragment = ListJobAppsFragment.newInstance(HOST_URL);
        createJobAppFragment = CreateJobAppFragment.newInstance(HOST_URL);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new ListJobAppsFragment())
                .commit();
    }

    @Override
    public void onDialogPositiveClick(String hostAddress) {
        HOST_URL = "http://" + hostAddress + ":8080";
    }

    @Override
    public void onDialogNegativeClick() {

    }
}
