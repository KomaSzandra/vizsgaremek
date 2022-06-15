FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
COPY target/conference-*.jar /opt/app/conference.jar
CMD ["java", "-jar", "/opt/app/conference.jar"]