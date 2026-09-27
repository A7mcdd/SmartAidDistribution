package project;

import java.io.IOException;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import project.core.*;
import project.views.*;
import project.model.*;
import project.exceptions.*;




public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AidManager manager = new AidManager();
			BorderPane root = new BorderPane();

			DashboardView dashboad = new DashboardView(manager);
			BeneficiariesView beneficiaries = new BeneficiariesView(manager);
			AidItemsView aidItems = new AidItemsView(manager);
			FilesView files = new FilesView(manager);
			ReportsView reports = new ReportsView(manager);
			DistributionView distribution = new DistributionView(manager);

			//bar
			Menu fileMenu = new Menu("File");
			Menu exitMenu = new Menu("Exit");

			MenuItem saveText = new MenuItem("Save (Text)");
			MenuItem loadText = new MenuItem("Load (Text)");
			MenuItem saveBinary = new MenuItem("Save (Binary)");
			MenuItem loadBinary = new MenuItem("Load (Binary)");
			MenuItem exitItem = new MenuItem("Close");

			saveText.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
			loadText.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
			saveBinary.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+S"));
			loadBinary.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+L"));
			exitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));
			//bar actions
			saveText.setOnAction(e -> {
				try {
					manager.saveToTextFile("beneficiaries.txt");
					Alertt.info("Data saved successfully");
				} catch (IOException ex) {
					Alertt.error("Failed to save\n" + ex.getMessage());
					ex.printStackTrace();
				}
			});
			loadText.setOnAction(e -> {
				try {
					manager.loadFromTextFile("beneficiaries.txt");
					Alertt.info("Data loaded successfully");
				} catch (IOException ex) {
					Alertt.error("Failed to load \nFile may not exist");
				}
			});
			saveBinary.setOnAction(e -> {
				try {
					manager.saveToBinaryFile("data.dat");
					Alertt.info("Data saved successfully");
				} catch (IOException ex) {
					Alertt.error("Failed to save\n" + ex.getMessage());
				}
			});
			loadBinary.setOnAction(new EventHandler<ActionEvent>() {
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
			
			ExitHandler Ehandler = new ExitHandler();
			exitItem.setOnAction(Ehandler);

			fileMenu.getItems().addAll(saveText,loadText,saveBinary,loadBinary);
			exitMenu.getItems().add(exitItem);

			MenuBar menuBar = new MenuBar(fileMenu, exitMenu);



			//tabs
			TabPane tabs = new TabPane();

			Tab dashboardTab = new Tab("Dashboard");
			Tab benTab = new Tab("Beneficiaries");
			Tab itemTab = new Tab("Aid Items");
			Tab distributionTab = new Tab("Distribution");
			Tab reportsTab = new Tab("Reports");
			Tab fileTab = new Tab("Files");
			dashboardTab.setContent(dashboad);
			benTab.setContent(beneficiaries);
			itemTab.setContent(aidItems);
			fileTab.setContent(files);
			reportsTab.setContent(reports);
			distributionTab.setContent(distribution);
			//tab actions refresh data عشان كل ما افتح تاب يسوي وحدة جديدة ويسوي ريفريش للبيانات
			dashboardTab.setOnSelectionChanged(e -> {
			    if (dashboardTab.isSelected()) {
			    	dashboardTab.setContent(new DashboardView(manager));
			    }
			});
			benTab.setOnSelectionChanged(e -> {
			    if (benTab.isSelected()) {
			    	benTab.setContent(new BeneficiariesView(manager));
			    }
			});
			distributionTab.setOnSelectionChanged(e -> {
			    if (distributionTab.isSelected()) {
			        distributionTab.setContent(new DistributionView(manager));
			    }
			});
			itemTab.setOnSelectionChanged(e -> {
			    if (itemTab.isSelected()) {
			    	itemTab.setContent(new AidItemsView(manager));
			    }
			});
			reportsTab.setOnSelectionChanged(e -> {
			    if (reportsTab.isSelected()) {
			    	reportsTab.setContent(new ReportsView(manager));
			    }
			});
			fileTab.setOnSelectionChanged(e -> {
			    if (fileTab.isSelected()) {
			    	fileTab.setContent(new FilesView(manager));
			    }
			});
			
			
			
			tabs.getTabs().addAll(dashboardTab,benTab,itemTab,distributionTab,reportsTab,fileTab);
			tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);// can not close the tab


			root.setCenter(tabs);
			root.setTop(menuBar);

			Scene scene = new Scene(root,1000,700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Smart Aid Distribution System");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	class ExitHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			Platform.exit();
		}
	}
}
