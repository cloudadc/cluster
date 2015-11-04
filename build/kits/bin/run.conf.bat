if not "x%JAVA_OPTS%" == "x" (
  echo "JAVA_OPTS already set in environment; overriding default settings with values: %JAVA_OPTS%"
  goto JAVA_OPTS_SET
)

set "JAVA_OPTS=-Xms512m -Xmx512m -XX:MaxPermSize=128m -Djava.net.preferIPv4Stack=true -Dorg.jboss.resolver.warning=true -Djbosscache.config.validate=false -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.dgc.server.gcInterval=3600000"

set "JAVA_OPTS=%JAVA_OPTS% -Djava.net.preferIPv4Stack=true"

:JAVA_OPTS_SET