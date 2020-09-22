package com.flyaway.view;


public class MessageFormatter {
    public static String format(Formats format, String message) {
        String formattedString = null;
        if(format == Formats.ERROR)
            formattedString = String.format("<h3 style=\"color: red\">%s</h3>", message);
        else if(format == Formats.SUCCESS)
            formattedString =  String.format("\n" +
                    "<h3 style=\"color:  blue\">%s</h3>", message);
        else
            formattedString = String.format("\n" +
                    "<h3 style=\"color: cornflowerblue\"> %s</h3>", message);
        return formattedString;
    }

}
