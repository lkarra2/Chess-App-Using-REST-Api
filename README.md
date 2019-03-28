
1. Run docker
2. docker build --no-cache -t chess-server . `To build docker image`
3. docker run -it -p 8080:8080 chess-server `To run the server on port 8080`
4. docker run -it -p 8090:8090 -e 8090 chess-server chess-server --server.port=8090 `Explicitly specifying port 8090`
5. docker run -it chess-server chess-client ``

chess-server: Name of the command

docker run -it -p 8080:8080 chess-server

docker run -it -p 8090:8090 -e 8090 chess-server chess-server --server.port=8090

docker run -it chess-server chess-challenger -URL1 http://172.17.0.2:8090 -URL2 http://172.17.0.3:8091

copy . chess-server --> git clone `bit bucket URL`