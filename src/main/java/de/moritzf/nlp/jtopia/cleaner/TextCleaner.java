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
 * <p>
 * <p>
 * Normalizes and tokenizes the input text
 */
public class TextCleaner {

    private static final Logger logger = LogManager.getLogger(TextCleaner.class);

    private static final String MATCH_PATTERN = "(\\p{Punct})|.*?(?=\\s|$|\\p{Punct})";

    /**
     * Normalizing the new line spaces in input text
     *
     * @param text the text
     * @return string
     */
    public String normalizeText(String text) {
        logger.debug("Input to normalize text : " + text);
        if (text != null && !text.isEmpty()) {
            text = text.replaceAll("\\n", " . ");
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
        List<String> tokenizedWords = new ArrayList<>();

        Matcher m = Pattern.compile(MATCH_PATTERN).matcher(text);

        while (m.find()) {
            tokenizedWords.add(m.group());
        }

        return tokenizedWords;

    }


}
