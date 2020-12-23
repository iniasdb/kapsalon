package application;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjuster;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class Controller {
	
	public AnchorPane pane;
	public Label bewonerLabel, datumLabel, uurLabel, watLabel, freqLabel;
	public TextArea infoTA, extraInfo;
	public ChoiceBox<String> weekCB, watCB;
	public ChoiceBox<Integer> uurCB, minCB, freqCB;
	public ComboBox<Bewoner> bewonerCB;
	//public ChoiceBox<Bewoner> bewonerCB;
	public ListView<Afspraak> afspraakLV;
	public DatePicker dateInput;
	public CheckBox uurCheckBox;
	
	String[] watArray = {"Brush", "MP", "Knippen", "Perm", "Kleur", "Meshen"};
	ArrayList<Bewoner> bewoners = new ArrayList<Bewoner>();
	ArrayList<Afspraak> afspraken = new ArrayList<Afspraak>();

	public void initialize() {
		
		//choicebox initialisations
		weekCB.setValue("week 1");
		for (int i = 1; i <= 52; i++) {
			weekCB.getItems().add("week " + i);
		}
		
		watCB.setValue(watArray[0]);
		for (String x : watArray) {
			watCB.getItems().add(x);
		}
		
		freqCB.setValue(1);
		for (int i = 0; i <= 6; i++) {
			freqCB.getItems().add(i);
		}
		
		uurCB.setValue(7);
		for (int i = 7; i <= 15; i++) {
			uurCB.getItems().add(i);
		}
		
		minCB.setValue(00);
		minCB.getItems().add(00);
		minCB.getItems().add(30);
		
		//disable editing of info textarea
		infoTA.setEditable(false);
		
		//add bewoner verwijderen
		bewoners.add(new Bewoner("Jos", 02, 1, true, "Maandag", false, true, "0488329303"));
		
		//bewoner combobox cellfactory
		bewonerCB.setCellFactory(param -> new ListCell<Bewoner>() {
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
		
		for (int i = 0; i < bewoners.size(); i++) {
			bewonerCB.getItems().add(bewoners.get(i));
		}
		
		//afspraak listview cellfactory
		afspraakLV.setCellFactory(param -> new ListCell<Afspraak>() {
		    @Override
		    protected void updateItem(Afspraak afspraak, boolean empty) {
		        super.updateItem(afspraak, empty);

		        if (empty || afspraak == null || afspraak.getBewoner().getNaam() == null) {
		            setText(null);
		        } else {
		            setText(afspraak.getBewoner().getNaam());
		        }
		    }
		});
		
		afspraakLV.getSelectionModel().selectedItemProperty().addListener(ChangeListener -> showData());
		
		uurCheckBox.setSelected(true);
		uurCB.setDisable(true);
		minCB.setDisable(true);
		
		uurCheckBox.selectedProperty().addListener(e -> {
			if (!uurCheckBox.isSelected()) {
				uurCB.setDisable(true);
				minCB.setDisable(true);
			} else {
				uurCB.setDisable(false);
				minCB.setDisable(false);
			}
		});
		
//		weekCB.getSelectionModel().selectedItemProperty().addListener(e -> {
//
//			afspraakLV.getItems().clear();
//						
//			String weekString = weekCB.getValue();
//			int week = Integer.valueOf(weekString.substring(weekString.length()-2).trim());
//			
//			for (int i = 0; i < afspraken.size(); i++) {
//				
//				if (Math.ceil((double)afspraken.get(i).getDate().getDayOfYear()/7) == week) {
//					
//					afspraakLV.getItems().add(afspraken.get(i));
//					
//				}
//				
//			}
//			
//		});
		
	}
	
	public void filter(ActionEvent e) {
		afspraakLV.getItems().clear();
		
		String weekString = weekCB.getValue();
		int week = Integer.valueOf(weekString.substring(weekString.length()-2).trim());
		
		for (int i = 0; i < afspraken.size(); i++) {
			
			if (Math.ceil((double)afspraken.get(i).getDate().getDayOfYear()/7) == week) {
				
				afspraakLV.getItems().add(afspraken.get(i));
				
			}
			
		}
	}
	
	public void afspraakMaken(ActionEvent e) {
		
		if (checkInput()) {
			
			Bewoner bewoner = bewonerCB.getValue();
			LocalDate date = dateInput.getValue();
			
			System.out.println(date);
			
			LocalTime time;
			if (uurCB.isDisabled()) {
				time = null;
			} else {
				int uur = uurCB.getValue();
				int min = minCB.getValue();
				time = LocalTime.of(uur, min);
			}
			String wat = watCB.getValue();
			int freq = freqCB.getValue();
			String info = extraInfo.getText();
						
			nextAppointements(date, new Afspraak(bewoner, date, time, wat, freq, info));
			
		}
		
	}
	
	public void nextAppointements(LocalDate date, Afspraak afspraak) {
		
		//afspraken.add(afspraak);
		
		//add custom temporalAdjuster
		CustomTemporalAdjuster cta = new CustomTemporalAdjuster();
		//set days variable in class
		cta.setDays(afspraak.getFreq()*7);
		//instanciate TemporalAdjuster
		TemporalAdjuster ta = cta;
		
		//set enddate at end of next year
		LocalDate endDate = LocalDate.of(2021, 12, 31);
				
		//add new appointments until enddate is reached
		//LocalDate date = afspraak.getDate();
		System.out.println(date);
		
		while (date.compareTo(endDate) < 0) {
			LocalDate newDate = date.with(ta);
			
			afspraken.add(new Afspraak(afspraak.getBewoner(), date, afspraak.getUur(), afspraak.getWat(), afspraak.getFreq(), afspraak.getInfo()));

			date = newDate;
			System.out.println(date);
		}
		
	}
	
	public boolean checkInput() {
		if (bewonerCB.getValue() == null) {
			return false;
		}
		
		if (dateInput.getValue() == null) {
			return false;
		}
		
		return true;
	}
	
	public void showData() {
		bewonerLabel.setText("");
		datumLabel.setText("");
		uurLabel.setText("");
		watLabel.setText("");
		freqLabel.setText("");
		infoTA.setText("");	
		try {
			Afspraak selected = afspraakLV.getSelectionModel().getSelectedItem();
			
			String bewonerNaam = selected.getBewoner().getNaam();
			LocalDate date = selected.getDate();
			LocalTime time = selected.getUur();
			String wat = selected.getWat();
			int freq = selected.getFreq();
			String info = selected.getInfo();
			
			bewonerLabel.setText(bewonerNaam);
			datumLabel.setText(date.toString());
			if (time!= null) {
				uurLabel.setText(time.toString());
			} else {
				uurLabel.setText("");
			}
			watLabel.setText(wat);
			freqLabel.setText(Integer.toString(freq));
			infoTA.setText(info);
			
			//debug
			//check if day is in right week
			System.out.println(selected.getDate().getDayOfYear());
			System.out.println(Math.ceil((double)selected.getDate().getDayOfYear()/7));
		} catch (Exception e) {
		}
	}
	
}
