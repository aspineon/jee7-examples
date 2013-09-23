package pl.marchwicki.jee7.jms;

import javax.jms.JMSConnectionFactoryDefinition;
import javax.jms.JMSDestinationDefinition;

@JMSConnectionFactoryDefinition(name = "java:global/jms/demoConnectionFactory", 
	className = "javax.jms.ConnectionFactory")
@JMSDestinationDefinition(name = "java:global/jms/demoQueue", 
	interfaceName = "javax.jms.Queue", 
	destinationName = "demoQueue")
public class JmsConfiguration {

}
