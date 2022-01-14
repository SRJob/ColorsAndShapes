package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestShapesColors {

	private static final String COMMA_DELIMITER = ",";
	private static String responseTest=null;
	public static void main(String[] args) throws FileNotFoundException, IOException
	{		
		/*
		 * String testSrcColor = "Yellow"; String testSrcShape = "Circle";
		 */
		String testSrcColor = args[0]; String testSrcShape = args[1];
		List<Pair> list = new ArrayList<Pair>();
		TestShapesColors rp=new TestShapesColors();
		rp.readFile("Test.scv", list);System.out.println("INPUT -> Color:"+testSrcColor+", Shape:"+testSrcShape +"; \nOUTPUT-> Color & Shape is: "+ checkMatch(list, testSrcColor, testSrcShape)+";");
	}

	public static String checkMatch(List<Pair> list, String testSrcColor, String testSrcShape) {
		List<String> myList = new ArrayList<>();		
		for(Pair p : list) {			
			myList.add(p.getSrcColor().toString().equals(testSrcColor) && p.getShape().toString().equals(testSrcShape)?p.getTargetcolorAndShape():"")  ;
		}
		Stream<String> sequentialStream = myList.stream();
		Stream<String> highNumsSeq = sequentialStream.filter(p -> p != null && !p.isBlank() && !p.isEmpty());
		highNumsSeq.forEach(p -> {
			responseTest = p;
		});        
		return responseTest != null ? responseTest:"No match found";
	}


	public void readFile(String fileName, List<Pair> list) throws IOException
	{
		InputStream inputStream=null;
		try {
			ClassLoader classLoader = this.getClass().getClassLoader();	 
			inputStream = classLoader.getResourceAsStream("Test.csv");
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			try (Stream<String> stream = reader.lines()) {
				stream.forEach(s -> {
					String[] values = s.split(COMMA_DELIMITER);
					Pair pair1 = new Pair();
					pair1.setSrcColor(values[0]);
					pair1.setShape(values[1]);
					pair1.setTargetcolorAndShape(values[2]);
					list.add(pair1);
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			inputStream.close();
		}
	}
}
