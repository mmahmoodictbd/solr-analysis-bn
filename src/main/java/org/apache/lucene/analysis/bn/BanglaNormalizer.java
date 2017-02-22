package org.apache.lucene.analysis.bn;

import static org.apache.lucene.analysis.util.StemmerUtil.*;

public class BanglaNormalizer {
	/**
	 * Normalize an input buffer of Bangla text
	 * http://www.panl10n.net/english/outputs
	 * /Collation%20Book/Collation%20Book/Final%20Versions/pdfs/Bengali.pdf
	 * 
	 * @param s
	 *            input buffer
	 * @param len
	 *            length of input buffer
	 * @return length of input buffer after normalization
	 */
	public int normalize(char s[], int len) {

		for (int i = 0; i < len; i++) {

			switch (s[i]) {

			// ZERO WIDTH JOINER/NON-JOINER -> delete
			case '\u200D':
			case '\u200C':
				len = delete(s, i, len);
				i--;
				break;

			// hasanto (Virama) -> delete
			case '\u09CD':
				len = delete(s, i, len);
				i--;
				break;

			// nukta -> delete
			case '\u09BC':
				len = delete(s, i, len);
				i--;
				break;

			// nukta based character -> replace
			case '\u09DC': // ড় -> ড
				s[i] = '\u09A1';
				break;
			case '\u09DD': // ঢ় -> ঢ
				s[i] = '\u09A2';
				break;
			case '\u09DF': // য় -> য
				s[i] = '\u09AF';
				break;

			// two part dependent vowels -> merge
			case '\u09C7': // ে + া = ো, ে + ৗ = ৌ
				if (i + 1 < len) {
					if (s[i + 1] == '\u09BE') {
						s[i] = '\u09CB';
						len = delete(s, i + 1, len);
					} else if (s[i + 1] == '\u09D7') {
						s[i] = '\u09CC';
						len = delete(s, i + 1, len);
					}
				}
				break;

			// long to short dependent vowels
			case '\u09C0': // কী -> কি
				s[i] = '\u09BF';
				break;
			case '\u09C2': // ভূল -> ভুল
				s[i] = '\u09C1';
				break;
			default:
				break;
			}
		}

		return len;
	}
}
