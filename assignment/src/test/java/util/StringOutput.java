package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Test helper, to be able to redirect a PrintStream into a String. Mocking a
 * print stream is a bit awkward and makes the tests very brittle, because the
 * actual method used to print is less important than the actual output
 * produced.
 *
 * So do not mock it, but instead prepare an implementation specific for testing
 * that collects the data output for later verification.
 *
 * To avoid cluttering the string, only one print stream can be produced per
 * string output.
 *
 * @author Pieter van den Hombergh
 */
public class StringOutput extends OutputStream {

    private final ByteArrayOutputStream byteArrayOuputStream = new ByteArrayOutputStream();

    @Override
    public void write( int b ) throws IOException {
        byteArrayOuputStream.write( b );
    }

    public void clear() {
        byteArrayOuputStream.reset();
    }

    @Override
    public String toString() {
        return new String( byteArrayOuputStream.toByteArray(), StandardCharsets.UTF_8 );
    }

    /**
     * Helps ensure only one PrintStream is created using this StringOutput.
     */
    private volatile boolean secondPrinter = false;

    /**
     * Create a print stream that writes to this string output.
     *
     * @return a print stream
     * @throws IllegalStateException on second invocation on this StringOutput.
     */
    public PrintStream asPrintStream() {
        if ( secondPrinter ) {
            throw new IllegalStateException( "cannot create multiple "
                    + "printstreams for one StringOutput" );
        }
        secondPrinter = true;
        return new PrintStream( this );
    }
}
