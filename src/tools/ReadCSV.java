package tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ReadCSV{
	public static String[][] readToMetrix(String filePath) throws IOException{
	List<String[]> rows = new ArrayList<String[]>();
	BufferedReader br = new BufferedReader(new FileReader(filePath));
	String row;
	while ((row = br.readLine()) != null) {
	    String[] cells = row.split(",");
	        rows.add(cells);
	    }
	br.close();
	String[][] resNew =rows.stream().toArray(String[][]::new);
	int longLine = findMaxLongLine(resNew);
	String[][] complatArr =  new String[resNew.length][longLine];
	for (int i = 0; i < complatArr.length; i++) {
		for (int j = 0; j < complatArr[0].length; j++) {
			try{
				complatArr[i][j] = resNew[i][j];
			}catch (Exception e) {
				complatArr[i][j] = "0";
			}
		}
	}
	return complatArr;
	}

	public static int[][] readToIntMetrix(String filePath) throws IOException{
	String[][] metrix = ReadCSV.readToMetrix(filePath);
	int[][] res = new int[metrix.length][metrix[0].length];
	for (int i = 2; i < metrix.length; i++) {
		for (int j = 0; j < metrix[i].length; j++) {
			try{
			res[i][j] = Integer.parseInt(metrix[i][j]);
			}catch (Exception e) {
				//it is ok
				//There are tables that do not start with numbers at first
				res[i][j] = 0 ;
			}
		}
	}
	return res;
	}

	public static int findMaxLongLine(String[][] arr){
		Optional<String[]> res =  Arrays.stream(arr).max((a,b)->a.length-b.length);
		if(res.isPresent())
			return res.get().length;
		return 0;
 	}
}