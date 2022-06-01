package ar.validator;

import com.crowdar.api.rest.APIManager;
import com.example.model.apiModel.CharacterResponse;
import com.example.report.CucumberReporter;

public class CharacterValidator extends BaseValidator {

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

        getSoftAssert().assertTrue(CommonValidator.fechaFormatoYMDTHMS(response.getCreated()));
    }
}
