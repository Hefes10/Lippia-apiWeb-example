package com.example.services.apiServices;

import com.example.model.apiModel.CharacterResponse;
import com.crowdar.api.rest.Response;
import java.util.HashMap;
import java.util.Map;

public class CharacterService extends BaseService {
    public static final ThreadLocal<String> ID = new ThreadLocal<>();

    public static Response get(String jsonName) {
        return get(jsonName, setParams(), CharacterResponse.class);
    }

    private static Map<String, String> setParams() {
        Map<String, String> params = new HashMap<>();
        params.put("id", ID.get());
        return params;
    }
}
