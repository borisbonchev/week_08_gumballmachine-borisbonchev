package gumballmachine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * Magically colored ball.
 *
 * @author Pieter van den Hombergh
 */
public class Gumball {

    /**
     * Randomizer to determine ball colour.
     */
    static Random random = new Random();

    /**
     * Name of color of this ball.
     */
    final String colorName;

    /**
     * List of supported colors.
     */
    static String[] availableColors = {"ALICEBLUE", "AZURE", "BLACK", "CHOCOLATE",
        "VIOLET", "RED", "ROSYBROWN", "YELLOW", "GREEN"};

    Gumball(String colorName) {
        this.colorName = colorName;
    }

    static Collection<Gumball> newBalls(int amount) {
        
        Collection<Gumball> balls = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            final String bc = availableColors[random.nextInt(availableColors.length)];
            final Gumball ball = new Gumball(bc);
            balls.add(ball);
        }
        return balls;
    }

    @Override
    public String toString() {
        return "Gumball{" + "color=" + colorName + '}';
    }

}
