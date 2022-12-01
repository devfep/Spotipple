package com.devfep.spotipple.dto;

import java.util.HashMap;
import java.util.Map;

public class Playground {
    public static void main(String[] args) {
        System.out.println(stringProcessor("Nnnnnaaameeee"));
        System.out.println(emailAddressChecker("felix@gmail..cm"));
        String username = "hello";
        System.out.println(String.format("User with username:'%s' not found", username));
        System.out.println(maxSumIndexGenerator(new int[]{3, 7, 0, -1}));
    }

    public static String stringProcessor(String name) {
        if (name.length() == 1) return name;
        StringBuilder processed = new StringBuilder();
        char[] nameArr = name.toLowerCase().toCharArray();
        int i = 0;
        int j = 1;
        while (j < name.length()) {                //    i
            //     j
            if (nameArr[j] == nameArr[i]) {      //Nnaaame
                j++;
            } else if (nameArr[i] != nameArr[j]) {
                processed.append(nameArr[i]);
                i = j;
            }
        }
        return processed.toString();
    }

    public static boolean emailAddressChecker(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    }


    public static int maxSumIndexGenerator(int[] arr) {
        int windowSum = 0, maxSum = 0, windowStart = 0;
        Map<Integer, Integer> indexMaxSum = new HashMap<>();

        for (int windowEnd = 0; windowEnd < arr.length; windowEnd++) {
            windowSum += arr[windowEnd];

            if (windowEnd >= arr.length - 1) {
                indexMaxSum.put(windowSum, windowStart);
                maxSum = Math.max(maxSum, windowSum);
                windowSum -= arr[windowStart];
                windowStart++;
            }
            int index = 0;

            for (Object i : indexMaxSum.values().toArray()) {
//                i
            }
            return index;
        }


        return maxSum;
    }
}
