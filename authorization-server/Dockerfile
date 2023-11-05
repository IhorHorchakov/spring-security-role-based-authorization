FROM openjdk:17-oracle
EXPOSE 8080 9251

ADD target/authorization-server.jar authorization-server.jar
ADD docker-image-debug-entrypoint.sh docker-image-debug-entrypoint.sh

ENTRYPOINT ["sh", "/docker-image-debug-entrypoint.sh"]
