## Chat

This demo simulate group chat, one of group members set a message, all group member will recieve this message.

~~~
./bin/chat.sh -name node-1
./bin/chat.sh -name node-2
~~~

##  Draw

Shared whiteboard, each new instance joins the same group. Each instance chooses a random color, mouse moves are broadcast to all group members, which then apply them to their canvas.

~~~
./bin/draw.sh -name node-1
./bin/draw.sh -name node-2

./bin/draw.sh -name node-1 -state
./bin/draw.sh -name node-2 -state

./bin/draw.sh -name node-1 -props tcp.xml
./bin/draw.sh -name node-2 -props tcp.xml
~~~

## Whiteboard

Shows 2 instances of Whiteboard. Whiteboard is a simple presence application, showing each cluster node with a box with the user's name and information about the operating system and JGroups address.
There's also a simple chatting functionality, where messages can be sent to individual nodes, or to all nodes in the cluster. 

~~~
./bin/whiteboard.sh
./bin/whiteboard.sh
~~~

## ReplicatedHashMap

Uses the ReplicatedHashMap building block, which subclasses java.util.HashMap and overrides the methods that modify the hashmap (e.g. put()). Those methods are multicast to the group, whereas read-only methods such as get() use the local copy. A ReplicatedtHashMap is created given the name of a group; all hashmaps with the same name find each other and form a group.

~~~
./bin/replicatedHashMap.sh
./bin/replicatedHashMap.sh
~~~

## ReplCache

This demo shows 4 instances of ReplCache forming a cluster.

~~~
./bin/replCache.sh
./bin/replCache.sh
~~~

## TankWar

JGroups TankWar Demo show some advanced jGroups features like use multiple channels instead of use single one, sharing a transport between multiple channels in a JVM, etc.

~~~
./bin/tankwar.sh -name node-1 good
./bin/tankwar.sh -name node-2
~~~


