package main;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;

public class Main {
	private static String path = "C:\\Users\\wqu\\Desktop\\tData";
	
	public static void main(String[] args) {
//		Runnable r1 = new MyRunnable("C:\\Users\\wqu\\Desktop\\tData\\f1","C:\\Users\\wqu\\Desktop\\sData\\f\\");
//		Runnable r2 = new MyRunnable("C:\\Users\\wqu\\Desktop\\tData\\f2","C:\\Users\\wqu\\Desktop\\sData\\f\\");
//		Runnable r3 = new MyRunnable("C:\\Users\\wqu\\Desktop\\tData\\f3","C:\\Users\\wqu\\Desktop\\sData\\f\\");
//		Runnable r4 = new MyRunnable("C:\\Users\\wqu\\Desktop\\tData\\f4","C:\\Users\\wqu\\Desktop\\sData\\f\\");
//		Runnable r5 = new MyRunnable("C:\\Users\\wqu\\Desktop\\tData\\f5","C:\\Users\\wqu\\Desktop\\sData\\f\\");
//		Runnable r6 = new MyRunnable("C:\\Users\\wqu\\Desktop\\tData\\f6","C:\\Users\\wqu\\Desktop\\sData\\f\\");
//		Runnable r7 = new MyRunnable("C:\\Users\\wqu\\Desktop\\tData\\f7","C:\\Users\\wqu\\Desktop\\sData\\f\\");
//		Runnable r8 = new MyRunnable("C:\\Users\\wqu\\Desktop\\tData\\f8","C:\\Users\\wqu\\Desktop\\sData\\f\\");
//		Runnable r9 = new MyRunnable("C:\\Users\\wqu\\Desktop\\tData\\f9","C:\\Users\\wqu\\Desktop\\sData\\f\\");
//		Runnable r10 = new MyRunnable("C:\\Users\\wqu\\Desktop\\tData\\f10","C:\\Users\\wqu\\Desktop\\sData\\f\\");
//		new Thread(r1).start(); 
//		new Thread(r2).start();
//		new Thread(r3).start();
//		new Thread(r4).start();
//		new Thread(r5).start();
//		new Thread(r6).start();
//		new Thread(r7).start();
//		new Thread(r8).start();
//		new Thread(r9).start();
//		new Thread(r10).start();
		//Purge p = new Purge("C:\\Users\\wqu\\Desktop\\ttest\\f2","C:\\Users\\wqu\\Desktop\\tData\\f2\\");
//		Preprocessor pr = new Preprocessor("C:\\Users\\wqu\\Desktop\\ttest");
		Merge m = new Merge("C:\\Users\\wqu\\Desktop\\sData\\f", "C:\\Users\\wqu\\Desktop\\fData\\");
		try {
//			//pr.rawToCSV();
			m.m();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
