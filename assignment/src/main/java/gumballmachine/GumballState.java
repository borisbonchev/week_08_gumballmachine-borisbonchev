package gumballmachine;

/**
 * Abstract state that accepts events (method calls). Default implementations 
 * will typically be overridden when supported / meaningful in a certain state.
 *
 * @author Pieter van den Hombergh / Richard van den Ham
 */
interface GumballState {

    /**
     * Coin insert.
     *
     * @param gbm context of this state.
     */
    default void insertCoin( Context gbm ) {
        printReasonUnsupportedOperation( gbm );
    }

    /**
     * Coin eject.
     *
     * @param gbm context of this state.
     */
    default void ejectCoin( Context gbm ) {
        printReasonUnsupportedOperation( gbm );
    }

    /**
     * Release gum ball.
     *
     * @param gbm context of this state.
     */
    default void draw( Context gbm ) {
        printReasonUnsupportedOperation( gbm );
    }

    /**
     * Fill the machine.
     * Since this action can be executed from each and every state, a meaningful
     * implementation can be given on this level already.
     *
     * @param gbm context of this state.
     * @param count of balls to add.
     */
    default void refill( Context gbm, int count ) {
        gbm.addBalls( count );
        gbm.getOutput().println( "refilled with " + count + " ball(s)" );
    }

    /**
     * Print message explaining reason of this operation to be not supported 
     * in this state.
     * @param gbm 
     */
    default void printReasonUnsupportedOperation( Context gbm ) {
        gbm.getOutput().println( reason() );
    }

    /**
     * Method giving a reason why a method has no effect.
     *
     * @return the reason
     */
    String reason();

    /**
     * Invoke on change state away from this state.
     *
     * This implementation is a No-Op.
     *
     * @param ctx the context of this state
     */
    default void exit( Context ctx ) {
    }

    /**
     * Invoke on change state into this state.
     *
     * This implementation is a No-Op.
     *
     * @param ctx the context of this state
     */
    default void enter( Context ctx ) {
    }
}
