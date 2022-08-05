package ar.validator;

import com.example.model.apiModel.error.GenericErrorResponse;
import org.apache.commons.validator.routines.UrlValidator;
import com.crowdar.core.actions.WebActionManager;
import com.crowdar.api.rest.APIManager;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.IOException;
import org.testng.Assert;
import java.util.List;
import java.net.URL;

public class CommonValidator extends BaseValidator {

    /**
     * Valida que una fecha tenga el formato pasado por parametro
     * Ejemplos de formato:
     * dd/MM/yyyy
     * yyyy-MM-dd
     * yyyy-MM-dd hh:mm:ss
     * yyyy-MM-dd'T'hh:mm:ss
     *
     * @param fecha de tipo String
     * @param formato debe tener un formato valido
     * @return boolean
     */
    public boolean validateFormatDate(String fecha, String formato) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(formato);
            formatter.parse(fecha);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Valida que un String sea numerico
     * Ej: "34446", "-200"
     *
     * @param strNum de tipo String
     * @return boolean
     */
    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Valida el orden alfabetico de una lista de nombres
     * Nota: Este metodo no tiene en cuenta el primer nombre de la lista
     *
     * @param nombres de tipo List<String>
     * @return boolean
     */
    public Boolean validateOrdenAlfabetico(List<String> nombres) {
        int aux;
        boolean flag = true;
        nombres.remove(0);
        if (nombres.size() > 1) {
            String var1 = nombres.get(0);
            aux = var1.compareTo(nombres.get(1));
            if (aux == 0) {
                flag = validateOrdenAlfabetico(nombres);
            } else if (aux < 0) {
                flag = validateOrdenAlfabetico(nombres);
            } else {
                flag = false;
            }
        }
        return flag;
    }

    public boolean validateURL(String url) {
        String[] schemes = {"http", "https"}; //DEFAULT schemes = "http", "https", "ftp"
        UrlValidator urlValidator = new UrlValidator(schemes);
        return urlValidator.isValid(url);
    }

    public boolean doesURLExist(URL url) throws IOException {
        // We want to check the current URL
        HttpURLConnection.setFollowRedirects(false);

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        // We don't need to get data
        httpURLConnection.setRequestMethod("HEAD");

        // Some websites don't like programmatic access so pretend to be a browser
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
        int responseCode = httpURLConnection.getResponseCode();

        // We only accept response code 200
        return responseCode == HttpURLConnection.HTTP_OK;
    }

    public void validateBoton(String boton, boolean isEnabled, String name) {
        softAssert.assertTrue(WebActionManager.isPresent(boton), "El boton " + name + " no está presente.");
        softAssert.assertTrue(WebActionManager.isVisible(boton), "El boton " + name + " no es visible.");
        if (isEnabled) {
            softAssert.assertTrue(WebActionManager.isEnabled(boton), "El boton " + name + " no está habilitado.");
        } else {
            softAssert.assertTrue(!WebActionManager.isEnabled(boton), "El boton " + name + " no debe estar habilitado.");
        }
        strEsperado = name;
        strEncontrado = WebActionManager.getText(boton);
        check("El nombre del boton", false);
    }

    public void validateData(String locator, String type) {
        WebActionManager.waitPresence(locator);
        softAssert.assertTrue(WebActionManager.isPresent(locator), "El locator " + locator + " no esta presente.");
        softAssert.assertTrue(WebActionManager.isVisible(locator), "El locator " + locator + " no es visible.");
        String text = WebActionManager.getText(locator);
        softAssert.assertNotEquals(text, "", "El texto del locator " + locator + " no debe ser vacio.");
        type = type.toLowerCase();

        switch (type) {
            case "string":
                softAssert.assertFalse(isNumeric(text), "El locator " + locator + " no es tipo String.");
                break;
            case "numeric":
                softAssert.assertTrue(isNumeric(text), "El locator " + locator + " no es tipo numerico.");
                break;
            case "date":
                softAssert.assertTrue(validateFormatDate(text, "dd/MM/yyyy"), "El locator " + locator + " no es de tipo date.");
                break;
            default:
                softAssert.fail("Tipo no soportado");
        }
    }

    public void validateErrorMessage(String message) {
        GenericErrorResponse errorResponse = (GenericErrorResponse) APIManager.getLastResponse().getResponse();
        Assert.assertEquals(errorResponse.getError(), message);
    }
}
