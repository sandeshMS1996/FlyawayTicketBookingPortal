package com.flyaway.view.restricted;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

public class ValidateInput {

    public  static Set<String> validateInput(HttpServletRequest request, Properties properties) {

        Set<String> errors = new HashSet<>();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String s = names.nextElement();
            if (!Pattern.matches(properties.getProperty(s), request.getParameter(s)))
                errors.add(s + " is not correct");
        }

        return errors;

    }
}
