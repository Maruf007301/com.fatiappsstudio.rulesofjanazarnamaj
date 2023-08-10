package com.fatiappsstudio.rulesofjanazarnamaj;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.List;
import java.util.UUID;

/**
 * Created by Nishat on 5/19/2017.
 */

public class PersonPagerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String EXTRA_PERSON_ID = "com.mrappsbd.rulesofjanazarnamaj.person_id";
    private ViewPager mViewPager;
    private List<Person> mPersons;
    private Person mPerson;
    private Button mPreviousButton, mNextButton;
    private ImageButton mShareButton;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    public static Intent newIntent(Context packageContext, UUID personID) {
        Intent intent = new Intent(packageContext, PersonPagerActivity.class);
        intent.putExtra(EXTRA_PERSON_ID, personID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_pager);

        MobileAds.initialize(this, getResources().getString(R.string.ads_name));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.inter_youtube_marketing));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                //requestNewInterstitial();
            }
        });
        requestNewInterstitial();


        mAdView = (AdView) findViewById(R.id.banner_ads);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        UUID personId = (UUID) getIntent().getSerializableExtra(EXTRA_PERSON_ID);


        mViewPager = (ViewPager) findViewById(R.id.activity_person_pager_view_pager);
        mPersons = PersonLab.get(this).getPersons();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Person person = mPersons.get(position);
                return PersonFragment.newInstance(person.getID());
            }



            @Override
            public int getCount() {
                return mPersons.size();
            }
        });

        for (int i = 0; i < mPersons.size(); i++) {
            if (mPersons.get(i).getID().equals(personId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPerson = mPersons.get(mViewPager.getCurrentItem());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mPreviousButton = (Button) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(this);

        mShareButton = (ImageButton) findViewById(R.id.share_button);
        mShareButton.setOnClickListener(this);
        mPerson = mPersons.get(mViewPager.getCurrentItem());

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.previous_button:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                break;
            case R.id.share_button:
                String itemShareList = "";
                itemShareList += Html.fromHtml(mPerson.getName()) +
                        "\n\n" + Html.fromHtml(mPerson.getDetails()) + "\n\n" + getString(R.string.awesome_app) + "\n\n";

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/*");
                intent.putExtra(Intent.EXTRA_TEXT, itemShareList);
                intent.putExtra(Intent.EXTRA_SUBJECT,
                        getString(R.string.app_name));
                intent = Intent.createChooser(intent, getString(R.string.share_person));
                startActivity(intent);

                break;
            case R.id.next_button:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                break;
            default:
                break;
        }
    }

    private void requestNewInterstitial() {
        // Request a new ad if one isn't already loaded, hide the button, and kick off the timer.
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        }
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        // code here to show dialog
        showInterstitial();
        super.onBackPressed();  // optional depending on your needs
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (mAdView != null) {
            mAdView.resume();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

}