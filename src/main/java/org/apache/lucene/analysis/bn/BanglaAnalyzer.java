package org.apache.lucene.analysis.bn;

import java.io.IOException;
import java.io.Reader;
import java.util.Set;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.ar.ArabicStemFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.in.IndicNormalizationFilter;
import org.apache.lucene.analysis.miscellaneous.SetKeywordMarkerFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.StopwordAnalyzerBase;
import org.apache.lucene.util.Version;

public final class BanglaAnalyzer extends StopwordAnalyzerBase {

	/**
	 * File containing default Bangla stopwords.
	 */
	public final static String DEFAULT_STOPWORD_FILE = "stopwords.txt";

	private static final String STOPWORDS_COMMENT = "#";

	/**
	 * Returns an unmodifiable instance of the default stop words set.
	 * 
	 * @return default stop words set.
	 */
	public static CharArraySet getDefaultStopSet() {
		return DefaultSetHolder.DEFAULT_STOP_SET;
	}

	private static class DefaultSetHolder {

		static final CharArraySet DEFAULT_STOP_SET;

		static {
			try {
				DEFAULT_STOP_SET = loadStopwordSet(false, BanglaAnalyzer.class,
						DEFAULT_STOPWORD_FILE, STOPWORDS_COMMENT);
			} catch (IOException ex) {
				throw new RuntimeException(
						"Unable to load default stopword set");
			}
		}

	}

	private final CharArraySet stemExclusionSet;

	/**
	 * Builds an analyzer with the default stop words:
	 * {@link #DEFAULT_STOPWORD_FILE}.
	 */
	public BanglaAnalyzer(Version version) {
		this(version, DefaultSetHolder.DEFAULT_STOP_SET);
	}

	/**
	 * Builds an analyzer with the given stop words
	 * 
	 * @param matchVersion
	 *            lucene compatibility version
	 * @param stopwords
	 *            a stopword set
	 */
	public BanglaAnalyzer(Version version, CharArraySet stopwords) {
		this(version, stopwords, CharArraySet.EMPTY_SET);
	}

	/**
	 * Builds an analyzer with the given stop word. If a none-empty stem
	 * exclusion set is provided this analyzer will add a
	 * {@link SetKeywordMarkerFilter} before {@link BanglaStemFilter}.
	 * 
	 * @param matchVersion
	 *            lucene compatibility version
	 * @param stopwords
	 *            a stopword set
	 * @param stemExclusionSet
	 *            a set of terms not to be stemmed
	 */
	public BanglaAnalyzer(Version version, CharArraySet stopwords,
			Set<?> stemExclusionSet) {
		super(version, stopwords);
		this.stemExclusionSet = CharArraySet.unmodifiableSet(CharArraySet.copy(
				version, stemExclusionSet));

	}

	/**
	 * Creates {@link org.apache.lucene.analysis.Analyzer.TokenStreamComponents}
	 * used to tokenize all the text in the provided {@link Reader}.
	 * 
	 * @return {@link org.apache.lucene.analysis.Analyzer.TokenStreamComponents}
	 *         built from an {@link StandardTokenizer} filtered with
	 *         {@link StandardFilter}, {@link IndicNormalizationFilter},
	 *         {@link BanglaNormalizationFilter}, {@link StopFilter},
	 *         {@link SetKeywordMarkerFilter} if a stem exclusion set is
	 *         provided and {@link ArabicStemFilter}.
	 */
	@Override
	protected TokenStreamComponents createComponents(String fieldName,
			Reader reader) {

		Tokenizer source = new StandardTokenizer(getVersion(), reader);
		TokenStream result = new StandardFilter(getVersion(), source);

		result = new StopFilter(getVersion(), result, stopwords);
		result = new IndicNormalizationFilter(result);
		result = new BanglaNormalizationFilter(result);

		if (!stemExclusionSet.isEmpty()) {
			result = new SetKeywordMarkerFilter(result, stemExclusionSet);
		}

		return new TokenStreamComponents(source, new BanglaStemFilter(result));
	}
}