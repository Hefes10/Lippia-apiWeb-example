package com.example.services.apiServices;

import com.example.model.apiModel.error.GenericErrorResponse;
import com.crowdar.api.rest.Response;

public class ErrorService extends BaseService{
    public static Response get(String jsonRequest) {
        return get(jsonRequest, GenericErrorResponse.class);
    }

    public static Response post(String jsonRequest) {
        return post(jsonRequest, GenericErrorResponse.class);
    }

}
