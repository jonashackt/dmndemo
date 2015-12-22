package de.jonashackt.dmndemo;

import static org.junit.Assert.*;

import java.io.IOException;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.test.DmnEngineRule;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DmndemoApplication.class)
public class DmndemoApplicationTests {

    @Value("classpath:rules/states2ship.dmn")
    private Resource states2shipFile;
    
    @Rule
    public DmnEngineRule dmnEngineRule = new DmnEngineRule();

    public DmnEngine dmnEngine;
    public DmnDecision states2ship;

    @Before
    public void parseDecision() throws IOException {
      dmnEngine = dmnEngineRule.getDmnEngine();
      states2ship = dmnEngine.parseDecision("states2ship", states2shipFile.getInputStream());
    }
    
	@Test
	public void shouldEvaluateShipment() {		
	    // Should be ok
		VariableMap variables = Variables
	            .putValue("state", "France")
	            .putValue("product", "RedCar")
	            .putValue("zip", "99425");

	    DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(states2ship, variables);
	    assertNotNull(result.getSingleResult());
	    assertEquals("notok", result.getSingleResult().getEntryMap().get("result"));
	    assertEquals("sorry, no shipment possible", result.getSingleResult().getEntryMap().get("reason"));
	    
	    // Should be wrong
	    variables = Variables
	            .putValue("state", "France")
	            .putValue("product", "RedCar")
	            .putValue("zip", "100");

	    result = dmnEngine.evaluateDecisionTable(states2ship, variables);
	    assertNull(result.getSingleResult());
	    
	    // Should be wrong
	    variables = Variables
	            .putValue("state", "France")
	            .putValue("product", "RedCar")
	            .putValue("zip", "100");

	    result = dmnEngine.evaluateDecisionTable(states2ship, variables);
	    assertNull(result.getSingleResult());
	}
}
