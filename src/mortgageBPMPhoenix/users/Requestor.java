package mortgageBPMPhoenix.users;

import java.util.Date;
import java.util.Set;

import mortgageBPMPhoenix.properties.Property;
import mortgageBPMPhoenix.utilities.util;

public class Requestor extends User { 

	private double creditScore;
	private int salary;
	// properties will be created only when creating a new MtgRequest
	private Set<Property> assurances; 

	// Requestor will be created only when creating a new mortgage request
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

	// sums up requestor's property worth
	public int propWorth() {
		int worth = 0;
		for (Property property : this.assurances) {
			worth += property.getCurrentPrice();
		}
		return worth;
	}
	
	@Override
	//Requestor gets SMS because he's not part of the system interface
	protected void notifier(String message) {
		util.sendSMS(this.fullName+", "+(message));
	}

	@Override
	public String toString() {
		return "Requestor [" + super.toString() + " creditScore=" + creditScore + ", salary=" + salary + ", assurances="
				+ assurances + "]";
	}

}
