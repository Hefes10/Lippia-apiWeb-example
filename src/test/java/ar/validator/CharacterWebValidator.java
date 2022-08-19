package ar.validator;

import com.example.model.apiModel.character.CharacterResponse;
import com.example.services.apiServices.BaseService;
import com.crowdar.core.actions.WebActionManager;
import com.example.constants.CharacterConstants;
import com.example.report.CucumberReporter;
import com.crowdar.driver.DriverManager;

public class CharacterWebValidator extends BaseValidator {
    private CharacterResponse characterResponse;

    public void validate(String nombre, String id) {
        setResponses(id);

        CucumberReporter.addTestStepLog("****************************** VALIDACIONES ******************************");

        validateResponse(nombre);

        CucumberReporter.addTestStepLog("**************************** FIN VALIDACIONES ****************************");
        softAssert.assertAll();
        DriverManager.getDriverInstance().close();
    }

    private void setResponses(String id) {
        BaseService.PARAMS.get().put("id", id);
        characterResponse = (CharacterResponse) BaseService.callService("GET", "CHARACTER", "character/rq_consultaPorId").getResponse();
    }

    private void validateResponse(String nombre) {
        CharacterResponse response = characterResponse;

        WebActionManager.waitPresence(CharacterConstants.EPISODIOS);
        WebActionManager.waitVisibility(CharacterConstants.EPISODIOS);
        softAssert.assertTrue(isVisible(CharacterConstants.EPISODIOS));

        strEsperado = response.getName();
        strEncontrado = WebActionManager.getText(CharacterConstants.NOMBRE_PERSONAJE);
        check("El nombre del character");

        softAssert.assertEquals(nombre, response.getName(), "El nombre del personaje no es correcto");

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
