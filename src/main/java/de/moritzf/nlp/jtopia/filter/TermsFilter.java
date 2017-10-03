package de.moritzf.nlp.jtopia.filter;

import de.moritzf.nlp.jtopia.Configuration;
import de.moritzf.nlp.jtopia.helpers.DateUtils;
import de.moritzf.nlp.jtopia.helpers.PunctuationRemover;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Final terms filter according to term occurance and strength
 *
 * @author sree
 */
public class TermsFilter {

    private static final Logger logger = LogManager.getLogger(TermsFilter.class);
    /**
     * The Single strength min occur.
     */
    int singleStrengthMinOccur;
    /**
     * The No limit strength.
     */
    int noLimitStrength;

    /**
     * The Values.
     */
    ArrayList<Integer> values;
    /**
     * The Term.
     */
    String term;

    /**
     * The Dt offset.
     */
    DateUtils dtOffset = new DateUtils();

    /**
     * Default configuration singleStrenght =3 and noLimit = 2
     *
     * @param singleStrenght the single strenght
     * @param noLimit        the no limit
     */
    public TermsFilter(int singleStrenght, int noLimit) {
        singleStrengthMinOccur = singleStrenght;
        noLimitStrength = noLimit;
    }

    /**
     * Filter the extracted terms according to the singleStrength and noLimit criteria.
     *
     * @param extractedTerms the extracted terms
     * @return map
     */
    public Map<String, ArrayList<Integer>> filterTerms(Map<String, Integer> extractedTerms) {
        Map<String, ArrayList<Integer>> filteredTerms = new HashMap<String, ArrayList<Integer>>();
        Map<String, ArrayList<Integer>> finalFilteredTerms = new HashMap<String, ArrayList<Integer>>();

        Set keySet = extractedTerms.keySet();
        for (Object key : keySet) {
            String term = (String) key;
            Integer count = extractedTerms.get(key);
            String[] wordCount = term.split(" ");
            int strength = wordCount.length;
            if (term != null && !term.isEmpty()) {
                /*If the term is a single word and it occurred more often than defined by singleStrengthMinOccur
                 * OR
                 *if it is constituted multiple words and the number of words is higher or equal to noLimitStrength
                 *THEN include it in the final list of terms */
                if ((strength == 1 && count >= singleStrengthMinOccur) || (strength >= noLimitStrength)) {
                    ArrayList<Integer> values = new ArrayList<Integer>();
                    values.add(count);
                    values.add(strength);
                    filteredTerms.put(term, values);
                }
            }
        }

        finalFilteredTerms = cleanUp(filteredTerms);

        return finalFilteredTerms;
    }

    /**
     * Clean up filters in one place
     *
     * @param filteredTerms
     * @return
     */
    private Map<String, ArrayList<Integer>> cleanUp(Map<String, ArrayList<Integer>> filteredTerms) {
        filteredTerms = removeStopWordsAndPunctuations(filteredTerms);
        filteredTerms = removeDateTerms(filteredTerms);
        filteredTerms = removeDuplicateSingleWords(filteredTerms);

        return filteredTerms;
    }

    /**
     * Remove duplicate single words which already in another multi word keyword
     *
     * @param filteredTerms
     * @return
     */
    private Map<String, ArrayList<Integer>> removeDuplicateSingleWords(
            Map<String, ArrayList<Integer>> filteredTerms) {
        Set keySet = filteredTerms.keySet();
        Map<String, ArrayList<Integer>> finalTerms = new HashMap<String, ArrayList<Integer>>();

        boolean isWordExist = false;
        try {
            for (Object key : keySet) {
                isWordExist = false;
                term = (String) key;
                values = new ArrayList<Integer>();
                values = filteredTerms.get(key);
                String[] termArray = null;
                int wordsCount = 1;
                termArray = term.split(" ");
                wordsCount = termArray.length;
                if (wordsCount == 1) {
                    isWordExist = isWordPresent(term.trim(), keySet);
                }
                if (!isWordExist) {
                    finalTerms.put(term, values);
                }
            }
        } catch (Exception ex) {
            logger.error(ex.toString(), ex);
        }
        return finalTerms;
    }

