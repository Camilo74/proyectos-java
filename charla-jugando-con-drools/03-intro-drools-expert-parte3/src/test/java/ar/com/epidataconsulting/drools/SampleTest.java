package ar.com.epidataconsulting.drools;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.WorkingMemoryEntryPoint;
import org.junit.Test;

public class SampleTest {
	
	private static Logger logger = Logger.getLogger(SampleTest.class);
	
	@Test
	public void run() throws Exception {
		//KnowledgeBuilder: Has a Collection of DRL files, so our rules set can be devided in several files
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		
		//Auto load local file
		kbuilder.add(ResourceFactory.newClassPathResource("changeset.xml", getClass()),ResourceType.CHANGE_SET);
		
		if(kbuilder.hasErrors()){
			logger.error(kbuilder.getErrors().toString());
		}
		
		//KnowledgeBaseConfiguration: We use this class to set the Event Processing Node as STRING
		KnowledgeSessionConfiguration config = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
		
		//KnowledgeBase: We create our KnowledgeBase considering the Collection of DRL files the KnowledgeBuilder
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		
		//StatefulKnowledgeSession : Once we have our KnowledgeBase we create a Session to use it.
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession(config,null);
		
		//Each Event that is inserted into our WorkingMemory does it through an entry-point.
		WorkingMemoryEntryPoint entryPointStore = ksession.getWorkingMemoryEntryPoint("seccion 1");

        //insertamos un objeto al motor
        entryPointStore.insert(new Persona(23,"Pepe",Persona.MASCULINO));
        entryPointStore.insert(new Persona(33,"Maria",Persona.FEMENINO));
        
        ksession.fireAllRules();
        
	}
    
}