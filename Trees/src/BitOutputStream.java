import java.io.IOException;
import java.io.OutputStream;

public class BitOutputStream extends OutputStream {
	private int next = 0, bitsSoFar = 0;
	private OutputStream stream;
	
	public BitOutputStream(OutputStream stream) {
		this.stream = stream;
	}
	
	@Override
	public void flush() throws IOException {
		write(next);
		super.flush();
	}
	
	public void write(boolean b) throws IOException {
		int value = b ? 1 << 7 - bitsSoFar : 0;
		bitsSoFar++;
		next |= value;
		if (bitsSoFar == 8) {
			stream.write(next);
			next = 0;
			bitsSoFar = 0;
		}
	}
	
	@Override
	public void write(int b) throws IOException {
		for (int i = 7; i >= 0; i--)
			write((1 << i & b) != 0);
	}
}
