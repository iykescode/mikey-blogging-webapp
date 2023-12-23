package com.iykescode.blog.mikeybloggingwebapp.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StringUtil {

    public List<String> findNonExistingIteminList(List<String> list1, List<String> list2) {
        return list1.stream()
                .filter(element -> !list2.contains(element))
                .collect(Collectors.toList());
    }

    public String processString(String input) {
        // Remove spaces after commas
        String stringWithoutSpaces = input.replaceAll(",\\s+", ",");

        // Capitalize first letter of each word
        String[] words = stringWithoutSpaces.split(",");
        StringBuilder resultBuilder = new StringBuilder();

        for (String word : words) {
            resultBuilder.append(capitalizeFirstLetter(word)).append(",");
        }

        // Remove the trailing comma if the input is not empty
        if (words.length > 0) {
            resultBuilder.deleteCharAt(resultBuilder.length() - 1);
        }

        return resultBuilder.toString();
    }

    public String capitalizeFirstLetter(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    public static String spaceTwoStrings(String one, String two) {
        return one + " " + two;
    }

    public String joinTwoStringsByDash(String one, String two) {
        return one + "-" + two;
    }

    public String generateRandomString(int stringLength) {
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < stringLength; i++) {
            String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }
}
