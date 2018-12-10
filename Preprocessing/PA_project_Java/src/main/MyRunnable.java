package main;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;

public class MyRunnable implements Runnable{
	private String in;
	private String out;
	public MyRunnable(String in, String out) {
		this.in = in;
		this.out = out;
	}
	@Override
	public void run() {
		Preprocessor pr = new Preprocessor(in, out);
		//Purge p = new Purge(in, out);
		try {
		//	p.p();
			pr.rawToCSV();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
