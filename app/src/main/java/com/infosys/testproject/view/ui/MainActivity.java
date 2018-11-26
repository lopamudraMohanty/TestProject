package com.infosys.testproject.view.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import com.infosys.testproject.R;
import com.infosys.testproject.service.model.Country;
import com.infosys.testproject.service.model.CountryProfile;
import com.infosys.testproject.utility.Util;
import com.infosys.testproject.viewmodel.CountryViewModel;

/**
 * Created by LOPA on 11/23/2018.
 */

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    MainFragment newFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout)).setOnRefreshListener(this);

        //added new fragment
        newFragment = new MainFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, newFragment);
        transaction.commit();

        observeViewModel();
    }


    /**
     * Declare the observer of viewmodel to receive events if data changes
     */
    private void observeViewModel() {

        CountryViewModel viewModel = ViewModelProviders.of(this)
                .get(CountryViewModel.class);
        viewModel.getObservableProject().observe(this, new Observer<Country>() {
            @Override
            public void onChanged(@Nullable Country country) {
                if(country != null) {

                    //set actionbar title according to the response
                    getSupportActionBar().setTitle(country.getName());
                    newFragment.setData(country.getProfile());
                    ((SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout)).setRefreshing(false);

                }else if(!Util.hasNetwork(MainActivity.this.getApplicationContext())) {
                    Util.showOKAlert(MainActivity.this, "", MainActivity.this.getResources().getString(R.string.internet_connectivity_error));
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Util.dismissDialog();
    }

    @Override
    public void onRefresh() {
        //fetch fresh data from server
        ViewModelProviders.of(this)
                .get(CountryViewModel.class).fetchData();
        observeViewModel();
    }
}
