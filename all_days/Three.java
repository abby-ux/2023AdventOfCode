package all_days;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

class Coordinates {
    private int row;
    private int col;
    
    public Coordinates(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    public int getRow() { return row; }
    public int getCol() { return col; }
}

public class Three {
    public static boolean isSymbol(char c) {
        return !Character.isDigit(c) && c != '.';
    }
    
    public static boolean isAdjToSymbol(ArrayList<String> inputStr, int row, int startCol, int endCol) {
        // Check all adjacent positions including diagonals (8 total)
        // Just used Math.max/min to make sure we don't go out of bounds 
        for (int r = Math.max(0, row - 1); r <= Math.min(row + 1, inputStr.size() - 1); r++) {
            for (int c = Math.max(0, startCol - 1); c <= Math.min(endCol, inputStr.get(r).length() - 1); c++) {
                if (isSymbol(inputStr.get(r).charAt(c))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int countAdjToSymbol(ArrayList<String> inputStr, int row, int startCol, int endCol) {
        int adjCount = 0;
        for (int r = Math.max(0, row-1); r <= Math.min(row + 1, inputStr.size()-1); r++) {
            for (int c = Math.max(0, startCol-1); c <= Math.min(endCol+1, inputStr.get(r).length()-1); c++) {
                if ((inputStr.get(r).charAt(c) == '*')) {
                    adjCount++;
                }
            }
        }
        return adjCount;
    }
    
    public static int getPartsSum(String filename) {
        int sum = 0;
        ArrayList<String> inputStr = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filename))) {
        while (sc.hasNextLine()) {
            inputStr.add(sc.nextLine());
        }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            return 0;
        }

        for (int row = 0; row < inputStr.size(); row++) {
            String line = inputStr.get(row);
            int startCol = 0;
            
            while (startCol < line.length()) {
                while (startCol < line.length() && !Character.isDigit(line.charAt(startCol))) {
                    startCol++;
                }
                if (startCol >= line.length()) {
                    break;
                }

                int endCol = startCol;
                while (endCol < line.length() && Character.isDigit(line.charAt(endCol))) {
                    endCol++;
                }

                String partNum = line.substring(startCol, endCol);
                if (isAdjToSymbol(inputStr, row, startCol, endCol)) {
                    sum += Integer.parseInt(partNum);
                    // System.out.println("Part number: " + partNum);
                }
                
                startCol = endCol;
            }
        }
        
        return sum;
    }

    public static ArrayList<Integer> getBorderingNumbers(ArrayList<String> inputStr, int row, int col) {
        ArrayList<Integer> borderingNums = new ArrayList<>();
        for (int r = Math.max(0, row-1); r <= Math.min(row + 1, inputStr.size() - 1); r++) {
            int c = Math.max(0, col-1);
            String line = inputStr.get(r);
            while(c <= Math.min(col + 1, line.length()-1)) {
                if (!Character.isDigit(line.charAt(c))) {
                    c++;
                    continue;
                }
                int numStart = c;
                while (numStart > 0 && Character.isDigit(line.charAt(numStart - 1))) {
                    numStart--;
                }
                
                int numEnd = c;
                while (numEnd < line.length() && Character.isDigit(line.charAt(numEnd))) {
                    numEnd++;
                }

                String partNum = line.substring(numStart, numEnd);
                borderingNums.add(Integer.parseInt(partNum));

                c = numEnd;
            }
        }
        return borderingNums;
    }

    public static int getGearRatio(String filename) {
        int sumOfGearRatios = 0;
        ArrayList<String> inputStr = new ArrayList<>();
        
        try (Scanner sc = new Scanner(new File(filename))) {
            while (sc.hasNextLine()) {
                inputStr.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            return 0;
        }
    
        for (int row = 0; row < inputStr.size(); row++) {
            String line = inputStr.get(row);
            for (int col = 0; col < line.length(); col++) {
                if (line.charAt(col) == '*') {
                    ArrayList<Integer> adjacentNumbers = getBorderingNumbers(inputStr, row, col);
                    // Check if this is a valid gear (exactly 2 adjacent numbers)
                    if (adjacentNumbers.size() == 2) {
                        int gearRatio = adjacentNumbers.get(0) * adjacentNumbers.get(1);
                        sumOfGearRatios += gearRatio;
                    }
                }
            }
        }
        return sumOfGearRatios;
    }
}