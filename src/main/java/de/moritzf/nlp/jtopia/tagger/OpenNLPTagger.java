package de.moritzf.nlp.jtopia.tagger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import de.moritzf.nlp.jtopia.TermDocument;
import de.moritzf.nlp.jtopia.container.TaggedTermsContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Open nlp tagger.
 *
 * @author sree openNLP POS Tagger
 */
public class OpenNLPTagger extends DefaultTagger implements Tagger {
	/**
	 * The Tagger.
	 */
	POSTaggerME tagger = null;
	/**
	 * The Model stream.
	 */
	InputStream modelStream = null;
	private static final Logger logger = LogManager.getLogger(OpenNLPTagger.class);

	/**
	 * Initialize the openNLP POS tagger
	 *
	 * @param lexiconFileName the lexicon file name
	 */
	public OpenNLPTagger(String lexiconFileName) {
		try {
			modelStream = new FileInputStream(lexiconFileName);
			POSModel model = new POSModel(modelStream);
			tagger = new POSTaggerME(model);
		} catch (IOException e) {
			logger.error(e.toString(),e);
		} 
	}

	/**
	 * Tag the terms using openNLP POS Tagger
	 */
	public TermDocument tag(TermDocument termDocument) {
		TaggedTermsContainer taggedTermsContainer = new TaggedTermsContainer();
		for(String term : termDocument.getTerms()) {
			String[] tag = tagger.tag(new String[]{term});
			// Since open NLP has tagged terms like establish/VB , we only need the POS tag
			// Some POS tags in openNLP has special charaters at end like their/PRP$

			try {
				taggedTermsContainer.addTaggedTerms(term, tag[0].replaceAll("\\$", ""), term);
			}catch(Exception e) {
			}
			
		}
		
		termDocument.setTaggedContainer(taggedTermsContainer);
		termDocument = postTagProcess(termDocument);
		if(modelStream != null) {
			try {
				modelStream.close();
			} catch (IOException e) {
				logger.error(e.toString(),e);
			}
		}
		return termDocument;
	}

}
