# SDLC Assignment 4

1. [Consul](https://www.consul.io/downloads.html) Server: A Service Discovery Server. 
2. [user_service] It is an account management service. It provides registration, authentication and authorization services. Also, it propagates JWT tokens to RequestsService and OffersService. UserService stores users' data in MySQL instance and uses the Micronaut Data Framework to handle the data.
3. [request_service] This service produces all services which are related to requests. The service stores the requests in MySQL instance. Also, it will consume the offers services as required.  
4. [offer_service] This service produces all services which are related to the offers. Offers objects will be stored in a MySQL instance. The OfferService invokes services from RequestService as requires 
5. [gateway](https://start.spring.io/#!type=gradle-project&language=java&platformVersion=2.2.0.RELEASE&packaging=jar&jvmVersion=1.8&groupId=io.hashimati&artifactId=gateway&name=gateway&description=Demo%20project%20for%20Spring%20Boot&packageName=io.hashimati.gateway&dependencies=cloud-zuul,oauth2-resource-server,cloud-eureka,cloud-starter-consul-discovery,thymeleaf):  a Netfix Zuul gateway service. 



## Running Application Locally

1. To start the Consul, run `consul agent -data-dir=your-consul-data-file -dev -ui`
1. Run the user_service,request_service and offer_service by running the below command
		```source set-env-vars.sh,
		   ./run-mysql.sh,
		   ./gradlew run```
1. Run the gateway application with `./gradlew bootRun`
