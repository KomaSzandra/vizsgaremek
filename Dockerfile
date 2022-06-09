FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
COPY target/conference-0.0.1-SNAPSHOT.jar /opt/app/conference.jar
CMD ["java", "-jar", "/opt/app/conference.jar"]