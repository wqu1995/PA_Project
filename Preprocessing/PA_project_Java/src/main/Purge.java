package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;

public class Purge {
	private String in;
	private String out;
	public Purge(String in, String out) {
		this.in = in;
		this.out = out;
	}
	
	public void p() throws IOException {
		File dir = new File(in);
		DateTimeFormatter formatter = DateTimeFormat.forPattern("EEE MMM dd HH:mm:ss Z yyyy");
		DateTime current = null, temp;
		int count = 0;
		int x = 0;
		int linec=0;
		int fc = 0;
		for(File data : dir.listFiles()) {
			StringBuilder builder = new StringBuilder();
			BufferedWriter writer = new BufferedWriter(new FileWriter(out+data.getName()));
			BufferedReader br = new BufferedReader(new FileReader(data));
			String line;
			linec=0;
			while((line = br.readLine())!=null) {
				linec++;
				System.out.println(linec);
				JSONObject json = new JSONObject(line);
//				if(count == 0) {
//					current = formatter.withOffsetParsed().parseDateTime(json.get("created_at").toString());
//				}
				if(!json.has("limit")) {
					temp = formatter.withOffsetParsed().parseDateTime(json.get("created_at").toString());
					JSONObject obj = new JSONObject();
					obj.put("Date", temp.toString(formatter));
					obj.put("Text", json.get("text"));
					
					builder.append(obj.toString()+"\n");
					x++;
				}

//				if(current.equals(temp)) {
//					if(count<1) {
//						JSONObject obj = new JSONObject();
//						obj.put("Date", temp.toString(formatter));
//						obj.put("Text", json.get("text"));
//						
//						builder.append(obj.toString()+"\n");
//						x++;
//					}
//					count++;
//				}
//				else {
//					count = 0;
//				}
				
			}
			fc++;
			writer.write(builder.toString());
			writer.close();
			System.out.println(fc+" "+x);
		}
		
	}
}
