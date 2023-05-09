package gumballmachine;

import java.io.PrintStream;

/**
 * Minimal public API for a gum ball state machine.
 *
 * @author Pieter van den Hombergh
 */
public interface GumballAPI {

    /**
     * Give the coin back, if any.
     */
    void ejectCoin();

    /**
     * Insert a new coin.
     */
    void insertCoin();

    /**
     * Add balls.
     *
     * @param count of balls to add.
     *
     */
    void refill( int count );

    /**
     * Draw to get a ball.
     */
    void draw();

    /**
     * Creates a machine.
     * The package private init() method is used to construct and initialize
     * a new gumball machine.
     *
     * @return a brand new machine.
     */
    static GumballAPI createMachine() {
        return GumballMachine.init();
    }

    /**
     * Connect to output.
     * Supports redirecting the output to a specific output stream (e.g a file)
     *
     * @param output to use for messages
     */
    void setOutput( PrintStream output );
}
