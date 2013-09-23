package pl.marchwicki.jee7.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SexConverter implements AttributeConverter<UserSex, String>{

	@Override
	public String convertToDatabaseColumn(UserSex arg0) {
		switch(arg0){
		case MALE: return "M";
		case FEMALE: return "F";
		default: throw new IllegalArgumentException(arg0 + " not known.");
		}

	}

	@Override
	public UserSex convertToEntityAttribute(String arg0) {
		switch(arg0){
		case "M": return UserSex.MALE;
		case "F": return UserSex.FEMALE;
		default: throw new IllegalArgumentException(arg0 + " not known.");
		}

	}

}
