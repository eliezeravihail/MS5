package tools;

import java.io.File;
import java.util.Arrays;

import Model.pathCalc;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class Map {
	static int[][] metrix;
	static int orginalmin, orginalmax;
	static IntegerProperty x = new SimpleIntegerProperty();
	static IntegerProperty y = new SimpleIntegerProperty();
	static int planeX = 0;
	static int planeY = 0;
	public static double targetX = 0;
	public static double targetY = 0 ;
	private static pathCalc calcer = new pathCalc(metrix);



    public static void drewMap(int[][] newMap, Canvas map) throws Exception {
        if(newMap == null){throw new Exception("new Map is null, load valid map!");}
        metrix = newMap;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        //find min and max
        int[] flatint = Arrays.stream(metrix).flatMapToInt(Arrays::stream).toArray();
        for(int i: flatint){
        	max =  max<i?i:max;
        	min =  min>i?i:min;
        }
      //keep to last time
        orginalmax = max;
        orginalmin = min;
      //min-max normalization to 255-0 range (range of RGB color space);
        for (int i = 0; i < metrix.length; i++) {
			for (int j = 0; j < metrix[i].length; j++) {
				int val = metrix[i][j];
				metrix[i][j] = (int) (((val-min)*(255-0))/(double)(max-min));
				}
		}
        redraw(map);
    }

    public static void redraw(Canvas map) throws Exception {
        if(metrix == null){throw new Exception("Map is null, load map!");}
        double H = map.getHeight();
        double W = map.getWidth();
        double HRel = H / metrix.length;
        double WRel = W / metrix[0].length;

        GraphicsContext gc = map.getGraphicsContext2D();
        gc.setFill(Color.rgb(255,255,255));//the clac is not important
        gc.fillRect(0, 0, W, H);
        for (int i = 0; i < metrix.length; i++) {
            for (int j = 0; j < metrix[i].length; j++) {
                int colorvalue = metrix[i][j];
                gc.setFill(Color.rgb(normColor(255-colorvalue),normColor(colorvalue),normColor(colorvalue/10)));//the clac is not important
                gc.fillRect((j * WRel), (i * HRel), WRel, HRel);
                if(metrix[i].length<20){
                	gc.setFill(Color.BLACK);
                	gc.setTextAlign(TextAlignment.LEFT);
                	gc.fillText((int)((colorvalue/(double)255)*10)+"", j*WRel+5, i*HRel+15);//reverse normal` and mul at 1000
                }

            }
        }
        try{
           	Image img = new Image(new File("./src/maps/þþplane.png").toURI().toString());
        	gc.drawImage(img, planeX*WRel,planeY*HRel);
        }catch (Exception e) {
			System.out.println(e.getMessage());
		}
     }

    public static void drewPlane(Canvas map, int x, int y) throws Exception {
        Map.planeX = x;
        Map.planeY = y;
    	redraw(map);
     }


    public static int normColor(int i ){
    	if(i<=255 && i>=0){return i;}
    	if(i<0){return 0;}
    	return 255;
    }

	public static void drewX(Canvas map, double x2, double y2) {
		GraphicsContext gc = map.getGraphicsContext2D();
        try{
        	Image img = new Image(new File("./src/maps/x.png").toURI().toString());
        	gc.drawImage(img, x2,y2);
        }catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	//vector is list of [x,y], points to drew from one to second
	public static void drewLine(Canvas map, int[][] vector) throws Exception {
		if(vector.length<2){throw new Exception("the line is verey short!");}
		double HRel = map.getHeight() / metrix.length;
        double WRel = map.getWidth() / metrix[0].length;
        GraphicsContext gc = map.getGraphicsContext2D();
        gc.setLineWidth(2);
    	gc.setFill(Color.BLUE);
        for(int i=0;i<vector.length-1;i++){
        gc.moveTo(vector[i][0]*HRel-HRel/2,vector[i][1]*WRel-HRel/2);
        gc.lineTo(vector[i+1][0]*HRel-HRel/2,vector[i+1][1]*WRel-HRel/2);
        gc.stroke();
	}
	}

	public static void calcAndDrewPath(Canvas map) throws Exception {
		double HRel = map.getHeight() / metrix.length;
        double WRel = map.getWidth() / metrix[0].length;
//		int[][] way = calcer.calc(new int[]{(int)(planeX/WRel),(int)(planeY/HRel)},new int[]{(int)(targetX/WRel),(int)(targetY/HRel)});
		calcAndDrewPath(map, new int[]{(int)(planeX),(int)planeY},new int[]{(int)(targetX),(int)(targetY)});

	}
	public static void calcAndDrewPath(Canvas map, int[] from, int[] to) throws Exception {
		double H = map.getHeight();
        double W = map.getWidth();
        double HRel = H / metrix.length;
        double WRel = W / metrix[0].length;
		GraphicsContext gc = map.getGraphicsContext2D();
        gc.setLineWidth(1);
    	gc.setFill(Color.BLUE);
        gc.strokeLine(from[0]*HRel,from[1]*WRel,to[0],to[1]);
    	}





}