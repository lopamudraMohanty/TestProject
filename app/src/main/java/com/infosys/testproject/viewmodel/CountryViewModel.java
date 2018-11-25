package com.infosys.testproject.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.infosys.testproject.R;
import com.infosys.testproject.service.model.Country;
import com.infosys.testproject.service.repository.CountryRepository;

/**
 * Created by LOPA on 11/23/2018.
 */

public class CountryViewModel extends AndroidViewModel {

    private LiveData<Country> countryLiveData;
    public CountryViewModel(@NonNull Application application) {
        super(application);
        fetchData();
    }

    public LiveData<Country> getObservableProject() {
        return countryLiveData;
    }
    public void fetchData()
    {
        countryLiveData = CountryRepository.getInstance(this.getApplication().getBaseContext()).getCountryData();
    }

}
