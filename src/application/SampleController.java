package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import tools.Map;

public class SampleController implements Initializable{
    private String[] code;
    @FXML
    private Canvas map;
    @FXML
    private TextArea codeArea;
    @FXML
	private TextField portOut;
	@FXML
	private TextField ipOut;
	@FXML
	private TextField portIn;
	@FXML
	private TextField ipIn;
	@FXML
	private Circle joistic;
	@FXML
	private Circle limitofJoistic;
	private double orginalCenterY;
	private double orginalCenterX;
	private boolean joisticIsClic = false;

	@FXML
	private DoubleProperty throttle;

	@FXML
	private DoubleProperty rudder;

	public void start(){

	}
    public void clic(){
        System.out.println(this);
    }

    public void loadMap() throws IOException, Exception{
            FileChooser fc =  new FileChooser();
            fc.setInitialDirectory(new File("./src/maps"));
            fc.setTitle("Chose map file flease");
            File chosed =  fc.showOpenDialog(null);
            if(chosed != null){
                Map.drewMap(tools.ReadCSV.readToIntMetrix(chosed.getPath()),map);
                }
    }

    @SuppressWarnings("static-access")
    public void loadCode() throws Exception {
        FileChooser fc =  new FileChooser();
        fc.setInitialDirectory(new File("./src/scripts"));
        fc.setTitle("Chose Script file flease");
        File chosed =  fc.showOpenDialog(null);
        if(chosed == null){throw new Exception("file is null");}
        List<String> rows = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(chosed));
        String line;
        while ((line = br.readLine()) != null) {
                rows.add(line);
            }
        br.close();
        code = rows.toArray(new String[0]);
        codeArea.setText("".join("\n", rows));
    }

    public void runCode() throws Exception {
    	System.out.println("run");

    }
    public void markTarget(double x, double y){
    	Map.drewX(map, x, y);
    }
   	EventHandler<MouseEvent> markTarget = new EventHandler<MouseEvent>() {
   		@Override
   		public void handle(MouseEvent e) {
   			Map.targetX = e.getX();
   			Map.targetY = e.getY();
   			markTarget(e.getX(),e.getY());

    	}
    };
    public void calcPath() throws Exception{
//    	Map.calcAndDrewPath(map);
    	System.out.println("calc");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	joistic.setOnMousePressed(Pressed);
		joistic.setOnMouseDragged(Dragged);
		joistic.setOnMouseReleased(Released);
		map.setOnMouseClicked(markTarget);
		try {
			Map.drewPlane(map, 3, 4);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("events loaeded");

    }

    public void conectToSim() {
		System.out.println("conection....");
	}


    public void continiuJ() {
		if(!joisticIsClic){return;}
    	double Radiuslimit = limitofJoistic.getRadius();
    	double radius  = joistic.getRadius();
		double centerX = joistic.getCenterX();
		double centerY = joistic.getCenterY();
		orginalCenterX = joistic.getCenterX();
		orginalCenterY = joistic.getCenterY();
		joistic.setCenterX(centerX+=1);
		joistic.setCenterY(centerY+=1);

    }
    public void startJ(){
    	joisticIsClic = true;
    	}
	public void stopJ(){
		joistic.setCenterY(orginalCenterX);
		joistic.setCenterX(orginalCenterY);
	}

	EventHandler<MouseEvent> Pressed = new EventHandler<MouseEvent>() {
    	@Override
        public void handle(MouseEvent e) {
        	orginalCenterX = joistic.getCenterX();
            orginalCenterY = joistic.getCenterY();
            startJ();
    		System.out.println("start");

    	}
    };

    EventHandler<MouseEvent> Released = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent t) {
        	stopJ();
    		System.out.println("stop");

            }
	};

	EventHandler<MouseEvent> Dragged = new EventHandler<MouseEvent>() {
    	@Override
        public void handle(MouseEvent e) {
    		double  transX = e.getX();
            double  transY = e.getY();
            System.out.println((orginalCenterX+transX)+"all");
            System.out.println(orginalCenterX+"org");
            System.out.println(transX+"move");
            if(inLimit(orginalCenterX+transX,orginalCenterY+transY)){
            	joistic.setCenterY(orginalCenterY+transY);
            	joistic.setCenterX(orginalCenterX+transX);
            	Data.getTheData().put("aileron", ""+Math.abs(orginalCenterX-transX));
            	Data.getTheData().put("elevator", ""+Math.abs(orginalCenterY-transY));
            }
    	}



		private boolean inLimit(double x, double y) {
			double dist = OcDist(limitofJoistic.getCenterX(),limitofJoistic.getCenterY(),x,y);

			if(dist<limitofJoistic.getRadius()-joistic.getRadius()){
				return true;
			}
			return false;
		}
	};

	private double OcDist(double x1, double y1, double x2, double y2){
		double sum = Math.pow((x1-x2),2)+Math.pow((y1-y2),2);
		return Math.abs(Math.sqrt(sum));
	}

}


