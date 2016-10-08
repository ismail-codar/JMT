**Background**

JMT is a java template solution like turning a simple template code to java code.

You can import java packages with @@import statement and you can use @@args statement for output class contstructors in template.
Therefore, there are not any restrictions in terms of flexibility and speed. In background all of java classes.

Alternatively freemarker,jtwig,thymeleaf,velocity,mustache etc .. There are many template solutions in the world.
Mostly custom template language is used in this solutions.
However, developers are forced to learn a template language. Being trapped in the capabilities of this template structure. Sometimes encounter difficulties in development.

JMT approach is instead of custom template language it aims to use the all capabilities of Java.

**Example Template:**

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

**About the structure of the templates**

By default, such as JSP using with <% %> java code can be injected.
If desired, tag delimiters can be change. Beginning of the template @@tags {%|%} as a definition for using {% %} tag delimeters.
