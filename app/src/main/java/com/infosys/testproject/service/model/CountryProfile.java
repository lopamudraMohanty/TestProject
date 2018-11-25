package com.infosys.testproject.service.model;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.infosys.testproject.R;

/**
 * Created by LOPA on 11/23/2018.
 */

public class CountryProfile {

    private String title ;
    private String description;
    private String imageHref;
    private Boolean hasTitle = (title == null || title.isEmpty())?false:true;
    private Boolean hasDesc = (description == null || title.isEmpty())?false:true;
    private Boolean hasImageHref = (imageHref == null || title.isEmpty())?false:true;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }

    public boolean hasTitle()
    {
        return (title == null || title.isEmpty())?false:true;
    }
    public boolean hasDesc()
    {
        return (description == null || title.isEmpty())?false:true;
    }
    public boolean hasImageHref()
    {
        return (imageHref == null || title.isEmpty())?false:true;
    }
}
