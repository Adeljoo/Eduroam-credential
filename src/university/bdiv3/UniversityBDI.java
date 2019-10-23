package university.bdiv3;

import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.PlanBody;
//import jadex.bdiv3.annotation.PlanPrecondition;
import jadex.bdiv3.annotation.Trigger;
import jadex.bdiv3.features.IBDIAgentFeature;
import jadex.bdiv3.runtime.IPlan;
import jadex.bdiv3.runtime.impl.PlanFailureException;
import jadex.bridge.IInternalAccess;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.AgentFeature;
import jadex.micro.annotation.Configuration;
import jadex.micro.annotation.Configurations;

import java.text.SimpleDateFormat;
//import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 *  using free wifi in the university  
 *  Winikoff, Padgham: developing intelligent agent systems, 2004.
 */
//@BDIConfigurations({
//	@BDIConfiguration(name="sunny", initialbeliefs=@NameValue(name="raining", value="false")),
//	@BDIConfiguration(name="rainy", initialbeliefs=@NameValue(name="raining", value="true"))
//})
@Configurations({@Configuration(name="Ali"), @Configuration(name="Jan")})
@Agent
public class UniversityBDI
{
	// Annotation to inform FindBugs that the uninitialized field is not a bug.
	@SuppressFBWarnings(value="UR_UNINIT_READ", justification="Agent field injected by interpreter")
	
	/** The bdi agent. */
	/** The time. */
	//protected long time;
	
	/** The date formatter. */
	//public SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	@AgentFeature
	protected IBDIAgentFeature bdi;
	@Agent
	protected IInternalAccess agent;
	protected Map<String, String> wordtable;
	@AgentCreated
	public void init()
	{
//		System.out.println("Created: "+this);
		this.wordtable = new HashMap<String, String>();
		this.wordtable.put("Ali", "1234");
		this.wordtable.put("Jan", "345");
		this.wordtable.put("Jack", "34567");
	}
	@Belief
	protected boolean student = agent.getConfiguration().equals("Ali");
	/*@Belief
	protected boolean student = agent.getConfiguration().equals("123456");*/
	@Goal
	protected class UseFreeWiFiGoal
	{
	}
	
	@Goal
	protected static class TakeXGoal
	{
		public enum Type{HomeUniversity, VisitingUniversity};
		
		protected Type type;
		
		public TakeXGoal(Type type)
		{
			this.type = type;
		}

		public Type getType()
		{
			return type;
		}
	}
	
	@AgentBody
	public void body()
	{
		bdi.adoptPlan("checkId");
		//System.out.println("Ali: "+student);
		Date date = new Date();
	    SimpleDateFormat simpDate;

	    simpDate = new SimpleDateFormat("hh:mm:ss a");
	    System.out.println(simpDate.format(date));
//		if(agent.getConfiguration().equals("rainy"))
//			raining = true;
				
		try
		{
			agent.getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(new UseFreeWiFiGoal()).get();
		}
		catch(Exception e)
		{
			System.out.println("Warning");
		}
	}
	
	// HomeUniversity 
	
	
	@Plan(trigger=@Trigger(goals=UseFreeWiFiGoal.class))
	protected class WiFiPlan
	{
		
		/*@PlanPrecondition
		protected boolean getcheckId()
		{
			return checkId();
		}*/
		
		@PlanBody
		protected void beinginHomeUniversity(IPlan plan)
		{
			System.out.println("Trying to connect Eduroam from a HomeUniversity SP.");
			 plan.dispatchSubgoal(new TakeXGoal(TakeXGoal.Type.HomeUniversity)).get();
			System.out.println("Connected via HomeUniversity SP.");
			
		}
		
	}

	// visitingUniversity 
	@Plan(trigger=@Trigger(goals=UseFreeWiFiGoal.class))
	protected void VisitingUniversityPlan(IPlan plan)
	{ 
		
	
		System.out.println("Trying to connect from VisitingUniversity SP.");
		plan.dispatchSubgoal(new TakeXGoal(TakeXGoal.Type.VisitingUniversity)).get();
		System.out.println("Connected via VisitingUniversity SP.");
	}
	
	@Plan(trigger=@Trigger(goals=TakeXGoal.class))
	protected void takeX(TakeXGoal goal)
	{
		System.out.println("Searching for Eduroam WiFi connetion.");
		System.out.println("Trying to connect to Eduroam by Providing a valid ID.");
		if(Math.random()>0.8)
		{
			System.out.println("Wait time is too long, failed.");
			throw new PlanFailureException();
		}
		else
		{
			System.out.println("Connecting to Eduroam WiFi via "+goal.getType());
		}
	}
	
	@Plan
	public void checkId()
	{
		String eword = "Ali";
		String gword = wordtable.get(eword);
		System.out.println("Student name: "+eword+" ID number: "+gword);
		//return student;
		
	}
}
