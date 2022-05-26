package ar.apiExampleProject;

import io.cucumber.core.api.Scenario;
import org.apache.log4j.Logger;
import io.cucumber.java.Before;
import io.cucumber.java.After;

public class Hooks {

    @Before()
    public void setScenario(Scenario scenario) {
        Logger.getRootLogger().info(" Opening-----------" + scenario.getName() + "-----------");
    }

    @After()
    public void dismissAll(Scenario scenario) {
        Logger.getRootLogger().info(" Ending -----------" + scenario.getName() + "-----------");
    }
}
