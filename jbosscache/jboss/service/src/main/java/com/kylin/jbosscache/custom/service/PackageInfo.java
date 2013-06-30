package com.kylin.jbosscache.custom.service;

/**

1. Edit
     $JBOSS_HOME/server/PROFILE/deploy/cluster/jboss-cache-manager.sar/META-INF/jboss-cache-manager-jboss-beans.xml file
   Add an additional element inside newConfigurations <map>:
     <bean name="CacheConfigurationRegistry" class="org.jboss.ha.cachemanager.DependencyInjectedConfigurationRegistry">
     	<property name="newConfigurations">
     		<map keyClass="java.lang.String" valueClass="org.jboss.cache.config.Configuration">
     		  // add to this position
     		</map>
     	</property>
     </bean>

<entry><key>my-custom-cache</key>
   <value>      
      <bean name="MyCustomCacheConfig" class="org.jboss.cache.config.Configuration">
         <property name="transactionManagerLookupClass">org.jboss.cache.transaction.BatchModeTransactionManagerLookup</property>
         <property name="clusterName">${jboss.partition.name:DefaultPartition}-CustomizedCache</property>
         <property name="multiplexerStack">${jboss.default.jgroups.stack:udp}</property>
         <property name="fetchInMemoryState">true</property>
         
         <property name="nodeLockingScheme">PESSIMISTIC</property>
         <property name="isolationLevel">REPEATABLE_READ</property>
         <property name="useLockStriping">false</property>
         <property name="cacheMode">REPL_ASYNC</property>
      
         <property name="syncReplTimeout">17500</property>
         <property name="lockAcquisitionTimeout">15000</property>
         <property name="stateRetrievalTimeout">60000</property>
         <property name="useRegionBasedMarshalling">false</property>
         <property name="inactiveOnStartup">false</property>
         
         <property name="serializationExecutorPoolSize">0</property>        
         <property name="listenerAsyncPoolSize">0</property>
         <property name="exposeManagementStatistics">true</property>

         <property name="buddyReplicationConfig">
            <bean class="org.jboss.cache.config.BuddyReplicationConfig">
               <property name="enabled">false</property>
               <property name="buddyPoolName">default</property>
               <property name="buddyCommunicationTimeout">17500</property>
               <property name="autoDataGravitation">false</property>
               <property name="dataGravitationRemoveOnFind">true</property>
               <property name="dataGravitationSearchBackupTrees">true</property>
               
               <property name="buddyLocatorConfig">
                  <bean class="org.jboss.cache.buddyreplication.NextMemberBuddyLocatorConfig">
                     <property name="numBuddies">1</property>
                     <property name="ignoreColocatedBuddies">true</property>
                   </bean>
               </property>
            </bean>
         </property>
         <property name="cacheLoaderConfig">
            <bean class="org.jboss.cache.config.CacheLoaderConfig">
                   <property name="passivation">true</property>
                   <property name="shared">false</property>
                   
                   <property name="individualCacheLoaderConfigs">
                     <list>
                        <bean class="org.jboss.cache.loader.FileCacheLoaderConfig">
                           <property name="location">${jboss.server.data.dir}${/}customized</property>
                           <property name="async">false</property>
                           <property name="fetchPersistentState">true</property>
                           <property name="purgeOnStartup">true</property>
                           <property name="ignoreModifications">false</property>
                           <property name="checkCharacterPortability">false</property>
                        </bean>
                     </list>
                   </property>
            </bean>
         </property>
      </bean>      
   </value>
   </entry>


2. Start JBoss 
      ./run.sh -c all -g test
   If test Cache replicate between cluster nodes, simply start nodes as below:
      ./run.sh -c node1 -b 10.66.192.231 -g test -u 239.255.100.100
      ./run.sh -c node2 -b 10.66.192.48 -g test -u 239.255.100.100
    
3. How to Build?
   1) modify .../server/pom.xml, make sure jboss.home point to correct position.
   2) execute 'mvn clean install'
   
4. How to Run?
   1) deploy remote-server.jar to JBoss
   2) run client  
       java -cp server/target/remote-server-client.jar:client/target/jbosscache-jboss-ejb-remote-client-1.0.jar:/home/kylin/work/eap/jboss-eap-5.1/jboss-as/client/* com.kylin.ejb.remote.client.JBossCacheServiceClient
   3) import project to eclipse, there are clients existed which expose request to EJB
         JBossCacheServiceClient
         JBossCacheService2NodesClient
         JBossCacheService2NodesReplClient


*/