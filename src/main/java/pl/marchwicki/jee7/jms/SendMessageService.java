package pl.marchwicki.jee7.jms;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

@Stateless
public class SendMessageService {

//	@Resource(lookup = "java:global/jms/demoConnectionFactory")
	ConnectionFactory connectionFactory;
//	@Resource(lookup = "java:global/jms/demoQueue")
	Queue demoQueue;

	public void sendMessage(String payload) {
		try {
			Connection connection = connectionFactory.createConnection();
			try {
				Session session = connection.createSession(false,
						Session.AUTO_ACKNOWLEDGE);
				MessageProducer messageProducer = session
						.createProducer(demoQueue);
				TextMessage textMessage = session.createTextMessage(payload);
				messageProducer.send(textMessage);
			} finally {
				connection.close();
			}
		} catch (JMSException ex) {
			ex.printStackTrace();
		}
	}

}
