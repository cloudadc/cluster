1. How to build?
  mvn clean dependency:copy-dependencies install 
  ant

2. How to run?
  java -jar jboss-modules-1.1.2.GA.jar -mp "modules" com.kylin.jbosscluster -jaxpmodule "javax.xml.jaxp-provider"
