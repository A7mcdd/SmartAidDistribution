package project.model;

public class Individual extends Beneficiary implements Comparable<Individual>{
	private static final long serialVersionUID = 1L;
	private String status;

	public Individual(int id, String name, String city,String status) {
		super(id, name, city);
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String getType() {
		return "Individual";
	}
	// بسوي مقارنة بين الحالتين
	@Override
	public int compareTo(Individual o) {
		return this.status.compareTo(o.status);
	}

	@Override
	public String toString() {
		return super.toString() + ", Status: " + status;
	}


}