    /**
     * @param singleTerm
     * @param keySet
     * @return
     */
    private boolean isWordPresent(String singleTerm, Set keySet) {
        singleTerm = singleTerm.trim().toLowerCase();
        for (Object key : keySet) {
            String termInMap = (String) key;
            String[] termArray = null;
            if (termInMap.contains(" ")) {
                termArray = termInMap.split(" ");
            }
            if (termArray != null && termArray.length > 1) {
                for (int arrayIndex = 0; arrayIndex < termArray.length; arrayIndex++) {

                    if (termArray[arrayIndex].trim().toLowerCase()
                            .equals(singleTerm)) {
                        // logger.info("found duplicates "+singleTerm
                        // +" and "+termArray[arrayIndex].trim().toLowerCase() +
                        // " for "+termInMap);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * remove stopwords
     * remove apostrophes
     *
     * @param filteredTerms
     * @return term with no stopwords and punctuation that we don't want
     */
    private Map<String, ArrayList<Integer>> removeStopWordsAndPunctuations(
            Map<String, ArrayList<Integer>> filteredTerms) {
        Iterator<String> keySetItr = filteredTerms.keySet().iterator();


        Set<String> stopWords = Configuration.getStopWords();

        Map<String, ArrayList<Integer>> finalFilterdTerms = new HashMap<String, ArrayList<Integer>>();
        Set keySet = filteredTerms.keySet();

        try {
            for (Object key : keySet) {
                term = (String) key;
                values = new ArrayList<Integer>();
                values = filteredTerms.get(key);
                // replacing extra whitespaces
                term = term.replaceAll("\\s+", " ");
                String[] termArray = null;
                if (term.contains(" ")) {
                    termArray = term.split(" ");
                }
                boolean isStopWrod = false;
                boolean hasDigitOnlyWord = false;
                for (String word : stopWords) {
                    if (termArray != null) {
                        for (int arrayIndex = 0; arrayIndex < termArray.length; arrayIndex++) {
                            isStopWrod = false;
                            hasDigitOnlyWord = false;
                            if (termArray[arrayIndex].trim().toLowerCase()
                                    .equals(word.trim())) {
                                isStopWrod = true;
                                break;
                            } else if (hasDigitOrPunctuation(termArray[arrayIndex]
                                    .trim())) {
                                hasDigitOnlyWord = true;
                                break;
                            }
                        }

                    } else { // if term has only one word
                        if (term.trim().toLowerCase().equals(word.trim())) {
                            isStopWrod = true;
                        } else if (hasDigitOrPunctuation(term.trim())) {
                            hasDigitOnlyWord = true;
                        }
                    }
                }

                int wordLength = values.get(1);
                if (!isStopWrod && !hasDigitOnlyWord) {
                    term = removeApostrophes(term, wordLength);
                    // remove punctuation from start and beginning
                    term = PunctuationRemover.remove(term);

                    // keep some punctuation .@&-_' '/\  in between words
                    String regex = "[\\p{Punct}&&[^_.&\\\\/-]]";
                    term = term.replaceAll(regex, "").trim();
                    // remove punctuation which doesn't need to preserve
                    term = filterPunctuations(term.trim().toCharArray(), term);

                    // skip words having length less than 3
                    if (term.length() > 3 && !isDigitOnly(term))
                        finalFilterdTerms.put(term, values);
                } else {
                    logger.debug("Discarding term " + term + " isStopword "
                            + isStopWrod + " hasDigitonlyWord "
                            + hasDigitOnlyWord);
                }
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return finalFilterdTerms;
    }

    /**
     * Remove Date like patterns from the extracted set
     *
     * @param filterdTerms
     * @return
     */
    private Map<String, ArrayList<Integer>> removeDateTerms(
            Map<String, ArrayList<Integer>> filterdTerms) {
        Iterator<String> keySetItr = filterdTerms.keySet().iterator();

        Map<String, ArrayList<Integer>> finalFilterdTerms = new HashMap<String, ArrayList<Integer>>();
        Set keySet = filterdTerms.keySet();
        for (Object key : keySet) {
            term = (String) key;
            values = new ArrayList<Integer>();
            values = filterdTerms.get(key);
            String term = (String) key;
            String[] termArray = null;
            if (term.contains(" ")) {
                termArray = term.split(" ");
            }
            boolean isDate = false;
            for (String offset : dtOffset.getDateOffsets()) {
                if (termArray != null) {
                    for (int arrayIndex = 0; arrayIndex < termArray.length; arrayIndex++) {
                        isDate = false;
                        if (termArray[arrayIndex].trim().equals(offset.trim())) {
                            logger.debug("Date " + offset + " found in " + term);
                            isDate = true;
                            break;
                        }
                    }
                } else {
                    if (term.trim().equals(offset.trim())) {
                        isDate = true;
                    }
                }

            }
            if (!isDate) {
                finalFilterdTerms.put(capitalizeTerm(term), values);
            }
        }

        return finalFilterdTerms;
    }

    private String capitalizeTerm(String text){

        String[] words = text.split(" ");
        StringJoiner joiner = new StringJoiner(" ");

        for (String word: words) {
            //Add capitalized words to joiner
            joiner.add(word.substring(0, 1).toUpperCase() + word.substring(1));
        }


        return joiner.toString();
    }

    private boolean isDigitOnly(String term) {
        Pattern p = Pattern.compile("^[0-9]+$");
        Matcher m = p.matcher(term);
        if (m.find()) {
            return true;
        }
        return false;
    }

    private boolean hasDigitOrPunctuation(String term) {
        Pattern p = Pattern.compile("^[0-9\\p{Punct}]+$");
        Matcher m = p.matcher(term);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * Punctuation filter
     *
     * @param ch
     * @param term
     * @return
     */
    private String filterPunctuations(char[] ch, String term) {
        try {
            for (int index = 1; index < ch.length - 1; index++) {
                char character = isPunctuation(ch[index]);
                if (character == 'N') { // character is not punctuation
                    continue;
                } else {
                    if (character != '\\') {
                        if (!(Character.isLetterOrDigit(term.charAt(index - 1)) && Character
                                .isLetterOrDigit(term.charAt(index + 1)))) {
                            logger.debug("Next/Previous charactor is not letterorDigit "
                                    + term.charAt(index - 1)
                                    + " "
                                    + term.charAt(index + 1)
                                    + term
                                    + " "
                                    + index + " " + character);
                            if (isBlank(term.charAt(index - 1))) {
                                term = removeBlankCharacters(term, index - 1);
                            } else if (isBlank(term.charAt(index + 1))) {
                                term = removeBlankCharacters(term, index + 1);
                            } else {
                                term = term.replace(character, ' ');
                            }
                        }
                    } else {
                        if (!(Character.isLetterOrDigit(term.charAt(index - 1)))) {
                            logger.debug("Previous charactor is not letterorDigit "
                                    + term.charAt(index - 1));
                            if (isBlank(term.charAt(index - 1))) {
                                term = removeBlankCharacters(term, index - 1);
                            }
                            term = term.replace(character, ' ').trim();

                        }

                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return removeUnwantedCharacters(term);
    }

    /**
     * remove whitespace Character from string
     *
     * @param term     the term
     * @param position the position
     * @return string without blank character
     */
    public static String removeBlankCharacters(String term, int position) {
        return term.substring(0, position) + '#' + term.substring(position + 1);
    }

    /**
     * check if character is whitespace
     *
     * @param ch the ch
     * @return true if whitespace
     */
    public static boolean isBlank(char ch) {
        return Character.isWhitespace(ch);
    }

    /**
     * removing all unwanted characters
     *
     * @param term the term
     * @return term without multi-spaces
     */
    public static String removeUnwantedCharacters(String term) {
        term = term.replaceAll("#|,|”|“|’|‘", "");
        return term.replaceAll("\\s+", " ").trim();
    }

    /***
     * remove apostrophes
     *
     * @param term
     * @param wordLength
     * @return term without apostrophes
     */
    private String removeApostrophes(String term, int wordLength) {
        if (term.contains("’s")) {
            term = term.replaceAll("’s", "");
            wordLength = wordLength - 1;
            values.remove(1);
            values.add(wordLength);

        }
        return term;
    }

    /**
     * check a character is punctuation or not
     *
     * @param c the c
     * @return 'N' if not punctuation
     */
    public static char isPunctuation(char c) {
        boolean flag = false;
        if (c == '.' || c == '@' || c == '_' || c == '&' || c == '/'
                || c == '-' || c == '\\')
            flag = true;
        if (flag == true)
            return c;
        return 'N';
    }
}
