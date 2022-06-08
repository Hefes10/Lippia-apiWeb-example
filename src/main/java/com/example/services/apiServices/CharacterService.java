package com.example.services.apiServices;

import com.example.model.apiModel.character.CharacterResponse;
import com.crowdar.api.rest.Response;
import java.util.HashMap;
import java.util.Map;

public class CharacterService extends BaseService {

    public static Response get(String jsonName) {
        return get(jsonName, setParams(), CharacterResponse.class);
    }

    static Map<String, String> setParams() {
        Map<String, String> params = new HashMap<>();
        if (ID.get() != null) params.put("id", ID.get());
        return params;
    }
}
