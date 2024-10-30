package day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CalibrationValues {
    private String filename;
    private static Map<String, Integer> wordToNumber;

    public CalibrationValues(String filename) {
        this.filename = filename;

        wordToNumber = new HashMap<>();
        wordToNumber.put("one", 1);
        wordToNumber.put("two", 2);
        wordToNumber.put("three", 3);
        wordToNumber.put("four", 4);
        wordToNumber.put("five", 5);
        wordToNumber.put("six", 6);
        wordToNumber.put("seven", 7);
        wordToNumber.put("eight", 8);
        wordToNumber.put("nine", 9);
    }

    public void readFile() {
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }

    public static Integer getNumber(String line, int idx) {
        if (Character.isDigit(line.charAt(idx))) {
            return Character.getNumericValue(line.charAt(idx));
        }
        for (String word : wordToNumber.keySet()) {
            if (idx + word.length() <= line.length()) {
                String substring = line.substring(idx, idx + word.length());
                if (substring.equals(word)) {
                    return wordToNumber.get(word);
                }
            }
        }
        return -1;
    }

    public int getCalibrationValue(String file) {
        int sum = 0;
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int firstNum = -1;
                int lastNum = -1;  // renamed from secondNum for clarity
                
                for (int i = 0; i < line.length(); i++) {
                    Integer num = getNumber(line, i);
                    if (num != null) {
                        if (firstNum == -1) {
                            firstNum = num;
                        }
                        lastNum = num;
                    }
                }
                
                if (firstNum != -1) {  // if we found any numbers
                    if (lastNum == -1) {
                        lastNum = firstNum;
                    }
                    int lineValue = (firstNum * 10) + lastNum;
                    sum += lineValue;
                    System.out.println("Line numbers: " + firstNum + ", " + lastNum + " = " + lineValue);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
        System.out.println("Total sum: " + sum);
        return sum;
    }

    public static void main(String[] args) {
        CalibrationValues cv = new CalibrationValues("input.txt");
        int result = cv.getCalibrationValue("input.txt");
        System.out.println("Final sum: " + result);
    }
}