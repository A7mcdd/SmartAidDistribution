package project.views;



import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import project.core.AidManager;
import project.exceptions.CityNotServedException;
import project.exceptions.DuplicateRegistrationException;
import project.model.Beneficiary;
import project.model.Family;
import project.model.Individual;

public class BeneficiariesView extends VBox{
	private HBox hbox1,hbox2,hbox3;
	private	RadioButton family,invdividual;
	private	TextField id,name,city,mcount,searchField;
	private	Button btAdd,btClear,btSearch;
	private	TableView<Beneficiary> table;
	private ObservableList<Beneficiary> blist;

	@SuppressWarnings("unchecked")
	public BeneficiariesView(AidManager manager){

		family = new RadioButton("Family");
		invdividual = new RadioButton("Individual");
		ToggleGroup tg = new ToggleGroup();
		family.setToggleGroup(tg);
		invdividual.setToggleGroup(tg);
		hbox1 = new HBox(15);
		hbox1.getChildren().addAll(family,invdividual);
		family.setSelected(true);

		id = new TextField();
		id.setPromptText("ID");
		name = new TextField();
		name.setPromptText("Name");
		city = new TextField();
		city.setPromptText("City");
		mcount = new TextField();
		mcount.setPromptText("Members Count");
		searchField = new TextField();
		searchField.setPromptText("Search by ID");

		btAdd = new Button("Add Beneficiary");
		btClear = new Button("Clear Fields");
		btSearch = new Button("Search");
		hbox2 = new HBox(15);
		hbox2.getChildren().addAll(btAdd,btClear);
		hbox2.setAlignment(Pos.CENTER);
		hbox3 = new HBox(15);
		hbox3.getChildren().addAll(searchField,btSearch);

		table = new TableView<Beneficiary>();
		blist = FXCollections.observableArrayList(manager.beneficiaries);

		TableColumn<Beneficiary,Integer> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

		TableColumn<Beneficiary, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Beneficiary, String> cityColumn = new TableColumn<>("City");
		cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));

		TableColumn<Beneficiary, String> typeColumn = new TableColumn<>("Type");
		typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

		TableColumn<Beneficiary, String> mCountColumn = new TableColumn<>("Member Count");
		mCountColumn.setCellValueFactory(c->{
			if (c.getValue() instanceof Family f)
				return new SimpleStringProperty(String.valueOf(f.getMembersCount()));
			return new SimpleStringProperty("");
		});
		mCountColumn.setMinWidth(100);

		TableColumn<Beneficiary, String> statusColumn = new TableColumn<>("Status");
		statusColumn.setCellValueFactory(c->{
			if (c.getValue() instanceof Individual i)
				return new SimpleStringProperty( i.getStatus());
			return new SimpleStringProperty("");
		});
		
		idColumn.setSortable(true);
		table.getColumns().addAll(idColumn,nameColumn,cityColumn,typeColumn,mCountColumn,statusColumn);
		table.getSortOrder().add(idColumn);
		idColumn.setSortType(TableColumn.SortType.ASCENDING);
		table.setItems(blist);
		table.sort();
		
		
		//actions
		family.setOnAction(e->{
			if(family.isSelected())
				mcount.setPromptText("Members Count");
		});
		invdividual.setOnAction(e->{
			if(invdividual.isSelected())
				mcount.setPromptText("Status");
		});
		btAdd.setOnAction(e->{
			Beneficiary b = null;
			if((!id.getText().trim().isEmpty() && !city.getText().trim().isEmpty()&&
					!name.getText().trim().isEmpty())) {// بتاكد لو احد الحقول فاضية
			try {
				int bid = Integer.parseInt(id.getText());
				if (bid <= 0) {//بمنع ايدي سالب
				    Alertt.error("ID must be positive");
				    return;
				}
				String cityIn = city.getText().trim();
	            String bcity = cityIn.substring(0, 1).toUpperCase() + cityIn.substring(1).toLowerCase();//عشان تنكتب بطريقة منصقة
				String bname = name.getText();
				if (family.isSelected())
					b = new Family(bid, bname, bcity, Integer.parseInt(mcount.getText()));
				if (invdividual.isSelected())
					b = new Individual(bid, bname, bcity, mcount.getText());
				try {
					manager.addBeneficiary(b);
				} catch (DuplicateRegistrationException | CityNotServedException e1) {
					Alertt.error(e1.getMessage());
				}
			} catch (Exception e2) {
				Alertt.error("Fill all Fields with vaild Inputs");
			}
			}else
				Alertt.error("Fill all Fields");
			blist.setAll(manager.beneficiaries);//refresh
			table.sort();
			
		});
		btClear.setOnAction(e -> {
			id.clear();
			name.clear();
			city.clear();
			mcount.clear();
			searchField.clear();
			family.setSelected(true);
		});
		btSearch.setOnAction(e-> {
			String text = searchField.getText().trim();
			if (text.isEmpty()) {//لو ما حط شي بكون ما ببحث فبرجع الاراي الاساسية
				table.setItems(FXCollections.observableArrayList(manager.beneficiaries));
			} else {
				ObservableList<Beneficiary> search = FXCollections.observableArrayList();
				for (Beneficiary b : manager.beneficiaries) {
					if (String.valueOf(b.getId()).contains(text)) {
						search.add(b);
					}
				}
				table.setItems(search);
				table.sort();
			}

		});

		
		setSpacing(15);
		setPadding(new Insets(20));
		setAlignment(Pos.CENTER);
		getChildren().addAll(hbox1,id,name,city,mcount,hbox2,hbox3,table);

	}

	public HBox getHbox() {
		return hbox1;
	}
	public RadioButton getFamily() {
		return family;
	}
	public RadioButton getInvdividual() {
		return invdividual;
	}
	public TextField getName() {
		return name;
	}
	public TextField getCity() {
		return city;
	}
	public TextField getMcount() {
		return mcount;
	}
	public Button getBtAdd() {
		return btAdd;
	}
	public TableView<Beneficiary> getTable() {
		return table;
	}

	public HBox getHbox1() {
		return hbox1;
	}

	public HBox getHbox2() {
		return hbox2;
	}

	public HBox getHbox3() {
		return hbox3;
	}

	public TextField getSearchField() {
		return searchField;
	}

	public Button getBtClear() {
		return btClear;
	}

	public Button getBtSearch() {
		return btSearch;
	}

	public ObservableList<Beneficiary> getBlist() {
		return blist;
	}




}
