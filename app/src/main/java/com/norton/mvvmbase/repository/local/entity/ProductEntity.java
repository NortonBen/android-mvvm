package com.norton.mvvmbase.repository.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.norton.mvvmbase.model.Product;

/**
 * Created by norton on 11/04/2018.
 */
@Entity(tableName = "products")
public class ProductEntity implements Product {

    @PrimaryKey
    private int id;
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name =  "image",typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    @ColumnInfo(name = "price")
    private  int price;

    @ColumnInfo(name = "detail")
    private String detail;

    @ColumnInfo(name = "category_id")
    private int category_id;



    public ProductEntity(int id, String name, byte[] image, int price, String detail, int category_id) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.detail = detail;
        this.category_id = category_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public  String getPriceStr() {
        return String.valueOf(this.price) + " VND";
    }

    public void tranformWitTMP(String html, String tag) {
        this.detail = html.replace(tag, this.detail);
    }
}
