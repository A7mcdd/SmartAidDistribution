package project.core;
import project.exceptions.CityNotServedException;
import project.exceptions.DuplicateRegistrationException;
import project.exceptions.ItemNotFoundException;
import project.model.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;


public class AidManager implements FileOperations {
	public ArrayList<Beneficiary> beneficiaries = new ArrayList<>();
	public ArrayList<AidItem> aiditems = new ArrayList<>();
	public ArrayList<DistributionEvent> events = new ArrayList<>();
	public List<String> cities = Arrays.asList("Gaza", "Rafah", "Khan Younis", "Jerusalem", "Jenin", "Tulkarm", "Hebron", "Ramallah");

	//beneficiaries
	public void addBeneficiary(Beneficiary b) throws DuplicateRegistrationException, CityNotServedException {
		boolean cityFound = false;
		for (String city : cities) {
			if (city.equalsIgnoreCase(b.getCity())) {
				cityFound = true;
				break;
			}
		}

		if (!cityFound) {
			throw new CityNotServedException("City not served. Available cities: " + cities);
		}
		for (Beneficiary exist : beneficiaries) {
			if (exist.equals(b)) throw new DuplicateRegistrationException("Duplicate ID");
		}

		beneficiaries.add(b);
	}

	public void displayBeneficiaries() {
		for (Beneficiary b : beneficiaries)
			System.out.println(b);
	}

	//AidItems
	public void addAidItem(AidItem item) throws DuplicateRegistrationException {

		for (AidItem i : aiditems) {
			if (i.getCode().equalsIgnoreCase(item.getCode())) {
				throw new DuplicateRegistrationException(
						"Aid item code already exists: " + item.getCode()
						);
			}
		}

		aiditems.add(item);
	}


	public void displayAidItems () {
		for (AidItem a : aiditems)
			System.out.println(a);
	}

	//events
	public void recordDistribution(AidItem item, Beneficiary b, GregorianCalendar date) throws ItemNotFoundException {
		if (!aiditems.contains(item))throw new ItemNotFoundException("Aid item not found");
		events.add(new DistributionEvent(item, b, date));
	}

	public void displayEvents () {
		for (DistributionEvent e : events)
			System.out.println(e);
	}

	//statistical reports
	public void reportFamiliesInCity(String city) {
	    ArrayList<Integer> servedFamilies = new ArrayList<>();
	    for (DistributionEvent e : events) {
	        Beneficiary b = e.getBeneficiary();
	        if (b instanceof Family && b.getCity().equalsIgnoreCase(city)) {
	            if (!servedFamilies.contains(b.getId())) {
	                servedFamilies.add(b.getId());
	            }
	        }
	    }
	    System.out.println("Families served in " + city + ": " + servedFamilies.size());
	}

	public void reportTotalAidItems() {
		System.out.println("Total aid items distributed: " + events.size());
	}



	public void reportCountByCategory() {

		int foodCount = 0;
		int medicalCount = 0;
		int winterCount = 0;
		int emergencyCount = 0;

		for (DistributionEvent e : events) {
			String cat = e.getItem().getCategory();

			if (cat.equals("Food Package")) foodCount++;
			else if (cat.equals("Medical Kit")) medicalCount++;
			else if (cat.equals("Winter Bag")) winterCount++;
			else if (cat.equals("Emergency Kit")) emergencyCount++;
		}

		System.out.println("Food Packages: " + foodCount);
		System.out.println("Medical Kits: " + medicalCount);
		System.out.println("Winter Bags: " + winterCount);
		System.out.println("Emergency Kits: " + emergencyCount);
	}


	public void reportBeneficiariesBetweenDates(GregorianCalendar start, GregorianCalendar end) {
		for (DistributionEvent e : events) {
			GregorianCalendar d = e.getDate();

			if ((d.after(start) || d.equals(start)) && (d.before(end)  || d.equals(end))) {

				System.out.println(e.getBeneficiary());
			}
		}
	}

