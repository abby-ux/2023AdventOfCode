package all_days;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;

public class Six {

    // public static HashMap<Integer, Integer> race = new HashMap<>();

    public static int getWaysToWin(int time, int dist) {
        int ways = 0;
        // System.out.println(dist);
        for (int i = 0; i <= time; i++) {
            int speed = i;
            int timeLeft = time-i;
            int totalDist = speed*timeLeft;
            // System.out.println(speed);
            // System.out.println(timeLeft);
            // System.out.println(totalDist);
            if (totalDist > dist) {
                ways++;
                // System.out.println(ways);
            }
        }
        return ways;
    }

    public static int getTotalWays(String filename) {
        int ways = 1;
        ArrayList<ArrayList<Integer>> races = fillRaces(filename);

        for (int i = 0; i < races.get(0).size(); i++) {
            int time = races.get(0).get(i);
            // System.out.println(races.get(0).get(i));
            int dist = races.get(1).get(i);
            // System.out.println(races.get(1).get(i));
            ways *= getWaysToWin(time, dist);
            // System.out.println(ways);
            
        }
        return ways;
    }

    public static ArrayList<ArrayList<Integer>> fillRaces(String filename) {
        ArrayList<ArrayList<Integer>> races = new ArrayList<>();
        ArrayList<Integer> totalTimes = new ArrayList<>();
        ArrayList<Integer> totalDists = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filename))) {

            String timeLine = sc.nextLine();
            String distLine = sc.nextLine();
            String[] times = timeLine.split(":")[1].trim().split("\\s+");
            String[] dists = distLine.split(":")[1].trim().split("\\s+");

            for (int i = 0; i < times.length; i++) {
                if (!times[i].isEmpty()) {
                totalTimes.add(Integer.parseInt(times[i]));
                // System.out.println(totalTimes.get(i));
                totalDists.add(Integer.parseInt(dists[i]));
                // System.out.println(totalDists.get(i));
            }}

            races.add(totalTimes);
            races.add(totalDists);

            

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
        return races;
    }

    public static long getWaysOneRace(String filename) {
        long ways = 0;

        try(Scanner sc = new Scanner(new File(filename))) {
            while (sc.hasNextLine()) {
                // String line[] = sc.nextLine().split(": ");

                String timeLine = sc.nextLine();
                String distLine = sc.nextLine();
                String[] times = timeLine.split(":")[1].trim().split("\\s+");
                String[] dists = distLine.split(":")[1].trim().split("\\s+");

                StringBuilder time = new StringBuilder();
                StringBuilder dist = new StringBuilder();

                for (int i = 0; i < times.length; i++) {
                    time.append(times[i]);
                    dist.append(dists[i]);
                }
                String timeString = time.toString();
                String distString = dist.toString();

                long timeInt = Long.parseLong(timeString);
                long distInt = Long.parseLong(distString);

                for (int i = 0; i <= timeInt; i++) {
                    long speed = i;
                    long timeLeft = timeInt-i;
                    long totalDist = speed*timeLeft;
                    if (totalDist > distInt) {
                        ways++;
                        // System.out.println(ways);
                    }
                }
                return ways;
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }


        return ways;
    }
    
}
