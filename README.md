# Homework 4
#### Description: 
#### You can obtain this Git repo using the command git clone git@bitbucket.org:lkarra2/lakshmi_manaswi_karra_cs441_hw4.git

# Chess Game VAP running on Docker and OSv    

### Introduction

This Project is the Virtuallization of the Open Source project [JavaOpenChess](). The Application has been built in layers as follows:

#### chessgame package
**ChessGame Class** - Wrapper around the JavaOpenChess, makes only necessary functions visible [newGame(), move(), quit()]
**GameBuilder Class** - Class copied from JavaOpenChess, helps build() the game
**Move Class** - Creates a move by taking "begin" and "end"

#### chessengine package
**IChessEngine Interface** - Interface defining the functions available to the Client
**ChessEngine Class** - Implementation of the IChessEngine Interface
**Response Class** - Return type of any function called on the server
**Session Class** - It helps to keep track of the state of the game by storing unique ID

#### chessserver package
**Application Class** - The main class of the Springboot server
**GameController Class** - Defines all the Postmapping functions ["/new-game", "/move", "/status", "/quit", "/delete"]
  
#### chessclient package
**ChessSession Class** - A helper Class that defines functions used by the ChessClient and ChessChallenger to create Client calls
**ChessClient Class** - Runs a client that connects to a running Chess Server and allows you to play by passing moves in the form [eg: b2-b4]
**ChessChallenger Class** - Runs a chess challenge between two running chess servers and plays till there is a winner

- [YouTube Link] () 

### Prerequisites

Libraries and dependencies that you will need to run the code. 

1. [Java JDK 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
2. [Docker](https://www.docker.com/)
3. [JUnit Test](https://junit.org/junit4/)

### Installing

A step by step break down of how to get a development environment running

1. Download or clone repository by cloning using command - git clone git@bitbucket.org:lkarra2/lakshmi_manaswi_karra_cs441_hw2.git
2. Open in IntelliJ or move to project folder

```
Steps to run using gradle
1. ./gradlew clean compile 
2. run chessserver.Application
3. run chessclient.ChessClient with valid input. Usage: ChessClient [-player playerName] [-white|-black] [-aiLevel 1|2] ServerURL
4. run chessclient.ChessChallenger after running two instances of ChessServer at 2 different ports. Usage: ChessClient [-player1 playerName] 
[-1white|-1black] [-aiLevel1 1|2] -URL1 ServerURL1 [-player2 playerName] [-2white|-2black] [-aiLevel2 1|2] -URL2 ServerURL2
```

```
Steps to run on IntelliJ
1. Open project as Scala Project
2. Build project
3. Run chessserver.Application
4. run chessclient.ChessClient with valid input. Usage: ChessClient [-player playerName] [-white|-black] [-aiLevel 1|2] ServerURL
5. run chessclient.ChessChallenger after running two instances of ChessServer at 2 different ports. Usage: ChessClient [-player1 playerName] 
       [-1white|-1black] [-aiLevel1 1|2] -URL1 ServerURL1 [-player2 playerName] [-2white|-2black] [-aiLevel2 1|2] -URL2 ServerURL2
```

```
Steps to run Docker locally:
1. Run docker
2. docker build --no-cache -t chess-server . [To build docker image]
3. docker run -it -p 8080:8080 chess-server [To run the server on port 8080 - to be run twice for chesschallenger]
4. docker run -it chess-server chess-client [Running the chess client]
5. docker run -it chess-server chess-challenger -URL1 http://[IP Address 1]:8080 -URL2 http://[IP Address 2]:8080
```

```
Running Docker image on Dockerhub:
1. Download Docker image
2. Build the image 
3. docker run -it <dockerImageName> `Run once/twice for 1/2 running servers`
5. docker run -it <dockerImageName> chess-client <IPAddress>:<portNumber> `Running the chess client`
6. docker run -it <dockerImageName> chess-challenger -URL1 <IPAddress>:<portNumber> -URL2 <IPAddress>:<portNumber>
```

```
Running OSv image on Docker:
1. Build Docker image as shown above
2. Navigate to capstan-project/ and run bash script create-osv-image
3. capstan run -f chess-server
```


### Resources




## Running the tests

The tests 

To run the tests,
```
1. Run Tests on IntelliJ OR
2. run using gradle
```


## Built With

* [Springboot]() - 
* [JUnit]() - 
* [Logback]() -  
* [Docker]() - 
* [OSv]() - 
* [Apache HTTPClient]() -  


## Authors

Lakshmi Manaswi Karra - 657276611 - [https://github.com/lkarra2]

## Acknowledgments

* Mark Grechanik for assigning this homework



./gradlew clean build installDist -x test

To build:
docker build -t chess-server . 


Running Docker locally:
1. Run docker
2. docker build --no-cache -t chess-server . `To build docker image`
3. docker run -it -p 8080:8080 chess-server `To run the server on port 8080`
4. docker run -it -p 8090:8090 -e 8090 chess-server chess-server --server.port=8090 `Explicitly specifying port 8090`
5. docker run -it chess-server chess-client `Running the chess client`
6. docker run -it chess-server chess-challenger -URL1 http://[IP Address 1]:8090 -URL2 http://[IP Address 2]:8091

Running Docker image on Dockerhub:
1. Download Docker image
2. Build the image 
3. docker run -it <dockerImageName> `Run once/twice for 1/2 running servers`
5. docker run -it <dockerImageName> chess-client <IPAddress>:<portNumber> `Running the chess client`
6. docker run -it <dockerImageName> chess-challenger -URL1 <IPAddress>:<portNumber> -URL2 <IPAddress>:<portNumber>

capstan package init --help
openjdk8-zulu-full

to build capstan --> docker run -it -v $(pwd)/capstan-project:/capstan-project chess-server bash -p

capstan package init -n chess-server -t 'Chess Server' -a 'Lakshmi Manaswi Karra' -v '1.0' --require openjdk8-zulu-full --runtime java
capstan package compose chess-server -p

To run:
capstan run -f 8080:8080 chess-server
