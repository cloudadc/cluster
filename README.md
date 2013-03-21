JGroups TankWar Demo
====================

* [Build cluster project](how-to-build.asciidoc), navigate to bin folder execute:
* 
~~~
./tankwar.sh -n node1 isGood
~~~

~~~
./tankwar.sh -n node2
~~~
Now, node1 is alliance tank, node2 is enemy tank, they can attack each other.

* TankWar need a group of members plan together, JGroups be used to replicate each member's status to other group members, [more details](https://github.com/kylinsoong/cluster/blob/master/docs/how_to_plan.asciidoc)

JBossCache HelloWorld Demo 
==========================



Related Link
============

* http://www.jgroups.org/
* https://github.com/belaban/JGroups
* http://www.jboss.org/projects


