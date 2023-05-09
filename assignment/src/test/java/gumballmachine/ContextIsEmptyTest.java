package gumballmachine;

import java.io.PrintStream;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

/**
 * Cover default methods in interfaces to get a nice green.
 *
 * @author Pieter van den Hombergh {@code pieter.van.den.hombergh@gmail.com}
 */
public class ContextIsEmptyTest {

    Context ctx = new Context() {

        int ballCount = 0;

        @Override
        public void addBalls( int count ) {
            ballCount = count;
        }

        @Override
        public int getBallCount() {
            return ballCount;
        }

        @Override
        public boolean isWinner() {
            throw new UnsupportedOperationException( "isWinner Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Gumball dispense() {
            throw new UnsupportedOperationException( "dispense Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public PrintStream getOutput() {
            throw new UnsupportedOperationException( "getOutput Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void changeState( GumballState newState ) {
            throw new UnsupportedOperationException( "changeState Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }
    };

    @Test
    public void ballCountEmpty() {
        assertThat( ctx.isEmpty() ).isTrue();
    }

    @Test
    public void ballCountNotEmpty() {
        ctx.addBalls( 5 );
        assertThat( ctx.isEmpty() ).isFalse();
    }
}
