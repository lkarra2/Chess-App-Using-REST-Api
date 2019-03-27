package chessserver;

/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.net.URI;
import java.net.URISyntaxException;
import chessengine.Response;
import chessengine.Session;
import chessgame.Move;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Basic integration tests for service demo application.
 *
 * @author Dave Syer
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class ApplicationTests {

    @LocalServerPort
    private int port;

    @Value("${local.management.port}")
    private int mgt;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldReturn200WhenSendingRequestToController() throws Exception {
        @SuppressWarnings("rawtypes")
        final String baseUrl = "http://localhost:" + this.port + "/echo";
        URI uri = new URI(baseUrl);
        Echo echo = new Echo();
        echo.setMessage("MANA");

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");

        HttpEntity<Echo> request = new HttpEntity<>(echo, headers);

        ResponseEntity<Echo> result = this.testRestTemplate.postForEntity(uri, request, Echo.class);

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("MANA", result.getBody().getMessage());
    }

    @Test
    public void testNewGameAsBlack() throws URISyntaxException {
        @SuppressWarnings("rawtypes")
        final String baseUrl = "http://localhost:" + this.port + "/new-game";
        URI uri = new URI(baseUrl);
        GameController.NewGameRequest gameRequest = new GameController.NewGameRequest();
        gameRequest.setPlayerName("KDM");
        gameRequest.setAiLevel(2);
        gameRequest.setFirstMove(false);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");

        HttpEntity<GameController.NewGameRequest> request = new HttpEntity<>(gameRequest, headers);

        ResponseEntity<Response> result = this.testRestTemplate.postForEntity(uri, request, Response.class);
        Assert.assertNotNull(result.getBody().getNextMove());
        Assert.assertNotNull(result.getBody().getSession());
        Assert.assertEquals(0, result.getBody().getStatus());
    }

    @Test
    public void testNewGameAsWhite() throws URISyntaxException {
        @SuppressWarnings("rawtypes")
        final String baseUrl = "http://localhost:" + this.port + "/new-game";
        URI uri = new URI(baseUrl);
        GameController.NewGameRequest gameRequest = new GameController.NewGameRequest();
        gameRequest.setPlayerName("KDM");
        gameRequest.setAiLevel(2);
        gameRequest.setFirstMove(true);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");

        HttpEntity<GameController.NewGameRequest> request = new HttpEntity<>(gameRequest, headers);

        ResponseEntity<Response> result = this.testRestTemplate.postForEntity(uri, request, Response.class);
        Assert.assertNull(result.getBody().getNextMove());
        Assert.assertNotNull(result.getBody().getSession());
        Assert.assertEquals(0, result.getBody().getStatus());
    }

    @Test
    public void testMove() throws URISyntaxException {
        @SuppressWarnings("rawtypes")

        Session s = createNewGame("player1", true, 1).getSession();

        final String baseUrl = "http://localhost:" + this.port + "/move";
        URI uri = new URI(baseUrl);
        GameController.NewMoveRequest moveRequest = new GameController.NewMoveRequest();
        Move move = new Move();
        move.setBegin("g7");
        move.setEnd("g6");
        moveRequest.setMove(move);
        moveRequest.setSession(s);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");

        HttpEntity<GameController.NewMoveRequest> request = new HttpEntity<>(moveRequest, headers);
        ResponseEntity<Response> result = this.testRestTemplate.postForEntity(uri, request, Response.class);

        Assert.assertNotNull(s);
        Assert.assertNotNull(result.getBody().getNextMove());
        Assert.assertEquals(0, result.getBody().getStatus());
    }

    @Test
    public void testQuit() throws URISyntaxException {
        Session s = createNewGame("player1", true, 1).getSession();

        final String baseUrl = "http://localhost:" + this.port + "/quit";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");

        HttpEntity<Session> request = new HttpEntity<>(s);
        ResponseEntity<Response> result = this.testRestTemplate.postForEntity(uri, request, Response.class);
        Assert.assertEquals(1, result.getBody().getGameStatus());
        Assert.assertEquals("Computer", result.getBody().getWinner());
    }

    @Test
    public void testDelete() throws URISyntaxException {
        Session s = createNewGame("player1", true, 1).getSession();

        final String baseUrl = "http://localhost:" + this.port + "/delete";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");

        HttpEntity<Session> request = new HttpEntity<>(s);
        ResponseEntity<Response> result = this.testRestTemplate.postForEntity(uri, request, Response.class);
        Assert.assertEquals(0, result.getBody().getStatus());
    }

    @Test
    public void testTwoGames() throws URISyntaxException {

        Session s1 = createNewGame("white", true, 2).getSession();
        Response r = createNewGame("black", false, 1);
        Session s2 = r.getSession();
        Move move = r.getNextMove();

        while(gameIsNotEnded(s1, s2)) {
            r = move(s1, move);
            if(!gameIsNotEnded(s1, s2))
                break;
            move = r.getNextMove();
            System.out.println("Move: " + move);
            r = move(s2, move);
            if(!gameIsNotEnded(s1, s2))
                break;
            move = r.getNextMove();
        }
        System.out.println("S1 Status: " + status(s1));
        System.out.println("S2 Status: " + status(s2));
    }

    private Response move(Session s, Move m) throws URISyntaxException {
        final String baseUrl = "http://localhost:" + this.port + "/move";
        URI uri = new URI(baseUrl);
        GameController.NewMoveRequest moveRequest = new GameController.NewMoveRequest();
        moveRequest.setMove(m);
        moveRequest.setSession(s);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");
        HttpEntity<GameController.NewMoveRequest> request = new HttpEntity<>(moveRequest, headers);
        ResponseEntity<Response> result = this.testRestTemplate.postForEntity(uri, request, Response.class);
        return result.getBody();
    }

    private boolean gameIsNotEnded(Session s1, Session s2) throws URISyntaxException {
        return (status(s1).getGameStatus() == 0 && status(s2).getGameStatus() == 0);
    }

    @Test
    public void testGetGameStatus() throws URISyntaxException {
        Session session = createNewGame("white", true, 1).getSession();
        Response result = status(session);
        Assert.assertEquals(0, result.getGameStatus());
        String baseUrl = "http://localhost:" + this.port + "/quit";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");

        HttpEntity<Session>request = new HttpEntity<>(session);
        result = this.testRestTemplate.postForEntity(uri, request, Response.class).getBody();
        Assert.assertEquals(1, result.getGameStatus());
        Assert.assertEquals("Computer", result.getWinner());
    }


    private Response status(Session s) throws URISyntaxException {
        String baseUrl = "http://localhost:" + this.port + "/status";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");

        HttpEntity<Session> request = new HttpEntity<>(s);
        ResponseEntity<Response> result = this.testRestTemplate.postForEntity(uri, request, Response.class);
        return result.getBody();
    }


    private Response createNewGame(String playerName, boolean firstMove, int aiLevel) throws URISyntaxException {
        final String baseUrl = "http://localhost:" + this.port + "/new-game";
        URI uri = new URI(baseUrl);
        GameController.NewGameRequest gameRequest = new GameController.NewGameRequest();
        gameRequest.setPlayerName(playerName);
        gameRequest.setAiLevel(aiLevel);
        gameRequest.setFirstMove(firstMove);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");

        HttpEntity<GameController.NewGameRequest> request = new HttpEntity<>(gameRequest, headers);

        ResponseEntity<Response> result = this.testRestTemplate.postForEntity(uri, request, Response.class);
        return result.getBody();
    }
}