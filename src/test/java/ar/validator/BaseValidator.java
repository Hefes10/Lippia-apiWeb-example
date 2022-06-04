package ar.validator;

import com.crowdar.core.actions.WebActionManager;
import org.springframework.util.ReflectionUtils;
import com.example.report.CucumberReporter;
import java.util.Arrays;
import java.util.List;

public class BaseValidator {
    public String strEsperado = "", strEncontrado = "";
    public double douEsperado, douEncontrado;
    public boolean boolEsperado, boolEncontrado;

    public ThreadLocal<SoftAssert> SOFTASSERT = new ThreadLocal<>();

    public SoftAssert getSoftAssert() {
        return SOFTASSERT.get();
    }

    public void setSoftAssert(SoftAssert softAssert) {
        SOFTASSERT.set(softAssert);
    }


    public void analyzeNotNull(Object obj) {
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            SOFTASSERT.get().assertNotNull(field.get(obj), "El campo " + field.getName() + " es null.");
        });

        String var = obj.getClass().getTypeName().replace(".", "/");
        var = var.substring(var.lastIndexOf("/") + 1);
        addMsgToReport("Se valida que los campos de " + var + " sean distintos de null, y ");
    }

    public void analyzeNotNullWhitoutMsg(Object obj) {
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            SOFTASSERT.get().assertNotNull(field.get(obj), "El campo " + field.getName() + " es null.");
        });
    }

    public void analyzeNotNull(Object obj, List<String> except) {
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            if (!except.contains(field.getName())) {
                SOFTASSERT.get().assertNotNull(field.get(obj), "El campo " + field.getName() + " es null.");
            }
        });

        String var = obj.getClass().getTypeName().replace(".", "/");
        var = var.substring(var.lastIndexOf("/") + 1);
        addMsgToReport("Se valida que los campos de " + var + " sean distintos de null, y ");
    }

    public void analyzeNotNullWhitoutMsg(Object obj, List<String> except) {
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            if (!except.contains(field.getName())) {
                SOFTASSERT.get().assertNotNull(field.get(obj), "El campo " + field.getName() + " es null.");
            }
        });
    }

    public void analyzeNotNull(Object obj, String[] except) {
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            if (Arrays.stream(except).noneMatch(str -> str.equals(field.getName()))) {
                SOFTASSERT.get().assertNotNull(field.get(obj), "El campo " + field.getName() + " es null.");
            }
        });

        String var = obj.getClass().getTypeName().replace(".", "/");
        var = var.substring(var.lastIndexOf("/") + 1);
        addMsgToReport("Se valida que los campos de " + var + " sean distintos de null, y ");
    }

    public void analyzeNotNullWhitoutMsg(Object obj, String[] except) {
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            if (Arrays.stream(except).noneMatch(str -> str.equals(field.getName()))) {
                SOFTASSERT.get().assertNotNull(field.get(obj), "El campo " + field.getName() + " es null.");
            }
        });
    }

    public void analyzeNotVoid(Object obj) {
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            SOFTASSERT.get().assertNotEquals("", field.get(obj), "El campo " + field.getName() + " esta vacio.");
        });

        String var = obj.getClass().getTypeName().replace(".", "/");
        var = var.substring(var.lastIndexOf("/") + 1);
        addMsgToReport("Se valida que los campos de " + var + " sean distintos de vacio, y ");
    }

    public void analyzeNotVoidWhitoutMsg(Object obj) {
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            SOFTASSERT.get().assertNotEquals("", field.get(obj), "El campo " + field.getName() + " esta vacio.");
        });
    }

    public void analyzeNotVoid(Object obj, List<String> except) {
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            if (!except.contains(field.getName())) {
                SOFTASSERT.get().assertNotEquals("", field.get(obj), "El campo " + field.getName() + " esta vacio.");
            }
        });

        String var = obj.getClass().getTypeName().replace(".", "/");
        var = var.substring(var.lastIndexOf("/") + 1);
        addMsgToReport("Se valida que los campos de " + var + " sean distintos de vacio, y ");
    }

    public void analyzeNotVoid(Object obj, String[] except) {
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            if (Arrays.stream(except).noneMatch(str -> str.equals(field.getName()))) {
                SOFTASSERT.get().assertNotEquals("", field.get(obj), "El campo " + field.getName() + " esta vacio.");
            }
        });

        String var = obj.getClass().getTypeName().replace(".", "/");
        var = var.substring(var.lastIndexOf("/") + 1);
        addMsgToReport("Se valida que los campos de " + var + " sean distintos de vacio, y ");
    }

    public void analyzeNotVoidWhitoutMsg(Object obj, String[] except) {
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            if (Arrays.stream(except).noneMatch(str -> str.equals(field.getName()))) {
                SOFTASSERT.get().assertNotEquals("", field.get(obj), "El campo " + field.getName() + " esta vacio.");
            }
        });
    }

    public void check(String msg){
        msg = Utilities.formatDescription(msg);
        SOFTASSERT.get().assertEquals(strEncontrado, strEsperado, msg + " no es correcto.");
        SOFTASSERT.get().assertEquals(douEncontrado, douEsperado, msg + " no es correcto.");
        SOFTASSERT.get().assertEquals(boolEncontrado, boolEsperado, msg + " no es correcto.");

        clearAuxVar();
    }

    public void checkAndReport(String msg){
        msg = Utilities.formatDescription(msg);
        SOFTASSERT.get().assertEquals(strEncontrado, strEsperado, msg + " no es correcto.");
        SOFTASSERT.get().assertEquals(douEncontrado, douEsperado, msg + " no es correcto.");
        SOFTASSERT.get().assertEquals(boolEncontrado, boolEsperado, msg + " no es correcto.");

        addMsgToReport(msg);

        clearAuxVar();
    }

    public void addMsgToReport(String msg) {
        if (SOFTASSERT.get().m_errors_aux.isEmpty()) {
            CucumberReporter.addTestStepLog(msg + " es correcto.");
        } else {
            CucumberReporter.addTestStepLog("---ERROR: " + msg + " NO es correcto.");
            SOFTASSERT.get().clearAuxErrors();
        }
    }

    public void checkContains(String msg){
        msg = Utilities.formatDescription(msg);
        SOFTASSERT.get().assertTrue(strEsperado.contains(strEncontrado), msg);

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
        while(attempts < 2) {
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
