# dmndemo
[![Build Status](https://travis-ci.org/jonashackt/dmndemo.svg?branch=master)](https://travis-ci.org/jonashackt/dmndemo)
[![Coverage Status](https://coveralls.io/repos/jonashackt/dmndemo/badge.svg)](https://coveralls.io/r/jonashackt/dmndemo)

Sample Project showing Usage of [camunda DMN engine] working with DMN-compliant Decision Tables.

It uses [SpringBoot](http://projects.spring.io/spring-boot/) as a basis and performs a Test on a predefined decision-Table (for modelling see the [DMN Modeler](https://camunda.org/dmn/tool/) ).

### Howto

Just clone, mvn clean eclipse:eclipse and run the Test - e.g. in your IDE, to see what´s going on.

### Call Java-Method from within DMN-Table to execute more complex logic

In some cases the build in Expression Language [FEEL](https://camunda.org/dmn/tutorial/#feel) isn´t sufficient to check more complex Logic. In this case, it´s nice to simply call a Java-Method from within your Decision-Table via the Groovy Expression Language, which is supported in camunda´s [DMN Expressions](https://docs.camunda.org/manual/7.4/user-guide/dmn-engine/expressions-and-scripts/).

For that you need to add Groovy to your Classpath (I omit the version here, because SpringBoot ships Groovy already):
```
<dependency>
  <groupId>org.codehaus.groovy</groupId>
  <artifactId>groovy-all</artifactId>
</dependency>
```

Then add your Custom-Logic within a Java-Class´ static method (that returns a boolean) to the Project,
import the Class in Groovy and call the Method - e.g. like that:
```
import de.jonashackt.dmndemo.PostalcodeChecker
return PostalcodeChecker.isValidPostalcode(cellInput);
```

Note the correct import of your Class and also note the usage of "cellInput" - which contains the Value to check, passed into the DMN-Engine along with the other Variables. For Example-Code, see [DmndemoApplicationTests.java](https://github.com/jonashackt/dmndemo/blob/master/src/test/java/de/jonashackt/dmndemo/DmndemoApplicationTests.java).

**Warning for users of DMN-Modeler**: If you use the camunda [DMN-Modeler](https://camunda.org/dmn/tool/) (which is a good idea :) ): it removes the Script-Call at the moment, altough this Usage of Groovy is described in the [camunda docs](https://docs.camunda.org/manual/7.4/user-guide/dmn-engine/expressions-and-scripts/).

To see all this in Action, simply run [DmndemoApplicationTests.java](https://github.com/jonashackt/dmndemo/blob/master/src/test/java/de/jonashackt/dmndemo/DmndemoApplicationTests.java).

[camunda DMN engine]:https://github.com/camunda/camunda-engine-dmn