FROM websphere-liberty:microProfile
COPY server.xml /config/
ADD target/ShadowCoronaTracker.war /opt/ibm/wlp/usr/servers/defaultServer/dropins/
ENV LICENSE accept
EXPOSE 9080

## Running the container locally
# mvn clean install
# docker build -t ShadowCoronaTracker:latest .
# docker run -d --name myjavacontainer ShadowCoronaTracker
# docker run -p 9080:9080 --name myjavacontainer ShadowCoronaTracker
# Visit http://localhost:9080/ShadowCoronaTracker/

## Push container to IBM Cloud
# docker tag ShadowCoronaTracker:latest registry.ng.bluemix.net/<my_namespace>/ShadowCoronaTracker:latest
# docker push registry.ng.bluemix.net/<my_namespace>/ShadowCoronaTracker:latest
# ibmcloud ic images # Verify new image
