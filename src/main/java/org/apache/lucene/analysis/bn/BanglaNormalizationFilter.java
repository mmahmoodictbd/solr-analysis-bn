package org.apache.lucene.analysis.bn;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;

public class BanglaNormalizationFilter extends TokenFilter {

	private final BanglaNormalizer normalizer = new BanglaNormalizer();
	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	private final KeywordAttribute keywordAtt = addAttribute(KeywordAttribute.class);

	public BanglaNormalizationFilter(TokenStream input) {
		super(input);
	}

	@Override
	public boolean incrementToken() throws IOException {

		if (input.incrementToken()) {
			if (!keywordAtt.isKeyword())
				termAtt.setLength(normalizer.normalize(termAtt.buffer(),
						termAtt.length()));
			return true;
		}

		return false;
	}
}
