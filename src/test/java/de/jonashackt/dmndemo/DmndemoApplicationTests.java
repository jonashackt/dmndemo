package de.jonashackt.dmndemo;

import static org.camunda.dmn.engine.test.asserts.DmnAssertions.assertThat;

import org.camunda.dmn.engine.test.DecisionResource;
import org.camunda.dmn.engine.test.DmnDecisionTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DmndemoApplication.class)
public class DmndemoApplicationTests extends DmnDecisionTest{

    private static final String STATE_2_SHIP_DMN = "rules/states2ship.dmn";
    private static final String MINIMAL_DMN = "rules/minimal.dmn";
    private static final String JUST_PRODUCT_DMN = "rules/justProduct.dmn";
    
	@Test
	@DecisionResource(resource = STATE_2_SHIP_DMN)
	public void shouldEvaluateShipment() {		
		assertThat(engine)
	      .evaluates(decision)
	      .withContext()
	        .setVariable("state", "Afghanistan")
	        .setVariable("product", "RedCar")
	        .build()
	      .hasResult()
	        .hasSingleOutput()
	          .hasEntry("result", "notok")
	          .hasEntry("reason", "sorry, no shipment possible");

	}
	
	@Test
	@DecisionResource(resource = MINIMAL_DMN)
	public void shouldEvaluateMinimal() {
		 assertThat(engine)
	      .evaluates(decision)
	      .withContext()
	        .setVariable("state", "Afghanistan")
	        .build()
	      .hasResult()
	        .hasSingleOutput()
	          .hasEntry("result", "notok");
	}
	

	@Test
	@DecisionResource(resource = JUST_PRODUCT_DMN)
	public void shouldEvaluateJustProduct() {
		 assertThat(engine)
	      .evaluates(decision)
	      .withContext()
	        .setVariable("product", "RedCar")
	        .build()
	      .hasResult()
	        .hasSingleOutput()
	          .hasEntry("result", "notok");
	}

}
