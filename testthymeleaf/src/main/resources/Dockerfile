FROM java:8
ADD testthymeleaf-0.0.1-SNAPSHOT.jar /testthymeleaf.jar
RUN mkdir -p /data/myobj/output
EXPOSE 8091
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/testthymeleaf.jar"]