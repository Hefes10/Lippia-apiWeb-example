package com.example.services.apiServices;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import com.crowdar.api.rest.MethodsService;
import org.springframework.http.MediaType;
import java.nio.charset.StandardCharsets;
import com.crowdar.api.rest.RestClient;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class CustomMethodService extends MethodsService {
    public static RestClient getCustomRestClient() {
        RestTemplate restTemplate =new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        List<HttpMessageConverter<?>> messageConverters =  new ArrayList<>();
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        stringHttpMessageConverter.setWriteAcceptCharset(false);
        messageConverters.add(stringHttpMessageConverter);
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        messageConverters.add(mappingJackson2HttpMessageConverter);
        restTemplate.setMessageConverters(messageConverters);
        return RestClient.getRestClient(restTemplate);
    }
}
