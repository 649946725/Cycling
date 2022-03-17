package com.cycling.utils;

import com.cycling.enumerate.PersonDataType;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * @Author xpdxz
 * @ClassName DataTypeFormatter
 * @Description TODO
 * @Date 2021/11/26 16:30
 */
public class DataTypeFormatter implements Formatter<PersonDataType> {
    @Override
    public PersonDataType parse(String text, Locale locale) throws ParseException {
        Integer type = Integer.parseInt(text);
        if (type.equals(0)) {
            return PersonDataType.DYNAMIC;
        } else if (type.equals(1)) {
            return PersonDataType.ACTIVE;
        }
        return null;
    }

    @Override
    public String print(PersonDataType object, Locale locale) {
        if (object == null) {
            return null;
        }
        return String.valueOf(object.getValue());
    }
}
