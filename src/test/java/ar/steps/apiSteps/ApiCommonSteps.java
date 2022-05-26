package ar.steps.apiSteps;

import com.example.apiConfiguration.EntityConfiguration;
import com.example.services.apiServices.BaseService;
import java.lang.reflect.InvocationTargetException;
import com.crowdar.core.PageSteps;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;

public class ApiCommonSteps extends PageSteps {

    @When("se realiza una peticion '(.*)' erronea al endpoint '(.*)' con el jsonName '(.*)'")
    public void seRealizaUnaPeticionErroneaAlEndpointConElJsonName(String operation, String entity, String jsonName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        EntityConfiguration.valueOf(entity).getEntityService().getMethod("setErrorParams").invoke("");
        String jsonPath = "request/".concat(jsonName);
        EntityConfiguration.valueOf("ERROR").getEntityService().getMethod(operation.toLowerCase(), String.class).invoke("", jsonPath);
    }

    @When("realizo una peticion '(.*)' al endpoint de la entidad '(.*)' con el jsonName '(.*)'")
    public void doRequest(String methodName, String entity, String jsonName) {
        BaseService.callService(methodName, entity, jsonName);
    }

    @And("deshabilito la opcion de screenshot")
    public void deshabilitoLaOpcionDeScreenshot() {
        BaseService.setScreenshotDisable(true);
    }
}
