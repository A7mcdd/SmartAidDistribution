package project.model;
import java.io.Serializable;

public abstract class Beneficiary implements Serializable {
	private static final long serialVersionUID = 1L;
	// attributes
	protected int id;
	protected String name;
	protected String city;

	//constructors
	public Beneficiary(int id, String name, String city) {
		this.id = id;
		this.name = name;
		this.city = city;
	}

	//setters + getters
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	//methods
	abstract String getType();

	@Override
	public boolean equals(Object obj) {
		//بفحص لو نفس المكان بلذاكرة
		if (this == obj) return true;
		//بيفحص لو نفس النوع
		if (!(obj instanceof Beneficiary))
			return false;
		//بيفحص ال id
		Beneficiary b = (Beneficiary)obj;
		return this.id == b.id ;
	}

	@Override
	public String toString() {
		return "ID: " + id + ", Name: " + name + ", City: " + city + ", Type: " + getType();
	}












}



