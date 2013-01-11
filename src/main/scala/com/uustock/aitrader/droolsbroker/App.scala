package com.uustock.aitrader.droolsbroker

/**
 * @author ${user.name}
 */
import org.drools.StatefulSession
import org.drools.runtime.StatefulKnowledgeSession
import org.drools.KnowledgeBase
import org.drools.KnowledgeBaseFactory
import org.drools.builder.KnowledgeBuilder
import org.drools.builder.KnowledgeBuilderFactory
import org.drools.builder.ResourceConfiguration
import org.drools.io.ResourceFactory
import org.drools.builder.ResourceType
import org.drools.event.rule.DebugAgendaEventListener
import org.drools.logger.KnowledgeRuntimeLoggerFactory
import org.drools.logger.KnowledgeRuntimeLogger
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;

/*
class TestResults {
    val results = new MutableList[(Float, String)]()

    def add(cost: Float, comment: String) {
        results += Tuple2(cost, comment)
    }
}
*/

object App {
  
  def foo(x : Array[String]) = x.foldLeft("")((a,b) => a + b)
  
  def GetKnowledgeSession() :
          StatefulKnowledgeSession = {
    
   // Get a new knowledge builder instance
   var kbuilder : KnowledgeBuilder 
      = KnowledgeBuilderFactory
          .newKnowledgeBuilder()
 
   // Add our ruleset (which will be parsed 
   // and compiled) to the knowledge builder
   kbuilder.add(ResourceFactory
      .newClassPathResource("WeatherRules.drl"),
         ResourceType.DRL)
 
         
        var errors:KnowledgeBuilderErrors  = kbuilder.getErrors();
		if (errors.size() > 0) {
			
		  print("erros"+ errors)
		   // for (KnowledgeBuilderError error: errors) {
	//			System.err.println(error);
		// 	}
			
		}
   // Create a new knowledgebase
   var kbase : KnowledgeBase 
     = KnowledgeBaseFactory.newKnowledgeBase()
 
   // Add the compiled rule sets and workflows 
   // into the knowledgebase
   kbase.addKnowledgePackages(
      kbuilder.getKnowledgePackages())
 
   // Create the knowledge session
   var ksession : StatefulKnowledgeSession 
      = kbase.newStatefulKnowledgeSession()
 
   // If you want to see what's going on within
   // the Drools Engine just uncomment this line
   var logger : KnowledgeRuntimeLogger = 
     KnowledgeRuntimeLoggerFactory
       .newConsoleLogger(ksession)
 
   // Return the knowledge session
    ksession.addEventListener( new DebugAgendaEventListener() )
 //   ksession.addEventListener( new DebugWorkingMemoryEventListener() )
    print("helo");
   ksession
} 
  def main(args : Array[String]) {
   
    println("Creating Knowledge Session")
    
   // Retrieve the knowledge session
   var ksession : StatefulKnowledgeSession 
      = GetKnowledgeSession()
     
   println("Creating and insertng Temperature")
     
   // Here is our first temperature object
   val shouldBeTooHot = new Temperature {
      def value = 10
   }
     
   // Here is our second temperature object
   val shouldBeTooCold = new Temperature {
      def value = 20
   }
     
   // Insert the temperatures into the 
   // knowledge session
   ksession.insert(shouldBeTooHot)
   ksession.insert(shouldBeTooCold)
     
   println("Firing all rules")
     
   // Fire the rules
   ksession.fireAllRules()
   println("hello1");
   System.out.println("hell2");
  }

}
