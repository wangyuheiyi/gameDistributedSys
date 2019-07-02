FROM wangyuheiyi/myalpine-java8:latest
ADD testthymeleaf-0.0.1-SNAPSHOT.jar /testthymeleaf.jar
RUN mkdir -p /data/myobj/output
EXPOSE 8092
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/testthymeleaf.jar"]