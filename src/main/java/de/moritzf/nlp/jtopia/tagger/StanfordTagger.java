package de.moritzf.nlp.jtopia.tagger;

import de.moritzf.nlp.jtopia.Configuration;
import de.moritzf.nlp.jtopia.TermDocument;
import de.moritzf.nlp.jtopia.container.TaggedTermsContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The type Stanford tagger. In order to use this, you must add edu.stanford.nlp - stanford-corenlp to your classpath.
 */
public class StanfordTagger extends DefaultTagger implements Tagger {
    private Method tagStringMethod = null;
    private Object maxentTagger = null;

    Logger logger = LogManager.getLogger(StanfordTagger.class);

    /**
     * Instantiates a new Stanford tagger.
     */
    public StanfordTagger() {


        try {
            Class maxentTaggerClass = Class.forName("edu.stanford.nlp.tagger.maxent.MaxentTagger");
            maxentTagger = maxentTaggerClass.getConstructor(String.class).newInstance(Configuration.getModelFileLocation());
            tagStringMethod = maxentTaggerClass.getMethod("tagString", String.class);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException
                | NoSuchMethodException | InvocationTargetException e) {
            logger.error("In order to use the stanford tagger, you must add " +
                            "edu.stanford.nlp - stanford-corenlp to your classpath " +
                            "(either commercial license or GPL)", e);
            e.printStackTrace();
        }


    }

    public TermDocument tag(TermDocument termDocument) {
        TaggedTermsContainer taggedTermsContainer = new TaggedTermsContainer();
        for (String term : termDocument.getTerms()) {

            String tag = null;
            try {
                tag = (String) tagStringMethod.invoke(maxentTagger, term);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            // Since Stanford POS has tagged terms like establish_VB , we only need the POS tag
            // Some POS tags in Stanford has special charaters at end like their/PRP$
            try {
                taggedTermsContainer.addTaggedTerms(term, tag.split("_")[1].replaceAll("\\$", ""), term);
            } catch (Exception e) {
            }
        }

        termDocument.setTaggedContainer(taggedTermsContainer);
        termDocument = postTagProcess(termDocument);


        return termDocument;
    }

}
