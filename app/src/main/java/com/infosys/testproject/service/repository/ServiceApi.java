package com.infosys.testproject.service.repository;

import com.infosys.testproject.service.model.Country;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by LOPA on 11/23/2018.
 */

public interface ServiceApi {
    String BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/";

    @GET("facts.json")
    Call<Country> getDataAboutCanada();
}
