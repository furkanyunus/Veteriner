package com.example.veterner.RestApi;

public class BaseManger {
    protected RestApi getRestApi()
    {
        RestApiClient restApiClient = new RestApiClient(BaseUrl.URL);
        return restApiClient.getRestApi();
    }
}