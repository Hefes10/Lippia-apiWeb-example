package ar.validator;

import com.crowdar.core.actions.WebActionManager;
import com.example.report.CucumberReporter;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import org.openqa.selenium.Keys;
import java.text.DecimalFormat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Locale;
import java.util.Date;

public class Utilities {

    /**
     * It takes a string in the format of "YYYY-MM-DD" and returns an integer in the format of "YYYYMMDD"
     * Toma una cadena en el formato "YYYY-MM-DD" y devuelve un número entero en el formato "YYYYMMDD"
     *
     * @param fecha The date in the format yyyy-MM-dd
     * @return The date in the format of YYYYMMDD
     */
    public static int convertFechaStringToInt(String fecha) {
        return Integer.parseInt(fecha.replace("-", ""));
    }

    /**
     * It takes a date in the format "yyyy-MM-dd" and returns it in the format "dd/MM/yyyy"
     * Transforma una fecha en formato String del tipo yyyy-MM-dd
     * a un tipo dd/MM/yyyy
     *
     * @param oldDate the date you want to format
     * @return A string
     */
    public static String formatDate(String oldDate) {
        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        try {
            date = parseador.parse(oldDate);
            return formateador.format(date);
        } catch (ParseException e) {
            CucumberReporter.addTestStepLog("---ERROR: error en Utilities.formatDate()");
            e.printStackTrace();
        }
        return "";
    }

    /**
     * It takes a date in the format "dd/MM/yyyy" and returns a date in the format "dd/MM"
     * Transforma una fecha en formato String del tipo yyyy-MM-dd
     * a un tipo dd/MM
     * Ej: "2019-06-27" a 27/06
     *
     * @param oldDate the date you want to format
     * @return The substring of the date.
     */
    public static String formatDateDDMM(String oldDate) {
        try {
            return formatDate(oldDate).substring(0,5);
        } catch (Exception e) {
            CucumberReporter.addTestStepLog("---ERROR: error en Utilities.formatDateDDMM()");
            e.printStackTrace();
        }
        return "";
    }

    /**
     * It takes a date in the format "yyyy-MM-dd" and returns a date in the format "dd MMM"
     *
     * @param fechaYMD the date in the format yyyy-MM-dd
     * @return A String with the date in the format DD MMM
     */
    public static String formatDateDDMmm(String fechaYMD) {
        String fechaDM = null;
        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
        Locale spanish = new Locale ( "es" , "ES" );
        SimpleDateFormat formateador = new SimpleDateFormat("dd MMM", spanish);
        Date date = null;
        try {
            date = parseador.parse(fechaYMD);
            fechaDM = formateador.format(date);
            fechaDM = fechaDM.replaceFirst(fechaDM.substring(3, 4), fechaDM.substring(3, 4).toUpperCase());
            fechaDM = fechaDM.substring(0,6);
        } catch (ParseException e) {
            CucumberReporter.addTestStepLog("---ERROR: error en Utilities.formatDateDDMmm()");
            CucumberReporter.addTestStepLog("date: " + date);
            CucumberReporter.addTestStepLog("fechaDM: " + fechaDM);
            e.printStackTrace();
        }
        return fechaDM;
    }

