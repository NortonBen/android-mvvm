package com.norton.mvvmbase.repository.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

import com.norton.mvvmbase.model.Category;

/**
 * Created by norton on 11/04/2018.
 */

@Entity(tableName = "categories")
public class CategoryEntity implements Category {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name =  "image",typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public CategoryEntity(int id, String name, byte[] image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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
}
