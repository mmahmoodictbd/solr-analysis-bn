package org.apache.lucene.analysis.bn;

import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.AbstractAnalysisFactory;
import org.apache.lucene.analysis.util.MultiTermAwareComponent;
import org.apache.lucene.analysis.util.TokenFilterFactory;

public class BanglaNormalizationFilterFactory extends TokenFilterFactory
		implements MultiTermAwareComponent {

	public BanglaNormalizationFilterFactory(Map<String, String> args) {

		super(args);
		if (!args.isEmpty()) {
			throw new IllegalArgumentException("Unknown parameters: " + args);
		}
	}

	public TokenStream create(TokenStream input) {
		return new BanglaNormalizationFilter(input);
	}

	public AbstractAnalysisFactory getMultiTermComponent() {
		return this;
	}
}
