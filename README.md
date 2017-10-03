jtopia
======
| jtopia  | jtopia-configurator  |
|---------|----------------------|
| [![Release](https://jitpack.io/v/moritzfl/jtopia.svg)](https://jitpack.io/#moritzfl/jtopia)  |  [![Release](https://jitpack.io/v/moritzfl/jtopia-configurator.svg)](https://jitpack.io/#moritzfl/jtopia-configurator) |

Keyword/-phrase extractor for existing texts. It may be used alone with your own language-resources or together with [jtopia-configurator](https://github.com/moritzfl/jtopia-configurator) which provides easy access to resources for Danish, Dutch, English, French, German, Portuguese, Spanish and Swedish (sidenote: jtopia-configurator → GPL3, jtopia itself → Apache License 2.0).

This project is a fork of [srijiths/jtopia](https://github.com/srijiths/jtopia) and includes fixes from [tipech/jtopia](https://github.com/tipech/jtopia)

What does this project do differently?
======================================
This project does not exclusively care for English but also for other languages.

This project fixes the following issues of the original jtopia:
- support for special characters in words
(eg. ä,ö,ü,ß in German - original jtopia could only handle a-z)
- easy access to multiple languages through [jtopia-configurator](https://github.com/moritzfl/jtopia-configurator)
- allows to define a custom list of stopwords instead of relying on a fixed set of English stopwords.
- removed dependency to [StringHelpers](https://github.com/srijiths/StringHelpers)
- removed dependency to apaches WordUtils as this was only used once in a situation where it could easily be replaced.
- migrated other library dependencies to current versions

Furthermore IntelliJ Idea is used for development resulting in some additional files in the repository. Logging was optimized for this IDE and migrated from log4j to log4j2. This should however not prevent you from using any other IDE if you choose to use this project in any form.

jtopia maven artifacts
======================
Maven artifacts are provided through jitpack.io. You therefore need to add the according repository to your project.

**Repository:**

	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
    
**Artifacts:**

*Note: jtopia-configurator already depends on jtopia, so in case you choose to use jtopia-configurator, you do not  need jtopia in your list of dependencies.*
    
    <dependency>
	    <groupId>com.github.moritzfl</groupId>
	    <artifactId>jtopia</artifactId>
	    <version>0.3</version>
	</dependency>

	<dependency>
	    <groupId>com.github.moritzfl</groupId>
	    <artifactId>jtopia-configurator</artifactId>
	    <version>0.3 </version>
	</dependency>


More information on jtopia
==========================
Java clone for Python term extractor [topia](https://github.com/turian/topia.termextract). It will extract important keywords/key phrases from text.

jtopia is a light-weight term extractor, which is domain independent in nature.
jtopia uses a rule based + POS tagged based approach to find out the keywords / key phrases.

You can even tune the parameters in jtopia to get some filtered keywords / key phrases.

jtopia just throws n number of keywords from the input text. It does not rank the keyword as first or second.

The numbers in the square is just an information about the extracted term. 

Hurricane Saturday Night=[1, 3]. Here 1 means the extracted keyword "Hurricane Saturday Night" has frequency 1 in the input text. 
The number 3 means the keyword is formed using 3 words "Hurricane", "Saturday" and "Night".


Expansion of jtopia in your domain
==================================

If you want to expand the power of jtopia , have a look at the below points.

* Create your own lexicon by using [english-lexicon.txt](https://github.com/moritzfl/jtopia-configurator/blob/master/src/main/resources/de/moritzf/jtopia/configurator/default-english-lexicon.txt) as a template. That way you can define terms specific for your domain. 

* use another POS tagger ( Stanford POS tagger / OpenNLP POS Tagger ) and make the POS output available to TermExtractor class.
	You can set this by passing "taggerType" to Configuration class. The values are "default","openNLP" or "stanford".
	Do not forget to set the stopwords by using Configuration.setStopWords(...) as well.

Fine tuning jtopia
==================

* You can change the extracted terms output using the TermsFilter class.There are two parameters (singleStrengthMinOccur and noLimitStrength) which filters the extracted jtopia output according to the parameters. 

* If you are dealing with short text, setting singleStrengthMinOccur and noLimitStrength to minimum will give you a largest possible number of keywords from the text.

* If you are dealing with  large text, setting singleStrengthMinOccur, noLimitStrength to a feasible maximum will filter out unwanted junk keywords and provide you with a smaller but more reasonable set of keywords.

singleStrengthMinOccur:
If the extracted term is a single word (eg. "Tower") and occurred at least as often as defined via singleStrength, it will be included in the final list of keywords.

noLimitStrength:
If the extracted term is constructed of at least as many separate words as defined in no limit strength, it will be included in the final list of keywords (eg. "Eiffel Tower" for noLimitStrength == 2).


How to Use
==========

jtopia-configurator (separate library) comes with language resources and a Configurator class to allow for easy configuration:

        Configurator.setConfigurationToModel(Configurator.OPENNLP_ENGLISH_POS_MAXENT);
        Configurator.setConfigurationToStopWords(Configurator.STOPWORD_ENGLISH_LONG);

        //You may choose to do some finetuning
        Configuration.setSingleStrength(3);
        Configuration.setNoLimitStrength(2);

        String text = "Keywords will be extracted from this text";

        TermsExtractor termExtractor = new TermsExtractor();
        TermDocument topiaDoc = termExtractor.extractTerms(text);

        logger.info("Extracted terms : " + topiaDoc.getExtractedTerms());
        logger.info("Final Filtered Terms : " + topiaDoc.getFinalFilteredTerms());


Configuration with jtopia alone requires you to provide the language resources and to set up a few more things manually:

        //for default lexicon POS tags
        //Configuration.setTaggerType("default");
        //for openNLP POS tagger
        Configuration.setTaggerType("openNLP");
        //for Stanford POS tagger (requires edu.stanford.nlp - stanford-corenlp)
        //Configuration.setTaggerType("stanford");
        
        //Set the path to the tagger model
        Configuration.setModelFileLocation("/some/path/some.model");
        
        //Define a set of stopwords
        Configuration.setStopWords(someSetOfStrings);

		//You may choose to do some finetuning
        Configuration.setSingleStrength(3);
        Configuration.setNoLimitStrength(2);

        String text = "Keywords will be extracted from this text";
        
        TermsExtractor termExtractor = new TermsExtractor();
        TermDocument topiaDoc = termExtractor.extractTerms(text);

        logger.info("Extracted terms : " + topiaDoc.getExtractedTerms());
        logger.info("Final Filtered Terms : " + topiaDoc.getFinalFilteredTerms());


Using the Stanford Tagger with jtopia
=====================================

jtopia may use th Stanford Tagger to extract terms from a text. As the Stanford Tagger is licensed under the terms of the GPL, a direct inclusion in jtopia was not possible while maintaining a permissive license (jtopias license is Apache2). Instead jtopia tries to load the classes at runtime which requires them to be present in your project. Note that there is also a commercial licensing available for the Stanford Tagger in case you absolutely rely on it and do not want to publish under GPL.

jtopia-configurator on the other hand already includes this dependency as it is published under the terms of the GPL anyways.

		<dependency>
			<groupId>edu.stanford.nlp</groupId>
			<artifactId>stanford-corenlp</artifactId>
			<version>3.8.0</version>
		</dependency>

jtopia dependencies
===================
Jtopia has dependencies to the following libraries:


		<dependency>
			<groupId>org.apache.logging.log4j</groupId>
			<artifactId>log4j-api</artifactId>
			<version>2.8.2</version>
		</dependency>
		<dependency>
			<groupId>org.apache.logging.log4j</groupId>
			<artifactId>log4j-core</artifactId>
			<version>2.8.2</version>
		</dependency>
		<dependency>
			<groupId>org.apache.opennlp</groupId>
			<artifactId>opennlp-tools</artifactId>
			<version>1.8.1</version>
		</dependency>

