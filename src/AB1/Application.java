package AB1;

/**
 * The Application class serves as the entry point to the program.
 * <p>This class is used to test and demonstrate the functionality of printing text in Braille format
 * using the LinePrinter, configured with a BrailleFont and a BrailleEncoder.</p>
 * <p>Any implementation is not subject to examination and assessment by the EP2-Team, but serves as
 * free test hub for students.</p>
 */
public class Application {
    public static void main(String[] args) {

        // example from documentation
        LinePrinter lp = new  LinePrinter(
                                new BrailleFont(
                                        3,
                                        2,
                                        'o',
                                        '.',
                                        new BrailleEncoder()),
                                12,
                                4
                            );
        lp.printString("Hello!! World");
        lp.flush();

        lp = new  LinePrinter(
                new BrailleFont(
                        3,
                        2,
                        'o',
                        '.',
                        new BrailleEncoder()),
                5,
                4
        );
        lp.printString("H");
        lp.flush();

        lp = new  LinePrinter(
                new BrailleFont(
                        3,
                        2,
                        'o',
                        '.',
                        new BrailleEncoder()),
                5,
                4
        );
        lp.printString("");
        lp.flush();

        lp = new  LinePrinter(
                new BrailleFont(
                        3,
                        2,
                        'o',
                        '.',
                        new BrailleEncoder()),
                1,
                4
        );
        lp.printString(" ");
        lp.flush();

        lp = new  LinePrinter(
                new BrailleFont(
                        3,
                        2,
                        'o',
                        '.',
                        new BrailleEncoder()),
                5,
                4
        );
        lp.printString("H!allo");
        lp.flush();
    }
}