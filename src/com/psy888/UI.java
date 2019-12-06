package com.psy888;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class UI {

    private Scanner scanner = new Scanner(System.in);


    public String getInputStr(String msg){
        System.out.println(msg);

        String userInput = scanner.next();

        System.out.println("userInput = " + userInput);
        return userInput;
    }
    public int getInputInt(String msg){
        System.out.println(msg);
        int userInput = scanner.nextInt();

        System.out.println("userInput = " + userInput);
        return userInput;
    }

    public  BetType multiplyChoice(String msg, BetType... betTypes){
        System.out.println(msg);
        for (int i = 0; i < betTypes.length; i++) {
            System.out.printf(i+1 + ". " + betTypes[i] + "\t");
            if(i>5) System.out.println();
        }
        int i = scanner.nextInt();
        return betTypes[i-1];
    }

    public  void printMsg(String msg){
        System.out.println(msg);
    }

    public  int[] getMultipleArgs(String msg, int numOfArgs){
//        scanner.
        System.out.println(msg);
        String userInput = scanner.nextLine().trim();
        while (userInput.isEmpty()){
            userInput = scanner.nextLine();
        }
        String[] argsArr = userInput.split(",|\\w ");
        if(argsArr.length==numOfArgs) {
            int[] result = new int[numOfArgs];
            for (int i = 0; i < numOfArgs; i++) {
                result[i] = Integer.parseInt(argsArr[i]);
            }
            return result;
        }
        return null;
    }
}
