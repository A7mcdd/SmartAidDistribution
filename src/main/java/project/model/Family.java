package project.model;

public class Family extends Beneficiary implements Comparable<Family>{
	private static final long serialVersionUID = 1L;
	private int membersCount;

	public Family(int id, String name, String city, int membersCount) {
		super(id, name, city);
		this.membersCount = membersCount;
	}


	public int getMembersCount() {
		return membersCount;
	}


	public void setMembersCount(int membersCount) {
		this.membersCount = membersCount;
	}

	@Override
	public String getType() {
		return "Family";
	}

	//بقارن عدد افراد العائلة
	@Override
	public int compareTo(Family o) {
		if (getMembersCount() > o.getMembersCount()) 
			return 1;
		else if (getMembersCount() < o.getMembersCount()) 
			return -1;
		else
			return 0;}

	@Override
	public String toString() {
		return super.toString() + ", Members: " + membersCount;
	}}