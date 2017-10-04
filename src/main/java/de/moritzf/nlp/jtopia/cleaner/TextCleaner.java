package de.moritzf.nlp.jtopia.cleaner;

import java.util.ArrayList;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Text cleaner.
 *
 * @author : sree
 *
 * <p>
 * Normalizes and tokenizes the input text
 */
public class TextCleaner {

    private static final Logger logger = LogManager.getLogger(TextCleaner.class);

    private static final String UMLAUT = "äåüöÄÅÖÜßæÆøØáéíóúÁÉÍÑÓÚèÈàÀùÙâêîôûÂÊÎÔÛçÇãÃõÕ";

    private static final String MATCH_PATTERN = "([^a-zA-Z" + UMLAUT + "]*)([a-z" + UMLAUT + "A-Z-\\.]*[a-zA-Z"
            + UMLAUT + "])([^a-zA-Z" + UMLAUT + "]*[a-zA-Z" + UMLAUT + "]*)";

    /**
     * Normalizing the new line spaces in input text
     *
     * @param text the text
     * @return string
     */
    public String normalizeText(String text) {
        logger.debug("Input to normalize text : " + text);
        if (text != null && !text.isEmpty()) {
            text = replaceAll(text, "\\n", " . ");
        }
        logger.debug("Input text normalized : " + text);
        return text.trim();
    }

    /**
     * Tokenizing the text using regex
     *
     * @param text the text
     * @return list
     */
    public List<String> tokenizeText(String text) {
        List<String> tokenizedWords = new ArrayList<String>();
        String[] words = text.split("\\s");
        Pattern pattern = Pattern.compile(MATCH_PATTERN, Pattern.DOTALL | Pattern.MULTILINE);

        for (String word : words) {
            // If the term is empty, skip it, since we probably just have multiple whitespace characters.
            if (word == null || word.isEmpty())
                continue;
            // Now, a word can be preceded or succeeded by symbols, so let's split those out
            Matcher matcher = pattern.matcher(word);
            if (!matcher.find()) {
                tokenizedWords.add(word);
                continue;
            }
            for (int i = 1; i <= matcher.groupCount(); i++) {
                if (matcher.group(i) != null && !matcher.group(i).isEmpty()) {
                    logger.debug("Matcher group : " + i + " text :  " + matcher.group(i));
                    tokenizedWords.add(matcher.group(i));
                }
            }
        }

        return tokenizedWords;

    }

    /**
     * Replace all the occurances of a regex with replacement string
     *
     * @param text
     * @param regex
     * @param replacement
     * @return the text after replacements
     */

    private String replaceAll(String text, String regex, String replacement) {
        text = text.replaceAll(regex, replacement);
        return text;
    }

}
