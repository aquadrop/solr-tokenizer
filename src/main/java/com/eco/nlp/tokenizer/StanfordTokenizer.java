package com.ecovacs.nlp.tokenizer;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.AttributeFactory;

public class StanfordTokenizer extends Tokenizer {

	private TokenizerStanfordCore adapter;

	private CharTermAttribute termAtt;

	public StanfordTokenizer() {
		this.termAtt = addAttribute(CharTermAttribute.class);
		adapter = TokenizerStanfordCore.getInstance(false);
	}

	/**
	 * @param factory
	 */
	public StanfordTokenizer(AttributeFactory factory) {
		super(factory);
	}

	@Override
	public boolean incrementToken() throws IOException {
		clearAttributes();
		if(adapter.hasNext()){
			String token = adapter.next();
			termAtt.append(token);
			termAtt.setLength(token.length());
			return true;
		}
		return false;
	}

	@Override
	public void reset() throws IOException {
		super.reset();
		adapter.reset(this.input);
	}
}