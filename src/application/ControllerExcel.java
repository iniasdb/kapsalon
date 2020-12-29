package application;

import java.util.ArrayList;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressBar;

public class ControllerExcel {
	
	public ChoiceBox<String> weekCB;
	public ProgressBar pb;
	static ArrayList<Afspraak> afspraken = new ArrayList<Afspraak>();

	public void initialize() {
		afspraken = Controller.afspraken;
		
		weekCB.setValue("week 1");
		for (int i = 1; i <= 52; i++) {
			weekCB.getItems().add("week " + i);
		}
	}
	
	public void exporteren(ActionEvent e) {
        final Task<Void> task = Excel.loadFromArmxml();
        
        // Bind our ProgressBar's properties
        pb.progressProperty().bind(task.progressProperty());

        // Run the task
        new Thread(task).start();
	}
	
}
