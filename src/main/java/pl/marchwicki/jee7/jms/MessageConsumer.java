package pl.marchwicki.jee7.jms;

import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(mappedName="global/jms/demoQueue")
public class MessageConsumer implements MessageListener {

	@Override
	public void onMessage(Message msg) {
		try {
			//No casting!!!
			String payload = msg.getBody(String.class);
			System.out.println(payload);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
