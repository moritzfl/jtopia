package de.moritzf.nlp.jtopia.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Remove punctuation in a given sentence.
 */
public class PunctuationRemover {
	/**
	 * Remove punctuation from a given string.
	 *
	 * @param sentence the sentence
	 * @return the sentence without punctuation
	 */
	public static String remove(String sentence) {

		Pattern p = Pattern.compile("^\\W*(.*?)\\W*$");
		Matcher m = p.matcher(sentence);
		if (m.matches()) {
			return m.group(1);
		}
		return sentence;
	}
}