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
		// FileCompressor.compress(f, "Hello World!");
		// File f = new File("files/test.jpre");
		// FileCompressor.compressJ(f, "Another Test! WHEEEEEEEEEEEEEEEEEE! IT WORKS! IT WORKS! IT COMPLETELY WORKS!");
		// System.out.println(FileCompressor.decompressJ(f));
		test(FileCompressor.decompress(new File("files/lilwomen.pre")));
		// System.out.println(FileCompressor.decompress(new File("files/constitution.pre")));
		// /FileCompressor.compressJ(new File("files/test.jpre"),
		// FileCompressor.decompress(new File("files/constitution.pre")));
		// System.out.println(FileCompressor.decompressJ(new File("files/test.jpre")));
		// test("ASDFASDFSAGASTQA#1251234234214");
	}

	public static void test(String test) throws IOException {
		File pre = new File("files/test.pre");
		File jpre = new File("files/test.jpre");
		FileCompressor.compress(pre, test);
		System.out.println(FileCompressor.decompress(pre).equals(test) ? "PASS" : "FAIL");
		FileCompressor.compressJ(jpre, test);
		System.out.println(FileCompressor.decompressJ(jpre).equals(test) ? "PASS" : "FAIL");
	}
}
