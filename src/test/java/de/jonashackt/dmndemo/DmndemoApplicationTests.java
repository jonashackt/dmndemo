package de.jonashackt.dmndemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DmndemoApplication.class)
public class DmndemoApplicationTests {

	@Test
	@DecisionResource
	public void contextLoads() {
	}

}
