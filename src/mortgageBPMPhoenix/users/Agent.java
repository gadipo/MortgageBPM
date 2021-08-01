package mortgageBPMPhoenix.users;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import mortgageBPMPhoenix.requests.MtgRequest;
import mortgageBPMPhoenix.requests.ReqStatus;

public class Agent extends User {

	private int seniority;
	private Set<MtgRequest> requests;
	private Manager manager;
	private double comissionRate;

	public Agent(int id, String fullName, Date dob, int seniority, Set<MtgRequest> requests, Manager manager,
			double comissionRate) {
		super(id, fullName, dob);
		this.seniority = seniority;
		this.requests = requests;
		this.manager = manager;
		this.comissionRate = comissionRate;
	}

	public int getSeniority() {
		return seniority;
	}

	public void setSeniority(int seniority) {
		this.seniority = seniority;
	}

	public Set<MtgRequest> getRequests() {
		return requests;
	}

	public void setRequests(Set<MtgRequest> requests) {
		this.requests = requests;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public double getComissionRate() {
		return comissionRate;
	}

	public void setComissionRate(double comissionRate) {
		this.comissionRate = comissionRate;
	}

	public void removeRequest(MtgRequest request) {
		try {
			this.requests.remove(request);
		} catch (Exception e) {
			this.notifier(e.getMessage());
		}
	}

	public void addRequest(MtgRequest request) {
		try {
			this.requests.add(request);
		} catch (NullPointerException e) {
			this.notifier("you have this request already");
		}
	}

	public double assessRisk(MtgRequest request) {
		Calendar today = new GregorianCalendar();
		today.setTime(new Date());
		Calendar bdate = new GregorianCalendar();
		bdate.setTime(request.getRequestor().getDob());
		double risk = ((today.get(Calendar.YEAR) - bdate.get(Calendar.YEAR)) +(request.getAmountWanted()/1000000)- (request.getRequestor().getCreditScore())
				- (request.getRequestor().getSalary() / 1000) - (request.getRequestor().propWorth() / 1000000));
		this.notifier("Risk for request " + request.getId() + " is: " + risk);
		return risk;
	}

	public boolean processResult(MtgRequest request) {
		if (assessRisk(request) > 3) {
			this.notifier("The risk is too high ! Sending decline message to requestor");
			request.setStatus(ReqStatus.Declined);
			request.getRequestor().notifier("We're sorry. Your Mortgage request was declined. Contact Agent for further details");
			return false;
		} else {
			this.notifier("request is valid ! Would u like to Send to manager review? yes/no");
			String answer = this.scan.next();
			if (answer.equalsIgnoreCase("yes")) {
				request.setStatus(ReqStatus.ManagerAssessment);
				this.notifier("Mortgage request sent to manager for review !");
				SendToManager(request);
				return true;
			} return false;
		}
	}

	private void SendToManager(MtgRequest request) {
		try {
			this.manager.getRequests().add(request);
			this.manager.notifier("you have a new MtgRequest for review");
		} catch (Exception e) {
			this.notifier(e.getMessage());
		}
	}

	@Override
	public String toString() { // not printing MtgRequests to avoid recursive printing
		return "Agent [" + super.toString() + "seniority=" + seniority + ", manager=" + manager + ", comissionRate="
				+ comissionRate + "]";
	}

}
