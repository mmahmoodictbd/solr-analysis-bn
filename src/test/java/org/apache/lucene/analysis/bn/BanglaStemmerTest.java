package org.apache.lucene.analysis.bn;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

public class BanglaStemmerTest {

	@Test
	public void aa() {
		BanglaStemmer stemmer = new BanglaStemmer();

		String words[] = { "হয়", "১৮৯০ সালে তৈরি হয় মন্দিরটি।", "হয" };
		for (String word : words) {
			int stemWordLen = stemmer.stem(word.toCharArray(), word.length());
			System.out.println(word + "(" + word.length() + ") -> "
					+ word.substring(0, stemWordLen));
		}
	}

	@Ignore
	@Test
	public void suffixMakerTest() {

		String suffix[] = { "টা", "রা", "গুলো", "গুলোই", "দের", "দেরকে",
				"দেরও", "েই", "েও", "টাই", "টাও", "টাতে", "টাতেই", "টায়",
				"টার", "টিই", "টিও", "টিতে", "টির", "টুকু", "টুকুই", "টুকুও",
				"টুকুতে", "টুকুতেও", "টুকুর", "টুকুরই", "টাকে", "টিকে",
				"দেরকেও", "গুলোর", "গুলোতে", "ই", "গুলোকে", "গুলোকেও", "তা",
				"টি", "টির", "কেই", "গুলোয়", "কারীই", "কারীও", "কারীকে",
				"কারীদের", "কারীর", "কারীরা", "কারীরাও", "কারীসহ", "সহ",
				"টাকেই", "টুকু", "কে", "র", "রই", "ের", "ভাবে", "কারী", "গুলি",
				"গুলিতে", "য়", "রাও", "শীল", "টার", "ছিলাম", "চ্ছিলে", "ছিলে",
				"চ্ছি", "চ্ছিলাম", "ছেন", "িতেছি", "লেন", "বলী", "তাম", "ও",
				"ত্ব", "ত্বকে", "ত্বই", "ত্বমূলক", "ত্বের", "হীন", "ি", "িক",
				"গুলা", "মূলক", "খোর", "ন্ত", "তি", "তে", "তেও", "তায়", "তার",
				"িত", "ীয়", "েরও", " িদের", "কেও", "গুলোতেও", " েরই", "য়ের",
				"দেরকে", "দেরকেই", "টারই", "য়েন" };

		Map<Integer, Set<String>> map = new HashMap<Integer, Set<String>>();

		for (String string : suffix) {
			System.out.println(string + " -> " + string.length());
			if (map.get(string.length()) == null) {
				Set<String> set = new HashSet<String>();
				set.add("\"" + string + "\"");
				map.put(string.length(), set);
			} else {
				map.get(string.length()).add("\"" + string + "\"");
			}
		}

		System.out.println(map);

		String wordToAnalyze = "কৌ";
		for (int i = 0; i < wordToAnalyze.length(); i++) {
			System.out.println(wordToAnalyze.charAt(i));
		}

	}

	@Ignore
	@Test
	public void stemmerTest() {

		BanglaStemmer stemmer = new BanglaStemmer();

		String words[] = { "আবারও", "নেতাকে", "প্রত্যাহারের", "লীগের",
				"জনসভায়", "ছেলেমেয়েদের", "করেছিলেন", "যুদ্ধাপরাধীদের",
				"উড়িয়েছেন", "বই" };
		for (String word : words) {
			int stemWordLen = stemmer.stem(word.toCharArray(), word.length());
			System.out.println(word + "(" + word.length() + ") -> "
					+ word.substring(0, stemWordLen));
		}
	}
}
