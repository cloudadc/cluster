1. How to build?
   mvn clean install dependency:copy-dependencies

2. How to run?

 *   java -cp target/infinispan-stu-helloworld-1.0.jar:target/dependency/* -Djava.net.preferIPv4Stack=true com.kylin.infinispan.helloworld.clustered.replication.ReplNode 1
 *   java -cp target/infinispan-stu-helloworld-1.0.jar:target/dependency/* -Djava.net.preferIPv4Stack=true com.kylin.infinispan.helloworld.clustered.replication.ReplNode 2
 *   java -cp target/infinispan-stu-helloworld-1.0.jar:target/dependency/* -Djava.net.preferIPv4Stack=true com.kylin.infinispan.helloworld.clustered.replication.ReplNode 3
 *   java -cp target/infinispan-stu-helloworld-1.0.jar:target/dependency/* -Djava.net.preferIPv4Stack=true com.kylin.infinispan.helloworld.clustered.replication.ReplNode 4

 *   java -cp target/infinispan-stu-helloworld-1.0.jar:target/dependency/* -Djava.net.preferIPv4Stack=true com.kylin.infinispan.helloworld.clustered.distribution.DistNode 1
 *   java -cp target/infinispan-stu-helloworld-1.0.jar:target/dependency/* -Djava.net.preferIPv4Stack=true com.kylin.infinispan.helloworld.clustered.distribution.DistNode 2
 *   java -cp target/infinispan-stu-helloworld-1.0.jar:target/dependency/* -Djava.net.preferIPv4Stack=true com.kylin.infinispan.helloworld.clustered.distribution.DistNode 3
 *   java -cp target/infinispan-stu-helloworld-1.0.jar:target/dependency/* -Djava.net.preferIPv4Stack=true com.kylin.infinispan.helloworld.clustered.distribution.DistNode 4
