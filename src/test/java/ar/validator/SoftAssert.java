package ar.validator;

import com.example.report.CucumberReporter;
import org.testng.asserts.Assertion;
import org.testng.collections.Maps;
import org.testng.asserts.IAssert;
import java.util.Map;

public class SoftAssert extends Assertion {
    public final Map<AssertionError, IAssert<?>> m_errors = Maps.newLinkedHashMap();
    public final Map<AssertionError, IAssert<?>> m_errors_aux = Maps.newLinkedHashMap();
    public StringBuilder var1 = new StringBuilder("The following asserts failed:");
    public boolean flag = false;

    public SoftAssert() {
    }

    protected void doAssert(IAssert<?> var1) {
        this.onBeforeAssert(var1);

        try {
            var1.doAssert();
            this.onAssertSuccess(var1);
        } catch (AssertionError var6) {
            this.onAssertFailure(var1, var6);
            this.m_errors.put(var6, var1);
            this.m_errors_aux.put(var6, var1);
        } finally {
            this.onAfterAssert(var1);
        }
    }

    public void assertAll() {
        if (!this.m_errors.isEmpty()) {
            CucumberReporter.addTestStepLog("Las siguientes afirmaciones fallaron: ");
            boolean var2 = true;

            for (Map.Entry<AssertionError, IAssert<?>> assertionErrorIAssertEntry : this.m_errors.entrySet()) {
                if (var2) {
                    var2 = false;
                } else {
                    var1.append(",");
                }
                var1.append("\n\t");
                var1.append(((AssertionError) ((Map.Entry) assertionErrorIAssertEntry).getKey()).getMessage());
                CucumberReporter.addTestStepLog(((AssertionError) ((Map.Entry) assertionErrorIAssertEntry).getKey()).getMessage());
            }
            flag = true;
            clearErrors();
        }
    }

    private void clearErrors() {
        this.m_errors.clear();
    }

    public void clearAuxErrors() {
        this.m_errors_aux.clear();
    }

    public void isAssert() {
        if (this.flag){
            throw new AssertionError(var1.toString());
        }
    }
}