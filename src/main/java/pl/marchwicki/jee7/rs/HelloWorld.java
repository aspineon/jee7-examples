package pl.marchwicki.jee7.rs;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Path("/hello")
public class HelloWorld {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String sayHello() {
		return "Hello world!";
	}

	
	@Path("/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ListWrapper sayHello(@PathParam("name") String name) {
		return new ListWrapper(new String[] { "Hello " + name, "Guten tag " + name });
	}

	@XmlRootElement
	public static class ListWrapper {
		@XmlElement
		List<String> data;
		
		public ListWrapper() {
			
		}
		
		public ListWrapper(String[] args) {
			this.data = Arrays.asList(args);
		}
	}
	
}
