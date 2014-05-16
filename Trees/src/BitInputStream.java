import java.io.IOException;
import java.io.InputStream;

public class BitInputStream extends InputStream {
	private int currentInt, bitsLeft = 0;
	private InputStream stream;

	public BitInputStream(InputStream stream) {
		this.stream = stream;
	}

	@Override
	public int available() throws IOException {
		return stream.available();
	}

	public int bitsAvailable() throws IOException {
		return 8 * stream.available() + bitsLeft;
	}

	@Override
	public void close() throws IOException {
		stream.close();
	}

	@Override
	public int read() throws IOException {
		int result = 0;
		for (int i = 0; i < 8; i++)
			result = result << 1 | (readBit() ? 1 : 0);
		return result;
	}

	public boolean readBit() throws IOException {
		if (bitsLeft == 0) {
			currentInt = stream.read();
			bitsLeft = 8;
		}
		return (currentInt & 1 << --bitsLeft) != 0;
	}
}
