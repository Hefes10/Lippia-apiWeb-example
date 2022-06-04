package ar.validator;

import com.example.model.apiModel.character.CharacterResponse;
import com.example.services.apiServices.CharacterService;
import com.crowdar.core.actions.WebActionManager;
import com.example.constants.CharacterConstants;
import com.example.report.CucumberReporter;
import com.crowdar.driver.DriverManager;
import com.crowdar.api.rest.APIManager;

public class CharacterWebValidator extends BaseValidator {
    private CharacterResponse characterResponse;

    public void validate(String nombre, String id) {
        setSoftAssert(new SoftAssert());

        CallService(id);

        CucumberReporter.addTestStepLog("****************************** VALIDACIONES ******************************");

        validateResponse(characterResponse, nombre);

        CucumberReporter.addTestStepLog("**************************** FIN VALIDACIONES ****************************");

        if (!getSoftAssert().m_errors.isEmpty()) {
            getSoftAssert().assertAll();
        }
        getSoftAssert().isAssert();
        DriverManager.getDriverInstance().close();
    }

    private void CallService(String id) {
        CharacterService.ID.set(id);
        CharacterService.callService("GET", "CHARACTER", "character/rq_consultaPorId");
        characterResponse = (CharacterResponse) APIManager.getLastResponse().getResponse();
    }

    private void validateResponse(CharacterResponse response, String nombre) {
        WebActionManager.waitPresence(CharacterConstants.EPISODIOS);
        WebActionManager.waitVisibility(CharacterConstants.EPISODIOS);
        getSoftAssert().assertTrue(isVisible(CharacterConstants.EPISODIOS));

        strEsperado = response.getName();
        strEncontrado = WebActionManager.getText(CharacterConstants.NOMBRE_PERSONAJE);
        check("El nombre del character");

        getSoftAssert().assertEquals(nombre, response.getName(), "El nombre del personaje no es correcto");

        strEsperado = "Episodios: " + response.getEpisode().size();
        strEncontrado = WebActionManager.getText(CharacterConstants.EPISODIOS);
        check("La cantidad de episodios del character");

        strEsperado = "Status: " + response.getStatus();
        strEncontrado = WebActionManager.getText(CharacterConstants.STATUS);
        check("El status del character");

        strEsperado = "Species: " + response.getSpecies();
        strEncontrado = WebActionManager.getText(CharacterConstants.SPECIES);
        check("La especie del character");

        strEsperado = "Gender: " + response.getGender();
        strEncontrado = WebActionManager.getText(CharacterConstants.GENDER);
        check("El género del character");

        strEsperado = "Origin: " + response.getOrigin().getName();
        strEncontrado = WebActionManager.getText(CharacterConstants.ORIGIN);
        check("El origen del character");

        strEsperado = "Last location: " + response.getLocation().getName();
        strEncontrado = WebActionManager.getText(CharacterConstants.LAST_LOCATION);
        check("La última ubicación del character");

        addMsgToReport("Se validan los datos del character y");
    }
}
