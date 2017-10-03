package de.moritzf.nlp.jtopia;


import java.util.HashSet;
import java.util.Set;

/**
 * The type Configuration.
 *
 * @author Sree Configurations for different POS taggers, filter values
 */
public class Configuration {

    /**
     * The Tagger type.
     */
    static String taggerType = "";
    /**
     * The Single strength min occur.
     */
    static int singleStrengthMinOccur;
    /**
     * The No limit strength.
     */
    static int noLimitStrength;
    /**
     * The Model file location.
     */
    static String modelFileLocation = "";
    /**
     * The Stop words.
     */
    static Set<String> stopWords = new HashSet<>();


    /**
     * Sets stop words.
     *
     * @param stopWords the stop words
     */
    public static void setStopWords(Set<String> stopWords) {
        Configuration.stopWords = stopWords;
    }

    public static void addStopWords(Set<String> stopWords) {
        Configuration.stopWords.addAll(stopWords);
    }

    public static void removeStopWords(Set<String> stopWords) {
        Configuration.stopWords.removeAll(stopWords);
    }

    public static void addStopWord(String stopWord) {
        Configuration.stopWords.add(stopWord);
    }

    public static void removeStopWord(String stopWord) {
        stopWords.remove(stopWord);
    }

    /**
     * Sets tagger type.
     *
     * @param type the type
     */
    public static void setTaggerType(String type) {
        taggerType = type;
    }

    /**
     * Gets tagger type.
     *
     * @return the tagger type
     */
    public static String getTaggerType() {
        return taggerType;
    }

	 /*If the term is a single word and it occurred more often than defined by singleStrengthMinOccur
      * OR
      *if it is constituted multiple words and the number of words is higher or equal to noLimitStrength
      *THEN include it in the final list of terms */

    /**
     * Sets single strength. Single strength is defines a contraint on what words will be included in the final list of keywords.
     * If the extracted term is a single word and occured at least as often as defined via singleStrength, it will be
     * included in the final list of keywords.
     *
     * @param strength the single Strength
     */
    public static void setSingleStrength(int strength) {
        singleStrengthMinOccur = strength;
    }

    /**
     * Gets single strength.
     * Single strength is defines a contraint on what words will be included in the final list of keywords.
     * If the extracted term is a single word and occured at least as often as defined via singleStrength, it will be
     * included in the final list of keywords.
     *
     * @return the single strength
     */
    public static int getSingleStrength() {
        return singleStrengthMinOccur;
    }

    /**
     * Sets no limit strength.
     * No limit strength defines a constraint on what words will be included in the final list of keywords.
     * If the extracted term is constructed of at least as many separate words as defined in no limit strength,
     * it will be included in the final list of keywords.
     *
     * @param noLimit the no limit
     */
    public static void setNoLimitStrength(int noLimit) {
        noLimitStrength = noLimit;
    }

    /**
     * Gets no limit strength.
     * No limit strength defines a constraint on what words will be included in the final list of keywords.
     * If the extracted term is constructed of at least as many separate words as defined in no limit strength,
     * it will be included in the final list of keywords.
     *
     * @return the no limit strength
     */
    public static int getNoLimitStrength() {
        return noLimitStrength;
    }

    /**
     * Sets model file location.
     *
     * @param modelFile the model file
     */
    public static void setModelFileLocation(String modelFile) {
        modelFileLocation = modelFile;
    }

    /**
     * Gets model file location.
     *
     * @return the model file location
     */
    public static String getModelFileLocation() {
        return modelFileLocation;
    }


    /**
     * Gets stop words.
     *
     * @return the stop words
     */
    public static Set<String> getStopWords() {
        return stopWords;
    }
}
