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

    public void analyze(Object obj, String condition, Boolean message) {
        doWithFields(obj, condition);
        if (message) addMsg(obj);
    }

    public void analyze(Object obj, String condition, Boolean message, String[] except) {
        doWithFields(obj, condition, except);
        if (message) addMsg(obj);
    }

    public void analyze(Object obj, String condition, Boolean message, List<String> except) {
        doWithFields(obj, condition, except);
        if (message) addMsg(obj);
    }

    private void doWithFields(Object obj, String condition) {
        String finalCondition = condition.toUpperCase().replaceAll("\\s+","");
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            switchCondition(field, obj, finalCondition);
        });
    }

    private void doWithFields(Object obj, String condition, List<String> except) {
        String finalCondition = condition.toUpperCase().replaceAll("\\s+","");
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            if (!except.contains(field.getName())) {
                switchCondition(field, obj, finalCondition);
            }
        });
    }

    private void doWithFields(Object obj, String condition, String[] except) {
        String finalCondition = condition.toUpperCase().replaceAll("\\s+","");
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            if (Arrays.stream(except).noneMatch(str -> str.equals(field.getName()))) {
                switchCondition(field, obj, finalCondition);
            }
        });
    }

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

    private void addMsg(Object obj) {
        String var = obj.getClass().getTypeName().replace(".", "/");
        var = var.substring(var.lastIndexOf("/") + 1);
        addMsgToReport("Se valida que los campos de " + var + " sean distintos de null, y ");
    }

    public void check(String msg, Boolean message) {
        msg = Utilities.formatDescription(msg);
        softAssert.assertEquals(strEncontrado, strEsperado, msg + " no es correcto.");
        softAssert.assertEquals(douEncontrado, douEsperado, msg + " no es correcto.");
        softAssert.assertEquals(boolEncontrado, boolEsperado, msg + " no es correcto.");

        if (message) addMsgToReport(msg);

        clearAuxVar();
    }

    public void addMsgToReport(String msg) {
        if (softAssert.m_errors_aux.isEmpty()) {
            CucumberReporter.addTestStepLog(msg + " es correcto.");
        } else {
            CucumberReporter.addTestStepLog("---ERROR: " + msg + " NO es correcto.");
            softAssert.clearAuxErrors();
        }
    }

    public void checkContains(String msg) {
        msg = Utilities.formatDescription(msg);
        softAssert.assertTrue(strEsperado.contains(strEncontrado), msg);

        clearAuxVar();
    }

    private void clearAuxVar() {
        strEncontrado = "";
        strEsperado = "";
        douEncontrado = 0;
        douEsperado = 0;
        boolEncontrado = false;
        boolEsperado = false;
    }

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
