package com.norton.mvvmbase.repository.remote.data.LoginAPI;

/**
 * Created by norton on 08/04/2018.
 */

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("UserName")
    public final String UserName;

    @SerializedName("Password")
    public final  String Password;

    @SerializedName("remember")
    public final boolean remember;


    public Login(String UserName, String password, boolean remember) {
        this.UserName = UserName;
        this.Password = password;
        this.remember = remember;
    }

}
