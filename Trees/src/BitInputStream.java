import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitInputStream extends InputStream {
	private int currentInt, bitsLeft = 0;
	private InputStream stream;

	/**
	 * Creates a BitInputStream, with a stream to write the data to
	 * 
	 * @param stream
	 *            - the stream to read from
	 */
	public BitInputStream(InputStream stream) {
		this.stream = new BufferedInputStream(stream);
	}

	/**
	 * @return the number of bytes currently available
	 * @throws IOException
	 *             if an error occurs
	 */
	@Override
	public int available() throws IOException {
		return stream.available();
	}

	/**
	 * @return the number of bits available
	 * @throws IOException
	 *             if an error occurs
	 */
	public int bitsAvailable() throws IOException {
		return 8 * stream.available() + bitsLeft;
	}

	/**
	 * Closes this output stream
	 * 
	 * @throws IOException
	 *             in case the bit cannot be read
	 */
	@Override
	public void close() throws IOException {
		stream.close();
	}

	/**
	 * Reads a byte from this output stream
	 * 
	 * @return the byte
	 * @throws IOException
	 *             in case the byte cannot be read
	 */
	@Override
	public int read() throws IOException {
		int result = 0;
		for (int i = 0; i < 8; i++)
			result = result << 1 | (readBit() ? 1 : 0);
		return result;
	}

	/**
	 * Reads a single bit from this output stream
	 * 
	 * @return the bit
	 * @throws IOException
	 *             in case the bit cannot be read
	 */
	public boolean readBit() throws IOException {
		if (bitsLeft == 0) {
			currentInt = stream.read();
			bitsLeft = 8;
		}
		return (currentInt & 1 << --bitsLeft) != 0;
	}
}
