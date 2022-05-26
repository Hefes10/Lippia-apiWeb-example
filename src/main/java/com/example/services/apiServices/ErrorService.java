package com.example.services.apiServices;

import com.example.model.apiModel.error.GenericErrorResponse;
import com.crowdar.api.rest.Response;

import java.util.Map;

public class ErrorService extends BaseService{
    public static final ThreadLocal<Map<String, String>> PARAMS = new ThreadLocal<>();

    public static Response get(String jsonRequest) {
        return get(jsonRequest, PARAMS.get(), GenericErrorResponse.class);
    }

    public static Response post(String jsonRequest) {
        return post(jsonRequest, PARAMS.get(), GenericErrorResponse.class);
    }

}
