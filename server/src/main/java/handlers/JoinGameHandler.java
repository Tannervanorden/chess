package handlers;

import com.google.gson.Gson;
import model.GameData;
import model.JoinGameRequest;
import service.JoinGameService;
import spark.Request;
import spark.Response;

import java.util.Map;

public class JoinGameHandler {
    private Gson gson = new Gson();
    private JoinGameService joinGameService = new JoinGameService();

     public Object joinGame(Request request, Response response) {
         try {
             String authToken = request.headers("authorization");
             JoinGameRequest requestBody = gson.fromJson(request.body(), JoinGameRequest.class);
             long id = requestBody.gameID();
             String playerColor = requestBody.playerColor();

             if (authToken == null || authToken.isEmpty()) {
                 response.status(400);
                 return gson.toJson(Map.of("message", "Error: unauthorized"));
             }


             GameData game = joinGameService.joinGame(id, playerColor, authToken);
             response.status(200);
             return gson.toJson(game);
         } catch (Exception e) {
             String errorMessage = e.getMessage();
             if (errorMessage.equals("Unauthorized")){
                 response.status(401);
                 return gson.toJson(Map.of("message", "Error: unauthorized"));
             } else if (errorMessage.equals("Game not found") || errorMessage.equals("Invalid Player Color")){
                 response.status(400);
                 return gson.toJson(Map.of("message", "Error: bad request"));
             } else if (errorMessage.equals("Error: already taken")){
                 response.status(403);
                 return gson.toJson(Map.of("message", "Error: already taken"));
             } else {
                 response.status(500);
                 return gson.toJson(Map.of("message", errorMessage));
             }
         }
     }
}
