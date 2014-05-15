import java.io.File;
import java.io.IOException;

public class DataCompression {
	public static void main(String[] args) throws IOException {
		File f = new File("C:/Users/Jay/Desktop/files/test.pre");
		FileCompressor.compress(f, FileCompressor.decompress(new File("C:/Users/Jay/Desktop/files/lilwomen.pre")));
		System.out.println(FileCompressor.decompress(f));
	}
}
