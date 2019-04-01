# Homework 4
Chess Game VAP running on Docker and OSv    

## Description
JavaOpenChess is an opensource chess application. In this application, a Virtual Application has been built over JavaOpenChess which can be communicated 
to using REST API calls. The application can be obtained using the command git clone git@bitbucket.org:lkarra2/lakshmi_manaswi_karra_cs441_hw4.git

##Design 
The application has been built with multiple packages. You can find a brief description of each below.

### Chessgame package
This package consists of classes that wrap the JavaOpenChess application to display only the functions that are necessary for the sake of our VAP.

##### Chessgame
Wrapper around the JavaOpenChess, makes only necessary functions visible [newGame(), move(), quit()]

#### GameBuilder
Class copied from JavaOpenChess, helps build() the game

#### Move
Creates a move by taking "begin" and "end"

### Chessengine package
This package consists of classes that help build the REST API

#### IChessEngine Interface
Interface defining the functions available to the Client

#### ChessEngine Class
Implementation of the IChessEngine Interface

#### Response Class
Return type of any function called on the server

#### Session Class
It helps to keep track of the state of the game by storing unique ID

### Chessserver package
This package consists of classes that build the RESTful API with the help of chessengine package.

#### Application Class
The main class of the Springboot server

#### GameController Class
Defines all the Postmapping functions ["/new-game", "/move", "/status", "/quit", "/delete"]
  
### Chessclient package
This package consists of classes that create 2 client applications that connect to the Chess VAP Server

#### ChessSession Class
A helper Class that defines functions used by the ChessClient and ChessChallenger to create Client calls

#### ChessClient Class
Runs a client that connects to a running Chess Server and allows you to play by passing moves in the form [eg: b2-b4]

#### ChessChallenger Class
Runs a chess challenge between two running chess servers and plays till there is a winner

## Building the software 

#### Building locally
The application has been built using gradle, where the build.gradle creates multiple scripts, allowing you to run ChessClient, ChessChallenger separately.

##### Steps to execute locally
```
Steps to run using gradle
1. ./gradlew clean installDist -x test 
2. run chessserver.Application
3. run chessclient.ChessClient with valid input. Usage: ChessClient [-player playerName] [-white|-black] [-aiLevel 1|2] ServerURL
4. run chessclient.ChessChallenger after running two instances of ChessServer at 2 different ports. Usage: ChessClient [-player1 playerName] 
[-1white|-1black] [-aiLevel1 1|2] -URL1 ServerURL1 [-player2 playerName] [-2white|-2black] [-aiLevel2 1|2] -URL2 ServerURL2
```

```
Steps to run on IntelliJ
1. Open project
2. Build project
3. Run chessserver.Application
4. run chessclient.ChessClient with valid input. Usage: ChessClient [-player playerName] [-white|-black] [-aiLevel 1|2] ServerURL
5. run chessclient.ChessChallenger after running two instances of ChessServer at 2 different ports. Usage: ChessClient [-player1 playerName] 
       [-1white|-1black] [-aiLevel1 1|2] -URL1 ServerURL1 [-player2 playerName] [-2white|-2black] [-aiLevel2 1|2] -URL2 ServerURL2
```

### Building on Docker
```
Steps to run Docker locally:
1. Run docker
2. docker build --no-cache -t chess-server . [To build docker image]
3. docker run -it -p 8080:8080 chess-server [To run the server on port 8080 - to be run twice for chesschallenger]
4. docker run -it chess-server chess-client [Running the chess client]
5. docker run -it chess-server chess-challenger -URL1 http://[IP Address 1]:8080 -URL2 http://[IP Address 2]:8080
```

### Installing
Run `./gradlew clean installDist -x test` --> This instruction must be run before docker build/run is called. 

### Building OSv image
To build OSv image using capstan, run  --> `docker run -it -v $(pwd)/shared:/shared chess-server bash -p`
Followed by, -> `sh capstan-project/create-osv-image`

## Resources
* [SpringbootActuator](https://spring.io/guides/gs/actuator-service/)
* [JUnit](https://junit.org/junit4/)
* [Logback]()
* [Docker]()
* [OSv]() 
* [Apache HTTPClient]()   
* [JDK 1.8]() 

##Deliverables
The project is avaialble on bitbucket and can be cloned with --> git clone git@bitbucket.org:lkarra2/lakshmi_manaswi_karra_cs441_hw4.git
It is also available on Dockerhub at --> [Dockerhub Repository](https://cloud.docker.com/repository/docker/lkarra2/chess-server/general)
The YouTube link is available at --> [YouTube Link showing working of 2 Chess VAP instances on AWS EC2](https://youtu.be/4p11jsvx50A) 

```
Running Docker image on Dockerhub:
1. docker pull 
2. docker run -it lkarra2/chess-server `Run once/twice for 1/2 running servers`
3. docker run -it lkarra2/chess-server chess-client <IPAddress>:<portNumber> `Running the chess client`
4. docker run -it lkarra2/chess-server chess-challenger -URL1 <IPAddress>:<portNumber> -URL2 <IPAddress>:<portNumber>
```

```
Running OSv image on Docker:
1. Build Docker image as shown above
2. Navigate to capstan-project/ and run bash script create-osv-image
3. capstan run -f chess-server
```

## Running the tests

The tests 

To run the tests,
```
1. Run Tests on IntelliJ OR
2. run using gradle
```

## Authors

Lakshmi Manaswi Karra - 657276611 - [https://github.com/lkarra2]

## Acknowledgments

* Mark Grechanik for assigning this homework
