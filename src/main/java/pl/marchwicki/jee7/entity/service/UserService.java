package pl.marchwicki.jee7.entity.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.marchwicki.jee7.entity.UserBean;
import pl.marchwicki.jee7.entity.UserSex;

@Stateless
public class UserService {

	@PersistenceContext
	EntityManager em;
	
	public void saveUser() {
		UserBean u = new UserBean();
		u.setName("Jakub");
		u.setSex(UserSex.MALE);
		
		em.persist(u);
	}
	
}
