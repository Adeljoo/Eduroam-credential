package university.bdiv3;

import java.util.HashMap;
import java.util.Map;

import jadex.bdiv3.annotation.PlanBody;

public class IdpPlan {
	/** The wordtable. */
	protected Map<String, String> wordtable;

	//-------- methods --------

	/**
	 *  Create a new TranslationPlan.
	 */
	public IdpPlan()
	{
//		System.out.println("Created: "+this);
		this.wordtable = new HashMap<String, String>();
		this.wordtable.put("Ali", "1234");
		this.wordtable.put("Jan", "345");
		this.wordtable.put("Jack", "34567");
			}
	
	/**
	 *  Plan body invoke once when plan is activated. 
	 */
	@PlanBody
	public void checkId()
	{
		String eword = "Ali";
		String gword = wordtable.get(eword);
		System.out.println("Student name: "+eword+" ID number: "+gword);
	}
	
	
}
