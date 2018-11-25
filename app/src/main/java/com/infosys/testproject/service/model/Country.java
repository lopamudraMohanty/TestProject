package com.infosys.testproject.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by LOPA on 11/23/2018.
 */

public class Country {
    @SerializedName("title")
    private String name;

    @SerializedName("rows")
    private ArrayList<CountryProfile> profiles;

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<CountryProfile> getProfile() {
        return profiles;
    }

    public void setProfile(ArrayList<CountryProfile> profiles) {
        this.profiles = profiles;
    }
}
