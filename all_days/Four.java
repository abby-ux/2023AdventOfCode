package all_days;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.io.File;

import java.io.FileNotFoundException;

public class Four {

    static int[] copies;

    public static int getLinePoints(String line) {
        int matches = 0;
        String sets[] = line.split(" \\| ");
        ArrayList<Integer> winning = new ArrayList<>();

        String[] winningSet = sets[0].trim().split("\\s+");
        String[] hasSet = sets[1].trim().split("\\s+");
        for (String num : winningSet) {
            if (!num.isEmpty()) {
                // System.out.println("Num: " + num);
                winning.add(Integer.parseInt(num));
            }
        }
        for (String num : hasSet) {
            if (!winning.isEmpty() && winning.contains(Integer.parseInt(num))) {
                matches++;
            }
        }
        return matches > 0 ? (int)Math.pow(2, matches-1) : 0;
    }

    // didnt end up using but could be helpful
    public static void initializeCopies(int size) {
        for (int i = 0; i < size; i++) {
            copies[i] = 1;
        }
    }
    
    public static int getTotalPoints(String filename) {
        int points = 0;
        try (Scanner sc = new Scanner(new File(filename))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] splitLine = line.split(": ");
                points += getLinePoints(splitLine[1]);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
        return points;
    }

    public static int getMatches(String line) {
        int matches = 0; 
        String[] sets = line.split(" \\| ");
        ArrayList<Integer> winning = new ArrayList<>();
        String[] winningSet = sets[0].split("\\s+");
        String[] hasSet = sets[1].split("\\s+");
        for (String num : winningSet) {
            if (!num.isEmpty()) {  
                try {
                    int number = Integer.parseInt(num.trim());  
                    winning.add(number);
                } catch (NumberFormatException e) {
                    // Skip invalid numbers
                    continue;
                }
            }
        }
        for (String num : hasSet) {
            if (!num.isEmpty() && winning.contains(Integer.parseInt(num))) {
                matches++;
            }
        }
        return matches;
    }

    public static int getScratchcards(String filename) {
        ArrayList<String> cards = new ArrayList<>();
        
        try (Scanner sc = new Scanner(new File(filename))) {
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(": ");
                cards.add(line[1]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return 0;
        }
        int[] cardCopies = new int[cards.size()];
        for (int i = 0; i < cardCopies.length; i++) {
            cardCopies[i] = 1;
        }
    
        for (int cardNum = 0; cardNum < cards.size(); cardNum++) {
            int matches = getMatches(cards.get(cardNum));
            
            for (int i = 0; i < matches && cardNum + i + 1 < cards.size(); i++) {
                // Add copies based on how many of the current card we have
                cardCopies[cardNum + i + 1] += cardCopies[cardNum];
            }
        }

        int totalCards = 0;
        for (int count : cardCopies) {
            totalCards += count;
        }
        return totalCards;
    }
}
