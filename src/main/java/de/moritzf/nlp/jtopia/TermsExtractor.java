package de.moritzf.nlp.jtopia;

import de.moritzf.nlp.jtopia.cleaner.TextCleaner;
import de.moritzf.nlp.jtopia.tagger.Tagger;
import de.moritzf.nlp.jtopia.tagger.LexiconTagger;
import de.moritzf.nlp.jtopia.tagger.OpenNLPTagger;
import de.moritzf.nlp.jtopia.tagger.StanfordTagger;
import de.moritzf.nlp.jtopia.extractor.TermExtractor;
import de.moritzf.nlp.jtopia.filter.TermsFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Terms extractor.
 *
 * @author sree
 */
public class TermsExtractor {

	private static final Logger logger = LogManager.getLogger(TermsExtractor.class);

    /**
     * Instantiates a new Terms extractor.
     */
    public TermsExtractor(){

		if( Configuration.getModelFileLocation() != null && !Configuration.getModelFileLocation().isEmpty() ){

			if(Configuration.taggerType.equalsIgnoreCase("default")) {
				logger.info("Using English Lexicon POS tagger..");
				tagger = new LexiconTagger(Configuration.getModelFileLocation());

			}else if(Configuration.taggerType.equalsIgnoreCase("openNLP")) {
				logger.info("Using openNLP POS tagger..");
				tagger = new OpenNLPTagger(Configuration.getModelFileLocation());

			}else if(Configuration.taggerType.equalsIgnoreCase("stanford")) {
				logger.info("Using Stanford POS tagger..");
				tagger = new StanfordTagger();
			}
		}

		
	}

	private static Tagger tagger = null;

    /**
     * Extract terms term document.
     *
     * @param text the text
     * @return the term document
     */
    public TermDocument extractTerms(String text) {
		return performTermExtraction(text);
	}
	
	private TermDocument performTermExtraction(String text) {
		if(text != null && !text.isEmpty()) {
			logger.debug("Input String and lexicon is not null / empty");
			TermDocument termDocument = new TermDocument();
			TextCleaner textCleaner = new TextCleaner();
			
			termDocument.setNormalizedText(textCleaner.normalizeText(text));
			termDocument.setTerms(textCleaner.tokenizeText(termDocument.getNormalizedText()));

			if(Configuration.taggerType.equalsIgnoreCase("default")) {
				LexiconTagger lexiconTagger = (LexiconTagger) tagger;
				termDocument.setTagsByTerm(lexiconTagger.getTags());
				termDocument = lexiconTagger.tag(termDocument);

			}else if(Configuration.taggerType.equalsIgnoreCase("openNLP")) {
				OpenNLPTagger openNLPTagger = (OpenNLPTagger) tagger;
				termDocument = openNLPTagger.tag(termDocument);

			}else if(Configuration.taggerType.equalsIgnoreCase("stanford")) {
				StanfordTagger stanfordTagger = (StanfordTagger) tagger;
				termDocument = stanfordTagger.tag(termDocument);
			}
			
			TermExtractor termExtractor = new TermExtractor();
			termDocument.setExtractedTerms(termExtractor.extractTerms(termDocument.getTaggedContainer()));
			
			TermsFilter termsFilter = new TermsFilter(Configuration.getSingleStrength(),Configuration.getNoLimitStrength());
			termDocument.setFinalFilteredTerms(termsFilter.filterTerms(termDocument.getExtractedTerms()));
			
			return termDocument;
		}else {
			logger.debug("Input text / lexicon is null or empty..exiting..");
			return null;
		}
	}
	
}
