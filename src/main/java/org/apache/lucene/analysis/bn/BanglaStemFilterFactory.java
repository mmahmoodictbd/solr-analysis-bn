package org.apache.lucene.analysis.bn;

import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

public class BanglaStemFilterFactory extends TokenFilterFactory {

	public BanglaStemFilterFactory(Map<String, String> args) {

		super(args);
		if (!args.isEmpty()) {
			throw new IllegalArgumentException("Unknown parameters: " + args);
		}
	}

	public TokenStream create(TokenStream input) {
		return new BanglaStemFilter(input);
	}
}
