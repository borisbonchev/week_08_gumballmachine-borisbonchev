/**
 * Idea from Head First Design Patterns (ISBN 9780596007126), implementing a
 * state machine using the state pattern, modified to use enum as state
 * implementation and made testable.
 *
 * <ul>
 * <li>This version uses Java 8 features which makes it possible to do a lot of
 * work in the {@link State} interface, using {@code default} and {@code static}
 * methods and avoids code duplication, while still being able to use a java
 * enum as the concrete state implementations.
 * <li>
 * There is one disadvantage with this approach: the methods will be in the
 * interface and in Java that means public, which is alleviated a bit by keeping
 * the state and possibly the implementing class package private, so that no one
 * outside the package can fiddle with these parts.
 * </ul>
 * <h2>The state diagram and class diagram</h2>
 *
 * <img width='640' style='vertical-align:middle' src='doc-files/statemachine.svg'
 *    alt='State machine'>
 * <img width='640' style='vertical-align:middle' src='doc-files/context.svg' 
 *   alt='class diagram of state behavior implementation'>
 *
 * @author Pieter van den Hombergh
 */
package gumballmachine;
