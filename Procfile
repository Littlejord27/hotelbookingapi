web: java $JAVA_OPTS -jar target/fantastix-1.0-SNAPSHOT.jar db migrate configuration.yml && java $JAVA_OPTS -Ddw.server.connector.port=$PORT -jar target/fantastix-1.0-SNAPSHOT.jar server configuration.yml