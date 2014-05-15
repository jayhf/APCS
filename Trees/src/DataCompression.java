import java.io.File;
import java.io.IOException;

/**
 * 
 * A test file for my data compressor and decompressor.
 * 
 * @author Jay Fleischer
 * @version 1.0
 * 
 */
public class DataCompression {
	public static void main(String[] args) throws IOException {
		// BitInputStream s = new BitInputStream(System.in);
		// BitOutputStream s2 = new BitOutputStream(System.out);
		// for (int i = 0; i < 8; i++) {
		// boolean b = s.readBit();
		// // System.out.println(b);
		// s2.write(b);
		// }
		// s.close();
		// s2.close();
		FileCompressor.compressJ(new File("files/test.jpre"), "this is a test");
		System.out.println(FileCompressor.decompressJ(new File("files/test.jpre")));
		// System.out.write('a');
		File f = new File("files/test.pre");
		FileCompressor.compress(f, "Hello World!");
		System.out.println(FileCompressor.decompress(f));
	}
}
