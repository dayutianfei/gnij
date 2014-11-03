package cn.ac.iie.s3.util;

public class EscapeUtil {

	private static final String[] escapableTermExtraFirstChars = { "+", "-", "@" };

	private static final String[] escapableTermChars = { "<", ">", "=", "!", "(", ")", "^", "[", "{", ":", "]", "}", "~", "/" };

	private static final String[] escapableWhiteChars = { " ", "\t", "\n", "\r", "\f", "\b", "\u3000" };

	private static final String[] escapableWordTokens = { "AND", "OR", "NOT", "TO", "WITHIN", "SENTENCE", "PARAGRAPH", "INORDER" };

	public static String escape(String text) {
		if (text == null || text.length() == 0) {
			return text;
		}
		return escapeTerm(text).toString();
	}

	private static final CharSequence escapeTerm(CharSequence term) {
		if (term == null)
			return term;

		// Escape single Chars
		term = escapeChar(term);
		term = escapeWhiteChar(term);

		// Escape Parser Words
		for (int i = 0; i < escapableWordTokens.length; i++) {
			if (escapableWordTokens[i].equalsIgnoreCase(term.toString()))
				return "\\" + term;
		}
		return term;
	}

	private static final CharSequence escapeChar(CharSequence str) {
		if (str == null || str.length() == 0)
			return str;

		CharSequence buffer = str;

		// regular escapable Char for terms
		for (int i = 0; i < escapableTermChars.length; i++) {
			buffer = replaceIgnoreCase(buffer, escapableTermChars[i].toLowerCase(), "\\");
		}

		// First Character of a term as more escaping chars
		for (int i = 0; i < escapableTermExtraFirstChars.length; i++) {
			if (buffer.charAt(0) == escapableTermExtraFirstChars[i].charAt(0)) {
				buffer = "\\" + buffer.charAt(0) + buffer.subSequence(1, buffer.length());
				break;
			}
		}

		return buffer;
	}

	/**
	 * escape all tokens that are part of the parser syntax on a given string
	 * 
	 * @param str
	 *            string to get replaced
	 * @param locale
	 *            locale to be used when performing string compares
	 * @return the new String
	 */
	private static final CharSequence escapeWhiteChar(CharSequence str) {
		if (str == null || str.length() == 0)
			return str;

		CharSequence buffer = str;

		for (int i = 0; i < escapableWhiteChars.length; i++) {
			buffer = replaceIgnoreCase(buffer, escapableWhiteChars[i].toLowerCase(), "\\");
		}
		return buffer;
	}

	/**
	 * replace with ignore case
	 * 
	 * @param string
	 *            string to get replaced
	 * @param sequence1
	 *            the old character sequence in lowercase
	 * @param escapeChar
	 *            the new character to prefix sequence1 in return string.
	 * @return the new String
	 */
	private static CharSequence replaceIgnoreCase(CharSequence string, CharSequence sequence1, CharSequence escapeChar) {
		if (escapeChar == null || sequence1 == null || string == null)
			throw new NullPointerException();

		// empty string case
		int count = string.length();
		int sequence1Length = sequence1.length();
		if (sequence1Length == 0) {
			StringBuilder result = new StringBuilder((count + 1) * escapeChar.length());
			result.append(escapeChar);
			for (int i = 0; i < count; i++) {
				result.append(string.charAt(i));
				result.append(escapeChar);
			}
			return result.toString();
		}

		// normal case
		StringBuilder result = new StringBuilder();
		char first = sequence1.charAt(0);
		int start = 0, copyStart = 0, firstIndex;
		while (start < count) {
			if ((firstIndex = string.toString().toLowerCase().indexOf(first, start)) == -1)
				break;
			boolean found = true;
			if (sequence1.length() > 1) {
				if (firstIndex + sequence1Length > count)
					break;
				for (int i = 1; i < sequence1Length; i++) {
					if (string.toString().toLowerCase().charAt(firstIndex + i) != sequence1.charAt(i)) {
						found = false;
						break;
					}
				}
			}
			if (found) {
				result.append(string.toString().substring(copyStart, firstIndex));
				result.append(escapeChar);
				result.append(string.toString().substring(firstIndex, firstIndex + sequence1Length));
				copyStart = start = firstIndex + sequence1Length;
			} else {
				start = firstIndex + 1;
			}
		}
		if (result.length() == 0 && copyStart == 0)
			return string;
		result.append(string.toString().substring(copyStart));
		return result.toString();
	}

	public static String discardEscape(String input) {
		StringBuilder sb = new StringBuilder();
		int length = input.length();
		for (int i = 0; i < length; i++) {
			char c = input.charAt(i);
			if (c == '\\') {
				if (i + 1 < length) {
					char next = input.charAt(i + 1);
					if (next == '\\' || next == '\"' || next == '\'') {
						sb.append(next);
						i++;
					} else {
						sb.append(c);
					}
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String discardEscape_Wildcard(String input) {
		StringBuilder sb = new StringBuilder();
		int length = input.length();
		for (int i = 0; i < length; i++) {
			char c = input.charAt(i);
			if (c == '\\') {
				if (i + 2 < length) {
					char c1 = input.charAt(i + 1);
					char c2 = input.charAt(i + 2);
					if (c1 == '\\' && (c2 == '*' || c2 == '?')) {
						sb.append(new String(new char[] { c1, c2 }));
						i += 2;
					} else {
						sb.append(c);
					}
				} else {
					sb.append(c);
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}