package mortgageBPMPhoenix.users;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import mortgageBPMPhoenix.requests.MtgRequest;
import mortgageBPMPhoenix.requests.ReqStatus;

public class Manager extends User {

	private String branch;
	private Set<Agent> agents;
	private Set<MtgRequest> requests;

	public Manager(int id, String fullName, Date dob, String branch, Set<Agent> agents, Set<MtgRequest> requests) {
		super(id, fullName, dob);
		this.branch = branch;
		this.agents = agents;
		this.requests = requests;
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

	public void setAgents(Set<Agent> agents) {
		this.agents = agents;
	}

	public Set<MtgRequest> getRequests() {
		return requests;
	}

	public void setRequests(Set<MtgRequest> requests) {
		this.requests = requests;
	}
	
	public void addAgent(Agent agent) {
		try {
			this.agents.add(agent);
		} catch (Exception e) {
			notifier(e.getMessage());
		}
	}
	
	public void addRequest(MtgRequest request) {
		try {			
			this.requests.add(request);
		} catch (Exception e) {
			notifier(e.getMessage());
		}
	}
	
	public void removeRequest(MtgRequest request) {
		try {			
			this.requests.remove(request);
		} catch (Exception e) {
			notifier(e.getMessage());
		}
	}

	public void removeAgent(Agent agent) {
		try {
			this.agents.remove(agent);
			agent.setManager(null);
		} catch (Exception e) {
			notifier(e.getMessage());
		}
	}
	
	public void setAgentCommission(Agent agent, double commission) {
		agent.setComissionRate(commission);
	}

	public Set<MtgRequest> getAgentRequests() {
		Set<MtgRequest> allrequests = new HashSet<MtgRequest>();
		for (Agent agent : agents) {
			allrequests.addAll(agent.getRequests());
		}
		return allrequests;
	}

	public boolean reviewForApproval(MtgRequest request) {
		if (request.getStatus() != ReqStatus.ManagerAssessment) {
			notifier("this request is not ready !");
			return false;
		}
		notifier("processing...");
		notifier("what to do? approve/improve/decline");
		String answer = scan.next();
		switch (answer) {

		case "approve":
			notifier("Mortgage " + request.getId() + " has been approved !");
			request.setStatus(ReqStatus.Approved);
			request.getRequestor().notifier(
					"Congratualations ! your request has been approved ! contact agent for further instructions !");
			return true;
		case "improve":
			notifier("MtgRequest " + request.getId() + " not approved ! what r your comments?");
			scan.nextLine();
			String comments = scan.nextLine();
			request.setStatus(ReqStatus.AgentAssessment);
			request.getAgent()
					.notifier("Request Not approved by " + this.fullName + ". Here are his comments: " + comments);
			return false;
		case "decline":
			notifier("MtgRequest " + request.getId() + " Declined !");
			request.getAgent().notifier("MtgRequest" + request.getId() + " Declined !");
			request.getRequestor().notifier("We're Sorry :( Your mortgage was declined");
			request.setStatus(ReqStatus.Declined);
			return false;
		}
		return false;
	}

	@Override
	public String toString() { // not printing requests and agents to avoid recursive printing
		return "Manager [" + super.toString() + " branch=" + branch + "]";
	}

}
