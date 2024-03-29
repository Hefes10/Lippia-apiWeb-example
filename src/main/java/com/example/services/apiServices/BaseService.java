package com.example.services.apiServices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.apiConfiguration.EntityConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.InvocationTargetException;
import com.example.report.CucumberReporter;
import com.crowdar.core.PropertyManager;
import com.crowdar.api.rest.APIManager;
import com.crowdar.api.rest.Response;
import com.crowdar.api.rest.Request;
import com.crowdar.core.JsonUtils;
import java.util.Map;

public class BaseService extends CustomMethodService {

    public static final ThreadLocal<Map<String, String>> PARAMS = new ThreadLocal<>();
    public static final ThreadLocal<Boolean> SCREENSHOT_DISABLE = new ThreadLocal<>();

    public static <T> Response get(String jsonRequest, Class<T> classModel) {
        Request request = getRequest(jsonRequest, setParams());
        request.getHeaders().put("Accept-Charset", "utf-8");
        showRequest(request);
        Response resp = get(request, classModel, getCustomRestClient());
        String entity = classModel.getName().substring(classModel.getName().lastIndexOf(".") + 1);
        showResponse(entity);
        return resp;
    }

    public static <T> Response post(String jsonRequest, Class<T> classModel) {
        Request request = getRequest(jsonRequest, setParams());
        request.getHeaders().put("Accept-Charset", "utf-8");
        showRequest(request);
        Response resp = post(request, classModel, getCustomRestClient());
        String entity = classModel.getName().substring(classModel.getName().lastIndexOf(".") + 1);
        showResponse(entity);
        return resp;
    }

    private static Map<String, String> setParams() {
        PARAMS.get().put("base.url", PropertyManager.getProperty("base.url"));
        return PARAMS.get();
    }

    private static void showRequest(Request request) {
        CucumberReporter.addTestStepLog("<---------------------------------------- REQUEST ---------------------------------------->");
        String strRequest =
                "<textarea readonly=\"\" class=\"code-block\">" +
                        "\nEndpoint: " + request.getUrl() + request.getEndpoint() +
                        "\nHeader: " + JsonUtils.serialize(request.getHeaders()) +
                        "\nBody: " + JsonUtils.serialize(request.getBody()).replace("\\", "") +
                        "</textarea>";
        CucumberReporter.addTestStepLog(strRequest);
    }

    public static void showResponse(String entity) {
        ObjectMapper mapper = new ObjectMapper();
        Response response = APIManager.getLastResponse();
        Object obj = response.getResponse();
        CucumberReporter.addTestStepLog("<----------------------------------------" + String.format("\nRESPONSE %s: ", entity) + "---------------------------------------->");
        String strResponse;
        try {
            strResponse = "<textarea readonly=\"\" class=\"code-block\">" +
                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj) +
                    "</textarea>";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            strResponse = "ERROR al mostrar la respuesta del servicio.";
        }
        CucumberReporter.addTestStepLog(strResponse);
        CucumberReporter.addTestStepLog(
                "\nStatusCode: " + response.getStatusCode() +
                        "\nMessage: " + response.getMessage());
    }

    public static Boolean getScreenshotDisable() {
        return SCREENSHOT_DISABLE.get();
    }

    public static void setScreenshotDisable(Boolean screenshotDisable) {
        SCREENSHOT_DISABLE.set(screenshotDisable);
    }

    public static Response callService(String method, String entity, String jsonName) {
        doRequest(method, entity, jsonName);
        return APIManager.getLastResponse();
    }

    public static void doRequest(String methodName, String entity, String jsonName) {
        Class<?> entityService = EntityConfiguration.valueOf(entity).getEntityService();
        String jsonPath = "request/".concat(jsonName);
        try {
            entityService.getMethod(methodName.toLowerCase(), String.class).invoke("", jsonPath);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
