package com.example.services.apiServices;

import com.example.model.apiModel.CharacterResponse;
import com.crowdar.api.rest.Response;

public class CharacterService extends BaseService {

    public static Response get(String jsonName) {
        return get(jsonName, setParams(), CharacterResponse.class);
    }
}
