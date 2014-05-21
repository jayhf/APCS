import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BitOutputStream extends OutputStream {
	private int next = 0, bitsSoFar = 0;
	private OutputStream stream;

	/**
	 * Creates a BitOutputStream
	 * 
	 * @param stream
	 *            - the stream to output the data to
	 */
	public BitOutputStream(OutputStream stream) {
		this.stream = new BufferedOutputStream(stream);
	}

	/**
	 * Flushes and closes this output stream
	 * 
	 * @throws IOException
	 *             if an error occurs
	 */
	@Override
	public void close() throws IOException {
		flush();
		stream.close();
	}

	/**
	 * Flushes this output stream, adding 0s at the end, in case there are a number of bits that cannot evenly be
	 * divided into bytes
	 * 
	 * @throws IOException
	 *             if an error occurs
	 */
	@Override
	public void flush() throws IOException {
		if (bitsSoFar != 0) {
			write(next);
			next = 0;
			bitsSoFar = 0;
		}
		stream.flush();
	}

	/**
	 * Writes a single bit to this output stream
	 * 
	 * @throws IOException
	 *             if the bit cannot be written
	 */
	public void write(boolean b) throws IOException {
		int value = b ? 1 << 7 - bitsSoFar : 0;
		next |= value;
		if (++bitsSoFar == 8) {
			stream.write(next);
			next = 0;
			bitsSoFar = 0;
		}
	}

	/**
	 * Writes a byte to this outputStream
	 * 
	 * @throws IOException
	 *             if the byte cannot be written
	 */
	@Override
	public void write(int b) throws IOException {
		for (int i = 7; i >= 0; i--)
			write((1 << i & b) != 0);
	}
}
