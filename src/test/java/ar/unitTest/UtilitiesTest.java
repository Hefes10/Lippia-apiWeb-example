package ar.unitTest;

import ar.validator.Utilities;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UtilitiesTest {

    @Test
    public void testStrToDouble1() {
        Assert.assertEquals(835.23, Utilities.strToDouble("$ 835,23"));
    }

    @Test
    public void testStrToDouble2() {
        Assert.assertEquals(835.23, Utilities.strToDouble("u$s 835,23"));
    }

    @Test
    public void testStrToDouble3() {
        Assert.assertEquals(9835.23, Utilities.strToDouble("$ 9.835,23"));
    }

    @Test
    public void testStrToDouble4() {
        Assert.assertEquals(10835.23, Utilities.strToDouble("u$s 10.835,23"));
    }
}