package ar.steps.webSteps;

import com.crowdar.core.actions.WebActionManager;
import com.example.constants.HomeConstants;
import ar.validator.CharacterWebValidator;
import com.crowdar.core.PageSteps;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CharacterSteps extends PageSteps {

    @When("hago click en en el personaje (.*)")
    public void hagoClickEnEnElPersonajePersonaje(String nombre) {
        WebActionManager.waitVisibility(HomeConstants.NOMBRE_PERSONAJE.replace("$nombre", nombre));
        WebActionManager.click(HomeConstants.NOMBRE_PERSONAJE.replace("$nombre", nombre));
    }

    @Then("aparece la informacion del personaje (.*) (.*)")
    public void apareceLaInformacionDelPersonajePersonaje(String nombre, String id) {
        CharacterWebValidator characterWebValidator = new CharacterWebValidator();
        characterWebValidator.validate(nombre, id);
    }
}
