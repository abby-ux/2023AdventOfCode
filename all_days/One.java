package all_days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class One {
    static Map<String, Integer> wordToDigit = new HashMap<>();

    static {
        wordToDigit.put("one", 1);
        wordToDigit.put("two", 2);
        wordToDigit.put("three", 3);
        wordToDigit.put("four", 4);
        wordToDigit.put("five", 5);
        wordToDigit.put("six", 6);
        wordToDigit.put("seven", 7);
        wordToDigit.put("eight", 8);
        wordToDigit.put("nine", 9);
    }

    public static int checkForNumWord(String line) {
        int first = -1;
        int last = -1;
        
        for (int i = 0; i < line.length(); i++) {
            // Check for numeric digit
            if (Character.isDigit(line.charAt(i))) {
                int digit = Character.getNumericValue(line.charAt(i));
                if (first == -1) first = digit;
                last = digit;
                continue;
            }
            
            // Check for word numbers
            String remaining = line.substring(i);
            for (Map.Entry<String, Integer> entry : wordToDigit.entrySet()) {
                if (remaining.startsWith(entry.getKey())) {
                    if (first == -1) first = entry.getValue();
                    last = entry.getValue();
                    break;
                }
            }
        }
        
        return first * 10 + last;
    }

    public static int getCalibrationValue(String filename) {
        int sum = 0;
        try (Scanner sc = new Scanner(new File(filename))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                sum += checkForNumWord(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println("Calibration Sum: " + getCalibrationValue("input.txt"));
    }
}
