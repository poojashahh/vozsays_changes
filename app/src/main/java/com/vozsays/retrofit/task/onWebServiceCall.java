package com.vozsays.retrofit.task;

import retrofit2.Response;

public interface onWebServiceCall<T>
{
    public void onResponseComplete(int responsecode, int statuscode, Response response);
    public void onErrorComplete(int responsecode, int statuscode, String ErrorListener);
}
