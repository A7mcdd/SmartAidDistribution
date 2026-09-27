package project.views;

import java.time.LocalDate;
import java.util.GregorianCalendar;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import project.core.AidManager;
import project.exceptions.ItemNotFoundException;
import project.model.AidItem;
import project.model.Beneficiary;
import project.model.DistributionEvent;

public final class DistributionView extends VBox {
	private TableView<DistributionEvent> table;
	private ObservableList<DistributionEvent> list;

	@SuppressWarnings("unchecked")
	public DistributionView(AidManager manager){
		manager.events.removeIf(e ->
		e.getBeneficiary() == null || e.getItem() == null);

		table = new TableView<>();
		list = FXCollections.observableArrayList(manager.events);

		TableColumn<DistributionEvent, String> benCol = new TableColumn<>("Beneficiary");
		benCol.setCellValueFactory(c -> {
			if (c.getValue().getBeneficiary() == null) 
				return new SimpleStringProperty("-");
			return new SimpleStringProperty(c.getValue().getBeneficiary().getName());
		});
		TableColumn<DistributionEvent, String> itemCol = new TableColumn<>("Aid Item");
		itemCol.setCellValueFactory(c -> {
			if (c.getValue().getItem() == null)
				return new SimpleStringProperty("-");
			return new SimpleStringProperty(c.getValue().getItem().getDescription());
		});
		TableColumn<DistributionEvent, String> dateCol =new TableColumn<>("Date");
		dateCol.setCellValueFactory(c ->new SimpleStringProperty(c.getValue().getDate().getTime().toString()));
		table.getColumns().addAll(benCol, itemCol, dateCol);
		table.setItems(list);

		ComboBox<Beneficiary> benBox = new ComboBox<>();
		ComboBox<AidItem> itemBox = new ComboBox<>();
		benBox.getItems().setAll(manager.beneficiaries);
		itemBox.getItems().setAll(manager.aiditems);

		DatePicker date = new DatePicker(LocalDate.now());

		Button record = new Button("Record Distribution");
		record.setOnAction(e-> {
			try {
				if (benBox.getValue() == null || itemBox.getValue() == null) {
					Alertt.error("Please select The Beneficiary and The AidItem");
					return;
				}
				LocalDate d = date.getValue();
				
				if (d.isAfter(LocalDate.now())) {
					Alertt.error("Date can not be in the future");
					return;
				}

				manager.recordDistribution(itemBox.getValue(),benBox.getValue(),
						new GregorianCalendar(d.getYear(),d.getMonthValue() - 1, d.getDayOfMonth()));

				list.setAll(manager.events);//refresh
			} catch (ItemNotFoundException e1) {
				Alertt.error(e1.getMessage());
			}
		});



		setSpacing(15);
		setAlignment(Pos.CENTER);
		setPadding(new Insets(20));
		getChildren().addAll(new Label("Beneficiary"),benBox,new Label("Aid Item"),itemBox,new Label("Date"),date,record,table);

	}

	public TableView<DistributionEvent> getTable() {
		return table;
	}

	public ObservableList<DistributionEvent> getList() {
		return list;
	}


}
