package de.moritzf.nlp.jtopia.helpers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Date utils.
 */
public class DateUtils {
    /**
     * The constant DateOffsets.
     */
    public static final Set<String> DateOffsets;

	static {
		String elements[] = { "PM", "ET", "UST", "AM", "IST", "PDT", "AD" };
		DateOffsets = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(elements)));
	}

    /**
     * Gets date offsets.
     *
     * @return the date offsets
     */
    public Set<String> getDateOffsets() {
		return this.DateOffsets;
	}

}
