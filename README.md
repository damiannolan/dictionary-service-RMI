# An Asynchronous RMI Dictionary Service

## Overview

The following repository contains a relatively straight forward Apache Tomcat Web Application employing Java Servlets and JSP as well as a RMI Service for querying dictionary definitions. 
The web client presents a basic user interface in which a user can enter a search string and submit it to be processed by the application. The application has been setup to simulate a delay of requests being added to a queueing system.

The following diagram depicts the overall system architecture:

![diagram](https://i.imgur.com/0KsUbvT.png)

## Prerequisites

- [Apache Tomcat](http://tomcat.apache.org/)
- [Maven](https://maven.apache.org/)
- [RabbitMQ](https://www.rabbitmq.com/)

### Getting setup with RabbitMQ

If you are on OSX [Homebrew](https://brew.sh/) makes it easy to install various dependencies and manage services with [Brew Services](https://github.com/Homebrew/homebrew-services) such as RabbitMQ as well as various other dependencies such as databases - i.e. CouchDB or MySQL. To 
get started with RabbitMQ follow these instructions.

1. Update HomeBrew
```
brew update
```

2. Brew Install
```
brew install rabbitmq
```

3. Start the RabbitMQ Server
```
brew services start rabbitmq
```

To get setup using RabbitMQ on Windows please follow the instructions on the [RabbitMQ Installation Documentation](https://www.rabbitmq.com/install-windows.html). RabbitMQ offers a basic .exe installer that can be used to manage services - stopping, starting.

## Setup

Running a Maven Install inside the directory will generate the target directory and the associated war file - (Web Application Archieve). However this isn't necessary as I've included the associated war file and also the RMI Server Application jar file in this repository to avoid complications with different system environments and dependencies.

To use the application simply:

1. Clone this Respository
```
git clone 
```

2. Start the RMI Server Application
```
java -cp ./dictionary-service.jar ie.gmit.sw.server.ServiceSetup
```

3. Drag and Drop `dictionary-webapp.war` into an Apache Tomcat Installation directory under the folder named webapps.

4. Start your Tomcat Server and navigate to `http://localhost:8080/dictionary-webapp/`

#### OSX 
```
brew services start tomcat
```

#### Windows
```
-> C:\dev\tomcat\bin> startup
```

### Note

As previously mentioned running an Maven Install will also generate the war file and target directory as well as download and install the dependencies needed for the application. You can also import the project into Eclipse and use the Maven plugin associated.

#### Maven Install
```
mvn install
```

#### Import into Eclipse
```
Eclipse -> File -> Import -> Maven -> Existing Maven Projects
```

