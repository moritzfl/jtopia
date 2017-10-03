package de.moritzf.nlp.jtopia.tagger;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

import de.moritzf.nlp.jtopia.TermDocument;
import de.moritzf.nlp.jtopia.container.TaggedTermsContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Default POS tagger which uses model/default/english-lexicon.txt
 *
 * @author sree
 */
public class LexiconTagger extends DefaultTagger implements Tagger {


	private static final Logger logger = LogManager.getLogger(LexiconTagger.class);
	private LinkedHashMap<String, String> tagsByTerm = null;

	/**
	 * Initializes the default POS tagger lexicon
	 *
	 * @param lexiconFileName the lexicon file name
	 */
	public LexiconTagger(String lexiconFileName) {
		LinkedHashMap<String, String> tags = new LinkedHashMap<String, String>();
		logger.debug("Lexicon initialization started");
		FileInputStream fileInputStream = null;
		BufferedReader bufferedReader = null;
		try {
			fileInputStream = new FileInputStream(lexiconFileName);
		} catch (FileNotFoundException e) {
			logger.error(e.toString(), e);
		}

		DataInputStream dataInputStream = new DataInputStream(fileInputStream);
		bufferedReader = new BufferedReader(new InputStreamReader(
				dataInputStream));
		String line = "";
		try {
			while ((line = bufferedReader.readLine()) != null) {
				String[] terms = line.split(" ");
				tags.put(terms[0], terms[1]);
			}
		} catch (IOException e) {
			logger.error(e.toString(), e);
		}

		logger.debug("Lexicon initialization completed with size "+ tags.size());
		
		this.tagsByTerm = tags;
	}

	/**
	 * Gets tags.
	 *
	 * @return the tags
	 */
	public LinkedHashMap<String, String> getTags () {

		return tagsByTerm;
	}
	
	/**
	 * Tag the terms and post process the tagged terms. 
	 * Phase 1: Assign the tag from the lexicon. If the term is not found, it is assumed to be a default noun (NND).
	 * Phase 2: Run through some rules to improve the term tagging and normalized form.
	 * @param termDocument the term document
	 * @return {@link TermDocument}
	 */

	public TermDocument tag(TermDocument termDocument) {
		TaggedTermsContainer taggedTermsContainer = new TaggedTermsContainer();
		for(String term : termDocument.getTerms()) {
			if(termDocument.getTagsByTerm().containsKey(term)) {
				String tag = termDocument.getTagsByTerm().get(term);
				taggedTermsContainer.addTaggedTerms(term, tag,term);
			} else {
				String tag = "NND";
				taggedTermsContainer.addTaggedTerms(term, tag,term);
			}
		}

		termDocument.setTaggedContainer(taggedTermsContainer);
		termDocument = super.postTagProcess(termDocument);
		return termDocument;
	}

}
