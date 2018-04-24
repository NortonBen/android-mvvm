package com.norton.mvvmbase.repository.remote.data.LoginAPI;

import android.arch.persistence.room.ColumnInfo;

import com.norton.mvvmbase.model.User;

/**
 * Created by norton on 11/04/2018.
 */

public class UserResult {

    @ColumnInfo(name = "id")
    private final String id;

    @ColumnInfo(name = "full_name")
    private final String fullName;

    @ColumnInfo(name = "userName")
    private final String userName;

    @ColumnInfo(name = "email")
    private final String email;

    @ColumnInfo(name = "phoneNumber")
    private final String phoneNumber;

    public UserResult(User user, String id, String fullName, String userName, String email, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
