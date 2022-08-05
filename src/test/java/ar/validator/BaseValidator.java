package ar.validator;

import com.crowdar.core.actions.WebActionManager;
import org.springframework.util.ReflectionUtils;
import com.example.report.CucumberReporter;
import java.lang.reflect.Field;
import org.testng.Assert;
import java.util.Arrays;
import java.util.List;

public class BaseValidator {
    public String strEsperado = "", strEncontrado = "";
    public double douEsperado, douEncontrado;
    public boolean boolEsperado, boolEncontrado;

    protected SoftAssert softAssert = new SoftAssert();

    /**
     * Analyze the object and its fields, and if the message flag is true, add a message to the object.
     * Analiza el objeto y sus campos, y si el indicador de mensaje es verdadero, añade un mensaje al reporte.
     *
     * @param obj The object to be analyzed.
     * @param condition The condition to be checked.
     * @param message if true, the message will be added to the list of messages.
     */
    public void analyze(Object obj, String condition, Boolean message) {
        doWithFields(obj, condition);
        if (message) addMsg(obj);
    }

    /**
     * This function will analyze the fields of the object passed in, and if the condition is met, it will add a message
     * to the object
     * Esta función analizará los campos del objeto pasado, y si la condición se cumple, añadirá un mensaje al reporte
     *
     * @param obj The object to be analyzed.
     * @param condition The condition to be checked.
     * @param message if true, the message will be added to the list of messages.
     * @param except an array of field names that you want to exclude from the analysis.
     */
    public void analyze(Object obj, String condition, Boolean message, String[] except) {
        doWithFields(obj, condition, except);
        if (message) addMsg(obj);
    }

    /**
     * This function will analyze the fields of the object passed in, and if the condition is met, it will add a message
     * to the object
     * Esta función analizará los campos del objeto pasado, y si la condición se cumple, añadirá un mensaje al reporte
     *
     * @param obj The object to be analyzed.
     * @param condition The condition to be checked.
     * @param message if true, the message will be added to the list of messages.
     * @param except A list of fields that you don't want to be analyzed.
     */
    public void analyze(Object obj, String condition, Boolean message, List<String> except) {
        doWithFields(obj, condition, except);
        if (message) addMsg(obj);
    }

