package all_days;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;


public class Five {
    public static ArrayList<ArrayList<Long>> maps = new ArrayList<>();
    public static ArrayList<Long> seeds = new ArrayList<>();

    public static Long getLocs(Long seed, ArrayList<ArrayList<Long>> maps) {
        Long newSeed = seed;
        
        for (int i = 0; i < maps.size(); i++) {
            ArrayList<Long> list = maps.get(i);
            boolean foundNewSeed = false;
            Long tempSeed = newSeed;
            for (int j = 0; j < maps.get(i).size(); j += 3) {
                Long dest = list.get(j);
                Long src = list.get(j+1);
                Long range = list.get(j+2);
                if (src <= newSeed && newSeed <= (src+range)) {
                    Long diff = newSeed - src;
                    // System.out.println(diff + " " + newSeed + " " + src);
                    newSeed = dest + diff;
                    foundNewSeed = true;
                    // System.out.println("seed changed: " + newSeed);
                    break;
                } 
            }
            if (!foundNewSeed) {
                newSeed = tempSeed;
            }
        }
        return newSeed;
    }

    public static Long getLowestLoc2(String filename) {
        try (Scanner sc = new Scanner(new File(filename))) {
            String[] seedsJoined = sc.nextLine().split(": ");
            String[] seedStrs = seedsJoined[1].split(" ");
            
            // Read maps first
            ArrayList<Long> currentList = null;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.contains("map")) {
                    currentList = new ArrayList<>();
                    maps.add(currentList);
                    continue;
                }
                if (!line.isEmpty() && line.substring(0, 1).matches("\\d+")) {
                    String[] mapNums = line.split(" ");
                    for (String num : mapNums) {
                        currentList.add(Long.parseLong(num));
                    }
                }
            }
    
            // Process seed ranges and find lowest location
            Long lowest = Long.MAX_VALUE;
            for (int i = 0; i < seedStrs.length; i += 2) {
                Long start = Long.parseLong(seedStrs[i]);
                Long range = Long.parseLong(seedStrs[i + 1]);
                System.out.println("Processing range starting at " + start + " with length " + range);
                
                // Process the range in chunks to avoid memory issues
                Long chunkSize = 1_000_000L;
                for (Long j = 0L; j < range; j += chunkSize) {
                    Long endOfChunk = Math.min(j + chunkSize, range);
                    
                    // Process each seed in this chunk
                    for (Long k = 0L; k < endOfChunk - j; k++) {
                        Long seed = start + j + k;
                        Long location = getLocs(seed, maps);
                        if (location < lowest) {
                            lowest = location;
                        }
                    }
                    
                    // Print progress every chunk
                    System.out.println("Processed up to " + (start + j + chunkSize) + " (" + 
                        ((j + chunkSize) * 100 / range) + "%)");
                }
            }
            
            System.out.println("Final lowest location: " + lowest);
            return lowest;
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return 0L;
        }
    }
    
    // public static Long getLowestLoc2(String filename) {
    //     ArrayList<Long> locations = new ArrayList<>();
    //     try (Scanner sc = new Scanner(new File(filename))) {
    //         String[] seedsJoined = sc.nextLine().split(": ");
    //         String[] seedStrs = seedsJoined[1].split(" ");
            
    //         for (int i = 0; i < seedStrs.length; i+=2) {
    //             Long seedRange = Long.parseLong(seedStrs[i+1]);
    //             for (int j = 0; j < seedRange; j++) {
    //                 seeds.add(Long.parseLong(seedStrs[i]) + j);
    //             }

    //             // seeds.add(Long.parseLong(seedStrs[i]));
    //         }
    //         ArrayList<Long> currentList = null;
    //         while (sc.hasNextLine()) {
    //             String line = sc.nextLine();
    //             if (line.contains("map")) {
    //                 currentList = new ArrayList<>();
    //                 maps.add(currentList);
    //                 continue;
    //             }
    //             if (!line.isEmpty() && line.substring(0, 1).matches("\\d+")) {
    //                 // int list = listCounter;
    //                 ArrayList<Long> list = new ArrayList<>();
    //                 maps.add(list);
    //                 String[] mapNums = line.split(" ");
    //                 for (int i = 0; i < mapNums.length; i++) {
    //                     currentList.add(Long.parseLong(mapNums[i]));
    //                 }
    //             }
    //         }
    //         Long lowest = Long.MAX_VALUE;
    //         for (Long seed : seeds) {
    //             Long location = getLocs(seed, maps);
    //             // System.out.println("Seed: " + seed + ", Location: " + location);
    //             if (location < lowest) {
    //                 lowest = location;
    //             }
    //         }
    //         System.out.println("lowest: " + lowest);
    //         locations.add(lowest);
    //         // return lowest;
    //     } catch (FileNotFoundException e) {
    //         System.out.println("file not found");
    //     }
    //     // System.out.println(seeds);
    //     // System.out.println(maps);
    //     Long min = Long.MAX_VALUE;
    //    for (Long loc : locations) {
    //     if (loc < min) {
    //         min = loc;
    //     }
    //    }
    //    return min;
    // }

    public static Long getLowestLoc(String filename) {
        try (Scanner sc = new Scanner(new File(filename))) {
            String[] seedsJoined = sc.nextLine().split(": ");
            String[] seedStrs = seedsJoined[1].split(" ");
            
            for (int i = 0; i < seedStrs.length; i++) {
                seeds.add(Long.parseLong(seedStrs[i]));
            }
            ArrayList<Long> currentList = null;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.contains("map")) {
                    currentList = new ArrayList<>();
                    maps.add(currentList);
                    continue;
                }
                if (!line.isEmpty() && line.substring(0, 1).matches("\\d+")) {
                    // int list = listCounter;
                    ArrayList<Long> list = new ArrayList<>();
                    maps.add(list);
                    String[] mapNums = line.split(" ");
                    for (int i = 0; i < mapNums.length; i++) {
                        currentList.add(Long.parseLong(mapNums[i]));
                    }
                }
            }
            Long lowest = Long.MAX_VALUE;
            for (Long seed : seeds) {
                Long location = getLocs(seed, maps);
                System.out.println("Seed: " + seed + ", Location: " + location);
                if (location < lowest) {
                    lowest = location;
                }
            }
            System.out.println("lowest: " + lowest);
            
            return lowest;
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }
        // System.out.println(seeds);
        // System.out.println(maps);
        return (long) 0;
    }
    
}
