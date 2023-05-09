package gumballmachine;

import java.io.PrintStream;

/**
 * Context interface for the gumball machine. Abstract 'super' of the
 * context object. Interface with default methods where relevant and meaningful. 
 * This interface is package private and faces the state machine and states.
 *
 * @author Pieter van den Hombergh
 */
interface Context {

    /**
     * Add balls.
     *
     * @param count of balls to add.
     *
     */
    void addBalls( int count );

    /**
     * Count balls left.
     *
     * @return the ball count.
     */
    int getBallCount();

    /**
     * Check if the customer is lucky and can dispense another ball.
     *
     * @return true if another ball can and should be dispensed.
     */
    boolean isWinner();

    /**
     * Produce a ball.
     *
     * @return a ball
     */
    Gumball dispense();

    /**
     * Get the print stream. The implementation may default to System.out.
     * In the test context, we typically provide an output stream that we can
     * 'catch' and test the contents of.
     * @return the print stream defaults to system out.
     */
    PrintStream getOutput();

    /**
     * Check if all balls are dispensed.
     *
     * @return true if ball count == 0, false otherwise.
     */
    default boolean isEmpty() {
        return 0 == getBallCount();
    }

    /**
     * Change state, executing exit and entry methods on the go.
     *
     * @param newState next state
     *
     */
    void changeState( GumballState newState );
}
