package ar.steps.webSteps;

import com.example.services.webServices.WebCommonService;
import com.example.services.apiServices.BaseService;
import com.crowdar.core.PageSteps;
import io.cucumber.java.en.Given;

public class WebCommonSteps extends PageSteps {

    @Given("que me encuentro en la pantalla de home")
    public void queMeEncuentroEnLaPantallaDeHome() {
        BaseService.setScreenshotDisable(false); //habilito la opcion de screenshot
        WebCommonService.goToLogin();
    }
}
