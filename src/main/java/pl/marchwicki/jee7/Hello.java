package pl.marchwicki.jee7;

import javax.ejb.Stateless;

@Stateless
public class Hello {

	public String sayHello() {
		return "hello world!";
	}
	
}