    /**
     * It takes a string and returns a string with only the digits
     * Extrae los numeros de una cadena del tipo String y los retorna
     * Ej: "El total es $ 870.75" a "87075"
     *
     * @param input The string to be converted to a double
     */
    public static String getOnlyDigits(String input) {
        String result = "";
        try {
            Pattern pattern = Pattern.compile("[^0-9]");
            Matcher matcher = pattern.matcher(input);
            result = matcher.replaceAll("");
        } catch (Exception e) {
            CucumberReporter.addTestStepLog("---ERROR: error en Utilities.strToDouble()");
            CucumberReporter.addTestStepLog("input: " + input);
            CucumberReporter.addTestStepLog("result: " + result);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * It takes a string, removes all non-numeric characters, replaces commas with periods, and returns a double
     * Toma una cadena, elimina todos los caracteres no numéricos, sustituye las comas por puntos y devuelve un doble
     * Ej: "$ 805,32" a "805.35", "u$s 805,32" a "805.35"
     *
     * @param valor The value to be converted to a double.
     * @return A double value
     */
    public static double strToDouble(String valor) {
        double varDou = 0;
        try {
            Pattern pattern = Pattern.compile("([^0-9',])");
            Matcher matcher = pattern.matcher(valor);
            valor = matcher.replaceAll("");
            valor = valor.replace(",", ".");
            varDou = Double.parseDouble(valor);
        } catch (Exception e) {
            CucumberReporter.addTestStepLog("---ERROR: error en Utilities.strToDouble()");
            CucumberReporter.addTestStepLog("valor: " + valor);
            CucumberReporter.addTestStepLog("varDou: " + varDou);
            e.printStackTrace();
        }
        return varDou;
    }

    /**
     * It takes a double value and returns a string with two decimal places
     * Recibe un double y devuelve un string con dos decimales
     *
     * @param valor The value to be formatted.
     * @return A string with the value of the double with two decimals.
     */
    public static String getTwoDecimals(Double valor) {
        String varStr = "";
        try {
            DecimalFormat df = new DecimalFormat("###,###,##0.00", DecimalFormatSymbols.getInstance(Locale.GERMANY));
            varStr = df.format(valor);
        } catch (Exception e) {
            CucumberReporter.addTestStepLog("---ERROR: error en Utilities.getTwoDecimals()");
            CucumberReporter.addTestStepLog("valor: " + valor);
            CucumberReporter.addTestStepLog("varStr: " + varStr);
            e.printStackTrace();
        }
        return varStr;
    }

    /**
     * If the decimal part of the number is longer than the number of decimal places you want, truncate it to the desired
     * length. Otherwise, if the decimal part is only one digit long, return the number as is
     * Si la parte decimal del número es más larga que el número de decimales que desea, trúnquela a la longitud deseada.
     * En caso contrario, si la parte decimal sólo tiene un dígito, devuelve el número tal cual
     *
     * @param cuota The number you want to truncate.
     * @param dec The number of decimal places you want to keep.
     * @return A double value.
     */
    public static double truncateDecimal(float cuota, int dec) {
        String value = String.valueOf(cuota);
        String result = value;
        int decimalIndex = result.indexOf(".");
        if (decimalIndex != -1) {
            String decimalString = result.substring(decimalIndex + 1);
            if (decimalString.length() > dec) {
                result = value.substring(0, decimalIndex + dec + 1); // n + 1
            } else if (decimalString.length() == 1) {
                result = value;
            }
        }
        return Double.parseDouble(result);
    }

    /**
     * It clicks on the element, selects all the text, and then deletes it
     * Hace clic en el elemento, selecciona tod0 el texto y lo borra
     *
     * @param locator The locator of the element you want to clear.
     */
    public static void limpiarCampoDeTexto(String locator) {
        WebActionManager.click(locator);
        WebActionManager.getElement(locator).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        WebActionManager.getElement(locator).sendKeys(Keys.chord(Keys.DELETE));
    }

    /**
     * Press the escape key on the element identified by the locator.
     * Pulse la tecla de escape en el elemento identificado por el localizador.
     *
     * @param locator The locator of the element you want to press escape on.
     */
    public static void pressEscape(String locator) {
        WebActionManager.getElement(locator).sendKeys(Keys.chord(Keys.ESCAPE));
    }

    /**
     * It takes a string, makes it lowercase, then capitalizes the first letter
     * Toma una cadena, la pone en minúsculas y luego pone en mayúsculas la primera letra
     *
     * @param input the string to be formatted
     * @return The first letter of the input string is being capitalized.
     */
    public static String formatDescription(String input) {
        String output = "";
        try {
            output = input.toLowerCase();
            output = output.replaceFirst(output.substring(0, 1), output.substring(0, 1).toUpperCase());
        } catch (Exception e){
            CucumberReporter.addTestStepLog("---ERROR: error en Utilities.formatDescription()");
            CucumberReporter.addTestStepLog("input: " + input);
            CucumberReporter.addTestStepLog("result: " + output);
            e.printStackTrace();
        }
        return output;
    }

    /**
     * It removes all the extra spaces from a string
     * Elimina todos los espacios extra de una cadena
     *
     * @param texto The text to be processed.
     * @return The method is returning the texto variable.
     */
    public static String quitarEspaciosExtra(String texto) {
        texto = texto.trim();
        texto = texto.replaceAll("\\s{2,}", " ");
        return texto;
    }
}
