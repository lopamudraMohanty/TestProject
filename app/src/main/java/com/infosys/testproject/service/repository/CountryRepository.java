package com.infosys.testproject.service.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.infosys.testproject.BuildConfig;
import com.infosys.testproject.R;
import com.infosys.testproject.service.model.Country;
import com.infosys.testproject.service.model.CountryProfile;
import com.infosys.testproject.utility.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LOPA on 11/23/2018.
 */

public class CountryRepository {

    private static CountryRepository mInstance;
    private ServiceApi api;


    private CountryRepository(final Context context)
    {
        //Uses cache to hold the data for offline mode , if data exist in cache it will return the data
        long cacheSize = (5 * 1024 * 1024);
        Cache myCache = new Cache(context.getCacheDir(), cacheSize);
        OkHttpClient httpClient =
                new OkHttpClient.Builder().cache(myCache).addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();
                if(Util.hasNetwork(context))
                    original =   original.newBuilder().header("Cache-Control", "public, max-age=" + 5).build();
                else
                    original = original.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();

                return chain.proceed(original);
            }
        }).build();
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(ServiceApi.BASE_URL).
                addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        api = retrofit.create(ServiceApi.class);
    }

    public static CountryRepository getInstance(Context context)
    {
        if(mInstance == null)
        {
            mInstance = new CountryRepository(context);
        }
        return mInstance;
    }

    /**
     * @purpose : Sending request to server and getting response on callback
     * @return
     */
    public LiveData<Country> getCountryData()
    {
        final MutableLiveData<Country> data = new MutableLiveData<>();
        api.getDataAboutCanada().enqueue(new Callback<Country>() {

            @Override
            public void onResponse(Call<Country> call, Response<Country> response) {
                if(response.body() != null) {
                    data.setValue(response.body());
                    if (response.body().getProfile() != null)
                        removeNulls(response.body().getProfile());
                }else{
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<Country> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    /**
     * @Purpose : This method used to remove the item from list which contains null data
     * @param list
     */
    private  void removeNulls(ArrayList<CountryProfile> list)
    {
        Iterator itr = list.iterator();
        while (itr.hasNext())
        {
            CountryProfile curr = (CountryProfile)itr.next();
            if(curr == null || (curr.getTitle() == null && curr.getDescription() == null && curr.getImageHref() == null))
            {
                itr.remove();
            }
        }
    }

}
