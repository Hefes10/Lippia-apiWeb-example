package com.example.services.apiServices;

import com.example.model.apiModel.CharacterResponse;
import com.crowdar.api.rest.Response;

import java.util.HashMap;
import java.util.Map;

public class CharacterService extends BaseService {

    public static Response get(String jsonName) {
        return get(jsonName, setParams(), CharacterResponse.class);
    }

    private static Map<String, String> setParams() {
        return new HashMap<>();
    }
}
