import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Scanner;

class Person {
	int id;
	String name;

	public Person(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return name + "\t" + id;
	}
}

public class Test {
	private static DecimalFormat format = new DecimalFormat("#######");

	public static Person getPerson(int personID) throws IOException {
		URL url = new URL("http://projecthm.horacemann.org/projectx/base.php");
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.setRequestMethod("POST");
		request.setDoOutput(true);
		request.setDoInput(true);
		OutputStreamWriter post = new OutputStreamWriter(request.getOutputStream());
		String data = URLEncoder.encode("uid", "UTF-8") + "=" + URLEncoder.encode(format.format(personID), "UTF-8");
		post.write(data);
		post.flush();
		post.close();
		Scanner s = new Scanner(request.getInputStream());
		s.useDelimiter("ASFASB13qefRWZU&DAWOHlrf");
		String str = s.next();
		s.close();
		if (str.contains("Sorry, you died."))
			throw new IOException("Person dead.");
		int startIndex = str.indexOf("Welcome to Project X <span style=color:white>")
				+ "Welcome to Project X <span style=color:white>".length();
		String name = str.substring(startIndex, str.indexOf("</span>", startIndex));
		return new Person(personID, name);
	}

	public static Person getPersonTarget(int previousID) throws IOException {
		URL url = new URL("http://projecthm.horacemann.org/projectx/base.php");
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.setRequestMethod("POST");
		request.setDoOutput(true);
		request.setDoInput(true);
		OutputStreamWriter post = new OutputStreamWriter(request.getOutputStream());
		String data = URLEncoder.encode("uid", "UTF-8") + "=" + URLEncoder.encode(format.format(previousID), "UTF-8");
		post.write(data);
		post.flush();
		post.close();
		Scanner s = new Scanner(request.getInputStream());
		s.useDelimiter("ASFASB13qefRWZU&DAWOHlrf");
		String str = s.next();
		s.close();
		String name = str.substring(str.indexOf("'schedule.submit()' style=color:white>")
				+ "'schedule.submit()' style=color:white>".length(), str.indexOf("</span>."));
		int startIndex = str.indexOf("<input type=text style='display:none;' name='uid' value=");
		startIndex += "<input type=text style='display:none;' name='uid' value=".length();
		int id = Integer.parseInt(str.substring(startIndex, str.indexOf(">", startIndex)));
		return new Person(id, name);
	}

	public static void main(String[] args) throws IOException {
	printChain(262623);
		// ExecutorService service = Executors.newFixedThreadPool(40);
		// System.out.println(getPerson(178371));
		/*
		 * Person p = new Person(178371, "");
		 * do
		 * System.out.println(p = getPersonTarget(p.id));
		 * while (!p.name.equals("Jay Fleischer"));
		 */
		/*
		 * for (int i = 00000; i < 400000; i += 10000) {
		 * final int index = 910000;//i;
		 * service.submit(new Runnable() {
		 * @Override
		 */// public void run() {
		int index = 9110000;
		/*for (int i = index; i < index + 10000; i = i % 10 < 5 ? i + 1 : i + 5)
			try {
				System.out.println(getPerson(i));
			} catch (Exception e) {}*/
		/*
		 * }
		 * });
		 * }
		 */
		/*
		 * for (int i = 39512; i < 267202; i = i % 10 < 5 ? i + 1 : i + 5)
		 * try {
		 * System.out.println(i);
		 * System.out.println(getPerson(i));
		 * } catch (Exception e) {}
		 * System.out.println("Tasks Submitted!");
		 */
	}

	public static void printChain(int startID) {
		try {
			Person previous = getPerson(startID);
			System.out.println(previous);
			while (true)
				System.out.println(previous = getPersonTarget(previous.id));
		} catch (Exception E) {}
	}
}