package mortgageBPMPhoenix.users;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import mortgageBPMPhoenix.exceptions.AgentRemovedException;
import mortgageBPMPhoenix.requests.MtgRequest;
import mortgageBPMPhoenix.requests.ReqStatus;
import mortgageBPMPhoenix.utilities.util;

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

	// Sensitive Data protected by password
	public void setSeniority(int seniority, String password) {
		if (password.equalsIgnoreCase(this.manager.getPassword())) {
			this.seniority = seniority;
			this.manager.notifier("Agent " + this.fullName + " Seniority set to :" + seniority);

		} else
			this.manager.notifier("password incorrect");
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

	// Sensitive Data protected by password
	public void setManager(Manager manager, String password) {
		if (password.equalsIgnoreCase(this.manager.getPassword())) {
			Manager tmpMng = this.manager;
			this.manager = manager;
			if (manager == null)
				tmpMng.notifier("Agent " + this.getFullName() + " removed");
			else
				this.manager.notifier("Agent " + this.getFullName() + " added !");
		} else {
			this.manager.notifier("password incorrect");
		}
	}

	public double getComissionRate() {
		return comissionRate;
	}

	// Sensitive Data protected by password
	public void setComissionRate(double comissionRate, String password) {
		if (password.equalsIgnoreCase(this.manager.getPassword())) {
			this.comissionRate = comissionRate;
			this.manager.notifier("Agent " + this.fullName + " Commission Rate set to :" + comissionRate);
		} else
			this.manager.notifier("password incorrect");
	}

	// Adds request only if it is not present
	public void addRequest(MtgRequest request) {
		if (!this.requests.add(request))
			this.notifier("you have request " + request.getId() + " already");
		else
			this.notifier("Request " + request.getId() + " added !");
	}

	// Removes request only if present
	public void removeRequest(MtgRequest request) {
		if (!this.requests.remove(request))
			this.notifier("Request " + request.getId() + " not found");
		else
			this.notifier("Request " + request.getId() + " removed");
	}

	// Assesses the risk according to requestor +request params and prints it
	public double assessRisk(MtgRequest request) {
		if (!this.requests.contains(request)) {
			this.notifier("You have to add a new request first !");
			return 0;
		}
		Calendar today = new GregorianCalendar();
		today.setTime(new Date());
		Calendar bdate = new GregorianCalendar();
		bdate.setTime(request.getRequestor().getDob());
		double risk = ((today.get(Calendar.YEAR) - bdate.get(Calendar.YEAR)) + (request.getAmountWanted() / 1000000)
				- (request.getRequestor().getCreditScore()) - (request.getRequestor().getSalary() / 1000)
				- (request.getRequestor().propWorth() / 1000000));
		this.notifier("Risk for request " + request.getId() + " is: " + risk);
		return risk;
	}

	// Assesses the risk and prints again for safety, and only if not too risky
	// sends to manager after agent approval
	public boolean processResult(MtgRequest request) {
		if (!this.requests.contains(request)) {
			this.notifier("You have to add a new request first !");
			return false;
		}
		if (assessRisk(request) > 3) {
			this.notifier("The risk is too high ! Sending decline message to requestor");
			request.setStatus(ReqStatus.Declined);
			request.getRequestor()
					.notifier("We're sorry. Your Mortgage request was declined. Contact Agent for further details");
			return false;
		} else {
			this.notifier("request is valid ! Would u like to Send to manager review? yes/no");
			String answer = util.scan.next();
			if (answer.equalsIgnoreCase("yes")) {
				request.setStatus(ReqStatus.ManagerAssessment);
				try {
					SendToManager(request);
					this.notifier("Mortgage request sent to manager for review !");
				} catch (Exception e) {
					notifier(e.getMessage());
				}
				return true;
			}
			return false;
		}
	}

	// checks to see if agent is still connected to manager, then adds request to
	// his requests set and notifies him
	// this method is expose internally only, to make sure processing has been done.
	private void SendToManager(MtgRequest request) throws Exception {
		if (this.manager == null) {
			request.setStatus(ReqStatus.AgentAssessment);
			throw new AgentRemovedException();
		}
		this.manager.getRequests().add(request);
		this.manager.notifier("you have a new MtgRequest for review");
	}

	@Override
	// not printing MtgRequests to avoid recursive printing
	public String toString() {
		return "Agent [id=" + id + ", fullName=" + fullName + ", dob=" + dob + ", seniority=" + seniority + ", manager="
				+ manager + ", comissionRate=" + comissionRate + "]";
	}

}
