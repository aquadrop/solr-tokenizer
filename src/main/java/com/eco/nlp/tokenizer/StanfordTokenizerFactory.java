package com.ecovacs.nlp.tokenizer;

import java.io.Reader;
import java.util.Map;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;

public class StanfordTokenizerFactory extends TokenizerFactory {
	public StanfordTokenizerFactory(Map<String, String> args) {
		super(args);
	}

	@Override
	public Tokenizer create(AttributeFactory arg0) {

		return new StanfordTokenizer();
	}
	
	public static void main(String[] args) {
		new StanfordTokenizerFactory(null);
	}
}
