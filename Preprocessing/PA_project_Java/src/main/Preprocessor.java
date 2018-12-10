package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.*;
import org.jsoup.Jsoup;

import com.vdurmont.emoji.EmojiParser;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

public class Preprocessor {
	private String in;
	private String out;
	private String combined;
	private StanfordCoreNLP pipeline;
	
	public Preprocessor(String in, String out) {
		initCoreNLP();
		String rt_re = "RT ";
		String at_re = "@\\S+ *";
		String url_re = "https?:\\S+";
		String hashtag_re = "#";
		combined = String.join("|", rt_re, at_re, url_re, hashtag_re);
		this.in = in;
		this.out = out;
	}
	
	public void rawToCSV() throws IOException, JSONException, ParseException {
		DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	    DateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH);
	    inputFormat.setLenient(true);

		File dir = new File(in);

		//Pattern p = Pattern.compile(combined);
		
		for(File data : dir.listFiles()) {
			StringBuilder builder = new StringBuilder();
			BufferedWriter writer = new BufferedWriter(new FileWriter(out+"\\"+data.getName()+".csv"));
			builder.append("time, score\n");
			
			BufferedReader br = new BufferedReader(new FileReader(data));
			String line;
			long startTime = System.currentTimeMillis();
			int i = 0;
			int sc;
			double sentiment;
			Annotation ann;
			
			while((line = br.readLine())!=null) {
				i++;
				JSONObject json = new JSONObject(line);
				String str = Jsoup.parse((String) json.get("Text")).body().text();
				String rst = str.replaceAll(combined, "");
				
				//emoji remover
				rst = EmojiParser.removeAllEmojis(rst);
				
				//special char remover
				String regex = "[^\\p{L}\\p{N}\\p{P}\\p{Z}]";
				Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
				Matcher matcher = pattern.matcher(rst);
				String result = matcher.replaceAll("");
				
				ann = pipeline.process(result);
				sentiment = 0;
				sc = 0;
				for(CoreMap sentence : ann.get(CoreAnnotations.SentencesAnnotation.class)) {
					sentiment += RNNCoreAnnotations.getPredictedClass(sentence.get(SentimentAnnotatedTree.class));
					sc++;
				}
				//toSentiment(result);
				if (i%1000 == 0) {
					System.out.println("processed "+i +"in "+(System.currentTimeMillis()-startTime));
					startTime = System.currentTimeMillis();
				}
				Date date = (Date) inputFormat.parse(json.get("Date").toString());
				builder.append(outputFormat.format(date)+", "+(sentiment/sc)+"\n");
				
			}
			writer.write(builder.toString());
			writer.close();
		}
	}
	
	private void toSentiment(String s) {
		Annotation annotation = pipeline.process(s);
        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
        	
            Tree tree = sentence.get(SentimentAnnotatedTree.class);
            int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
            System.out.print(sentiment+" "+sentence);
//            String partText = sentence.toString();
//            if (partText.length() > longest) {
//                mainSentiment = sentiment;
//                longest = partText.length();
//            }

        }
        System.out.println();
	}
	
	private void initCoreNLP() {
		// TODO Auto-generated method stub
		Properties props = new Properties();
		
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");

		pipeline = new StanfordCoreNLP(props);
	}
	
}
