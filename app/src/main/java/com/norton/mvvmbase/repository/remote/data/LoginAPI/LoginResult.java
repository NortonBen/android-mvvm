package com.norton.mvvmbase.repository.remote.data.LoginAPI;

import com.google.gson.annotations.SerializedName;
import com.norton.mvvmbase.repository.remote.data.Result;

/**
 * Created by norton on 08/04/2018.
 */

public class LoginResult {

    @SerializedName("auth_token")
    public final String token;

    @SerializedName("id")
    public final String id;

    @SerializedName("expires_in")
    public final int expires_in;



    public LoginResult(String token, String id, int expires_in) {
        this.token = token;
        this.id = id;
        this.expires_in = expires_in;
    }
}
