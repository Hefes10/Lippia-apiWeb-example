package com.example.apiConfiguration;

import com.example.services.apiServices.*;

public enum EntityConfiguration {

    CHARACTER {
        @Override
        public Class<?> getEntityService() {
            return CharacterService.class;
        }
    },
    ERROR {
        @Override
        public Class<?> getEntityService() {
            return ErrorService.class;
        }
    };

    public abstract Class<?> getEntityService();
}
