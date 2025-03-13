package AB1;

import AB1.Interfaces.Encoder;

/**
 * The {@code BrailleFont} class represents a set of printable Braille characters of type {@code byte[][]} and corresponding
 * mappings from ASCII characters ({@code char}) to this set.
 * <p>All printable characters are initialized at construction time and are of equal size (monospaced).</p>
 */
public class BrailleFont implements AB1.Interfaces.Font {

    private static final int defaultHeight = 3;
    private static final int defaultWidth = 2;

    private final int height;   // height of characters
    private final int width;    // width of characters (remark: font is monospaced)

    /**
     * A 3-dimensional array containing printable Braille characters (bitmaps) in ascending alphabetic order.
     * <p>In detail, it is an array of 2-dimensional matrices (bitmaps):
     * {@code lowerCaseLetters[index of character][bitMap row][bitmap column]}. Each bitmap contains dot and space symbols
     * according to the letter's Braille cell configuration.</p>
     * <p>The array does not include representations for non-letter characters or capital letters.
     * Other characters, such as white space, are handled separately.</p>
     *
     */
    private final char[][][] lowerCaseLetters;    // bitmaps for all lowercase letters

    /**
     * Represents the white space character .
     * <p>This array provides a printable representation (bitmap) of a white space within Braille texts.</p>
     * <p>The array is initialized during the construction of the {@code BrailleFont} object.</p>
     */
    private final char[][] whiteSpace;    // bitmap for the white space character (contains space symbols only)


    /**
     * Constructs a {@code BrailleFont} object and calculates the font's bitmaps.
     *
     * @param height        number of lines of font's characters (bitmap height)
     * @param width         number of columns of font's characters (bitmap width)
     * @param dotSymbol     the character used to represent a filled cell (dot) within a character's bitmap.
     * @param spaceSymbol   the character used to represent an empty cell (space) within a character's bitmap.
     * @param encoder       the Braille encoder ({@code class BrailleEncoder}) used to calculate the font's bitmaps at construction time.
     *                      <p>Precondition: (encoder != null)</p>
     */
    // other size than 3x2 makes no sense
    public BrailleFont(int height, int width, char dotSymbol, char spaceSymbol, Encoder encoder) {
        this.height = height;
        this.width = width;
        lowerCaseLetters = new char[26][height][width];
        whiteSpace = new char[height][width];

        // fill bitmap array for every lower case letter
        for (int i = 0; i <= ('z' - 'a'); i++) {
            char charToParse = (char) ('a' + i);
            byte binary = encoder.toBinary(charToParse);

            for (int j = 0; j < height; j++) {
                for (int k = 0; k < width; k++) {
                    if (j < defaultHeight && k < defaultWidth) {
                        lowerCaseLetters[i][j][k] = ((binary >> j + (defaultHeight * k)) & 1) == 1 ? dotSymbol : spaceSymbol;
                    } else { // if size is bigger than expected, just set spaceSymbol
                        lowerCaseLetters[i][j][k] = spaceSymbol;
                    }
                }
            }
        }

        // fill whitespace array
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                whiteSpace[i][j] = spaceSymbol;
    }


    /**
     * Retrieves a bitmap ({@code char[][]}) of a given ASCII character.
     *
     * @param character the ASCII character for which a printable character will be returned.
     * @return a bitmap ({@code char[][]}) that corresponds to the given ASCII character.
     *         For letters, it returns the corresponding lowercase printable character from array {@code lowerCaseLetters[]}.
     *         For non-letters, it returns the representation of a white space ({@code whiteSpace}).
     */
    @Override
    public char[][] getBitmap(char character) {
        if (Character.isLetter(character))
            return lowerCaseLetters[Character.toLowerCase(character) - 'a'];

        return whiteSpace;
    }

    /**
     * Returns the font's height.
     *
     * @return the number of rows of a character's bitmap.
     */
    @Override
    public int getHeight(){
        return height;
    }
    /**
     * Returns the font's width (the font is monospaced).
     *
     * @return the number of columns of a character's bitmap.
     */
    @Override
    public int getWidth(){
        return width;
    }
}