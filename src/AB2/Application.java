package AB2;

import AB2.Interfaces.LineBuffer;

/**
 * The Application class serves as the entry point to the program.
 * <p>This class is used to test and demonstrate the functionality of printing text in Braille format
 * using the AdvancedLinePrinter, configured with a BrailleLineBuffer, a BrailleFont and a BrailleEncoder.</p>
 * <p>Any implementation is not subject to examination and assessment by the EP2-Team, but serves as
 * free test hub for students.</p>
 */
public class Application {
    public static void main(String[] args) {
        String[] result = new String[5];
        // example from documentation
        LineBuffer lineBuffer = new BrailleLineBuffer(3, 1.5, 0.3);
        AdvancedLinePrinter lp = new AdvancedLinePrinter(
                new BrailleFont(
                        3,
                        2,
                        'o',
                        '.',
                        new BrailleEncoder()),
                4,
                lineBuffer
        );

        System.out.printf("buffer size: %d,  characters in buffer: %d%n", lineBuffer.size(), lineBuffer.count());
        lp.printString("Hello");
        System.out.printf("buffer size: %d,  characters in buffer: %d%n", lineBuffer.size(), lineBuffer.count());

        lp.printString(" World");
        System.out.printf("buffer size: %d,  characters in buffer: %d%n", lineBuffer.size(), lineBuffer.count());

        System.out.println("modifying buffer...");
        lp.deleteCharacter(2);
        lp.insertCharacter(2, 'a');
        lp.deleteCharacter(3);
        lp.insertCharacter(3, 'r');
        lp.deleteCharacter(4);
        lp.deleteCharacter(8);
        lp.insertCharacter(5, 'm');
        lp.insertCharacter(6, 'y');
        lp.insertCharacter(7, ' ');
        System.out.printf("buffer size: %d,  characters in buffer: %d%n", lineBuffer.size(), lineBuffer.count());

        lp.flush();
        System.out.printf("buffer size: %d  characters in buffer: %d%n", lineBuffer.size(), lineBuffer.count());

        lp.printString("OK");
        System.out.printf("buffer size: %d  characters in buffer: %d%n", lineBuffer.size(), lineBuffer.count());

        lp.flush();

        // TODO: implementation of any developer specific tests (optional)
        lp.insertCharacter(1, 'm');
        System.out.printf("buffer size: %d,  characters in buffer: %d%n", lineBuffer.size(), lineBuffer.count());
        lp.insertCharacter(0, 'r');
        lp.flush();
        lp.insertCharacter(1, 'm');
        lp.insertCharacter(1, 'a');
        System.out.printf("buffer size: %d,  characters in buffer: %d%n", lineBuffer.size(), lineBuffer.count());
        lp.printBackSpace();
        System.out.printf("buffer size: %d,  characters in buffer: %d%n", lineBuffer.size(), lineBuffer.count());
        lp.insertCharacter(0, 'a');
        lp.deleteCharacter(2);
        lp.insertCharacter(0, 'a');
        lp.insertCharacter(0, 'a');
        lp.insertCharacter(0, 'a');
        lp.deleteCharacter(0);
        lp.deleteCharacter(0);
        lp.deleteCharacter(0);
        lp.deleteCharacter(0);
        lp.deleteCharacter(0);
        System.out.printf("buffer size: %d,  characters in buffer: %d%n", lineBuffer.size(), lineBuffer.count());
    }
}