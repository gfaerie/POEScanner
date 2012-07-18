package se.faerie.poescanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class StringUtils {

	public static String copyToString(Reader reader) throws IOException {
		StringBuffer stringBuffer = new StringBuffer();
		BufferedReader bufferedReader = new BufferedReader(reader);
		try {
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
			}
			return stringBuffer.toString();
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}

		}
	}

	public static String trimWhitespaces(String in) {
		StringBuffer stringBuffer = new StringBuffer();
		for (char c : in.toCharArray()) {
			if (!Character.isWhitespace(c)) {
				stringBuffer.append(c);
			}
		}
		return stringBuffer.toString();
	}

}
