package com.example.calendertestapp.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SubmitFeedbackForm {

    String BASE_URL = "https://docs.google.com/forms/d/e/";


    @POST("1FAIpQLScr3o1k4rRD_TuuhUrzmV9jXW_IRA45WL96wxAs557P0x1oNQ/formResponse")
    @FormUrlEncoded
    Call<Void> sendFeedback(@Field("entry.64080122") String feedback);
}
