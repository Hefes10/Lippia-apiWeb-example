package ar.steps.apiSteps;

import com.example.services.apiServices.BaseService;
import com.crowdar.core.PageSteps;
import io.cucumber.java.en.Given;

public class CharacterSteps extends PageSteps {

    @Given("el id '(.*)' de un character")
    public void elIdIdDeUnCharacter(String id) {
        BaseService.ID.set(id);
    }
}
