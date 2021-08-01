package mortgageBPMPhoenix.users;

import java.util.Date;
import java.util.Scanner;

public abstract class User {
	
	protected int id;
	protected String fullName;
	protected Date dob;
	protected Scanner scan; 

	public User(int id, String fullName, Date dob) {
		this.id = id;
		this.fullName = fullName;
		this.dob = dob;
		this.scan = new Scanner(System.in);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getDob() {
		return dob;
	}
	
	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	protected void notifier(String message) {
		System.out.println(this.fullName+", "+message);
	}

	@Override
	public String toString() {
		return "id=" + id + ", fullName=" + fullName + ", dob=" + dob+",";
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
