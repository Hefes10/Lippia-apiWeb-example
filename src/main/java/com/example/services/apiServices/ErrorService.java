package com.example.services.apiServices;

import com.example.model.apiModel.error.GenericErrorResponse;
import com.crowdar.api.rest.Response;
import java.util.HashMap;
import java.util.Map;

public class ErrorService extends BaseService{
    public static Response get(String jsonRequest) {
        return get(jsonRequest, setParams(), GenericErrorResponse.class);
    }

    public static Response post(String jsonRequest) {
        return post(jsonRequest, setParams(), GenericErrorResponse.class);
    }

    static Map<String, String> setParams() {
        Map<String, String> params = new HashMap<>();
        if (ID.get() != null) params.put("id", ID.get());
        return params;
    }
}
