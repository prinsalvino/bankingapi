package io.swagger.util;

import org.threeten.bp.LocalDate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidation {
    public static final String PHONE_VERIFICATION = "^\\d{10}$";
    private static final String regexEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static Pattern pattern;
    private static Matcher matcher;

    public boolean checkValidEmail(String email) {
        pattern = Pattern.compile(regexEmail);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean checkValidNumber(String phoneNumber) {
        //Phone validation
        pattern = Pattern.compile(PHONE_VERIFICATION);
        matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public boolean checkValidBirthDate(LocalDate birthDate) {
        LocalDate today = LocalDate.now();
        System.out.println(today.compareTo(birthDate));
        // Return true if greater than today
        return today.compareTo(birthDate) < 0;
    }
}
