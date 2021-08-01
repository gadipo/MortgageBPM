package mortgageBPMPhoenix.requests;

import java.util.Date;

import mortgageBPMPhoenix.properties.Property;
import mortgageBPMPhoenix.users.Agent;
import mortgageBPMPhoenix.users.Requestor;

public class MtgRequest {

	private int id;
	private Agent agent;
	private Requestor requestor;
	private Date reqDate;
	private int amountWanted;
	private Property mtgProperty;
	private ReqStatus status;

	public MtgRequest(int id, Agent agent, Date reqDate, Requestor requestor, int amountWanted, Property mtgProperty) {
		super();
		this.id = id;
		this.agent = agent;
		this.reqDate = reqDate;
		this.requestor = requestor;
		this.amountWanted = amountWanted;
		this.mtgProperty = mtgProperty;
		this.status = ReqStatus.AgentAssessment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getReqDate() {
		return reqDate;
	}

	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}

	public Requestor getRequestor() {
		return requestor;
	}

	public void setRequestor(Requestor requestor) {
		this.requestor = requestor;
	}

	public int getAmountWanted() {
		return amountWanted;
	}

	public void setAmountWanted(int amountWanted) {
		this.amountWanted = amountWanted;
	}

	public Property getMtgProperty() {
		return mtgProperty;
	}

	public void setMtgProperty(Property mtgProperty) {
		this.mtgProperty = mtgProperty;
	}

	public ReqStatus getStatus() {
		return status;
	}

	public void setStatus(ReqStatus status) {
		this.status = status;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	@Override
	public String toString() {
		return "MtgRequest [id=" + id + ", agent=" + agent + ", reqDate=" + reqDate + ", requestor=" + requestor
				+ ", amountWanted=" + amountWanted + ", mtgProperty=" + mtgProperty + ", status=" + status + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MtgRequest other = (MtgRequest) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
