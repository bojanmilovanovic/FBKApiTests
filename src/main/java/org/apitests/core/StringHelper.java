package org.apitests.core;

import java.util.Random;

public class StringHelper {

    // For generating random strings where a prefix sting is give
    public static String getRandomStringStartingWith(String start) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 15;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return start+"_"+generatedString;
    }

    // For working with CSV files. Based on a CSV string, return the array of column values.
    public static String[] getCSVColumnValue(String input, String column){
        String[] lines = input.split("\\r?\\n");
        String[] output = new String[lines.length-1];
        String[] headers = lines[0].split("[,]");
        int index = 0;
        for(int i=0; i<headers.length; i++){
            if(headers[i].equalsIgnoreCase(column)) {
                index = i;
                break;
            }
        }
        // Find all rows based on a column and add them to the output
        for(int i=1; i<lines.length; i++){
            String[] row = lines[i].split("[,]");
            output[i-1] = row[index];
        }
        return output;
    }


}
