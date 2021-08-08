package mortgageBPMPhoenix.users;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import mortgageBPMPhoenix.requests.MtgRequest;
import mortgageBPMPhoenix.requests.ReqStatus;
import mortgageBPMPhoenix.utilities.util;

public class Manager extends User {

	private String branch;
	private Set<Agent> agents;
	private Set<MtgRequest> requests;
	private String password;

	public Manager(int id, String fullName, Date dob, String branch, Set<Agent> agents, Set<MtgRequest> requests,
			String password) {
		super(id, fullName, dob);
		this.branch = branch;
		this.agents = agents;
		this.requests = requests;
		this.password = password;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public Set<Agent> getAgents() {
		return agents;
	}

	private void setAgents(Set<Agent> agents) {
		this.agents = agents;
	}

	public void removeAllAgents() {
		setAgents(new HashSet<Agent>());
	}

	public Set<MtgRequest> getRequests() {
		return requests;
	}

	private void setRequests(Set<MtgRequest> requests) {
		this.requests = requests;
	}

	public void removeAllRequests() {
		setRequests(new HashSet<MtgRequest>());
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void addAgent(Agent agent) {
		if (!this.agents.add(agent))
			this.notifier("you have Agent " + agent.getFullName() + " already");
		else {
			notifier("password?");
			String password = util.scan.next();
			agent.setManager(this, password);
		}
	}

	public void removeAgent(Agent agent) {
		if (!this.agents.remove(agent))
			this.notifier("Agent " + agent.getFullName() + " not found");
		else {
			notifier("Are you sure? password?");
			String password = util.scan.next();
			agent.setManager(null, password);
		}
	}

	public void setAgentRate(double rate, Agent agent) {
		notifier("password?");
		String password = util.scan.next();
		agent.setComissionRate(rate, password);
	}

	public void setAgentSeniority(int years, Agent agent) {
		notifier("password?");
		String password = util.scan.next();
		agent.setSeniority(years, password);

	}

	public void addRequest(MtgRequest request) {
		if (!this.requests.add(request))
			this.notifier("you have request " + request.getId() + " already");
		else
			this.notifier("Request " + request.getId() + " added !");
	}

	public void removeRequest(MtgRequest request) {
		if (!this.requests.add(request))
			this.notifier("you have request " + request.getId() + " already");
		else
			this.notifier("Request " + request.getId() + " added !");
	}

	// Gets all requests from all agents
	public Set<MtgRequest> getAgentRequests() {
		Set<MtgRequest> allrequests = new HashSet<MtgRequest>();
		for (Agent agent : agents) {
			allrequests.addAll(agent.getRequests());
		}
		return allrequests;
	}

	// Checks if the request is indeed ready,then Processes the request (A more
	// serious process can be added instead of "processing")
	// ,then asks for a manager decision: updates request status and sends messages
	// accordingly.
	public boolean reviewForApproval(MtgRequest request) {
		if (request.getStatus() != ReqStatus.ManagerAssessment) {
			notifier("Request " + request.getId() + " is not ready !");
			return false;
		}
		notifier("processing request " + request.getId() + " ...");
		notifier(" about rquest " + request.getId() + ", what to do? approve/improve/decline");
		String answer = util.scan.next();
		switch (answer) {

		case "approve":
			request.setStatus(ReqStatus.Approved);
			notifier("Mortgage " + request.getId() + " has been approved !");
			request.getAgent().notifier(
					"Request " + request.getId() + " has been approved ! sending message to requestor as well");
			request.getRequestor().notifier(
					"Congratualations ! your request has been approved ! contact agent for further instructions !");
			return true;
		case "improve":
			request.setStatus(ReqStatus.AgentAssessment);
			notifier("MtgRequest " + request.getId() + " not approved ! what r your comments?");
			util.scan.nextLine();
			String comments = util.scan.nextLine();
			request.getAgent()
					.notifier("Request Not approved by " + this.fullName + ". Here are his comments: " + comments);
			return false;
		case "decline":
			request.setStatus(ReqStatus.Declined);
			notifier("MtgRequest " + request.getId() + " Declined !");
			request.getAgent().notifier("MtgRequest" + request.getId() + " Declined !");
			request.getRequestor()
					.notifier("We're Sorry :( Your mortgage was declined.  Contact Agent for further details");
			return false;
		}
		return false;
	}

	@Override
	// not printing requests and agents to avoid recursive printing
	public String toString() {
		return "Manager [id=" + id + ", fullName=" + fullName + ", dob=" + dob + ", branch=" + branch + ", password="
				+ password + "]";
	}

}
