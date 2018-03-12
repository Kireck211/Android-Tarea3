package mx.iteso.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import mx.iteso.app.beans.ItemProduct;
import mx.iteso.app.fragments.FragmentElectronics;
import mx.iteso.app.fragments.FragmentTechnology;
import mx.iteso.app.fragments.FragmentHome;

import static mx.iteso.app.utils.Constants.CHANGE_PRODUCT_INFO;
import static mx.iteso.app.utils.Constants.FRAGMENT_ELECTRONICS;
import static mx.iteso.app.utils.Constants.FRAGMENT_HOME;
import static mx.iteso.app.utils.Constants.FRAGMENT_INTENT;
import static mx.iteso.app.utils.Constants.FRAGMENT_TECHNOLOGY;
import static mx.iteso.app.utils.Constants.ITEM_INTENT;
import static mx.iteso.app.utils.Constants.USER_PREFERENCES;

public class ActivityMain extends AppCompatActivity {

    private FragmentHome mFragmentHome;
    private FragmentTechnology mFragmentTechnology;
    private FragmentElectronics mFragmentElectronics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TabLayout tabLayout = findViewById(R.id.tabs);

        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        /*
      The {@link android.support.v4.view.PagerAdapter} that will provide
      fragments for each of the sections. We use a
      {@link FragmentPagerAdapter} derivative, which will keep every
      loaded fragment in memory. If this becomes too memory intensive, it
      may be best to switch to a
      {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        /*
      The {@link ViewPager} that will host the section contents.
     */
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_log_out:
                logOut();
                return true;
            case R.id.action_privacy_policy:
                showPrivacyPolicy();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showPrivacyPolicy() {
        Intent intent = new Intent(this, ActivityPrivacyPolicy.class);
        startActivity(intent);
    }

    private void logOut() {
        SharedPreferences sharedPreferences =
                getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(ActivityMain.this, ActivityLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (mFragmentTechnology == null) {
                        mFragmentTechnology = new FragmentTechnology();
                    }
                    return mFragmentTechnology;
                case 1:
                if (mFragmentHome == null) {
                        mFragmentHome = new FragmentHome();
                    }
                return mFragmentHome;
                case 2:
                    if (mFragmentElectronics == null) {
                        mFragmentElectronics = new FragmentElectronics();
                    }
                    return mFragmentElectronics;
                default:
                    if (mFragmentTechnology == null) {
                        mFragmentTechnology = new FragmentTechnology();
                    }
                    return mFragmentTechnology;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case FRAGMENT_TECHNOLOGY: return getString(R.string.tab2);
                case FRAGMENT_HOME: return getString(R.string.tab1);
                case FRAGMENT_ELECTRONICS: return getString(R.string.tab3);
            }
            return null;
        }
    }

    private void onChangeItemSelectedFragment(int selectedFragment, ItemProduct itemProduct) {
        switch (selectedFragment) {
            case FRAGMENT_TECHNOLOGY:
                mFragmentTechnology.onChangeItem(itemProduct);
                break;
            case FRAGMENT_HOME:
                break;
            case FRAGMENT_ELECTRONICS:
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHANGE_PRODUCT_INFO:
                if (resultCode == RESULT_OK && data != null) {
                    ItemProduct itemProduct = data.getParcelableExtra(ITEM_INTENT);
                    int selectedFragment = FRAGMENT_TECHNOLOGY;
                    if (data.getExtras() != null)
                        selectedFragment = data.getExtras().getInt(FRAGMENT_INTENT);
                    if (itemProduct != null) {
                        onChangeItemSelectedFragment(selectedFragment, itemProduct);
                    }
                }
                break;
        }
    }
}
