package application;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjuster;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller {
	
	public AnchorPane pane;
	public Label bewonerLabel, datumLabel, uurLabel, watLabel, freqLabel, errorLabel;
	public TextArea infoTA, extraInfo;
	public ChoiceBox<String> weekCB, watCB;
	public ChoiceBox<Integer> uurCB, minCB, freqCB;
	public ComboBox<Bewoner> bewonerCB;
	public ListView<Afspraak> afspraakLV;
	public DatePicker dateInput;
	public CheckBox uurCheckBox;
	public MenuItem export, createBewoner, exit, delete, deleteAll;
	public ContextMenu contextMenu;
	
	String[] watArray = {"Brush", "MP", "Knippen", "Perm", "Kleur", "Meshen"};
	static ArrayList<Bewoner> bewoners = new ArrayList<Bewoner>();
	static ArrayList<Afspraak> afspraken = new ArrayList<Afspraak>();
	Scanner scanner;

	public void initialize() {
		
		leesBewoners();
		leesAfspraken();
		
		scanner.close();
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
		
		export.setOnAction(e -> {
			System.out.println("export");
		});
		
		createBewoner.setOnAction(e -> {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("build2.fxml"));
				Stage stage = new Stage();
				stage.setScene(new Scene(root));
				stage.setTitle("Bewoner aanmaken");
				stage.show();
				
				stage.setOnCloseRequest(Listener -> updateBewoners());
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		exit.setOnAction(e -> {
			try {
				exit();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
        afspraakLV.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
        	 
            @Override
            public void handle(ContextMenuEvent event) {
 
                contextMenu.show(afspraakLV, event.getScreenX(), event.getScreenY());
            }
        });
		
		delete.setOnAction(e -> {
			
			Afspraak toDelete = afspraakLV.getSelectionModel().getSelectedItem();
			afspraken.remove(toDelete);
			
		});
		
		deleteAll.setOnAction(e -> {

			Afspraak selected = afspraakLV.getSelectionModel().getSelectedItem();
			ArrayList<Afspraak> toDelete = new ArrayList<Afspraak>();
			
			Bewoner b = selected.getBewoner();
			LocalDate date = selected.getDate();
			String wat = selected.getWat();
						
			try {
			for (int i = 0; i < afspraken.size(); i++) {
				if (afspraken.get(i).getBewoner().equals(b) && afspraken.get(i).getWat().equals(wat) && afspraken.get(i).getDate().compareTo(date) >= 0) {
					toDelete.add(afspraken.get(i));
				}
			}
			
			for (int i = 0; i < toDelete.size(); i++) {
				afspraken.remove(toDelete.get(i));
			}
			
			} catch (Exception el) {
				el.printStackTrace();
			}
		});
	}
	
	public void leesBewoners() {
		try {
			scanner = new Scanner(new File("C:/kapsalon/bewoners.txt"));
			while (scanner.hasNext()) {
				bewoners.add(new Bewoner(scanner.next(), scanner.nextInt(), scanner.nextInt(), scanner.nextBoolean(), scanner.next(), scanner.nextBoolean(), scanner.nextBoolean(), scanner.next()));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void leesAfspraken() {
		try {
			scanner = new Scanner(new File("C:/kapsalon/afspraken.txt"));
			System.out.println("ja");
			
			while (scanner.hasNext()) {
				String naam = scanner.next();
				Bewoner bewoner = null;
				
				for (Bewoner b : bewoners) {
					if (b.getNaam().equals(naam)) {
						bewoner = b;
					}
				}
				
				String datumStr = scanner.next();
				int jaar = Integer.valueOf(datumStr.substring(0, 4));
				int maand = Integer.valueOf(datumStr.substring(5, 7));
				int dag = Integer.valueOf(datumStr.substring(8, 10));
				
				String timeStr = scanner.next();
				LocalTime time;
				
				if (timeStr.equals("null")) {
					time = null;
				} else {
					int uur  = Integer.valueOf(timeStr.substring(0, 2));
					int min = Integer.valueOf(timeStr.substring(3));
					time = LocalTime.of(uur, min);
				}
				
				String wat = scanner.next();
				int freq = Integer.valueOf(scanner.next());
				
				String info = scanner.next();
				
				afspraken.add(new Afspraak(bewoner, LocalDate.of(jaar, maand, dag), time, wat, freq, info));
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void exit() throws IOException {
		try {
			System.out.println("closing");
			Writer bewonerWriter = new Writer("C:/kapsalon/bewoners.txt");
			Writer afspraakWriter = new Writer("C:/kapsalon/afspraken.txt");
			
			for (int i = 0; i < bewoners.size(); i++) {
				bewonerWriter.write(bewoners.get(i).getGegevens());
			}
			
			for (int i = 0; i < afspraken.size(); i++) {
				afspraakWriter.write(afspraken.get(i).getGegevens());
			}
			bewonerWriter.close();
			afspraakWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Platform.exit();
		System.exit(0);
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
			String info;
			if (extraInfo.getText().equals("")) {
				info = "null";
			} else {
				info = extraInfo.getText();
			}
						
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
		
		while (date.compareTo(endDate) < 0) {
			LocalDate newDate = date.with(ta);
			
			afspraken.add(new Afspraak(afspraak.getBewoner(), date, afspraak.getUur(), afspraak.getWat(), afspraak.getFreq(), afspraak.getInfo()));

			date = newDate;
		}
		
	}
	
	public boolean checkInput() {
		
		errorLabel.setText("");
		
		if (bewonerCB.getValue() == null) {
			errorLabel.setText("Geen bewoner aangeduid");
			return false;
		}
		
		if (dateInput.getValue() == null) {
			errorLabel.setText("Geen datum aangeduid");
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
			
			if (info.equals("null")) {
				info = "";
			}
			
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
			
		} catch (Exception e) {
		}
	}
	
	
	public void updateBewoners() {
		bewonerCB.getItems().clear();
		for (int i = 0; i < bewoners.size(); i++) {
			bewonerCB.getItems().add(bewoners.get(i));
		}
	}
	
	
}
