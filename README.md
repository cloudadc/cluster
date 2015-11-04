## JBoss Cluster Framework Demo

A series demo that allows you quick understand JBoss Cluster/Data Grid Framework(jGroups, JBossCache, Infinispan).

## Build

* install JDK 1.7 or higher
* install maven 3 - [http://maven.apache.org](http://maven.apache.org/download.html)

Enter the following:

~~~
$ git clone git@github.com:<yourname>/cluster.git
$ cd cluster
$ mvn clean install -P release
~~~

`cluster-demo-dist.zip` will be generated under `cluster/build/target` directory once the build is completed.

## Run 

~~~
$ unzip cluster-demo-dist.zip
$ cd cluster-demo-<version>/
$ ./bin/draw.sh
~~~

[Complete jGroups Demos Gallery](build/docs/jgroups-demos.md)

## Contribute

* Open an issue on [https://github.com/jbosschina/cluster/issues](https://github.com/jbosschina/cluster/issues)
* Fork and submit pull request 
