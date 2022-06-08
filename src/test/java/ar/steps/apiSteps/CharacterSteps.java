package ar.steps.apiSteps;

import com.example.services.apiServices.CharacterService;
import ar.validator.CharacterValidator;
import com.crowdar.core.PageSteps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.And;

public class CharacterSteps extends PageSteps {

    @Given("el id '(.*)' de un character")
    public void elIdIdDeUnCharacter(String id) {
        CharacterService.ID.set(id);
    }

    @And("la respuesta es la esperada para la consulta de character por id '(.*)'")
    public void laRespuestaEsLaEsperadaParaLaConsultaDeCharacterPorIdId(String id) {
        CharacterValidator characterValidator = new CharacterValidator();
        characterValidator.validate(id);
    }
}
