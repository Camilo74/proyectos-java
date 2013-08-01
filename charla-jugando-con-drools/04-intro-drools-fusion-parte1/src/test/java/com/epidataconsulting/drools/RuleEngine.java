package com.epidataconsulting.drools;


import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.conf.ClockTypeOption;
import org.drools.runtime.rule.WorkingMemoryEntryPoint;
import org.drools.time.SessionPseudoClock;
import org.junit.After;
import org.junit.Before;



public class RuleEngine {
	
	private static Logger logger = Logger.getLogger(RuleEngine.class);
	
	protected StatefulKnowledgeSession ksession;
	protected WorkingMemoryEntryPoint entryPointStore;
	
	private static EventListener listener = new EventListener();
	protected List<String> expectedRulesNames = new ArrayList<String>();
	
	@Before
	public void before(){
		//KnowledgeBuilder: Has a Collection of DRL files, so our rules set can be devided in several files
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		//Auto load local file
		kbuilder.add(ResourceFactory.newClassPathResource("changeset.xml", getClass()),ResourceType.CHANGE_SET);
		//show error
		if(kbuilder.hasErrors()){
			logger.info(kbuilder.getErrors().toString());
		}
		//KnowledgeBaseConfiguration: We use this class to set the Event Processing Node as STRING
		KnowledgeSessionConfiguration config = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
		config.setOption(ClockTypeOption.get("pseudo")); // Real Time (realtime) is the clock by default	
		//KnowledgeBase: We create our KnowledgeBase considering the Collection of DRL files the KnowledgeBuilder
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		//StatefulKnowledgeSession : Once we have our KnowledgeBase we create a Session to use it.
		ksession = kbase.newStatefulKnowledgeSession(config,null);
		ksession.addEventListener(listener);
		//Each Event that is inserted into our WorkingMemory does it through an entry-point.
		entryPointStore = ksession.getWorkingMemoryEntryPoint("time stream");
	}
	
	@After
	public void after(){
		ksession.fireAllRules();
		ksession.dispose();
		Assert.assertFalse("No se ejecuto ninguna regla",listener.isEmpty());
		Assert.assertTrue("La regla ejecutada no coincide con la esperada",listener.containsAll(expectedRulesNames));
		clearRulesNames();
	}
	
	private void clearRulesNames(){
		expectedRulesNames.clear();
		listener.clear();
	}
	
	protected void insert(Object fact){
		entryPointStore.insert(fact);
	}

	protected void addExpectedRules(String expected){
		expectedRulesNames.add(expected);
	}
	
	protected SessionPseudoClock getClock(){
		return ksession.getSessionClock();
	}
	
}