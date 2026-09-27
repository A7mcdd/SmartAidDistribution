package project.views;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import project.core.AidManager;
import project.model.Beneficiary;
import project.model.DistributionEvent;
import project.model.Family;

public class ReportsView extends VBox{
	private TextField cityField;
	private Label familiesResult,totalDist,categoryResult,mostCity;
	private Button familiesBtn,betweenBtn;
	private DatePicker from,to;
	private TableView<Beneficiary> table;
	private ObservableList<Beneficiary> betweenList;
	@SuppressWarnings("unchecked")
	public ReportsView(AidManager manager){

		cityField = new TextField();
		cityField.setPromptText("City");
		familiesResult = new Label();
		familiesBtn = new Button("Families Served");

		totalDist =new Label("Total Aid Items Distributed: " + manager.events.size());

		categoryResult = new Label(reportCountByCategory(manager));

		from = new DatePicker();
		to = new DatePicker();

		betweenBtn = new Button("Between Dates");

		betweenList = FXCollections.observableArrayList();
		table = new TableView<>(betweenList);
		TableColumn<Beneficiary, Integer> idCol = new TableColumn<>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Beneficiary, String> nameCol = new TableColumn<>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Beneficiary, String> cityCol = new TableColumn<>("City");
		cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
		table.getColumns().addAll(idCol, nameCol, cityCol);
		table.setMaxHeight(100);




		mostCity = new Label("Most Served City: " + DashboardView.reportMostServedCity(manager));

		Separator s1 = new Separator();
		Separator s2 = new Separator();
		Separator s3 = new Separator();
		Separator s4 = new Separator();

		//actions
		familiesBtn.setOnAction(e -> {
			if (cityField.getText().trim().isEmpty()) {//لو فاضي
				Alertt.info("Enter a city");
				familiesResult.setText("");
				return;}

			boolean cityFound = false;
			for (String c : manager.cities) {//بتاكد لونها مدينة موجودة
				if (c.equalsIgnoreCase(cityField.getText().trim())) {
					cityFound = true;
					break;
				}
			}

			if (!cityFound) {//وبعد ما يتاكد بطبع الاشعار
				Alertt.info("City not served. Available cities: " + manager.cities);
				familiesResult.setText("");
				return;
			}

			ArrayList<Integer> servedFamilies = new ArrayList<>();
			for (DistributionEvent d : manager.events) {
				if (d.getBeneficiary() == null) continue;

				if (d.getBeneficiary() instanceof Family &&d.getBeneficiary().getCity().equalsIgnoreCase(cityField.getText())) {
					servedFamilies.add(d.getBeneficiary().getId());
				}
			}
			familiesResult.setText("Families: " + servedFamilies.size());
		});

		betweenBtn.setOnAction(e -> {

			if (from.getValue() == null || to.getValue() == null) {//بتاكد انو مو فارغ
				Alertt.info("Select both start and end dates");
				betweenList.clear();
				return;
			}

			LocalDate f = from.getValue();
			LocalDate t = to.getValue();

			if ((f.isAfter(LocalDate.now())||(t.isAfter(LocalDate.now())))) {//مو لازم يكون تاريخ بالمتسقبل
				Alertt.error("Date cannot be in the future");
				return;
			}

			if (f.isAfter(t)) {//مو لازم يكون تاريخ البعد قبله
				Alertt.error("Start date can not be after end date");
				betweenList.clear();
				return;
			}

			GregorianCalendar start = new GregorianCalendar(f.getYear(), f.getMonthValue() - 1, f.getDayOfMonth());
			GregorianCalendar end = new GregorianCalendar(t.getYear(), t.getMonthValue() - 1, t.getDayOfMonth());

			betweenList.clear();
			for (DistributionEvent ev : manager.events) {
				if (ev.getBeneficiary() == null || ev.getDate() == null) continue;
				GregorianCalendar d = ev.getDate();
				if ((d.after(start) || d.equals(start)) && (d.before(end) || d.equals(end))) {
					betweenList.add(ev.getBeneficiary());
				}
			}
		});



		setSpacing(15);
		setAlignment(Pos.CENTER);
		setPadding(new Insets(20));
		getChildren().addAll(new Label("Families Served in City"),cityField, familiesBtn, familiesResult,
				s1,totalDist,s2,categoryResult, s3,
				new Label("Beneficiaries Between Dates"),from, to, betweenBtn,table,
				s4,mostCity);
	}


	public String reportCountByCategory(AidManager manager) {
		int foodCount = 0;
		int medicalCount = 0;
		int winterCount = 0;
		int emergencyCount = 0;

		for (DistributionEvent e : manager.events) {
			if (e.getItem() == null) 
				continue;
			String cat = e.getItem().getCategory();

			if (cat.equals("Food Package")) foodCount++;
			else if (cat.equals("Medical Kit")) medicalCount++;
			else if (cat.equals("Winter Bag")) winterCount++;
			else if (cat.equals("Emergency Kit")) emergencyCount++;
		}

		return "By Category:" +
		"\nFood: " + foodCount +
		"\nMedical: " + medicalCount +
		"\nWinter: " + winterCount +
		"\nEmergency: " + emergencyCount;
	}
}

