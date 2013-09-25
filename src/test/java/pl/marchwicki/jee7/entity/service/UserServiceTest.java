package pl.marchwicki.jee7.entity.service;

import static org.fest.assertions.Assertions.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.marchwicki.jee7.entity.SexConverter;
import pl.marchwicki.jee7.entity.UserBean;
import pl.marchwicki.jee7.entity.UserSex;

@RunWith(Arquillian.class)
public class UserServiceTest {

	@Inject
	UserService userService;
	
	@PersistenceContext
	EntityManager em;
	
	@Deployment
	public static WebArchive deployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addClasses(UserService.class, UserBean.class, UserSex.class, SexConverter.class)
				.addAsResource("META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Test
	public void persist() {
		userService.saveUser();
		
		UserBean user = em.createNamedQuery(UserBean.FIND_USER_BY_NAME, UserBean.class)
				.setParameter("name", "Jakub").getSingleResult();
		assertThat(user.getName()).isEqualTo("Jakub");
		assertThat(user.getSex()).isEqualTo(UserSex.MALE);
	}
}
