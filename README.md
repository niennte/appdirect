# tea-pot

To run the application with Maven, execute the following command:

`mvn springboot:run`


To run the application in a Docker container pre-configured with JVM 1.8, 

1) execute the following command:

`mvn clean package docker:build`

2) run the container: 

`docker run -d --name=xxxx -p nnnn:8080 com.samplecompany.teapot/tea-pot
`
where *xxxx* is the desired name for the container, and *nnnn* is the desired port on the host machine.
