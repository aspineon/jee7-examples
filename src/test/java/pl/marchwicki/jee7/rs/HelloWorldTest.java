package pl.marchwicki.jee7.rs;

import static org.fest.assertions.Assertions.*;

import java.io.StringReader;
import java.net.URI;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.fest.assertions.Condition;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class HelloWorldTest {
	
	@ArquillianResource
    private URL baseURL;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(HelloWorld.class, RestApp.class)
                .addAsWebInfResource(
                        EmptyAsset.INSTANCE, "beans.xml");
        return webArchive;
    }
    
    @Test
    public void shouldRetrieveListOfBooksAsPlainText() {
        URI uri = URI.create(
                (baseURL.toExternalForm()+"hello/Jakub" ) );
        System.out.printf("uri=%s\n", uri ) ;

        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(uri.toString());
        Response response = target.request().get();
        System.out.printf("response=%s\n", response);
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(target.request().get(String.class)).isEqualTo("{\"data\":[\"Hello Jakub\",\"Guten tag Jakub\"]}");
        
        //streaming API
        JsonParser parser = Json.createParser(new StringReader(target.request().get(String.class)));
        Event event = parser.next();
        assertThat(event).is(new Condition<Object>() {
			public boolean matches(Object value) {
				return value instanceof Event && value == Event.START_OBJECT;
			}
		});

        event = parser.next();
        assertThat(event).is(new Condition<Object>() {
			public boolean matches(Object value) {
				return value instanceof Event && value == Event.KEY_NAME;
			}
		});

        event = parser.next();
        assertThat(event).is(new Condition<Object>() {
			public boolean matches(Object value) {
				return value instanceof Event && value == Event.START_ARRAY;
			}
		});

        event = parser.next();
        assertThat(event).is(new Condition<Object>() {
			public boolean matches(Object value) {
				return value instanceof Event && value == Event.VALUE_STRING;
			}
		});
        assertThat(parser.getString()).isEqualTo("Hello Jakub");

        event = parser.next();
        assertThat(event).is(new Condition<Object>() {
			public boolean matches(Object value) {
				return value instanceof Event && value == Event.VALUE_STRING;
			}
		});
        assertThat(parser.getString()).isEqualTo("Guten tag Jakub");
        
        event = parser.next();
        assertThat(event).is(new Condition<Object>() {
			public boolean matches(Object value) {
				return value instanceof Event && value == Event.END_ARRAY;
			}
		});

        event = parser.next();
        assertThat(event).is(new Condition<Object>() {
			public boolean matches(Object value) {
				return value instanceof Event && value == Event.END_OBJECT;
			}
		});

        //object API
        JsonReader reader = Json.createReader(new StringReader(target.request().get(String.class)));
        
        JsonObject obj = reader.readObject();
        assertThat(obj.containsKey("data")).isTrue();
        
        JsonArray results = obj.getJsonArray("data");
        
        assertThat(results.size()).isEqualTo(2);
        assertThat(results.getString(0)).isEqualTo("Hello Jakub");
        assertThat(results.getString(1)).isEqualTo("Guten tag Jakub");
    }

}
