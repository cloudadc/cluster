package org.jgroups.demo.test.advanced;

/**
 * What's this?
 *   Tests for JGroups Bridging between remote clusters
 *   
 *   If you have 2 local clusters (data centers), you can use JGroups' RELAY to bridge the 2 and make them appear as if 
 *   they were a (virtual) global cluster.
 *   
 *   Assume we have 2 data centers located in 2 places, one in Beijing, The other in Shanghai;
 *   Use com.kylin.jgroups.demo.ChatDemo to illustrate data transfer between 2 data center
 *   
 *   There are 3 configuration files which are needed to do this:
 *     1. jgroups-relay1.xml and jgroups-relay2.xml define configurations for 2 different local clusters (data centers): they both 
 *        include RELAY (configured with jgroups-tcp.xml to be used as bridge), and only differ in mcast_addr andmcast_port, and in 
 *        the site ("Beijing" or "Shanghai").
 *     2. jgroups-tcp.xml: this configured the JGroups bridge to be used between 2 data centers        
 * 
 * How to Build?
 *   mvn clean install dependency:copy-dependencies
 * 
 * How to Run?
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar -Djava.net.preferIPv4Stack=true com.kylin.jgroups.demo.ChatDemo -n A -c Beijing-Groups -p jgroups-relay1.xml -discardOwn
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar -Djava.net.preferIPv4Stack=true com.kylin.jgroups.demo.ChatDemo -n B -c Beijing-Groups -p jgroups-relay1.xml -discardOwn
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar -Djava.net.preferIPv4Stack=true com.kylin.jgroups.demo.ChatDemo -n C -c Beijing-Groups -p jgroups-relay1.xml -discardOwn
 *   
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar -Djava.net.preferIPv4Stack=true com.kylin.jgroups.demo.ChatDemo -n D -c Shanghai-Groups -p jgroups-relay2.xml -discardOwn
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar -Djava.net.preferIPv4Stack=true com.kylin.jgroups.demo.ChatDemo -n E -c Shanghai-Groups -p jgroups-relay2.xml -discardOwn
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar -Djava.net.preferIPv4Stack=true com.kylin.jgroups.demo.ChatDemo -n F -c Shanghai-Groups -p jgroups-relay2.xml -discardOwn
 * 
 */
