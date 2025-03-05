# Use official Maven image to build the WAR file
FROM maven:3.8.4-openjdk-8 AS builder

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the project and generate WAR file
RUN mvn clean package

# Use official Tomcat 9 image to deploy the WAR file
FROM tomcat:9.0

# Set environment variables (optional)
ENV CATALINA_HOME /usr/local/tomcat

# Remove default Tomcat webapps
RUN rm -rf $CATALINA_HOME/webapps/*

# Copy WAR file from builder stage to Tomcat webapps directory
COPY --from=builder /app/target/onlinebookstore.war $CATALINA_HOME/webapps/onlinebookstore.war

# Expose port 8080
EXPOSE 8080

# Start Tomcat server
CMD ["catalina.sh", "run"]
