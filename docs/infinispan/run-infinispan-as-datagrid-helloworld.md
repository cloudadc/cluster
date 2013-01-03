What is it?
-----------

Shows how to use Infinispan in clustered mode, with expiration enabled

HelloWorld-JDG is a basic example that shows how to store and retrieve data to/from the cache. Users can access the cache
either from a servlet or from a JSF page through request scoped beans.

Infinispan is configured in clustered distributed mode with synchronous replication. Entries have their lifespan (expiration)
and are removed from the cache after 60 seconds since last update.

HelloWorld-JDG example works in _Library mode_. In this mode, the application and the data grid are running in the same
JVM. All libraries (JAR files) are bundled with the application and deployed to JBoss Enterprise Application Platform 6
or JBoss AS 7.  The library usage mode only allows local access to a single node in a distributed cluster. This usage
mode gives the application access to data grid functionality within a virtual machine in the container being used.


System requirements
-------------------

All you need to build this project is Java 6.0 (Java SDK 1.6) or better, Maven 3.0 or better.

The application this project produces is designed to be run on JBoss Enterprise Application Platform 6 or JBoss AS 7. 


Start JBoss Enterprise Application Platform 6 or JBoss AS 7
-----------------------------------------------------------

1. Open a command line and navigate to the root of the JBoss server directory.
2. The following shows the command line to start the server with the web profile:

        For Linux:   JBOSS_HOME/bin/domain.sh
        For Windows: JBOSS_HOME\bin\domain.bat

 
Build and Deploy the Quickstart
-------------------------------

_NOTE: The following build command assumes you have configured your Maven user settings. 

        mvn clean install

Deploy infinispan-stu-dg-helloworld.war to JBoss via either console, or cli


Access the application 
----------------------

The application will be running at the following URLs:

   <http://localhost:8080/infinispan-stu-dg-helloworld>  (first server instance)
   <http://localhost:8230/infinispan-stu-dg-helloworld>  (second server instance)

You can test replication of entries in the following way:

1. Access first server at <http://localhost:8080/infinispan-stu-dg-helloworld> and insert key "foo" with value "bar"
2. Access second server at <http://localhost:8230/infinispan-stu-dg-helloworld> and do the following:
   * Click on "Get Some"
   * Get the value for key "foo"
   * Click "Put Some More"
   * Insert key "mykey" with value "myvalue"
3. Access the first server at <http://localhost:8080/infinispan-stu-dg-helloworld> and do the following:
   * Click on "Get Some"
   * Get all mappings by clicking on "Get All"
4. All data entered on each server was replicated to the other server

NOTE: Entries expire and simply disappear after 60 seconds from last update.

To access predefined servlets and directly store/retrieve a key in the cache, access the following URLs:

<http://localhost:8080/infinispan-stu-dg-helloworld/TestServletPut>

<http://localhost:8230/infinispan-stu-dg-helloworld/TestServletGet>  (note the different port 8230)


