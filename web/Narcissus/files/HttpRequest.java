import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.regex.*;

public class HttpRequest implements Runnable {

	private final Socket socket;

	public HttpRequest(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			processRequest();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Processes the HTTP request.
	 * 
	 * @throws Exception when an exception occurs.
	 */
	private void processRequest() throws Exception {
		// Initiate the IO streams
		InputStream inputStream = socket.getInputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		String line = bufferedReader.readLine();

		// Get the details from the header of the request
		Matcher matcher = Pattern.compile("GET (\\S+) HTTP/(\\S+)").matcher(line);
		if (!matcher.matches()) {
			return;
		}
		String path = matcher.group(1);
		String version = matcher.group(2);
		
		System.out.println("GET path=`" + path + "`, version=`" + version + "`");

		// Skip headers
		while (!(line = bufferedReader.readLine()).isEmpty());

		if (path.equals("/index.html") || path.isEmpty() || path.equals("/")) {
			// Handle ind
			respond("index.html", dataOutputStream, version, true);
		} else if (path.equals("/projects")) {
			// Handle the project list
			respond("projects", dataOutputStream, version, false);
		} else if (path.startsWith("/project?name=")) {
			// Handle project lookup
			String name = path.substring("/project?name=".length());
			respond("projects/" + name, dataOutputStream, version, false);
		} else {
			// 404
			dataOutputStream.writeBytes("HTTP/" + version + " 404 Not Found\r\n\r\n");
		}
	}

	/**
	 * Respond with the contents of the given file or directory to a HTTP request.
	 * 
	 * @param path the file / directory to respond with.
	 * @param dataOutputStream the output stream to write the contents to.
	 * @param version the version of HTTP to respond with.
	 * @param html whether the contents is html.
	 * @throws IOException if an IOException occurs.
	 */
	private void respond(String path, DataOutputStream dataOutputStream, String version, boolean html) throws IOException {
		// Read the file contents
		String contents;
		try {
			contents = getPathContents(path);
		} catch (IOException e) {
			// Respond with 500 if exception occurs
			dataOutputStream.writeBytes("HTTP/" + version + " 500 Internal Server Error\r\n\r\n");
			return;
		}

		// Success, send response
		dataOutputStream.writeBytes("HTTP/" + version + " 200 OK\r\n");
		if (html) {
			// Add HTML header
			dataOutputStream.writeBytes("Content-Type: text/html\r\n");
		}
		dataOutputStream.writeBytes("\r\n");
		dataOutputStream.writeBytes(contents);
	}

	/**
	 * Gets the contents of a file or directory to a string.
	 * 
	 * @param path the file or directory.
	 * @return the string containing the contents of the given path.
	 * @throws IOException if an IOException occurs.
	 */
	private String getPathContents(String path) throws IOException {
		File file = new File(path);

		if (!file.exists()) {
			throw new IOException("File doesn't exist");
		}

		StringBuilder stringBuilder = new StringBuilder();
		if (file.isFile()) {
			// Read file
			Files.lines(file.toPath()).forEach(line -> {
				stringBuilder.append(line).append("\n");
			});
		} else {
			// List directory
			for (String subFile : file.list()) {
				stringBuilder.append(subFile).append("\n");
			}
		}

		return stringBuilder.toString();
	}
	
}
