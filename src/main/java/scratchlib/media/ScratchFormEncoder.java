package scratchlib.media;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import scratchlib.objects.fixed.data.ScratchObjectByteArray;
import scratchlib.objects.fixed.forms.ScratchObjectForm;


/**
 * Class with static methods for encoding images as {@link ScratchObjectForm}
 * instances, using Scratch's integer array encoding algorithm.
 */
public class ScratchFormEncoder
{
    private ScratchFormEncoder()
    {
    }

    /**
     * Encodes the given image as a Scratch form object.
     *
     * @param img The image to encode.
     * @return The image encoded as a form.
     */
    public static ScratchObjectForm encode(BufferedImage img)
    {
        short w = (short) img.getWidth();
        short h = (short) img.getHeight();

        ScratchObjectByteArray bytes = encodeByteArray(img);

        return new ScratchObjectForm(w, h, (short) 32, bytes);
    }

    /**
     * Encodes the given image with Scratch's encoding algorithm and returns a
     * byte array containing the resulting bytes.
     *
     * @param img The image to encode.
     * @return The encoded byte array.
     */
    public static ScratchObjectByteArray encodeByteArray(BufferedImage img)
    {
        int w = (short) img.getWidth();
        int h = (short) img.getHeight();

        int[] pixels = img.getRGB(0, 0, w, h, null, 0, w);

        return new ScratchObjectByteArray(encodeRaw(pixels));
    }

    /**
     * Encodes an array of pixel values with Scratch's integer array encoding
     * algorithm and returns the resulting bytes.
     *
     * @param pixels The array of pixel values to encode.
     * @return The encoded bytes.
     */
    public static byte[] encodeRaw(int[] pixels)
    {
        /*
         * Algorithm purpose: Compress an array of ints.
         *
         * Note that this implementation here is pretty lazy, in that it just
         * writes one bulk of literal words without that much compression.
         *
         * General steps:
         * 1) write amount of actual pixels
         * 2) write pairs of N and D:
         *    - L is the length of D
         *    - C is a data code describing the type of D
         *    - N is L shifted 2 bits to the left, OR'd with C
         *    - D is the data and depends on C.
         *      If C = 0, then there is no data (D is 0 bytes long).
         *      If C = 1, then D is 1 byte. When read, that byte is repeated 4x
         *          to form a word, and then that word is repeated L times.
         *      If C = 2, then D is a 4-byte word.
         *      If C = 3, then D is L consecutive words.
         *
         * As stated above, this implementation only writes one N (with
         * L = pixel array length and C = 3), and then writes L words.
         */

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            // write pixel count
            out.write(encodeInt(pixels.length));

            int runLength = pixels.length;
            int dataCode = 3;
            int n = runLength << 2 | dataCode;
            // write N
            out.write(encodeInt(n));

            // write D
            ByteBuffer pixelBytes = ByteBuffer.allocate(4);
            for (int pixel : pixels) {
                pixelBytes.rewind();
                pixelBytes.putInt(pixel);
                out.write(pixelBytes.array());
            }

            return out.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }

    /**
     * Encodes a single positive integer with Scratch's integer encoding
     * algorithm and returns the resulting bytes.
     *
     * @param i The positive integer to encode.
     * @return The byte representation of the given integer.
     */
    private static byte[] encodeInt(int i)
    {
        if (i < 0) {
            throw new IllegalArgumentException("cannot encode negative values");
        }

        /*
         * Algorithm purpose: Compress a given int as much as possible in the
         * binary representation by taking shortcuts for small values.
         *
         * The algorithm is best described by specifying the three possible
         * output categories.
         *
         * 1) 1 byte:  This is the int converted to a byte. Happens if i < 224.
         * 2) 2 bytes: This happens when the value is 224 or larger, but not too
         *             large. 224 has to be subtracted from the first byte, then
         *             it has to be multiplied by 256, and finally the second
         *             byte has to be added to form the original value.
         * 3) 5 bytes: The "magic" value 255 is written as the first byte. The
         *             following 4 bytes are the big-endian int representation.
         */

        if (i < 224) {
            return new byte[] { (byte) i };
        } else if (i <= (30 * 256 + 255)) {
            return new byte[] { (byte) (224 + (i / 256)), (byte) (i % 256) };
        } else {
            return ByteBuffer.allocate(5).put((byte) 255).putInt(i).array();
        }
    }
}
