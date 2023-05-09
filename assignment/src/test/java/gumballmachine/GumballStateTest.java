package gumballmachine;

import org.assertj.core.api.SoftAssertions;
import util.StringOutput;
import java.io.PrintStream;
import java.util.Map;
import java.util.function.BiConsumer;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static org.mockito.ArgumentMatchers.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;

/**
 * Example of using Parameterized tests with mockito.
 *
 * In this Junit5 test example we combine Mockito with a Parameterized test. We
 * read the test data from a CSV file:
 * src/test/resources/olifantysballs/testtable.csv.
 *
 * The csv file is read using junit5 csvfilesource. Note that the csv file can
 * have comments, as in lines that start with a #.
 *
 * Because the directory structure under src/test/resources/ mimics the
 * structure of the test sources, the test table file is in the same package as
 * this test class.
 *
 * The advantages and disadvantages of an external file are:
 * <ul>
 * <li> Advantage: The state transition table matches the one given on the web
 * site. This makes reading it easier and allows easy verification of
 * completeness, in particular for those triggers that should not result in a
 * state change.
 * </li>
 * <li> Disadvantage: The test methods are a bit more complex because they must
 * deal with all combinations of triggers, target states and guard values.
 * These can however easily be expressed.
 * </li>
 * </ul>
 *
 * All test methods continue with mock training, e.g. set the winner and empty
 * values, lookup up the initial state, final state if given, and triggerAction
 * and then verify the outcomes. There is one test for each of the outcomes:
 * <ul>
 * <li>Proper or no state transition</li>
 * <li>Dispensing of ball</li>
 * <li>add ball invocation of refill</li>
 * <li>Proper message</li>
 * </ul>
 *
 * @author Pieter van den Hombergh
 */
@TestMethodOrder( MethodOrderer.class )
@ExtendWith( MockitoExtension.class )
public class GumballStateTest {

    /**
     * Mocked context of the state.
     */
    @Mock
    Context ctx;

    /**
     * Output to be used.
     */
    StringOutput sout = new StringOutput();
    PrintStream out = sout.asPrintStream();

    /**
     * Map the trigger name from the csv file to a lambda expression of type
     * {@code BiConsumer<Context,GumballState>}.
     */
    static Map<String, BiConsumer<Context, GumballState>> triggerMap = Map.of(
            "insertCoin", ( c, s ) -> s.insertCoin( c ),
            "ejectCoin", ( c, s ) -> s.ejectCoin( c ),
            "draw", ( c, s ) -> s.draw( c ),
            "refill", ( c, s ) -> s.refill( c, 5 )
    );

    /**
     * Constructor sets up the context and connects input and output.
     */
    @BeforeEach
    void setupMocks() {
        lenient().when( ctx.getOutput() ).thenReturn( out );
    }
    
    /**
     * Assert the proper state transition. If the End State is none-null, ensure
     * that changeState(...) is invoked on the context with parameter of said End
     * State. If the End State is null, ensure that changeState(...) is called 0
     * times or never.
     *
     * @param initialStateName name of initial state.
     * @param triggerName      trigger to lookup in lambda map.
     * @param finalStateName   name of resulting state.
     * @param winner           true is winner.
     * @param empty            true if empty.
     * @param dispenseCount    the number of draws in the test, use in
     *                         {@code verify(..., times(..))}
     * @param addBallsCount    number of balls to add.
     * @param expectedText     expected text output by state.
     */
    @MockitoSettings( strictness = Strictness.WARN )
    @ParameterizedTest
    @CsvFileSource( resources = { "testtable.csv" } )
    public void t1verifyStateTransition(
            String initialStateName, String triggerName,
            String finalStateName, boolean empty, boolean winner,
            int dispenseCount, int addBallsCount, String expectedText ) {

        // Convert parameters to their corresponding type where necessary.
        // Train the mock for isEmpty() and isWinner().
        when(ctx.isEmpty()).thenReturn(empty);
        when(ctx.isWinner()).thenReturn(winner);
        // Invoke the trigger method.
        triggerMap.get(triggerName).accept(ctx, StateEnum.valueOf(initialStateName));
        // Verify that changeState(<finalState>) has been invoked the appropriate
        //    number of times (0 if no state change, 1 if new final state).
        SoftAssertions.assertSoftly(softly -> {
            if (finalStateName != null) {
                verify(ctx, atLeastOnce()).changeState(StateEnum.valueOf(finalStateName));
            } else {
                verify(ctx, never()).changeState(any());
            }
        });
        //TODO 01 Test correct state transition first and implement necessary parts of StateEnum afterwards.
    }

