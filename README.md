**Background**

JMT is a java template solution like turning a simple template code to java code.

You can import java packages with @@import statement and you can use @@args statement for output class render method arguments.

Therefore, there are not any restrictions in terms of flexibility and speed. In background all of java classes.

Alternatively freemarker,jtwig,thymeleaf,velocity,mustache etc .. there are many template solutions in the world.
Mostly custom template language is used in this solutions.
However, developers are forced to learn a template language. Being trapped in the capabilities of this template structure. Sometimes encounter difficulties in development.

JMT approach is instead of custom template language it aims to use the all capabilities of Java.

**Example Template (Model.jmt):**

    @@imports (
    java.util.Map
    java.util.Set
    )
    
    @@args String packageName, String className, JsonObject definition
    
    package <%= packageName + ".model" %>;
    
    public class <%= className %>{
    <%
    Map<String, Object> properties = definition.getJsonObject("properties").getMap();
    Set<String> keySet = properties.keySet();
    for(String key : keySet) {
    	JsonObject item = (JsonObject)properties.get(key);
    %>
    	public <%= item.getName() %> get<%= key %>() {
    		return <%= key %>;
    	}
    	public void set<%= key %>(<%= item.getName() %> value) {
    		<%= key %> = value;
    	}
    	
    <% } %>
    }

And output class (Model.java):

    package templates;

    import io.vertx.core.json.JsonObject;
    import java.util.Map;
    import java.util.Set;

    public class Model {
        public String render(String packageName, String className, JsonObject definition) {
            StringBuilder _sb = new StringBuilder();
            _sb.append("package ");
            _sb.append(packageName + ".model");
            _sb.append(";\n\npublic class ");
            _sb.append(className);
            _sb.append("{\n");
            Map<String, Object> properties = definition.getJsonObject("properties").getMap();
            Set<String> keySet = properties.keySet();
            for (String key : keySet) {
                JsonObject item = (JsonObject) properties.get(key);
                _sb.append("\n	public ");
                _sb.append(item.getName());
                _sb.append(" get");
                _sb.append(key);
                _sb.append("() {\n		return ");
                _sb.append(key);
                _sb.append(";\n	}\n	public void set");
                _sb.append(key);
                _sb.append("(");
                _sb.append(item.getName());
                _sb.append(" value) {\n		");
                _sb.append(key);
                _sb.append(" = value;\n	}\n	\n");
            }
            _sb.append("\n}");

            return _sb.toString();
        }
    }

**About the structure of the templates**

By default, such as JSP using with <% %> java code can be injected. If desired, tag delimiters can be change. Beginning of the template @@tags {%|%} as a definition for using {% %} tag delimeters.

**Using with maven**

1- Download this repository and compile it.

2- Add plugin definition in your pom.xml like the following example.

			<plugin>
				<groupId>org.jmt</groupId>
				<artifactId>jmt-maven-plugin</artifactId>
				<version>0.0.1-SNAPSHOT</version>
				<executions>
					<execution>
						<id>generate-jmt-templates</id>
						<phase>generate-sources</phase>
						<goals>
							<goal>generate</goal>
						</goals>
						<configuration>
							<jmt>
                                <!-- template directory -->
								<source>${basedir}/src/main/resources/jmt</source>
                                <!-- output classes -->
								<target>${basedir}/target/generated-sources/jmt</target>
							</jmt>
						</configuration>
					</execution>
				</executions>
			</plugin>

3- You must add output classes to your class path in your project settings and use it like normal java classes.
