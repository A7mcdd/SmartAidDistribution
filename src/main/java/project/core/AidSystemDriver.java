package project.core;
import project.model.*;

import java.util.GregorianCalendar;
import java.util.Scanner;



public class AidSystemDriver {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		AidManager manager = new AidManager();
		int choice=0;

		do {
			System.out.println(
					"\n==== Smart Aid Distribution System ====\n"
							+ "1. Register Beneficiary\n"
							+ "2. Add Aid Item\n"
							+ "3. Record Aid Distribution\n"
							+ "4. Display All Beneficiaries\n"
							+ "5. Display All Aid Items\n"
							+ "6. Display All Distribution Events\n"
							+ "7. Generate Statistical Reports\n"
							+ "8. Save to Text File\n"
							+ "9. Save to Binary File\n"
							+ "10. Load from Text File\n"
							+ "11. Load from Binary File\n"
							+ "0. Exit\n"
							+ "=======================================\n");
			System.out.println("Enter Choice: ");
			if(!input.hasNextInt()) {System.out.println("\nInvaild input. Please try again.");input.nextLine();continue;}//عشان نتاكد من المدخل قبل
			choice = input.nextInt();input.nextLine();

			try {
				switch (choice) {
				case 1:
					System.out.println("Choice the Type (Family / Individual)");
					String type = input.nextLine();
					if(!(type.equalsIgnoreCase("Family")||type.equalsIgnoreCase("Individual"))) {System.out.println("\nInvaild input. Please try again.");continue;}

					System.out.println("ID: ");
					if(!input.hasNextInt()) {System.out.println("\nInvaild input. Please try again.");input.nextLine();continue;}
					int id = input.nextInt();input.nextLine();

					System.out.println("Name: ");
					String name = input.nextLine();

					System.out.println("City: ");
					String city = input.nextLine();

					if (type.equalsIgnoreCase("Family")) {
						System.out.print("Members count: "); 
						if(!input.hasNextInt()) {System.out.println("\nInvaild input. Please try again.");input.nextLine();continue;}
						int members = input.nextInt();input.nextLine();
						manager.addBeneficiary(new Family(id, name, city, members));
						System.out.println("\nFamily registered successfully!");
					} else {
						System.out.print("Status: "); String status = input.nextLine();
						manager.addBeneficiary(new Individual(id, name, city, status));
						System.out.println("\nIndividual registered successfully!");
					}

					break;

				case 2: // Add Aid Item
					System.out.print("Type (FoodPackage/MedicalKit/WinterBag/EmergencyKit): ");
					String itemType = input.nextLine().toLowerCase();
					if(!(itemType.equalsIgnoreCase("FoodPackage")
							||itemType.equalsIgnoreCase("MedicalKit")
							||itemType.equalsIgnoreCase("WinterBag")
							||itemType.equalsIgnoreCase("EmergencyKit"))) {
						System.out.println("\nInvaild input. Please try again.");
						continue;
					}
					System.out.print("Code: ");
					String code = input.nextLine();

					System.out.print("Description: ");
					String desc = input.nextLine();

					AidItem item = switch (itemType) {
					case "FoodPackage" -> new FoodPackage(code, desc);
					case "MedicalKit" -> new MedicalKit(code, desc);
					case "WinterBag" -> new WinterBag(code, desc);
					case "EmergencyKit" -> new EmergencyKit(code, desc);
					default -> null;
					};
					if (item != null) {
						manager.addAidItem(item);
						System.out.println("Aid item added successfully!");}
					break;

				case 3:
					if (manager.aiditems.size() > 0 && manager.beneficiaries.size() > 0) {
						System.out.println("Available Aid Items:");
						for (int i = 0; i < manager.aiditems.size(); i++) {
							System.out.println(i + ". " + manager.aiditems.get(i));
						}


						System.out.print("\nSelect item index: ");
						if(!input.hasNextInt()) {System.out.println("\nInvaild input. Please try again.");input.nextLine();continue;}
						int itemIndex = input.nextInt();

						if(itemIndex < 0 || itemIndex >= manager.aiditems.size()) {
							System.out.println("\nInvalid index. Please try again.");
							input.nextLine();
							continue;
						}

						System.out.println("\nAvailable Beneficiaries:");
						for (int i = 0; i < manager.beneficiaries.size(); i++) {
							System.out.println(i + ". " + manager.beneficiaries.get(i));
						}

						System.out.println("\nSelect beneficiary index: ");
						if(!input.hasNextInt()) {System.out.println("\nInvaild input. Please try again.");input.nextLine();continue;}
						int benIndex = input.nextInt();
						if(benIndex < 0 || benIndex >= manager.beneficiaries.size()) {
							System.out.println("\nInvalid index. Please try again.");
							input.nextLine();
							continue;
						}
						input.nextLine();

						System.out.println("Enter date (yyyy mm dd): ");
						if(!input.hasNextInt()) {System.out.println("\nInvaild input. Please try again.");input.nextLine();continue;}
						int y = input.nextInt();
						int m = input.nextInt();
						int d = input.nextInt();
						input.nextLine();

						manager.recordDistribution(
								manager.aiditems.get(itemIndex),
								manager.beneficiaries.get(benIndex),
								new GregorianCalendar(y, m - 1, d)
								);
						System.out.println("\nDistribution recorded successfully!");
					} else {
						System.out.println("\nPlease add items and beneficiaries first!");
					}
					break;
				case 4: manager.displayBeneficiaries(); break;
				case 5: manager.displayAidItems(); break;
				case 6: manager.displayEvents(); break;
				case 7:  // Reports
					int reportChoice=0;
					do {
						System.out.println("\n==== Statistical Reports ====");
						System.out.println("1. Number of families served in a city");
						System.out.println("2. Total aid items distributed");
						System.out.println("3. Count aid items by category");
						System.out.println("4. Beneficiaries served between two dates");
						System.out.println("5. Most served city");
						System.out.println("0. Back to main menu");
						System.out.println("============================\n");
						System.out.println("Enter choice: ");

						if(!input.hasNextInt()) {System.out.println("\nInvaild input. Please try again.");input.nextLine();continue;}
						reportChoice = input.nextInt();
						input.nextLine(); // fix Scanner

						switch(reportChoice) {

						case 1:
							System.out.print("Enter city: ");
							String city1 = input.nextLine();
							manager.reportFamiliesInCity(city1);
							break;

						case 2:
							manager.reportTotalAidItems();
							break;

						case 3:
							manager.reportCountByCategory();
							break;

						case 4:
							System.out.print("Enter start date (yyyy mm dd): ");
							if(!input.hasNextInt()) {System.out.println("\nInvaild input. Please try again.");input.nextLine();continue;}
							int y1 = input.nextInt(), m1 = input.nextInt(), d1 = input.nextInt();
							input.nextLine();
							GregorianCalendar start = new GregorianCalendar(y1, m1 - 1, d1);

							System.out.print("Enter end date (yyyy mm dd): ");
							if(!input.hasNextInt()) {System.out.println("\nInvaild input. Please try again.");input.nextLine();continue;}
							int y2 = input.nextInt(), m2 = input.nextInt(), d2 = input.nextInt();
							input.nextLine();
							GregorianCalendar end = new GregorianCalendar(y2, m2 - 1, d2);

							manager.reportBeneficiariesBetweenDates(start, end);
							break;

						case 5:
							manager.reportMostServedCity();
							break;

						case 0:
							System.out.println("Returning to main menu...");
							break;

						default:
							System.out.println("Invalid choice!");
						}

					} while (reportChoice != 0);

					break;
				case 8: manager.saveToTextFile("beneficiaries.txt");
				System.out.println("Data saved to text file!");break;

				case 9: manager.saveToBinaryFile("data.dat");
				System.out.println("Data saved to binary file!");break;

				case 10: manager.loadFromTextFile("beneficiaries.txt"); break;
				case 11: manager.loadFromBinaryFile("data.dat");
				System.out.println("Data loaded from binary file!");break;




				}
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage()); }

		}while (choice != 0);
		input.close();

	}
}

