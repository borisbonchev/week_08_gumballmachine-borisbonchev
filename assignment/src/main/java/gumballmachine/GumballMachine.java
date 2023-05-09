package gumballmachine;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Head First Design Patterns Gum ball machine alternative implementation.
 *
 * @author Pieter van den Hombergh / Richard van den Ham
 */
class GumballMachine implements GumballAPI, Context {

    /**
     * Current state of Machine.
     */
    GumballState state;
    
    /**
     * List of balls available in inventory.
     */
    final List<Gumball> balls = new ArrayList<>();
    
    /**
     * OutputStream to write messages to. System.out by default.
     */
    PrintStream output = System.out;
    
    /**
     * Randomizer to determine winning chance.
     */
    final Random rnd = new Random();

    /**
     * Private constructor in order to make sure that the machine can only be 
     * created by one of the static init(...) factory methods.  
     * @param initialState 
     */
    private GumballMachine( GumballState initialState ) {
        state = initialState; // have this machine start in any state
    }

    /**
     * To prevent object leakage during construction, we us a factory method
     * that sets the initial state and invokes the init(state) method, to invoke
     * the enter method on the start state.
     * @return a machine set to the default SOLD_OUT state
     */
    static GumballMachine init() {
        return init( StateEnum.SOLD_OUT );
    }

    
    /**
     * To prevent object leakage during construction, we us a factory method
     * that sets the initial state and invokes the init(state) method, to invoke
     * the enter method on the start state.
     * @param initialState used to set as first state and initialize that state.
     * @return a machine set to the given initial state
     */
    static GumballMachine init( GumballState initialState ) {
        GumballMachine machine = new GumballMachine( initialState );
        machine.state.enter( machine );
        return machine;
    }

    @Override
    public void changeState( GumballState newState ) {
        state.exit( this );
        state = newState;
        state.enter( this );
    }

    /**
     * for tests.
     *
     * @return the current state
     */
    GumballState getState() {
        return state;
    }

    @Override
    public void draw() {
        state.draw( this );
    }

    @Override
    public int getBallCount() {
        return balls.size();
    }

    @Override
    public void addBalls( int count ) {
        balls.addAll( Gumball.newBalls( count ) );
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int count = getBallCount();
        result.append( "Fontys Magic Gumball Dispenser, Inc." )
                .append( "\nJava-enabled Standing Gumball Model #2022" )
                .append( "\nInventory: " )
                .append( count )
                .append( " gumball" )
                .append( ( count != 1 ) ? "s" : "" )
                .append( "\n" )
                .append( "Machine is in state " + state + "\n" );
        return result.toString();
    }

    /**
     * Remove one (the first) ball.
     */
    @Override
    public Gumball dispense() {
        return balls.remove( 0 );
    }

    /**
     * Defines a 10% chance to win.
     * @return true if the customer is a winner.
     */
    @Override
    public boolean isWinner() {
        return !isEmpty() && rnd.nextInt( 10 ) == 9;
    }

    @Override
    public void ejectCoin() {
        state.ejectCoin( this );
    }

    @Override
    public void insertCoin() {
        state.insertCoin( this );
    }

    @Override
    public void refill( int count ) {
        this.state.refill( this, count );
    }

    @Override
    public PrintStream getOutput() {
        return output;
    }

    @Override
    public void setOutput( PrintStream output ) {
        this.output = output;
    }
}
