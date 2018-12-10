package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;

public class Merge {
	private String in;
	private String out;
	public Merge(String in, String out) {
		this.in = in;
		this.out = out;
	}
	public void m() throws IOException {
		File dir = new File(in);
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss");
		int fc = 0;

		BufferedWriter writer = new BufferedWriter(new FileWriter(out+"tweetD.csv", true));
		writer.write("time,score\n");
		for(File data : dir.listFiles()) {
			fc++;
			StringBuilder builder = new StringBuilder();
			
			BufferedReader br = new BufferedReader(new FileReader(data));
			String line = br.readLine();

			while((line = br.readLine())!=null) {
				String s[] = line.split(", ");
				if(s[1].equals("NaN")) {
					s[1] = "0.0";
				}
				DateTime temp = formatter.parseDateTime(s[0]).toDateTime(DateTimeZone.UTC);
				builder.append(temp.toString(formatter)+","+s[1]+"\n");
				
			}
			System.out.println(fc);
			writer.write(builder.toString());
			
		}
		writer.close();
	}
}
