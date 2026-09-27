package project.views;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import project.core.AidManager;
import project.exceptions.DuplicateRegistrationException;
import project.model.*;

public class AidItemsView extends VBox{
	private TextField code,desc;
	private ComboBox<String> type;
	private Button btAdd;
	private TableView<AidItem> table;
	private ObservableList<AidItem> alist;
	@SuppressWarnings("unchecked")
	public AidItemsView(AidManager manager){


		code = new TextField();
		code.setPromptText("Code");
		desc = new TextField();
		desc.setPromptText("Description");

		type = new ComboBox<String>();
		String[] packages = {"Food","Medical","Winter","Emergency"};
		ObservableList<String> listp = FXCollections.observableArrayList(packages);
		type.getItems().addAll(listp);
		type.setValue("Food");

		btAdd = new Button("Add Item");

		table = new TableView<>();
		alist = FXCollections.observableArrayList(manager.aiditems);

		TableColumn<AidItem, String> codeColumn = new TableColumn<>("Code");
		codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));

		TableColumn<AidItem, String> descColumn = new TableColumn<>("Description");
		descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

		TableColumn<AidItem, String> categoryColumn = new TableColumn<>("Category");
		categoryColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCategory()));//لان سبكلاسات الايد ايتم مو بملف لحال .java 


		btAdd.setOnAction(e->{
			AidItem a = null;
			String acode= code.getText();
			String adesc = desc.getText();
			if ((!acode.trim().isEmpty() && !adesc.trim().isEmpty())) {// عشان ما يدخل وهو فاضي
				switch (type.getValue()) {
				case "Food":
					a = new FoodPackage(acode, adesc);
					break;
				case "Medical":
					a = new MedicalKit(acode, adesc);
					break;
				case "Winter":
					a = new WinterBag(acode, adesc);
					break;
				case "Emergency":
					a = new EmergencyKit(acode, adesc);
					break;
				}
				try {
					manager.addAidItem(a);
				} catch (DuplicateRegistrationException e1) {
					Alertt.error(e1.getMessage());
				}
				alist.setAll(manager.aiditems);//refresh
			}
			else
				Alertt.error("Fill all Fileds");
		});

		table.getColumns().addAll(codeColumn,descColumn,categoryColumn);
		table.setItems(alist);
		setSpacing(15);
		setAlignment(Pos.CENTER);
		setPadding(new Insets(20));
		getChildren().addAll(code,desc,type,btAdd,table);
	}
	public TextField getCode() {
		return code;
	}
	public TextField getDesc() {
		return desc;
	}
	public ComboBox<String> getType() {
		return type;
	}
	public Button getBtAdd() {
		return btAdd;
	}
	public TableView<AidItem> getTable() {
		return table;
	}
	public ObservableList<AidItem> getAlist() {
		return alist;
	}
	



}
