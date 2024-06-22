FROM  maven:3-jdk-8
WORKDIR /dsi-esprit-reclamationservice
COPY . .
RUN ["mvn", "install", "-Dmaven.test.skip=true"]
EXPOSE 8083

CMD mvn spring-boot:run