
start "Microservice: API Gateway" /D apigateway mvn spring-boot:run
start "Microservice: Authorization" /D authorization mvn spring-boot:run
start "Microservice: Produkte" /D produkte mvn spring-boot:run

explorer "http://localhost:8000/"