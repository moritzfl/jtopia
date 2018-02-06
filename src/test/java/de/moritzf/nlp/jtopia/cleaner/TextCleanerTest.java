package de.moritzf.nlp.jtopia.cleaner;


import org.junit.Test;



import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;


public class TextCleanerTest {

    @Test
    public void testTokenizeText(){
        String input = "This is some text. And this is the second sentence. \n now this is the nächste line.";
        TextCleaner cleaner = new TextCleaner();

        String[] items = {"This" ,"is", "some", "text", ".", "And", "this", "is", "the", "second", "sentence", ".",
                "now", "this", "is", "the", "nächste", "line", "."};

        assertThat( cleaner.tokenizeText(input), hasItems(items));
    }
}
