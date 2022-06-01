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
     * Valida que la fecha tenga un formato de tipo yyyy-MM-dd
     * Ej: "2020-03-16"
     *
     * @param fecha debe tener el formato yyyy-MM-dd
     * @return boolean
     */
    public static boolean fechaFormatoYMD(String fecha) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.parse(fecha);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Valida que la fecha tenga un formato de tipo yyyy-MM-ddThh:mm:ss
     * Ej: "2020-03-16T00:00:00"
     *
     * @param fecha debe tener el formato yyyy-MM-ddThh:mm:ss
     * @return boolean
     */
    public static boolean fechaFormatoYMDTHMS(String fecha) {
        if (fecha != null) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
                formatter.parse(fecha);
                return true;
            } catch (ParseException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Valida que la fecha tenga un formato de tipo yyyy-MM-dd hh:mm:ss
     * Ej: "2020-03-16 00:00:00"
     *
     * @param fecha debe tener el formato yyyy-MM-dd hh:mm:ss
     * @return boolean
     */
    public static boolean fechaFormatoYMDHMS(String fecha) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            formatter.parse(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Valida que la fecha tenga un formato de tipo dd/MM/yyyy
     * Ej: "20/11/2021"
     *
     * @param fecha debe tener el formato dd/MM/yyyy
     * @return boolean
     */
    public static boolean fechaFormatoDMY(String fecha) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            formatter.parse(fecha);
            return true;
        } catch (ParseException e) {
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
    public static boolean isNumeric(String strNum) {
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

    public static Boolean validateOrdenAlfabetico(List<String> nombres) {
        //valido el orden alfabetico de una lista de nombres
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

    public static boolean validateURL(String url) {
        String[] schemes = {"http", "https"}; //DEFAULT schemes = "http", "https", "ftp"
        UrlValidator urlValidator = new UrlValidator(schemes);
        return urlValidator.isValid(url);
    }

    public static boolean doesURLExist(URL url) throws IOException {
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

    public static void validateBoton(String boton, boolean isEnabled, String name) {
        SOFTASSERT.get().assertTrue(WebActionManager.isPresent(boton), "El boton " + name + " no está presente.");
        SOFTASSERT.get().assertTrue(WebActionManager.isVisible(boton), "El boton " + name + " no es visible.");
        if (isEnabled) {
            SOFTASSERT.get().assertTrue(WebActionManager.isEnabled(boton), "El boton " + name + " no está habilitado.");
        } else {
            SOFTASSERT.get().assertTrue(!WebActionManager.isEnabled(boton), "El boton " + name + " no debe estar habilitado.");
        }
        strEsperado = name;
        strEncontrado = WebActionManager.getText(boton);
        check("El nombre del boton");
    }

    public static void validateData(String locator, String type) {
        WebActionManager.waitPresence(locator);
        SOFTASSERT.get().assertTrue(WebActionManager.isPresent(locator), "El locator " + locator + " no esta presente.");
        SOFTASSERT.get().assertTrue(WebActionManager.isVisible(locator), "El locator " + locator + " no es visible.");
        String text = WebActionManager.getText(locator);
        SOFTASSERT.get().assertNotEquals(text, "", "El texto del locator " + locator + " no debe ser vacio.");
        type = type.toLowerCase();

        switch (type) {
            case "string":
                SOFTASSERT.get().assertFalse(CommonValidator.isNumeric(text), "El locator " + locator + " no es tipo String.");
                break;
            case "numeric":
                SOFTASSERT.get().assertTrue(CommonValidator.isNumeric(text), "El locator " + locator + " no es tipo numerico.");
                break;
            case "date":
                SOFTASSERT.get().assertTrue(CommonValidator.fechaFormatoDMY(text), "El locator " + locator + " no es de tipo date.");
                break;
            default:
                SOFTASSERT.get().fail("Tipo no soportado");
        }
    }

    public static void validateErrorMessage(String message) {
        GenericErrorResponse errorResponse = (GenericErrorResponse) APIManager.getLastResponse().getResponse();
        Assert.assertEquals(errorResponse.getError(), message);
    }
}
