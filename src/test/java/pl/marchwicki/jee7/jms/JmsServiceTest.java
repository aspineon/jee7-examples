package pl.marchwicki.jee7.jms;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class JmsServiceTest {

	@Inject
	MessageService service;
	
	@Deployment
	public static WebArchive deployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addClasses(MessageService.class, MessageConsumer.class, JmsConfiguration.class)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Test
	public void persist() throws Exception {
		service.sendMessage("Echo!!!");
		Thread.sleep(5000);
	}
	
}
