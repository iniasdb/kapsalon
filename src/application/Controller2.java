package application;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Controller2 {
	
	public TextField naamTF, kamerNrTF, telTF;
	public CheckBox badCB, ophalenCB, flatCB;
	public ChoiceBox<String> badDagCB;
	public ListView<Bewoner> bewonerLV;

	String[] dagen = {"Maandag", "Dinsdag", "Woensdag", "Donderdag", "Vrijdag", "Zaterdag", "Zondag"};
	
	public void initialize() {
		
		ArrayList<Bewoner> bewoners = Controller.bewoners;
		
		for (int i = 0; i < bewoners.size(); i++) {
			bewonerLV.getItems().add(bewoners.get(i));
		}
		
		//bewoner listview cellfactory
		bewonerLV.setCellFactory(param -> new ListCell<Bewoner>() {
		    @Override
		    protected void updateItem(Bewoner bewoner, boolean empty) {
		        super.updateItem(bewoner, empty);

		        if (empty || bewoner == null || bewoner.getNaam() == null) {
		            setText(null);
		        } else {
		            setText(bewoner.getNaam());
		        }
		    }
		});
				
		badCB.setSelected(false);

		badCB.selectedProperty().addListener(e -> {
			if (!badCB.isSelected()) {
				badDagCB.setDisable(true);
			} else {
				badDagCB.setDisable(false);
			}
		});
		
		for (int i = 0; i < 7; i++) {
			badDagCB.getItems().add(dagen[i]);
		}

	}
	
	public void add(ActionEvent e) {
		
		String naam = naamTF.getText();
		String kamerNr = kamerNrTF.getText();
		String badDag = badDagCB.getValue();
		boolean bad = badCB.isSelected();
		boolean flat = flatCB.isSelected();
		boolean ophalen = ophalenCB.isSelected();
		String tel = telTF.getText();
		
		Bewoner temp = new Bewoner(naam, Integer.valueOf(kamerNr), Integer.valueOf(kamerNr.substring(0, 1)), bad, badDag, flat, ophalen, tel);
		Controller.bewoners.add(temp);
		bewonerLV.getItems().add(temp);
		
	}
	
}
