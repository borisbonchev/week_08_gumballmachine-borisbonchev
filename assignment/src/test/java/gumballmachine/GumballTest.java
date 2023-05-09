package gumballmachine;

import java.util.Collection;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Test the ball, for coverage.
 * @author Pieter van den Hombergh
 */
public class GumballTest {

    /**
     * Test of toString method, of class Gumball.
     */
    @Test
    void testToString() {
        Gumball ball = new Gumball( "CORAL" );
        assertThat( ball.toString() )
                .as( "has colour" )
                .contains( "CORAL" );
    }

    @Test
    void newBalls() {
        Collection<Gumball> balls = Gumball.newBalls( 50 );
        assertThat( balls ).hasSize( 50 );
    }
}
