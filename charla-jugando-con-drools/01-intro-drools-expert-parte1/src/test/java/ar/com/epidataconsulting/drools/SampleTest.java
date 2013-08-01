package ar.com.epidataconsulting.drools;

import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.log4j.Logger;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatefulSession;
import org.drools.compiler.PackageBuilder;
import org.junit.Test;

public class SampleTest {
	
	private static Logger logger = Logger.getLogger(SampleTest.class);
	
	@Test
	public void run() throws Exception {
        //Leemos el archivo de reglas (DRL)
        Reader source = new InputStreamReader(SampleTest.class.getResourceAsStream("/archivoDeRegla.drl"));
        //Construimos un paquete de reglas
        PackageBuilder builder = new PackageBuilder();
        //Parseamos y compilamos las reglas en un único paso builder.addPackageFromDrl(source);
        builder.addPackageFromDrl(source);
        // Verificamos el builder para ver si hubo errores
        if (builder.hasErrors()) {
            logger.error(builder.getErrors().toString());
            throw new RuntimeException("No se pudo compilar el archivo de reglas.");
        }
        //Obtenemos el package de reglas compilado
        org.drools.rule.Package pkg = builder.getPackage();
        //Agregamos el paquete a la base de reglas (desplegamos el paquete de reglas).
        RuleBase ruleBase = RuleBaseFactory.newRuleBase();
        ruleBase.addPackage(pkg);
        //obtenemos el contexto de trabajo por default
        StatefulSession ksession = ruleBase.newStatefulSession();
        //insertamos un objeto al motor
        ksession.insert(new Persona(20,"Pepe"));
        //Ejecutamos la reglas
        ksession.fireAllRules();
	}
    
}