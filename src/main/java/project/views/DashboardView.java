package project.views;


import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
//import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.core.AidManager;
import project.model.DistributionEvent;

public class DashboardView extends VBox{

	private VBox benfBox,itemsBox,distBox,cityBox;

	public DashboardView(AidManager manager) {
// في صورة بس اطريت شيل الكود عشان ما بقدر ارفع ملف الصورة
		ImageView iv = new ImageView("aid_dashboard.png");
		iv.setFitWidth(280);
		iv.setPreserveRatio(true);



		benfBox = createBox("Beneficiaries",String.valueOf(manager.beneficiaries.size()), "#E3F2FF" );//blue
		itemsBox = createBox("Aid Items",String.valueOf(manager.aiditems.size()), "#E8F5EA" );//green
		distBox = createBox("Distributions",String.valueOf(manager.events.size()), "#FFF3E4" );//orange
		cityBox = createBox("Most Served City", reportMostServedCity(manager),"#F5F5F5");// gray

		HBox boxesRow = new HBox(20,benfBox,itemsBox,distBox);
		boxesRow.setAlignment(Pos.CENTER);

		setSpacing(15);
		setPadding(new Insets(20));
		setAlignment(Pos.CENTER);
		getChildren().addAll(iv,boxesRow,cityBox);

	}

	public VBox createBox(String title, String value, String color) {

		Label titlel = new Label(title);
		titlel.setFont(new Font("blod",15));

		Label valuel = new Label(value);
		valuel.setFont(new Font(25));

		VBox Square = new VBox(10, titlel, valuel);
		Square.setPadding(new Insets(20));
		Square.setAlignment(Pos.CENTER);
		Square.setPrefWidth(200);
		Square.setStyle("-fx-background-color: "+color+";-fx-background-radius: 10;-fx-border-radius: 10;-fx-border-color: #CCCCCC;");

		return Square;
	}

	public static String reportMostServedCity(AidManager manager) {

		if (manager.events.isEmpty()) {
			return "N/A";
		}

		String mostCity = "N/A";
		int max = 0;

		for (String city : manager.cities) {
			ArrayList<Integer> servedIds = new ArrayList<>();

			for (DistributionEvent e : manager.events) {
				if (e.getBeneficiary() == null) continue;//لو طلع فاضي بتعداه ما باثر صراحة بس اضمن

				String eventCity = e.getBeneficiary().getCity();
				int id = e.getBeneficiary().getId();
				
				if (eventCity != null && eventCity.equalsIgnoreCase(city)) {
					 if (!servedIds.contains(id))
						 servedIds.add(id);
				}
			}

			if (servedIds.size() > max) {
				max = servedIds.size();
				mostCity = city;
			}
		}

		if (max == 0) 
			return "N/A"; 
		else 
			return mostCity;
	}




	public VBox getBenfBox() {
		return benfBox;
	}

	public VBox getItemsBox() {
		return itemsBox;
	}

	public VBox getDistBox() {
		return distBox;
	}

	public VBox getCityBox() {
		return cityBox;
	}


}
