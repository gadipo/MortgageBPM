package mortgageBPMPhoenix.users;

import java.util.Date;
import java.util.Set;

import mortgageBPMPhoenix.properties.Property;

public class Requestor extends User {

	private double creditScore;
	private int salary;
	private Set<Property> assurances;

	public Requestor(int id, String fullName, Date dob, double creditScore, int salary, Set<Property> assurances) {
		super(id, fullName, dob);
		this.creditScore = creditScore;
		this.salary = salary;
		this.assurances = assurances;
	}

	public double getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(double creditScore) {
		this.creditScore = creditScore;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public Set<Property> getAssurances() {
		return assurances;
	}

	public void setAssurances(Set<Property> assurances) {
		this.assurances = assurances;
	}

	public int propWorth() {
		int worth = 0;
		for (Property property : this.assurances) {
			worth += property.getCurrentPrice();
		}
		return worth;
	}

	@Override
	public String toString() {
		return "Requestor [" + super.toString() + " creditScore=" + creditScore + ", salary=" + salary + ", assurances="
				+ assurances + "]";
	}

}