    /**
     * It takes an object and a condition as parameters, and then it iterates over all the fields of the object, and for
     * each field it calls the switchCondition function
     * Toma un objeto y una condición como parámetros, y luego itera sobre todos los campos del objeto,
     * y para cada campo llama a la función switchCondition
     *
     * @param obj the object to be processed
     * @param condition The condition to be checked.
     */
    private void doWithFields(Object obj, String condition) {
        String finalCondition = condition.toUpperCase().replaceAll("\\s+","");
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            switchCondition(field, obj, finalCondition);
        });
    }

    /**
     * It takes an object, a condition, and a list of fields to ignore, and then it iterates through all the fields of the
     * object, and if the field is not in the list of fields to ignore, it calls the switchCondition function
     * Toma un objeto, una condición y una lista de campos a ignorar, y luego itera a través de todos los campos del objeto,
     * y si el campo no está en la lista de campos a ignorar, llama a la función switchCondition
     *
     * @param obj The object to be processed
     * @param condition the condition to be applied to the field
     * @param except A list of fields that you don't want to be affected by the condition.
     */
    private void doWithFields(Object obj, String condition, List<String> except) {
        String finalCondition = condition.toUpperCase().replaceAll("\\s+","");
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            if (!except.contains(field.getName())) {
                switchCondition(field, obj, finalCondition);
            }
        });
    }

    /**
     * It takes an object, a condition, and an array of fields to ignore, and then it loops through all the fields of the
     * object, and if the field is not in the array of fields to ignore, it calls the switchCondition function
     * Toma un objeto, una condición y una matriz de campos a ignorar, y luego recorre todos los campos del objeto objeto,
     * y si el campo no está en la matriz de campos a ignorar, llama a la función switchCondition
     *
     * @param obj The object to be processed.
     * @param condition the condition to be applied to the fields
     * @param except An array of strings that represent the fields that you want to exclude from the conversion.
     */
    private void doWithFields(Object obj, String condition, String[] except) {
        String finalCondition = condition.toUpperCase().replaceAll("\\s+","");
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            if (Arrays.stream(except).noneMatch(str -> str.equals(field.getName()))) {
                switchCondition(field, obj, finalCondition);
            }
        });
    }

    /**
     * It takes a field, an object, and a condition, and then it checks if the field is null or empty, and if it is, it
     * throws an error
     * Toma un campo, un objeto y una condición, y luego comprueba si el campo es nulo o está vacío, y si lo es,
     * arroja un error
     *
     * @param field The field to be validated.
     * @param obj The object to be validated.
     * @param finalCondition The condition that will be used to validate the field.
     */
    private void switchCondition(Field field, Object obj, String finalCondition) throws IllegalAccessException {
        switch (finalCondition) {
            case "NOTNULL":
                softAssert.assertNotNull(field.get(obj), "El campo " + field.getName() + " es null.");
                break;
            case "NOTVOID":
                softAssert.assertNotEquals("", field.get(obj), "El campo " + field.getName() + " esta vacio.");
                break;
            case "NOTNULL,NOTVOID":
            case "NOTVOID,NOTNULL":
                softAssert.assertNotNull(field.get(obj), "El campo " + field.getName() + " es null.");
                softAssert.assertNotEquals("", field.get(obj), "El campo " + field.getName() + " esta vacio.");
                break;
            default:
                Assert.fail("Condition not found in BaseValidator.doWithFields()");
        }
    }

    /**
     * It takes an object as a parameter, gets the class name of the object, removes the package name, and adds a message
     * to the report
     * Toma un objeto como parámetro, obtiene el nombre de la clase del objeto, elimina el nombre del paquete y
     * añade un mensaje al reporte
     *
     * @param obj The object to be validated.
     */
    private void addMsg(Object obj) {
        String var = obj.getClass().getTypeName().replace(".", "/");
        var = var.substring(var.lastIndexOf("/") + 1);
        addMsgToReport("Se valida que los campos de " + var + " sean distintos de null, y ");
    }

    /**
     * It compares the values of the variables that were set in the previous step, and if they are different, it adds a
     * message to the report
     * Compara los valores de las variables que se establecieron en el paso anterior, y si son diferentes,
     * añade un mensaje en el reporte
     *
     * @param msg The message to be displayed in the report.
     * @param message The message to be displayed in the report.
     */
    public void check(String msg, Boolean message) {
        msg = Utilities.formatDescription(msg);
        softAssert.assertEquals(strEncontrado, strEsperado, msg + " no es correcto.");
        softAssert.assertEquals(douEncontrado, douEsperado, msg + " no es correcto.");
        softAssert.assertEquals(boolEncontrado, boolEsperado, msg + " no es correcto.");

        if (message) addMsgToReport(msg);

        clearAuxVar();
    }

    /**
     * If the list of errors is empty, then the test step is correct, otherwise, the test step is incorrect
     * Si la lista de errores está vacía, agrega un mensaje correcto al reporte, de lo contrario, agrega un mensaje incorrecto
     *
     * @param msg The message to be displayed in the report.
     */
    public void addMsgToReport(String msg) {
        if (softAssert.m_errors_aux.isEmpty()) {
            CucumberReporter.addTestStepLog(msg + " es correcto.");
        } else {
            CucumberReporter.addTestStepLog("---ERROR: " + msg + " NO es correcto.");
            softAssert.clearAuxErrors();
        }
    }

    /**
     * It checks if the string found contains the string expected
     * Comprueba si la cadena encontrada contiene la cadena esperada
     *
     * @param msg The message to be displayed in the report if the assertion fails.
     */
    public void checkContains(String msg) {
        msg = Utilities.formatDescription(msg);
        softAssert.assertTrue(strEsperado.contains(strEncontrado), msg);

        clearAuxVar();
    }

    /**
     * This function clears the variables that are used to store the values that are expected and the values that are found
     * Esta función borra las variables que se utilizan para almacenar los valores que se esperan y los valores que se
     * encuentran
     */
    private void clearAuxVar() {
        strEncontrado = "";
        strEsperado = "";
        douEncontrado = 0;
        douEsperado = 0;
        boolEncontrado = false;
        boolEsperado = false;
    }

    /**
     * If the element is visible, return true, else return false.
     * Si el elemento es visible, devuelve true, sino devuelve false.
     *
     * @param locatorElement The locator of the element you want to check if it's visible.
     * @return A boolean value.
     */
    public boolean isVisible(String locatorElement) {
        boolean result = false;
        int attempts = 0;
        while (attempts < 2) {
            try {
                WebActionManager.isVisible(locatorElement);
                result = true;
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
            attempts++;
        }
        return result;
    }
}
