package ar.validator;

import com.example.model.apiModel.character.CharacterResponse;
import com.example.report.CucumberReporter;
import com.crowdar.api.rest.APIManager;

public class CharacterValidator extends BaseValidator {
    private final CommonValidator commonValidator = new CommonValidator();
    private CharacterResponse characterResponse;

    public void validate(String id) {

        CallService();

        CucumberReporter.addTestStepLog("****************************** VALIDACIONES ******************************");

        validateResponse(characterResponse, id);

        CucumberReporter.addTestStepLog("**************************** FIN VALIDACIONES ****************************");

        if (!softAssert.m_errors.isEmpty()) {
            softAssert.assertAll();
        }
        softAssert.isAssert();
    }

    private void CallService() {
        characterResponse = (CharacterResponse) APIManager.getLastResponse().getResponse();
    }

    private void validateResponse(CharacterResponse response, String id) {
        String[] except = {"type"};
        analyze(response, "not null, not void", true, except);

        strEsperado =  id;
        strEncontrado = String.valueOf(response.getId());
        check("El id " + id + " del character", true);

        softAssert.assertTrue(commonValidator.fechaFormatoYMDTHMS(response.getCreated()));
    }
}
