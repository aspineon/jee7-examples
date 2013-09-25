package pl.marchwicki.jee7.entity;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.Table;

@Table(indexes = @Index(columnList = "name"))
@Entity
@NamedQuery(name = UserBean.FIND_USER_BY_NAME, query = "SELECT u FROM UserBean u WHERE u.name = :name")
@NamedStoredProcedureQuery(name = UserBean.DO_SOMETHING_WITH_USERS, procedureName = "USR_STRD_PRCR_CALL")
public class UserBean {
	public final static String FIND_USER_BY_NAME = "UserBean.findByName";
	public final static String DO_SOMETHING_WITH_USERS = "UserBean.doSomething";

	@Id
	private long id;
	private String name;
	
//	@Enumerated(EnumType.ORDINAL)
//	@Enumerated(EnumType.STRING)
//	@Transient
	@Convert(converter = SexConverter.class)
	private UserSex sex;

//	private String db_sex;
	
//	@PostLoad
//	public void afterLoad() {
//		switch(db_sex){
//		case "M": sex = UserSex.MALE;
//		case "F": sex = UserSex.FEMALE;
//		default: throw new IllegalArgumentException(db_sex + " not known.");
//		}
//	}
//	
//	@PrePersist
//	public void beforePersit() {
//		switch(sex){
//		case MALE: db_sex = "M";
//		case FEMALE: db_sex = "F";
//		default: throw new IllegalArgumentException(db_sex + " not known.");
//		}
//	}

	public UserSex getSex() {
		return sex;
	}

	public void setSex(UserSex sex) {
		this.sex = sex;
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
