package AB3;
import AB3.Interfaces.Tree;
import AB3.Provided.BrailleEncoder;
import AB3.Provided.TreeNode;

/**
 * The BrailleSymbolTree class represents a binary search tree structure for Braille symbol encoding and decoding.
 * It utilizes a binary representation of Braille characters to navigate the tree by using the binary encoding as path
 * descriptor. The class supports adding and retrieving leaf nodes within the tree, which represent ASCII characters
 * corresponding to their Braille binary format.
 */
public class BrailleSymbolTree implements Tree {
    private TreeNode root;
    private BrailleEncoder encoder = null;
    private static final byte whiteSpaceByte = 0b000000;


    /**
     * Constructs a new {@code BrailleSymbolTree} instance utilizing the given encoder.
     * <p>To build the search tree, all ASCII characters for the letters 'a' to 'z' and the ASCII character
     * for the white space (' '), having a binary encoding of {@code 0b000000} are inserted into the tree via {@code addNode()}.</p>
     * @param encoder     the {@code BrailleEncoder} used to convert ASCII characters into their Braille binary representation.
     *                    <p>Precondition: ( encoder != null )</p>
     */
    public BrailleSymbolTree(BrailleEncoder encoder) {
        this.encoder = encoder;
        root = new TreeNode();
        addNode(' ');
        for (char character = 'a'; character <= 'z'; character++)
            addNode(character);
    }

    /**
     * Inserts a new node into the BrailleSymbolTree based on the given ASCII character. The BrailleSymbolTree
     * has a constant depth of 7 ( 6 intermediate layers + leaves ). Symbols are stored in leave nodes only,
     * as all intermediate nodes are navigation nodes.
     * <p>A character's binary encoding is used as navigation aid, to traverse the binary tree. It is processed
     * from LSB to MSB, where each bit denotes a traversal to the left, if not set ({@code 0}), or a traversal to the
     * right, if set ({@code 1}), at the tree's corresponding layer.
     * A set LSB, for example, denotes a traversal to {@code right} at the tree's root node.</p>
     * <p>If a leave node is reached, the {@code asciiCharacter} is stored within the node's {@code symbol variable}</p>
     * <p>If the character is a white space (' '), a binary encoding of (0b000000) is assumed, otherwise
     * the encoder is used to calculate the binary encoding.</p>
     *
     * @param asciiCharacter the ASCII character to be inserted as a symbol into the tree.
     */
    @Override
    public void addNode(char asciiCharacter) {
        byte brailleCharBin = asciiCharacter == ' ' ? whiteSpaceByte : encoder.toBinary(asciiCharacter);
        TreeNode currentNode = root;
        for (int i = 0; i < 6; i++) {
            TreeNode nextNode;
            if ((brailleCharBin & (1 << i)) == 0) {
                nextNode = currentNode.getLeft();
                if (nextNode == null) {
                    nextNode = new TreeNode();
                    if (i == 5)
                        nextNode.setSymbol(asciiCharacter);
                    currentNode.setLeft(nextNode);
                }
            }
            else {
                nextNode = currentNode.getRight();
                if (nextNode == null) {
                    nextNode = new TreeNode();
                    if (i == 5)
                        nextNode.setSymbol(asciiCharacter);
                    currentNode.setRight(nextNode);
                }
            }
            currentNode = nextNode;
        }
    }


    /**
     * Traverses the binary tree based on the given encoded Braille representation of a ASCII character and
     * retrieves the corresponding leaf node, or returns {@code null} if the given binary encoding is not found
     * within the tree.
     * <b>The traversal is performed bit by bit, starting from the least significant bit.
     * A bit value of {@code 0} traverses left, and a bit value of {@code 1} traverses right.</b>
     *
     * @param encoded the byte encoding used to navigate the binary tree. Each bit represents a direction in the tree,
     *                where {@code 0} navigates to the left child and {@code 1} navigates to the right child.
     * @return the {@code TreeNode} at the leaf corresponding to the encoded path, or {@code null} if the path
     *         is invalid or the encoded value does not lead to a complete traversal of the tree.
     */
    @Override
    public TreeNode getNode(byte encoded) {
        TreeNode result = root;
        for (int i = 0; i < 6; i++) {
            if ((encoded & (1 << i)) == 0)
                result = result.getLeft();
            else
                result = result.getRight();
            if (result == null)
                break;
        }
        return result;
    }


    /**
     *   Method is required for submission testing.
     *   DO NOT EDIT.
     */
    @Override
    public TreeNode getRoot() {
        return root;
    }
}
