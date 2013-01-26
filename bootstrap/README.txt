1. How to build?
  mvn clean dependency:copy-dependencies install 
  ant

2. How to run?
  java -Ddemo.home.dir=/home/kylin/work/project/TankWar/bootstrap/build/Demo -jar jboss-modules-1.1.2.GA.jar -mp modules bootstrap -mode jbosscache -console -debug -config total-replication.xml

  ./run.sh -mode jbosscache -console -debug -config total-replication.xml


