package de.moritzf.nlp.jtopia.container;

import java.util.ArrayList;
import java.util.List;

/**
 * Tagged terms container class
 *
 * @author sree
 */
public class TaggedTermsContainer {
	/**
	 * The Tagged terms.
	 */
	public List<TaggedTerms> taggedTerms = new ArrayList<>();

	/**
	 * Add tagged terms.
	 *
	 * @param term the term
	 * @param tag  the tag
	 * @param norm the norm
	 */
	public void addTaggedTerms(String term,String tag,String norm) {
		taggedTerms.add(new TaggedTerms(term, tag,norm));
	}
}
