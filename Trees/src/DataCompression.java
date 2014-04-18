import java.io.File;
import java.io.IOException;

public class DataCompression {
	public static void main(String[] args) throws IOException {
		File f = new File("files/test.pre");
		FileCompressor.compress(f, "aba");
		System.out.println(FileCompressor.decompress(f));
	}
}
