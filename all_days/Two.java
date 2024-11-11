package all_days;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.File;

public class Two {

    static Map<String, Integer> colorToScore = new HashMap<>();
    
    static {
        colorToScore.put("blue", 14);
        colorToScore.put("green", 13);
        colorToScore.put("red", 12);
    }

    public static boolean isHandValid(String s) {
        String[] sets = s.split(", ");
        for (String set : sets){
            String[] cubes = set.trim().split(" ");
            int count = Integer.parseInt(cubes[0]);
            String color = cubes[1];
            if (colorToScore.containsKey(color) && count > colorToScore.get(color)) {
                return false; // Invalid hand if any set exceeds the limit
            }
        }
        
        return true;
    }

    public static boolean isGameValid(String s) {
        String[] hands = s.split(";");
        for (String hand : hands) {
            if (!isHandValid(hand)) {
                return false;
            }
        }
        return true;
    }

    public static int getHandSum(String s) {
        int handSum = 0;
        String[] sets = s.split(",");
        for (int i = 0; i < sets.length; i++) {
            String[] cubes = sets[i].split(" ");
            if (colorToScore.containsKey(cubes[1]) && Integer.parseInt(cubes[0]) <= colorToScore.get(cubes[1])) {
                
                handSum += s.charAt(i) - '0';
                // System.out.println("getHandSum: " + sum);
            }
        }
        return handSum;
    }
    
    public static int sumOfGames(String filename) {
        int sum = 0;
        int gameID = 1;
        try (Scanner sc = new Scanner(new File(filename))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] lines = line.split(": ");
                if (lines.length > 1 && isGameValid(lines[1])) {
                    sum += gameID;
                }
                gameID++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }

        return sum;
    }

    public static int getPowerSet(String s) {
        int blueMax = 0;
        int redMax = 0;
        int greenMax = 0;
        String[] rounds = s.split("; ");
        for (int i = 0; i < rounds.length; i++) {
            String[] hands = rounds[i].split(", ");
            for (int j = 0; j < hands.length; j++) {
                String[] hand = hands[j].split(" ");
                int num = Integer.parseInt(hand[0]);
                String color = hand[1];
                // int num = Integer.parseInt(hands[i].substring(j,j+1));
                // String color = hands[i].substring(j+2);
                if (color.startsWith("blue")){
                    if (num > blueMax) {
                        blueMax = num;
                    }
                }
                if (color.startsWith("red")){
                    if (num > redMax) {
                        redMax = num;
                    }
                }
                if (color.startsWith("green")){
                    if (num > greenMax) {
                        greenMax = num;
                    }
                }
            }
        }
        return blueMax * redMax * greenMax;
    }

    public static int getSumPowerSets(String filename) {
        int sum = 0;
        try (Scanner sc = new Scanner(new File(filename))) {
            while (sc.hasNextLine()) {
                String[] lines = sc.nextLine().split(": ");
                sum += getPowerSet(lines[1]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }

        return sum;
    }
}