	public void reportMostServedCity() {
		if (events.isEmpty()) { // بتحقق لو ما في افينيتز
			System.out.println("No distribution events recorded yet.");
			return;
		}

		String mostCity = "";
		int max = 0;
		for (String city : cities) {
			int count = 0;


			for (DistributionEvent e : events) {
				if (e.getBeneficiary().getCity().equalsIgnoreCase(city)) {
					count++;
				}
			}

			if (count > max) {
				max = count;
				mostCity = city;
			}
			// لو كل المدن صفر
			if (max == 0) {
				System.out.println("No city has been served yet.");
				return;
			}
		}

		System.out.println("Most served city: " + mostCity + " (" + max + " events)");
	}

















	//text file
	@Override
	public void saveToTextFile(String filename) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
			writer.write("BENEFICIARIES\n");
			for (Beneficiary b : beneficiaries) {
				if (b instanceof Family f) {
					writer.write("F," + f.getId() + "," + f.getName() + "," +f.getCity() + "," + f.getMembersCount());
				} else if (b instanceof Individual i) {
					writer.write("I," + i.getId() + "," + i.getName() + "," +i.getCity() + "," + i.getStatus());
				}
				writer.newLine();
			}

			writer.write("AIDITEMS\n");
			for (AidItem a : aiditems) {
				writer.write(a.getCategory() + "," + a.getCode() + "," + a.getDescription());
				writer.newLine();
			}

			writer.write("EVENTS\n");
			for (DistributionEvent e : events) {
				GregorianCalendar d = e.getDate();
				writer.write(e.getItem().getCode() + "," + e.getBeneficiary().getId() + "," +
						d.get(GregorianCalendar.DAY_OF_MONTH) + "," +d.get(GregorianCalendar.MONTH) + "," +d.get(GregorianCalendar.YEAR));
				writer.newLine();
			}
		}
	}


	@Override
	public void loadFromTextFile(String filename) throws IOException {
		beneficiaries.clear();
		aiditems.clear();
		events.clear();

		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			String mode = "";

			while ((line = reader.readLine()) != null) {

				if (line.equals("BENEFICIARIES") ||
						line.equals("AIDITEMS") ||
						line.equals("EVENTS")) {
					mode = line;
					continue;
				}

				String[] d = line.split(",");

				switch (mode) {

				case "BENEFICIARIES":
					if (d[0].equals("F")) {
						beneficiaries.add(new Family(
								Integer.parseInt(d[1]),d[2], d[3],Integer.parseInt(d[4])));
					} else {
						beneficiaries.add(new Individual(Integer.parseInt(d[1]), d[2],d[3], d[4]));
					}
					break;

				case "AIDITEMS":
					AidItem item = null;
					switch (d[0]) {
					case "Food Package":
						item = new FoodPackage(d[1], d[2]); break;
					case "Medical Kit":
						item = new MedicalKit(d[1], d[2]); break;
					case "Winter Bag":
						item = new WinterBag(d[1], d[2]); break;
					case "Emergency Kit":
						item = new EmergencyKit(d[1], d[2]); break;
					}
					if (item != null) aiditems.add(item);
					break;

				case "EVENTS":
					AidItem it = findAidItemByCode(d[0]);
					Beneficiary b = findBeneficiaryById(Integer.parseInt(d[1]));

					GregorianCalendar date = new GregorianCalendar( Integer.parseInt(d[4]),Integer.parseInt(d[3]), Integer.parseInt(d[2]));

					if (it != null && b != null)
						events.add(new DistributionEvent(it, b, date));
					break;
				}
			}
		}
	}

	//binary file
	@Override
	public void saveToBinaryFile(String filename) throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
			oos.writeObject(beneficiaries);
			oos.writeObject(aiditems);
			oos.writeObject(events);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void loadFromBinaryFile(String filename) throws IOException {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
			try {
				beneficiaries = (ArrayList<Beneficiary>) ois.readObject();
				aiditems = (ArrayList<AidItem>) ois.readObject();
				events = (ArrayList<DistributionEvent>) ois.readObject();
			} catch (ClassNotFoundException e) { e.printStackTrace(); }
		}
	}
	public AidItem findAidItemByCode(String code) {
		for (AidItem a : aiditems)
			if (a.getCode().equals(code)) return a;
		return null;
	}

	public Beneficiary findBeneficiaryById(int id) {
		for (Beneficiary b : beneficiaries)
			if (b.getId() == id) return b;
		return null;
	}



}
