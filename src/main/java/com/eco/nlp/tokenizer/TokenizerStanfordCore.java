package com.ecovacs.nlp.tokenizer;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.pipeline.demo.CoreNLPService;

public class TokenizerStanfordCore implements Iterator<String> {

	private static TokenizerStanfordCore instance;
	
	private JiebaSegmenter segmenter = new JiebaSegmenter();
	
	private Iterator<String> tokens;
	
	CoreNLPService coreNLPService;
	StanfordCoreNLP pipeline;
	
	public static TokenizerStanfordCore getInstance() {
		return getInstance(true);
	}
	
	public static TokenizerStanfordCore getInstance(boolean heavy) {
		if (instance == null) {
			instance = new TokenizerStanfordCore(heavy);
		}
		return instance;
	}
	
	private TokenizerStanfordCore(boolean heavy) {
		if (heavy) {
			this.coreNLPService = new CoreNLPService();
			this.pipeline = new StanfordCoreNLP("edu/stanford/nlp/pipeline/StanfordCoreNLP-chinese.properties");
		}
	}
	
	public String segment(String input) {
		return this.coreNLPService.segment(this.pipeline, input);
	}
	
	public List<String> arraySegment(String input) {
		String[] tokens = null;
		try {
			tokens = WebUtils.get("http://localhost:11415/segment?q=", input).split(" ");
		} catch (Exception e) {
			List<SegToken> segtokens = segmenter.process(input, SegMode.INDEX);
			tokens = new String[segtokens.size()];
			for (int i = 0; i < segtokens.size(); i++) {
				tokens[i] = segtokens.get(i).word;
			}
		}
//		String[] tokens = this.segment(input).split(" ");
		List<String> listTokens = new ArrayList<String>();
		for (String t : tokens) {
			listTokens.add(t);
		}
		
		return listTokens;
	}
	
	public String pos(String input) {
		return this.coreNLPService.pos(pipeline, input);
	}

	@Override
	public boolean hasNext() {
		return tokens.hasNext();
	}

	@Override
	public String next() {
		return tokens.next();
	}
	
	public void reset(Reader input) {
		System.out.println("reset in" + Thread.currentThread().getName());
		String raw = null;
		try {
			StringBuilder bdr = new StringBuilder();
			char[] buf = new char[1024];
			int size = 0;
			while ((size = input.read(buf, 0, buf.length)) != -1) {
				String tempstr = new String(buf, 0, size);
				bdr.append(tempstr);
			}
			raw = bdr.toString().trim();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String> list = this.arraySegment(raw);
		tokens = list.iterator();
	}
	
	public static void main(String[] args) {
		
		TokenizerStanfordCore core = TokenizerStanfordCore.getInstance(false);
	    String[] sentences =
	        new String[] {"这是一个伸手不见五指的黑夜。我叫孙悟空，我爱北京，我爱Python和C++。", "我不喜欢日本和服。", "雷猴回归人间。",
	                      "工信处女干事每月经过下属科室都要亲口交代24口交换机等技术性器件的安装工作", "结果婚的和尚未结过婚的"};
	    for (String sentence : sentences) {
	        System.out.println(core.arraySegment(sentence));
	    }
//		TokenizerStanfordCore core = TokenizerStanfordCore.getInstance();
//		Scanner input = new Scanner(System.in);
//		String text = null;
//		String segmentation = null;
//		String tagging = null;
//		do {
//			System.out.print("Enter a sentence:");
//			text = input.next();
//			segmentation = core.segment(text);
//			tagging = core.pos(text);
//			System.out.println("分词结果:"+segmentation);
//			System.out.println("词性标注结果:"+tagging);
//		} while (!text.equals("quit"));
	}

}
