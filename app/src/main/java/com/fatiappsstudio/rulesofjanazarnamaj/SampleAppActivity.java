package com.fatiappsstudio.rulesofjanazarnamaj;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class SampleAppActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    boolean doubleBackToExitPressedOnce = false;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, SampleAppActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RateItDialogFragment.show(this, getSupportFragmentManager());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                Intent intent = null;
                switch (menuItem.getItemId()) {
                    case R.id.nav_rate:
                        try {
                            appLinks(R.string.app_link);
                        } catch (ActivityNotFoundException e) {
                            appLinks(R.string.alternate_app_link);
                        }
                        break;
                    case R.id.nav_share:
                        intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.awesome_app));
                        intent.putExtra(Intent.EXTRA_SUBJECT,
                                getString(R.string.app_sharing_subject));
                        intent = Intent.createChooser(intent, getString(R.string.share_app));
                        startActivity(intent);
                        break;
                    case R.id.nav_more_free_apps:
                        try {
                            appLinks(R.string.developer_page);
                        } catch (ActivityNotFoundException e) {
                            appLinks(R.string.alternate_dev_page);
                        }
                        break;
                    case R.id.nav_about_this_app:
                        intent = new Intent(SampleAppActivity.this, AboutAppActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_quit:
                        finish();
                        System.exit(0);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new PersonListFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    private void appLinks(int id) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(getResources().getString(id)));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            /*
            case R.id.action_settings:
                showChangeLangDialog();
                return true;
            */
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.click_again, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
