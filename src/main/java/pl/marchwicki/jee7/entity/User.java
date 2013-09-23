package pl.marchwicki.jee7.entity;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sun.corba.ee.spi.trace.TraceInterceptor;

@Table(indexes = @Index(columnList = "name"))
@Entity
@NamedQuery(name = User.FIND_USER_BY_NAME, query = "from User u where name = ?")
@NamedStoredProcedureQuery(name = User.DO_SOMETHING_WITH_USERS, procedureName = "USR_STRD_PRCR_CALL")
public class User {
	public final static String FIND_USER_BY_NAME = "User.findByName";
	public final static String DO_SOMETHING_WITH_USERS = "User.doSomething";

	@Id
	private long id;
	private String name;
	
//	@Enumerated(EnumType.ORDINAL)
//	@Enumerated(EnumType.STRING)
//	@Transient
	@Convert(converter = SexConverter.class)
	private UserSex sex;

	private String db_sex;
	
	@PostLoad
	public void afterLoad() {
		switch(db_sex){
		case "M": sex = UserSex.MALE;
		case "F": sex = UserSex.FEMALE;
		default: throw new IllegalArgumentException(db_sex + " not known.");
		}
	}
	
	@PrePersist
	public void beforePersit() {
		switch(sex){
		case MALE: db_sex = "M";
		case FEMALE: db_sex = "F";
		default: throw new IllegalArgumentException(db_sex + " not known.");
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
