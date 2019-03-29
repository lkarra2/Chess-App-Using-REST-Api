
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


