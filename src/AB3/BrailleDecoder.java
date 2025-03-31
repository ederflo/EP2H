package AB3;
import AB3.Interfaces.Decoder;
import AB3.Provided.BrailleEncoder;
import AB3.Provided.TreeNode;

/**
 * The class implements a decoders, which decodes Braille symbols (bitmaps) into ASCII characters.
 * <p>It utilizes a binary search tree, to find ASCII characters corresponding to e letter's Braille
 * binary encoding.</p>
 */
public class BrailleDecoder implements Decoder {
    private static final int BITMAP_HEIGHT=3;
    private static final int BITMAP_WIDTH=2;
    private static final char SPACE_SYMBOL=' ';
    private BrailleSymbolTree decoderTree;

    /**
     * Constructs a BrailleDecoder object that decodes Braille symbols (bitmaps) into their
     * corresponding ASCII characters, utilizing a predefined binary search tree.
     *
     * @param encoder     the Braille encoder that corresponds to this decoder. Required by
     *                    the construction of the binary search tree.
     *                    Precondition: ( encoder != null )
     */
    public BrailleDecoder(BrailleEncoder encoder){
        decoderTree = new BrailleSymbolTree(encoder);
    }

    /**
     * Decodes a Braille bitmap into its corresponding ASCII character.
     * The bitmap is represented as a 2D array of characters. The raised dots in the
     * Braille bitmap are identified based on the provided `dotSymbol`. Uses a binary
     * encoding derived from the bitmap to determine the ASCII equivalent via a Braille
     * symbol tree.
     *
     * @param bitMap a 2D character array representing the Braille bitmap.
     *               The array must have dimensions corresponding to the expected
     *               Braille format (height and width). If the input is null or has
     *               incompatible dimensions, a null character (0) is returned.
     * @param dotSymbol the character that represents a raised dot in the Braille bitmap.
     * @return the corresponding ASCII character for the given Braille bitmap.
     *         Returns a space (' ') if the character is unknown or cannot be decoded,
     *         and a null character (0) if the input is invalid.
     */
    public char decodeBitmap(char[][] bitMap, char dotSymbol) {
        char result = ' ';
        if (bitMap == null || bitMap.length != BITMAP_HEIGHT || bitMap[0].length != BITMAP_WIDTH || bitMap[1].length != BITMAP_WIDTH || bitMap[2].length != BITMAP_WIDTH)
            return 0;

        byte brailleCharBin = 0;
        brailleCharBin |= (byte) ((bitMap[0][0] == dotSymbol ? 1 : 0)); // unterste Zeile -> Bit 0
        brailleCharBin |= (byte) ((bitMap[1][0] == dotSymbol ? 1 : 0) << 1); // mittlere Zeile  -> Bit 1
        brailleCharBin |= (byte) ((bitMap[2][0] == dotSymbol ? 1 : 0) << 2); // oberste Zeile   -> Bit 2

        // Zweite Spalte: von oben nach unten
        brailleCharBin |= (byte) ((bitMap[0][1] == dotSymbol ? 1 : 0) << 3); // oberste Zeile   -> Bit 3
        brailleCharBin |= (byte) ((bitMap[1][1] == dotSymbol ? 1 : 0) << 4); // mittlere Zeile  -> Bit 4
        brailleCharBin |= (byte) ((bitMap[2][1] == dotSymbol ? 1 : 0) << 5); // unterste Zeile  -> Bit 5

        TreeNode node = decoderTree.getNode(brailleCharBin);
        if (node != null && node.getSymbol() != 0)
            result = node.getSymbol();

        return result;
    }
}



