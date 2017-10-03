package de.moritzf.nlp.jtopia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import de.moritzf.nlp.jtopia.container.TaggedTermsContainer;

/**
 * The type Term document.
 *
 * @author sree
 */
public class TermDocument {

	/**
	 * The Terms.
	 */
	public List<String> terms = new ArrayList<>();

	/**
	 * The Tags by term.
	 */
	public LinkedHashMap<String, String> tagsByTerm = new LinkedHashMap<>();

	/**
	 * The Normalized text.
	 */
	public String normalizedText;

	/**
	 * The Tagged container.
	 */
	public TaggedTermsContainer taggedContainer = new TaggedTermsContainer();

	/**
	 * The Extracted terms.
	 */
	public Map<String,Integer> extractedTerms;

	/**
	 * The Final filtered terms.
	 */
	public Map<String, ArrayList<Integer>> finalFilteredTerms = new HashMap<String, ArrayList<Integer>>();


	/**
	 * Set terms
	 *
	 * @param terms the terms
	 */
	public void setTerms(List<String> terms) {
		this.terms = terms;
	}

	/**
	 * get terms
	 *
	 * @return terms terms
	 */
	public List<String> getTerms() {
		return this.terms;
	}

	/**
	 * set tags by term
	 *
	 * @param tagsByTerm the tags by term
	 */
	public void setTagsByTerm(LinkedHashMap<String, String> tagsByTerm) {
		this.tagsByTerm = tagsByTerm;
	}

	/**
	 * get tags by term
	 *
	 * @return tags by term
	 */
	public LinkedHashMap<String, String> getTagsByTerm() {
		return this.tagsByTerm;
	}

	/**
	 * set normalized test
	 *
	 * @param normalizedText the normalized text
	 */
	public void setNormalizedText(String normalizedText) {
		this.normalizedText = normalizedText;
	}

	/**
	 * get normalized text
	 *
	 * @return normalized text
	 */
	public String getNormalizedText() {
		return this.normalizedText;
	}

	/**
	 * set tagged terms container
	 *
	 * @param taggedContainer the tagged container
	 */
	public void setTaggedContainer(TaggedTermsContainer taggedContainer) {
		this.taggedContainer = taggedContainer;
	}

	/**
	 * get tagged terms container
	 *
	 * @return tagged container
	 */
	public TaggedTermsContainer getTaggedContainer() {
		return this.taggedContainer;
	}

	/**
	 * set extracted terms
	 *
	 * @param extractedTerms the extracted terms
	 */
	public void setExtractedTerms(Map<String,Integer> extractedTerms) {
		this.extractedTerms = extractedTerms;
	}

	/**
	 * get extracted terms
	 *
	 * @return extracted terms
	 */
	public Map<String,Integer> getExtractedTerms() {
		return this.extractedTerms;
	}

	/**
	 * set final filtered terms
	 *
	 * @param filteredTerms the filtered terms
	 */
	public void setFinalFilteredTerms(Map<String, ArrayList<Integer>> filteredTerms) {
		this.finalFilteredTerms = filteredTerms;
	}

	/**
	 * get final filteres terms
	 *
	 * @return final filtered terms
	 */
	public Map<String, ArrayList<Integer>> getFinalFilteredTerms() {
		return this.finalFilteredTerms;
	}
}
