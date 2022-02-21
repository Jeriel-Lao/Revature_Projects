package com.revature.Calculator;

import java.util.Scanner;

class OutOfBoundsException extends Exception {
    public OutOfBoundsException() {
        System.out.println("That is not a valid input, please try again.");
    }
}

public class Calculator {
    public static void main(String[] args) throws InterruptedException {
        Scanner scan = new Scanner(System.in);
        double input1 = 0, input2 = 0;
        int input3 = 1;
        boolean flag = false;
        String check = "";
        do {
            //read inputs
            do {
                try {
                    flag = false;
                    System.out.print("Enter first number: ");
                    input1 = scan.nextDouble();
                } catch (Exception e) {
                    System.out.println("That is not a valid input, please try again.");
                    flag = true;
                    System.out.println(flag);
                    System.out.println(input1);
                }
            } while (flag);
            do {
                try {
                    System.out.print("Enter second number: ");
                    flag = false;
                    input2 = scan.nextDouble();
                } catch (Exception e) {
                    System.out.println("That is not a valid input, please try again.");
                    flag = true;
                }
            } while (flag);
            //choose operator
            System.out.println("""
                    Select Option:
                    1 - Add
                    2 - Subtract
                    3 - Multiply
                    4 - Divide""");
            do {
                try {
                    flag = false;
                    input3 = scan.nextInt();
                    if (input3 < 1 || input3 > 4) {
                        throw new OutOfBoundsException();
                    }
                } catch (OutOfBoundsException e) {
                    e.getMessage();
                    flag = true;
                }
            } while (flag);
            switch (input3) {
                case 1:
                    System.out.println("Answer: " + (input1 + input2));
                    break;
                case 2:
                    System.out.println("Answer: " + (input1 - input2));
                    break;
                case 3:
                    System.out.println("Answer: " + (input1 * input2));
                    break;
                case 4:
                    System.out.println("Answer: " + (input1 / input2));
            }
            //operation complete, repeat or exit
            do {
                try {
                    flag = false;
                    System.out.print("Would you like to exit? y/n: ");
                    check = scan.next();
                    if ( !( check.equalsIgnoreCase("y") || check.equalsIgnoreCase("n") || check.equalsIgnoreCase("yes") || check.equalsIgnoreCase("no") || check.equalsIgnoreCase("yes.") || check.equalsIgnoreCase("no.") ) ) {
                        throw new OutOfBoundsException();
                    }
                } catch (OutOfBoundsException e) {
                    e.getMessage();
                    flag = true;
                }
            } while(flag);
            if (check.equalsIgnoreCase("y") || check.equalsIgnoreCase("yes") || check.equalsIgnoreCase("yes."))
                flag = true;
        }while (!flag);
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nShutting down");
        for (int i = 0; i < 3; i++) {
            Thread.sleep(800);
            System.out.print(" .");
        }
        Thread.sleep(800);
        System.out.println("\n\nGoodbye");
    }
}


