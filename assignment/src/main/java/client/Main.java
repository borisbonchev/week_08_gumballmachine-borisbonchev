package client;

import gumballmachine.GumballAPI;

/**
 * The application entry point. Not really very useful. This project is about
 * testing and using Java 11 features.
 *
 * @author Pieter van den Hombergh / Richard van den Ham
 */
public class Main {

    /**
     * Start the application.
     *
     * @param args not used
     */
    public static void main(String[] args) {

        GumballAPI machine = GumballAPI.createMachine();

        // Try to draw from empty machine
        insertCoinDraw(machine, 1);
        
        // Fill machine with 1 ball and draw that one
        machine.refill(1);
        insertCoinDraw(machine, 1);
        
        // Fill with 50 balls and see if you're a lucky winner
        // Be aware that in a WINNER state, invoking insertCoin will cause
        // the message "You should draw once more to get an extra ball"
        machine.refill(50);
        insertCoinDraw(machine, 30);
        // ... therefore, at the end your inventory will be 20 in all cases,
        // but you might not have payed for all of the 30 drawn balls.
    }

    private static void insertCoinDraw(GumballAPI machine, int numberOfTimes) {

        System.out.println("********************");
        System.out.println("MACHINE STATE BEFORE:");
        System.out.println( machine );
        System.out.println("********************");
        
        for (int i = 1; i <= numberOfTimes; i++) {
            System.out.println("ITERATION " + i);
            machine.insertCoin();
            machine.draw();
        }
        
        System.out.println("********************");
        System.out.println("MACHINE STATE AFTER:");
        System.out.println( machine );
        System.out.println("********************");
    }
}
