package gumballmachine;

import java.io.PrintStream;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import util.StringOutput;

/**
 * Verify that API messages are properly forwarded to the state. Test all
 * methods that have a concrete implementation in this class.
 *
 * @author Pieter van den Hombergh
 */
@ExtendWith(MockitoExtension.class)
public class GumballMachineTest {

    /**
     * Mock the GumballState as DOC when testing the Gumball machine (SUT).
     */
    @Mock
    GumballState state;

    /**
     * Make sure the context calls exit on the old state.
     */
    @Test
    public void changeStateCallsExit() {
        // Create a Gumball machine instance, passing the mocked state.
        GumballMachine gMachine = GumballMachine.init(state);
        // Let the machine change its state to the same 'old' state.
        gMachine.changeState(state);
        // Verify that the exit method is invoked on that state.
        verify(state, times(1)).exit(gMachine);
        // TODO 05 Test that exit is invoked on the old state
    }

    /**
     * Make sure that the constructor enters the initial state.
     */
    @Test
    public void constructorCallsEnter() {
        // The enter method on the initial state should be invoked at creation 
        // of a machine instance.
        GumballMachine gMachine = GumballMachine.init(state);
        verify(state, times(1)).enter(gMachine);
        // TODO 06 Test that constructor enters the initial state
    }

    /**
     * Ensure method forward from draw to draw(Context).
     */
    @Test
    public void drawCallsDrawWithContext() {
        // An invokation of draw() on the machine should result in an invokation
        // of draw(<Context>) on the initial state.
        GumballMachine gMachine = GumballMachine.init(state);
        gMachine.draw();
        verify(state, times(1)).draw(gMachine);
        // TODO 07 Test method forward from draw to draw(Context)
    }

    /**
     * Ensure method forward from ejectCoin to ejectCoin(Context).
     */
    @Test
    public void ejectCoinCallsEjectCoinWithContext() {
        // Invokation() of ejectCoin should result in an invokation
        // of ejectCoin(<Context>) on the initial state.
        GumballMachine gMachine = GumballMachine.init(state);
        gMachine.ejectCoin();
        verify(state, times(1)).ejectCoin(gMachine);
        // TODO 08 Test method forward from ejectCoin to ejectCoin(Context)
    }

    /**
     * Ensure method forward from insertCoin to insertCoin(Context).
     */
    @Test
    public void insertCoinCallsInsertCoinOnContext() {
        // Invokation() of insertCoin should result in an invokation
        // of insertCoin(<Context>) on the initial state.
        GumballMachine gMachine = GumballMachine.init(state);
        gMachine.insertCoin();
        verify(state, times(1)).insertCoin(gMachine);
        // TODO 09 Test method forward from insertCoin to insertCoin(Context)
    }

    /**
     * Ensure method forward from refill(int) insertCoin(Context,int).
     */
    @Test
    public void refillCallsRefillWithContextAndCount() {
        // Test that if refill is invoked on a machine with a certain number of
        // balls as parameter, that refill is invoked on the initial state 
        // with that same numer as parameter.
        GumballMachine gMachine = GumballMachine.init(state);
        gMachine.refill(13);
        verify(state, times(1)).refill(gMachine, 13);
        // TODO 10 Test method forward from refill(int) to insertCoin(Context,int)
    }

    /**
     * ToString is not empty.
     */
    @Test
    public void toStringTest() {
        GumballMachine gMachine = GumballMachine.init(state);
        assertThat(gMachine.toString()).isNotEmpty();
        // TODO 11 Test that toString on a machine object is not empty
    }

    /**
     * Assert that the default constructor called by the static method in the
     * API interface produces something useful.
     */
    @Test
    public void defaultMachinePerApiCall() {
        // Create a machine using the GumballAPI. Cast it to the implementation
        GumballMachine gMachine = (GumballMachine) GumballAPI.createMachine();
        // type and test it the default initial state is SOLD_OUT.
        assertThat(gMachine.getState()).isEqualTo(StateEnum.SOLD_OUT);
        // TODO 12 Test the default constructor goes to initial state
    }

    /**
     * Test if this is a fair machine, as in you win once in a while. Add a
     * plenty balls and have plenty coins ready and try until you are winner.
     * This method has a timeout of 500 milliseconds because we are not very
     * patient when unit testing.
     */
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    @Test
    public void isThereEverAWinner() {
        // Create a machine with enough balls. Insert coins and draw gumballs in
        GumballMachine gMachine = (GumballMachine) GumballAPI.createMachine();
        gMachine.refill(500);
        // a loop. Make sure that at least once your machine will answer positively
        // on isWinner(). The test will finish after a first winner case.
        boolean youWon = false;

        while (!youWon) {
            gMachine.insertCoin();
            gMachine.draw();
            if (gMachine.isWinner()) {
                youWon = true;
            }
        }
        assertThat(youWon).isEqualTo(true);
        // TODO 13 Test this is a fair machine, as in you win once in a while
    }

    /**
     * Ensure that setOutput indeed sets the output that is returned by
     * getOutput.
     */
    @Test
    void setOutputHasEffect() {
        GumballMachine gMachine = (GumballMachine) GumballAPI.createMachine();
        StringOutput output = new StringOutput();

        gMachine.setOutput(output.asPrintStream());
        assertThat(gMachine.getOutput()).isNotEqualTo(System.out);
        // TODO 14 Test set output by using a StringOutput instance
    }

    /**
     * Coverage, ensure that a machine is empty after the last ball is drawn.
     */
    @Test
    void machineWithOneBallIsEmptyAfterDispense() {
        GumballMachine gMachine = (GumballMachine) GumballAPI.createMachine();

        // isEmpty() should be true in initial state,
        // it should be false after a refill with 1 ball,
        // and it should be true after dispense again.
        // Use assertSoftly.
        SoftAssertions.assertSoftly( softly -> {
            softly.assertThat(gMachine.isEmpty()).isTrue();
            gMachine.refill(1);
            softly.assertThat(gMachine.isEmpty()).isFalse();
            gMachine.dispense();
            softly.assertThat(gMachine.isEmpty()).isTrue();
        });
        // TODO 15 Test ensure that a machine is empty after the last ball is drawn
    }
}
