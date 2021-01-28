package Model;

import java.util.ArrayList;

public class pathCalc {
	private int[][] matrix;
	public pathCalc(int[][] ametrix){
		this.matrix = ametrix;
		}
	//return 2d array
	public int[][] calc(int[] from, int[] to){
		int fromX = from[0];
		int fromY= from[1];
		int toX = to[0];
		int toY = to[1];
		ArrayList<int[]> arr = new ArrayList<int[]>();
		//body
		int tempX = fromX;
		int tempY = fromY;
		arr.add(new int[]{tempX,tempY});
		while(tempY!=toY){
			if(tempY<toY){
				++tempY;
			}else if(tempY>toY){
				--tempY;
			}
			arr.add(new int[]{tempX,tempY});
		}
		while(tempX!= toX){
			if(tempX<toX){
				++tempX;
			}else if(tempX>toX){
				--tempX;
			}
			arr.add(new int[]{tempX,tempY});
		}
		int[][] res =  new int[arr.size()][2];
		return arr.toArray(res);
	}

}






