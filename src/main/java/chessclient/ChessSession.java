package chessclient;

import chessgame.Move;
import org.apache.commons.codec.Charsets;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ChessSession {

    // A helper Class that defines functions used by the ChessClient and ChessChallenger to create Client calls

    private String baseUrl;
    private String playerName;
    private boolean firstMove;
    private int aiLevel;
    private CloseableHttpClient httpclient;
    private JSONObject session;
    private int gameStatus;

    public ChessSession(String baseUrl, String playerName, boolean firstMove, int aiLevel) {
        this.baseUrl = baseUrl;
        this.playerName = playerName;
        this.firstMove = firstMove;
        this.aiLevel = aiLevel;
    }

    public Move startGame() throws IOException {
        httpclient = HttpClients.createDefault();
        JSONObject request = createGameRequest();
        JSONObject response = postRequest("/new-game", request);
        session = response.getJSONObject("session");
        if(firstMove)
            return null;
        JSONObject nMove = response.getJSONObject("nextMove");
        return new Move(nMove.getString("begin"), nMove.getString("end"));
    }

    private JSONObject postRequest(String s, JSONObject request) throws IOException {
        System.out.println("Request[" + s + "]: " + request);
        HttpPost httpPost = new HttpPost(baseUrl + s);
        httpPost.setHeader("Accept", "application/json");
        StringEntity params = new StringEntity(request.toString());
        httpPost.addHeader("content-type", "application/json");
        httpPost.setEntity(params);
        CloseableHttpResponse response = null;
        response = httpclient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        Header encodingHeader = entity.getContentEncoding();
        Charset encoding = encodingHeader == null ? StandardCharsets.UTF_8 :
                Charsets.toCharset(encodingHeader.getValue());

        String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);

        JSONObject o = new JSONObject(json);
        System.out.println("Response: " + o);
        return o;
    }

    private JSONObject createGameRequest() {
        JSONObject jo = new JSONObject();
        jo.put("playerName", playerName);
        jo.put("firstMove", firstMove);
        jo.put("aiLevel", aiLevel);
        return jo;
    }

    public Move move(Move move) throws IOException {
        JSONObject request = createMoveRequest(move);
        JSONObject response = postRequest("/move", request);
        JSONObject nMove = response.getJSONObject("nextMove");
        return new Move(nMove.getString("begin"), nMove.getString("end"));
    }

    private JSONObject createMoveRequest(Move move) {
        JSONObject o = new JSONObject();
        o.put("session", session);
        JSONObject jMove = new JSONObject();
        jMove.put("begin", move.getBegin());
        jMove.put("end", move.getEnd());
        o.put("move", jMove);
        return o;
    }

    public void quit() throws IOException {
        JSONObject request = createIdRequest();
        postRequest("/quit", request);
        return;
    }

    public void delete() throws IOException {
        JSONObject request = createIdRequest();
        postRequest("/delete", request);
        return;
    }

    private JSONObject createIdRequest() {
        JSONObject jo = new JSONObject();
        jo.put("id", session.getString("id"));
        return jo;
    }

    public String status() throws IOException {
        JSONObject request = createIdRequest();
        JSONObject response = postRequest("/status", request);
        StringBuilder sb = new StringBuilder();
        int status = response.getInt("gameStatus");
        if(status == 0) {
            sb.append("Game gameStatus: Continuing");
            this.gameStatus = 0;
        }
        else if(status == 1) {
            sb.append("Game gameStatus: Checkmate");
            this.gameStatus = 1;
        }
        else if(status == 2) {
            sb.append("Game gameStatus: Draw");
            this.gameStatus = 2;
        }
        if(status == 1)
            sb.append(" Winner: " + response.getString("winner"));
        return sb.toString();
    }

    public String getPlayerName() { return playerName; }

    public int getGameStatus() {
        return gameStatus;
    }
}
