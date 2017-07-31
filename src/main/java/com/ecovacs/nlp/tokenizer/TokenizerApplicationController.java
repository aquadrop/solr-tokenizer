package com.ecovacs.nlp.tokenizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;


@SpringBootApplication
@EnableAutoConfiguration
@RestController
public class TokenizerApplicationController {
	
	TokenizerStanfordCore core = TokenizerStanfordCore.getInstance();
	
	@RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }
	
	@RequestMapping("/segment")
    @ResponseBody
    String segment(@RequestParam(value="q") String text) {
        return core.segment(text);
    }
	
	@RequestMapping("/pos")
    @ResponseBody
    String pos(@RequestParam(value="q") String text) {
        return core.pos(text);
    }

	public static void main(String[] args) {
		SpringApplication.run(TokenizerApplicationController.class, args);
	}
}