    /**
     * Assert that refill invokes the adds balls on the context.
     *
     * @param initialStateName name of initial state.
     * @param triggerName      trigger to lookup in lambda map.
     * @param finalStateName   name of resulting state.
     * @param winner           true is winner.
     * @param empty            true if empty.
     * @param dispenseCount    the number of draws in the test, use in
     *                         {@code verify(..., times(..))}
     * @param addBallsCount    number of balls to add.
     * @param expectedText     expected text output by state.
     */
    @ParameterizedTest
    @CsvFileSource( resources = { "testtable.csv" } )
    public void t2verifyRefillAddsBalls(
            String initialStateName, String triggerName,
            String finalStateName, boolean empty, boolean winner,
            int dispenseCount, int addBallsCount, String expectedText ) {

        triggerMap.get("refill").accept(ctx, StateEnum.valueOf(initialStateName));
        // Verify that addBalls is invoked on the context with anyInt() as
        // parameter. No training of Mock needed here.
        verify(ctx, atLeastOnce()).addBalls(anyInt());
        //TODO 02 Implement test refill first and implement necessary parts of StateEnum afterwards.
    }

    /**
     * Verify that a ball is dispensed.
     *
     * @param initialStateName name of initial state.
     * @param triggerName      trigger to lookup in lambda map.
     * @param finalStateName   name of resulting state.
     * @param winner           true is winner.
     * @param empty            true if empty.
     * @param dispenseCount    the number of draws in the test, use in
     *                         {@code verify(..., times(..))}
     * @param addBallsCount    number of balls to add.
     * @param expectedText     expected text output by state.
     */
    @ParameterizedTest
    @CsvFileSource( resources = { "testtable.csv" } )
    public void t3verifyDispense(
            String initialStateName, String triggerName,
            String finalStateName, boolean empty, boolean winner,
            int dispenseCount, int addBallsCount, String expectedText ) {

        triggerMap.get(triggerName).accept(ctx, StateEnum.valueOf(initialStateName));
        // Verify that the number of times the dispense() method is invoked 
        // on the context is correct. No training of Mock needed here.
        verify(ctx, times(dispenseCount)).dispense();
        //TODO 03 Test that balls are dispensed in proper states and implement necessary parts of StateEnum afterwards.
    }

    /**
     * Assert that correct message is produced.
     *
     * @param initialStateName name of initial state.
     * @param triggerName      trigger to lookup in lambda map.
     * @param finalStateName   name of resulting state.
     * @param winner           true is winner.
     * @param empty            true if empty.
     * @param dispenseCount    the number of draws in the test, use in
     *                         {@code verify(..., times(..))}
     * @param addBallsCount    number of balls to add.
     * @param expectedText     expected text output by state.
     */
    @MockitoSettings( strictness = Strictness.WARN )
    @ParameterizedTest
    @CsvFileSource( resources = { "testtable.csv" } )
    public void t4assertMessage(
            String initialStateName, String triggerName,
            String finalStateName, boolean empty, boolean winner,
            int dispenseCount, int addBallsCount, String expectedText ) {

        // Train the mock to return a new Gumball object with chosen color
        // on invocation of dispense()
        // Assert that the outputStream contains the expected text.
        when(ctx.dispense()).thenReturn(new Gumball("RED"));
        assertThat(ctx.dispense().toString()).contains("RED");
        //TODO 04 Make sure the message contains the correct info and implement necessary parts of StateEnum afterwards.
    }
}
