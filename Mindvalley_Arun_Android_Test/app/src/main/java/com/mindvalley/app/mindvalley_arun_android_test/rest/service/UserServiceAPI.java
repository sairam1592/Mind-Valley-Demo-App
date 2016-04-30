package com.mindvalley.app.mindvalley_arun_android_test.rest.service;

import com.mindvalley.app.mindvalley_arun_android_test.rest.model.UserModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by arun on 4/28/2016.
 */

public interface UserServiceAPI {

    /*Retrofit get annotation with our URL
    */
    @GET("/photos/?client_id=e061191080d96ca7f9af2a90e9db4944a2721d8361aa838f4d703c7456991eae")
    public void getUserDetails(Callback<List<UserModel>> response);
}

