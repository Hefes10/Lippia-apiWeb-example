package ar.unitTest;

import ar.validator.CommonValidator;
import org.testng.annotations.Test;
import java.util.ArrayList;
import org.testng.Assert;
import java.util.List;

public class CommonValidatorTest {
    CommonValidator commonValidator = new CommonValidator();

    //validateOrdenAlfabetico()

    @Test
    public void test1() {
        List<String> lista = new ArrayList<>();
        lista.add("Abel Pintos");
        lista.add("Abel Pintos");
        lista.add("Carlos Alvarez");
        lista.add("Juan Perez");
        lista.add("Luis Alberto Ramirez");
        lista.add("Maria Juarez");
        Assert.assertTrue(commonValidator.validateOrdenAlfabetico(lista));
    }

    @Test
    public void test2() {
        List<String> lista = new ArrayList<>();
        lista.add("Abel Alvarez");
        lista.add("Abel Alvarez");
        lista.add("Carlos Caceres");
        lista.add("Gabriel Gimenez");
        lista.add("Martin Miret");
        lista.add("Ramon Ramirez");
        Assert.assertTrue(commonValidator.validateOrdenAlfabetico(lista));
    }

    @Test
    public void test3() {
        List<String> lista = new ArrayList<>();
        lista.add("aaa aaa");
        lista.add("aaa aaa");
        lista.add("bbb bbb");
        lista.add("ccc ccc");
        lista.add("ddd ddd");
        Assert.assertTrue(commonValidator.validateOrdenAlfabetico(lista));
    }

    @Test
    public void test4() {
        List<String> lista = new ArrayList<>();
        lista.add("aaa aba");
        lista.add("aaa aba");
        lista.add("aaa aaa");
        lista.add("bbb bbb");
        lista.add("ccc ccc");
        Assert.assertFalse(commonValidator.validateOrdenAlfabetico(lista));
    }

    @Test
    public void test5() {
        List<String> lista = new ArrayList<>();
        lista.add("Abel Alvarez");
        lista.add("Abel Alvarez");
        lista.add("Carlos Caceres");
        lista.add("Gabriel Gimenez");
        lista.add("Ramon Ramirez");
        lista.add("Martin Miret");
        Assert.assertFalse(commonValidator.validateOrdenAlfabetico(lista));
    }
}
