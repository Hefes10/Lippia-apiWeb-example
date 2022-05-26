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
     * Transforma una fecha en formato String del tipo YYYY-MM-DD
     * A un tipo Integer
     * Ej: "2020-03-16" a 20200316
     *
     * @param fecha debe tener el formato "YYYY-MM-DD"
     * @return YYYYMMDD en formato int
     */
    public static int convertFechaStringToInt(String fecha) {
        return Integer.parseInt(fecha.replace("-", ""));
    }

    /**
     * Transforma una fecha en formato String del tipo yyyy-MM-dd
     * A un tipo dd/MM/yyyy
     * Ej: "2019-06-27" a 27/06/2019
     *
     * @param oldDate debe tener el formato "YYYY-MM-DD"
     * @return dd/MM/yyyy de tipo String
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
     * Transforma una fecha en formato String del tipo yyyy-MM-dd
     * A un tipo dd/MM
     * Ej: "2019-06-27" a 27/06
     *
     * @param oldDate debe tener el formato "YYYY-MM-DD"
     * @return dd/MM/yyyy de tipo String
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
     * Extrae los numeros de una cadena del tipo String y los retorna
     * Ej: "El total es $ 870.75" a "87075"
     *
     * @param input del tipo String
     * @return un String de numeros
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
     * Transforma numeros de una cadena del tipo String y los retorna
     * Ej: "$ 805,32" a "805.35"
     * "u$s 805,32" a "805.35"
     *
     * @param valor del tipo String
     * @return un double
     */
    public static double strToDouble(String valor) {
        double varDou = 0;
        try {
            valor = valor.replace("%", "");
            valor = valor.replace("+", "");
            valor = valor.replace("u$s", "");
            valor = valor.replace("U$S", "");
            valor = valor.replace("$", "");
            valor = valor.replace(" ", "");
            valor = valor.replace(".", "");
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
     * Recibe un double y devuelve un string con dos decimales
     *
     * @param valor de tipo double
     * @return string con dos decimales
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

    public static void limpiarCampoDeTexto(String locator) {
        WebActionManager.click(locator);
        WebActionManager.getElement(locator).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        WebActionManager.getElement(locator).sendKeys(Keys.chord(Keys.DELETE));
    }

    public static void pressEscape(String locator) {
        WebActionManager.getElement(locator).sendKeys(Keys.chord(Keys.ESCAPE));
    }

    /**
     * Formatea el texto de las descripciones segun formato establecido por el negocio
     * (Calitalize)
     *
     * @param input de tipo String
     * @return descripcion en formato Capitalize
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
     * Reemplaza dos o más espacios por un solo espacio en una cadena de texto
     *
     * @param texto de tipo string
     * @return texto sin exceso de espacios
     */
    public static String quitarEspaciosExtra(String texto) {
        texto = texto.trim();
        texto = texto.replaceAll("\\s{2,}", " ");
        return texto;
    }
}
