#!/bin/sh

DIRNAME=`dirname "$0"`
PROGNAME=`basename "$0"`

# Read an optional running configuration file
if [ "x$RUN_CONF" = "x" ]; then
    RUN_CONF="$DIRNAME/run.conf"
fi
if [ -r "$RUN_CONF" ]; then
    . "$RUN_CONF"
fi


# Setup DEMO_HOME
RESOLVED_DEMO_HOME=`cd "$DIRNAME/.."; pwd`
if [ "x$DEMO_HOME" = "x" ]; then
    # get the full path (without any relative bits)
    DEMO_HOME=$RESOLVED_DEMO_HOME
else
 SANITIZED_DEMO_HOME=`cd "$DEMO_HOME"; pwd`
 if [ "$RESOLVED_DEMO_HOME" != "$SANITIZED_DEMO_HOME" ]; then
   echo "WARNING DEMO_HOME may be pointing to a different installation - unpredictable results may occur."
   echo ""
 fi
fi
export DEMO_HOME

# Setup the JVM
if [ "x$JAVA" = "x" ]; then
    if [ "x$JAVA_HOME" != "x" ]; then
        JAVA="$JAVA_HOME/bin/java"
    else
        JAVA="java"
    fi
fi

# Display our environment
echo "========================================================================="
echo ""
echo "  JBoss Cluster FrameWork Demo Bootstrap Environment"
echo ""
echo "  DEMO_HOME: $DEMO_HOME"
echo ""
echo "  JAVA: $JAVA"
echo ""
echo "  JAVA_OPTS: $JAVA_OPTS"
echo ""
echo "========================================================================="
echo ""

if [ "x$LAUNCH_DEMO_IN_BACKGROUND" = "x" ]; then
    # Execute the JVM in the foreground
    eval \"$JAVA\" $JAVA_OPTS \
        -Ddemo.home.dir=\"$DEMO_HOME\" \
        -jar \"$DEMO_HOME/jboss-modules-1.1.2.GA.jar\" \
        -mp \"$DEMO_HOME/modules\" \
        bootstrap.infinispan \
        "$@"
    DEMO_STATUS=$?
    echo "JBoss Cluster FrameWork Demo Status: $DEMO_STATUS"
else
    # Execute the JVM in the background
    eval \"$JAVA\" $JAVA_OPTS \
        -Ddemo.home.dir=\"$DEMO_HOME\" \
        -jar \"$DEMO_HOME/jboss-modules-1.1.2.GA.jar\" \
        -mp \"$DEMO_HOME/modules\" \
        bootstrap.infinispan \
        "$@" "&"
    DEMO_PID=$!
    echo "JBoss Cluster FrameWork Demo PID: $DEMO_PID"
fi
