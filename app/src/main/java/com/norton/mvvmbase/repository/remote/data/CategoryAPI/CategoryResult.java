package com.norton.mvvmbase.repository.remote.data.CategoryAPI;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;
import com.norton.mvvmbase.Constants;
import com.norton.mvvmbase.repository.local.entity.CategoryEntity;
import com.norton.mvvmbase.utils.FileUtils;

/**
 * Created by norton on 11/04/2018.
 */

public class CategoryResult {

    @SerializedName("id")
    private final int id;

    @SerializedName("name")
    private final String name;

    @SerializedName("image")
    private final String image;

    private byte[] imageByte;

    public CategoryResult(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public CategoryResult loadImage() {
        imageByte = FileUtils.getByteFromURL(Constants.SERVICE+"/file/image/"+this.image);
        return this;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }


    public byte[] getImageByte() {
        return imageByte;
    }

    public CategoryEntity toCategoryEntity() {
        return  new CategoryEntity(id, name, imageByte);
    }
}
