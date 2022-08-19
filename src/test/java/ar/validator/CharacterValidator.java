package ar.validator;

import com.example.model.apiModel.character.CharacterResponse;
import com.example.report.CucumberReporter;
import com.crowdar.api.rest.APIManager;

public class CharacterValidator extends BaseValidator {
    private final CommonValidator commonValidator = new CommonValidator();
    private CharacterResponse characterResponse;

    public void validate(String id) {
        setResponses();

        CucumberReporter.addTestStepLog("****************************** VALIDACIONES ******************************");

        validateResponse(id);

        CucumberReporter.addTestStepLog("**************************** FIN VALIDACIONES ****************************");

        softAssert.assertAll();
    }

    private void setResponses() {
        characterResponse = (CharacterResponse) APIManager.getLastResponse().getResponse();
    }

    private void validateResponse(String id) {
        CharacterResponse response = characterResponse;
        String[] except = {"type"};
        analyze(response, "not null, not void", true, except);

        strEsperado =  id;
        strEncontrado = String.valueOf(response.getId());
        check("El id " + id + " del character", true);

        softAssert.assertTrue(commonValidator.validateFormatDate(response.getCreated(), "yyyy-MM-dd'T'hh:mm:ss"));
    }
}
