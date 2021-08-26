package mortgageBPMPhoenix;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import mortgageBPMPhoenix.properties.Property;
import mortgageBPMPhoenix.requests.MtgRequest;
import mortgageBPMPhoenix.requests.ReqStatus;
import mortgageBPMPhoenix.users.Agent;
import mortgageBPMPhoenix.users.Manager;
import mortgageBPMPhoenix.users.PropertyType;
import mortgageBPMPhoenix.users.Requestor;
import mortgageBPMPhoenix.users.User;

public class MortgageBPMPhoenix {

	public static void main(String[] args) {

		Set<Property> properties = new HashSet<Property>();
		Set<MtgRequest> requests = new HashSet<MtgRequest>();
		Set<Requestor> requestors = new HashSet<Requestor>();
		Set<Agent> agents = new HashSet<Agent>();
		Property p1 = new Property(0, 160, 4000000);
		Manager ponti =new Manager(2, "Ponti", new GregorianCalendar(1954,1,1).getTime(), "Habima", agents, requests,"1234");
		Manager manny =new Manager(3, "Manny", new GregorianCalendar(1973,1,1).getTime(), "Dizingoff", agents, requests,"1234");
		Requestor r1 = new Requestor(0,"Artiom Dolgopiat",new GregorianCalendar(1964,8,16).getTime(),83.5,9000,properties);
		Agent a1 = new Agent(1,"Tenzer",new GregorianCalendar(1976,3,24).getTime(),6,requests, manny,2.5);
		MtgRequest req1 = new MtgRequest(0, a1, new Date(), r1, 150000000, p1);
		MtgRequest req2 = new MtgRequest(2, a1, new Date(), r1, 1500000, p1);
		System.out.println(r1.toString());
		System.out.println(a1.toString());
		System.out.println(manny.toString());
		System.out.println(manny.getRequests());
		manny.addAgent(a1);
		manny.setAgentRate(1.5,	a1);
//		manny.removeAgent(a1);
		a1.removeRequest(req1);
//		a1.removeRequest(req2);
		a1.addRequest(req1);
		a1.addRequest(req1);
		a1.addRequest(req2);
//		a1.removeRequest(req1);
//		a1.removeRequest(req2);
		a1.assessRisk(req1);
		a1.assessRisk(req2);
		a1.processResult(req1);
		a1.processResult(req2);
		manny.reviewForApproval(req1);
		manny.reviewForApproval(req2);
		
		
	}

}
