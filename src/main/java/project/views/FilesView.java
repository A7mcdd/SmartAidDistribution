package project.views;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import project.core.AidManager;

public class FilesView extends VBox{
	Button saveT,loadT,saveB,loadB;
	public FilesView(AidManager manager) {
		saveT = new Button("Save to Text File");
		saveT.setMinWidth(140);
		loadT = new Button("Load from Text File");
		loadT.setMinWidth(140);
		saveB = new Button("Save to Binary File");
		saveB.setMinWidth(140);
		loadB = new Button("Load from Binary File");
		loadB.setMinWidth(140);
		
		saveT.setOnAction(e -> {
			try {
				manager.saveToTextFile("beneficiaries.txt");
				Alertt.info("Data saved successfully");
			} catch (IOException ex) {
				Alertt.error("Failed to save\n" + ex.getMessage());
				ex.printStackTrace();
			}
		});
		loadT.setOnAction(e -> {
			try {
				manager.loadFromTextFile("beneficiaries.txt");
				Alertt.info("Data loaded successfully");
			} catch (IOException ex) {
				Alertt.error("Failed to load \nFile may not exist");
			}
		});
		saveB.setOnAction(e -> {
			try {
				manager.saveToBinaryFile("data.dat");
				Alertt.info("Data saved successfully");
			} catch (IOException ex) {
				Alertt.error("Failed to save\n" + ex.getMessage());
			}
		});
		loadB.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					manager.loadFromBinaryFile("data.dat");
					Alertt.info("Data loaded successfully");
				} catch (IOException ex) {
					Alertt.error("Failed to load \nFile may not exist");
				}
			}
		});
		
		
		setSpacing(15);
		setAlignment(Pos.CENTER);
		setPadding(new Insets(20));
		getChildren().addAll(saveT,loadT,saveB,loadB);
	}
	
	public Button getSaveT() {
		return saveT;
	}
	public Button getLoadT() {
		return loadT;
	}
	public Button getSaveB() {
		return saveB;
	}
	public Button getLoadB() {
		return loadB;
	}
	
	
	
	
}
