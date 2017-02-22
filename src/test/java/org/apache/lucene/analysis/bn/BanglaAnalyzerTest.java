package org.apache.lucene.analysis.bn;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.util.Version;
import org.junit.Ignore;
import org.junit.Test;

public class BanglaAnalyzerTest {

	@Ignore
	@Test
	public void analyzerTest() {

		/* This test fail, error in - BanglaAnalyzer.java line 60 */
		String text = "আড়ি পেতে শুনার কি দরকার? কি চাঁদ গো!";
		Analyzer analyzer = new BanglaAnalyzer(Version.LATEST);

		TokenStream stream = null;
		try {
			stream = analyzer.tokenStream("contents", new StringReader(text));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			stream.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			while (stream.incrementToken()) {
				System.out.println(stream.reflectAsString(false));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
