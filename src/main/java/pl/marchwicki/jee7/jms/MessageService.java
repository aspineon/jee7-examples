package pl.marchwicki.jee7.jms;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

@Stateless
public class MessageService {

	@Inject
	@JMSConnectionFactory("java:global/jms/demoConnectionFactory")
	private JMSContext context;
	
	@Resource(lookup = "java:global/jms/demoQueue")
	private Queue inboundQueue;

	public void sendMessage(String payload) {
		context.createProducer().send(inboundQueue, payload);
	}

}
