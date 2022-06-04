package ar.validator;

import com.example.model.apiModel.character.CharacterResponse;
import com.example.report.CucumberReporter;
import com.crowdar.api.rest.APIManager;

public class CharacterValidator extends BaseValidator {
    CommonValidator commonValidator = new CommonValidator();

    public void validate(String id) {
        setSoftAssert(new SoftAssert());

        CharacterResponse response = (CharacterResponse) APIManager.getLastResponse().getResponse();

        CucumberReporter.addTestStepLog("****************************** VALIDACIONES ******************************");

        validateResponse(response, id);

        CucumberReporter.addTestStepLog("**************************** FIN VALIDACIONES ****************************");

        if (!getSoftAssert().m_errors.isEmpty()) {
            getSoftAssert().assertAll();
        }
        getSoftAssert().isAssert();
    }

    private void validateResponse(CharacterResponse response, String id) {
        analyzeNotNull(response);

        strEsperado =  id;
        strEncontrado = String.valueOf(response.getId());
        checkAndReport("El id " + id + " del character");

        getSoftAssert().assertTrue(commonValidator.fechaFormatoYMDTHMS(response.getCreated()));
    }
}
