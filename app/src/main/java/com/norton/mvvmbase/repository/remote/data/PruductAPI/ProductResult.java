package com.norton.mvvmbase.repository.remote.data.PruductAPI;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;
import com.norton.mvvmbase.Constants;
import com.norton.mvvmbase.repository.local.entity.ProductEntity;
import com.norton.mvvmbase.utils.FileUtils;

/**
 * Created by norton on 11/04/2018.
 */

public class ProductResult {
    @SerializedName("id")
    private final int id;

    @SerializedName("name")
    private final String name;

    @SerializedName("image")
    private final String image;

    @SerializedName("price")
    private final int price;

    @SerializedName("detail")
    private final String detail;

    @SerializedName("category_id")
    private final int category_id;

    private byte[] imageByte;

    public ProductResult(int id, String name, String image, int price, String detail, int category_id) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.detail = detail;
        this.category_id = category_id;
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

    public int getPrice() {
        return price;
    }

    public String getDetail() {
        return detail;
    }


    public ProductResult loadImage() {
        imageByte = FileUtils.getByteFromURL(Constants.SERVICE+"/file/image/"+this.image);
        return this;
    }

    public byte[] getImageByte() {
        return imageByte;
    }

    public ProductEntity toProductEntity() {
        return  new ProductEntity(id, name, imageByte, price, detail, category_id);
    }
}
