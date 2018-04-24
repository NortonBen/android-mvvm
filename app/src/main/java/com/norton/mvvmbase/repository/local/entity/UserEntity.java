package com.norton.mvvmbase.repository.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.graphics.Bitmap;

import com.norton.mvvmbase.model.User;
import com.norton.mvvmbase.utils.DateConverter;

import java.util.Date;

/**
 * Created by norton on 27/03/2018.
 */

@Entity(tableName = "users")
public class UserEntity {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "full_name")
    private String fullName;

    @ColumnInfo(name = "usernamr")
    private String usernamr;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "phoneNumber")
    private String phoneNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsernamr() {
        return usernamr;
    }

    public void setUsernamr(String usernamr) {
        this.usernamr = usernamr;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}