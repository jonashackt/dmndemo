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

In your Decisiontable you need to define a simple column with a name (in this example it´s "zip") you´ll use later when calling the DMN-Engine and a appropriate Datatype - e.g. String.

Then add your Custom-Logic within a Java-Class´ static method (that returns a boolean) to the Project,
import the Class in Groovy and call the Method - e.g. like that:
```
import de.jonashackt.dmndemo.PostalcodeChecker
return PostalcodeChecker.isValidPostalcode(cellInput);
```

Note the correct import of your Class and also note the usage of "cellInput" - which contains the Value to check, passed into the DMN-Engine along with the other Variables. 

**A Note for users of DMN-Modeler**: If you use the camunda [DMN-Modeler](https://camunda.org/dmn/tool/) (which is a good idea :) ): the last stable Version (0.3.x) removed the Script-Call at the moment - this didn´t happen with the current [Nightly-Build](https://camunda.org/release/camunda-modeler/nightly/) any more.

You need to resist to use the camunda-Modeler feature to insert a "Script" into a table column:

![camunda_modeler_expression_script](https://github.com/jonashackt/dmndemo/blob/master/camunda_modeler_expression_script_screenshot.jpg)

This will execute the script all the time the decisiontable is evaluated.

As long as this [feature](https://github.com/bpmn-io/dmn-js/issues/20) isn´t shipped, there are 2 Options: 

**Option 1:** You have to open the dmn.xml in a texteditor and alter a rule yourself - e.g. like this:

```
<inputEntry id="UnaryTests_0h6fb9j" expressionLanguage="Groovy">        <text><![CDATA[import de.jonashackt.dmndemo.PostalcodeChecker
return PostalcodeChecker.isValidPostalcode(cellInput);]]></text>
</inputEntry>
```
**Option 2:** You insert the groovy-Script via the camunda-Modeler and open the XML after saving it up, to add the expressionlanguage-Definition to the right rule:
```
 expressionLanguage="Groovy"
```

Now if you run a Test, which fill´s the VariableMap correctly, it should evaluate your Rule and call the Java-Method with your Custom logic:

```
// Should be ok
VariableMap variables = Variables
        .putValue("state", "France")
        .putValue("product", "RedCar")
        .putValue("zip", "99425");

DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(states2ship, variables);
assertNotNull(result.getSingleResult());
assertEquals("notok", result.getSingleResult().getEntryMap().get("result"));
assertEquals("sorry, no shipment possible", result.getSingleResult().getEntryMap().get("reason"));
```

To see all this in Action, simply run [DmndemoApplicationTests.java](https://github.com/jonashackt/dmndemo/blob/master/src/test/java/de/jonashackt/dmndemo/DmndemoApplicationTests.java).

[camunda DMN engine]:https://github.com/camunda/camunda-engine-dmn