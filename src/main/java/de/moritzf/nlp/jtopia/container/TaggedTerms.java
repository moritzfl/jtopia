package de.moritzf.nlp.jtopia.container;

/**
 * Tagged terms class
 *
 * @author sree
 */
public class TaggedTerms {
	/**
	 * The Term.
	 */
	public String term;
	/**
	 * The Tag.
	 */
	public String tag;
	/**
	 * The Norm.
	 */
	public String norm;

	/**
	 * Instantiates a new Tagged terms.
	 *
	 * @param term the term
	 * @param tag  the tag
	 * @param norm the norm
	 */
	public TaggedTerms(String term,String tag,String norm) {
		this.term = term;
		this.tag = tag;
		this.norm = norm;
	}


	/**
	 * Gets term.
	 *
	 * @return the term
	 */
	public String getTerm() {
		return this.term;
	}

	/**
	 * set term
	 *
	 * @param term the term
	 */
	public void setTerm(String term) {
		this.term = term;
	}

	/**
	 * get tag
	 *
	 * @return tag tag
	 */
	public String getTag() {
		return this.tag;
	}

	/**
	 * set tag
	 *
	 * @param tag the tag
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * set norm
	 *
	 * @param norm the norm
	 */
	public void setNorm(String norm) {
		this.norm = norm;
	}

	/**
	 * get norm
	 *
	 * @return norm norm
	 */
	public String getNorm() {
		return this.norm;
	}
}
