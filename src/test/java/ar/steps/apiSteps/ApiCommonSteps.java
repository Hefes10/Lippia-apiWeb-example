package ar.steps.apiSteps;

import com.example.services.apiServices.BaseService;
import ar.validator.CommonValidator;
import com.crowdar.core.PageSteps;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;

public class ApiCommonSteps extends PageSteps {

    @When("realizo una peticion '(.*)' al endpoint de la entidad '(.*)' con el jsonName '(.*)'")
    public void doRequest(String methodName, String entity, String jsonName) {
        BaseService baseService = new BaseService();
        baseService.callService(methodName, entity, jsonName);
    }

    @And("deshabilito la opcion de screenshot")
    public void deshabilitoLaOpcionDeScreenshot() {
        BaseService.setScreenshotDisable(true);
    }

    @And("se obtuvo el mensaje de error '(.*)'")
    public void seObtuvoElMensajeDeError(String message) {
        CommonValidator.validateErrorMessage(message);
    }
}
