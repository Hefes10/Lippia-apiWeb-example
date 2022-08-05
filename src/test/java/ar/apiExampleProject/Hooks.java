package ar.apiExampleProject;

import com.example.services.apiServices.BaseService;
import io.cucumber.core.api.Scenario;
import org.apache.log4j.Logger;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import java.util.HashMap;

public class Hooks {

    @Before()
    public void setScenario(Scenario scenario) {
        Logger.getRootLogger().info(" Opening-----------" + scenario.getName() + "-----------");
        BaseService.PARAMS.set(new HashMap<>());
    }

    @After()
    public void dismissAll(Scenario scenario) {
        Logger.getRootLogger().info(" Ending -----------" + scenario.getName() + "-----------");
    }
}
