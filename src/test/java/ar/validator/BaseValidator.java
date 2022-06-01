package ar.validator;

import com.example.report.CucumberReporter;
import org.springframework.util.ReflectionUtils;
import java.util.Arrays;
import java.util.List;

public class BaseValidator {
    public static String strEsperado = "", strEncontrado = "";
    public static double douEsperado, douEncontrado;
    public static boolean boolEsperado, boolEncontrado;

    //protected static SoftAssert softAssert = new SoftAssert();
    public static ThreadLocal<SoftAssert> SOFTASSERT = new ThreadLocal<>();

    public static SoftAssert getSoftAssert() {
        return SOFTASSERT.get();
    }

    public static void setSoftAssert(SoftAssert softAssert) {
        SOFTASSERT.set(softAssert);
    }


    public static void analyzeNotNull(Object obj) {
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            SOFTASSERT.get().assertNotNull(field.get(obj), "El campo " + field.getName() + " es null.");
        });

        String var = obj.getClass().getTypeName().replace(".", "/");
        var = var.substring(var.lastIndexOf("/") + 1);
        addMsgToReport("Se valida que los campos de " + var + " sean distintos de null, y ");
    }

    public static void analyzeNotNullWhitoutMsg(Object obj) {
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            SOFTASSERT.get().assertNotNull(field.get(obj), "El campo " + field.getName() + " es null.");
        });
    }

    public static void analyzeNotNull(Object obj, List<String> except) {
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

    public static void analyzeNotNullWhitoutMsg(Object obj, List<String> except) {
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            if (!except.contains(field.getName())) {
                SOFTASSERT.get().assertNotNull(field.get(obj), "El campo " + field.getName() + " es null.");
            }
        });
    }

    public static void analyzeNotNull(Object obj, String[] except) {
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

    public static void analyzeNotNullWhitoutMsg(Object obj, String[] except) {
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            if (Arrays.stream(except).noneMatch(str -> str.equals(field.getName()))) {
                SOFTASSERT.get().assertNotNull(field.get(obj), "El campo " + field.getName() + " es null.");
            }
        });
    }

    public static void analyzeNotVoid(Object obj) {
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            SOFTASSERT.get().assertNotEquals("", field.get(obj), "El campo " + field.getName() + " esta vacio.");
        });

        String var = obj.getClass().getTypeName().replace(".", "/");
        var = var.substring(var.lastIndexOf("/") + 1);
        addMsgToReport("Se valida que los campos de " + var + " sean distintos de vacio, y ");
    }

    public static void analyzeNotVoidWhitoutMsg(Object obj) {
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            SOFTASSERT.get().assertNotEquals("", field.get(obj), "El campo " + field.getName() + " esta vacio.");
        });
    }

    public static void analyzeNotVoid(Object obj, List<String> except) {
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

    public static void analyzeNotVoid(Object obj, String[] except) {
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

    public static void analyzeNotVoidWhitoutMsg(Object obj, String[] except) {
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            if (Arrays.stream(except).noneMatch(str -> str.equals(field.getName()))) {
                SOFTASSERT.get().assertNotEquals("", field.get(obj), "El campo " + field.getName() + " esta vacio.");
            }
        });
    }

    public static void check(String msg){
        msg = Utilities.formatDescription(msg);
        SOFTASSERT.get().assertEquals(strEncontrado, strEsperado, msg + " no es correcto.");
        SOFTASSERT.get().assertEquals(douEncontrado, douEsperado, msg + " no es correcto.");
        SOFTASSERT.get().assertEquals(boolEncontrado, boolEsperado, msg + " no es correcto.");

        clearAuxVar();
    }

    public static void checkAndReport(String msg){
        msg = Utilities.formatDescription(msg);
        SOFTASSERT.get().assertEquals(strEncontrado, strEsperado, msg + " no es correcto.");
        SOFTASSERT.get().assertEquals(douEncontrado, douEsperado, msg + " no es correcto.");
        SOFTASSERT.get().assertEquals(boolEncontrado, boolEsperado, msg + " no es correcto.");

        addMsgToReport(msg);

        clearAuxVar();
    }

    public static void addMsgToReport(String msg) {
        if (SOFTASSERT.get().m_errors_aux.isEmpty()) {
            CucumberReporter.addTestStepLog(msg + " es correcto.");
        } else {
            CucumberReporter.addTestStepLog("---ERROR: " + msg + " NO es correcto.");
            SOFTASSERT.get().clearAuxErrors();
        }
    }

    public static void checkContains(String msg){
        msg = Utilities.formatDescription(msg);
        SOFTASSERT.get().assertTrue(strEsperado.contains(strEncontrado), msg);

        clearAuxVar();
    }

    private static void clearAuxVar() {
        strEncontrado = "";
        strEsperado = "";
        douEncontrado = 0;
        douEsperado = 0;
        boolEncontrado = false;
        boolEsperado = false;
    }
}
