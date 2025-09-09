# Todo-list

A small application for managing a to-do list.

## Installation and execution

Here are the steps to follow to install and run the project after downloading it.

### Back-end

#### Installation

> Prerequisites : ensure that you have installed JDK 24 and Apache Maven on your machine.
> The *JAVA_HOME* and *MAVEN_HOME* environment variables must be correctly defined.

In a command prompt, navigate to the todo-list/backend folder, then run:

    mvn clean
    mvn install

#### Execution

In a command prompt, navigate to the todo-list/backend folder, then run:

    java -jar target/todolist-0.0.1-SNAPSHOT.jar

The application back-end is now launched and runs on http://localhost:8080.

### Front-end

#### Installation

> Prerequisites : ensure that you have installed the latest versions of Node.js and Angular CLI on your machine.

If needed, in a command prompt, run:

    npm install -g @angular/cli

#### Execution

In a command prompt, navigate to the todo-list/frontend folder.
If you want to run the application in French, run:

    ng serve --configuration=fr

If you want to run the application in English, run:

    ng serve