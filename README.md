# An Asynchronous RMI Dictionary Service

## Overview

The following repository contains a relatively straight forward Apache Tomcat Web Application employing Java Servlets and JSP as well as a RMI Service for querying dictionary definitions. 
The web client presents a basic user interface in which a user can enter a search string and submit it to be processed by the application. The application has been setup to simulate a delay of requests being added to a queueing system.

The following diagram depicts the overall system architecture:

![diagram](https://i.imgur.com/0KsUbvT.png)

## Prerequisites

- [Apache Tomcat](http://tomcat.apache.org/)
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