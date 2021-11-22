# POWS
RabbitMQ Webservices using Springboot, JPA, Swagger and MYSQL

Clone projects into machine.
Open application.properties and application.yaml to configure path, port and db connection (both projects folder).
Run projects.
Open browser to use the swagger eg: http://localhost:8181/swagger-ui.html & http://localhost:8282/swagger-ui.html
RabbitMQ transaction occur on update order - stock in product will be deducted based on message received from quantity's ordered.
Message can be view in RabbitMQ screen.