# An Asynchronous RMI Dictionary Service

## Overview

The following repository contains a relatively straight forward Apache Tomcat Web Application employing Java Servlets and JSP as well as a RMI Service for querying dictionary definitions. 
The web client presents a basic user interface in which a user can enter a search string and submit it to be processed by the application. The application has been setup to simulate a delay of requests being added to a queueing system.

The following diagram depicts the overall system architecture:

![diagram](https://i.imgur.com/0KsUbvT.png)

