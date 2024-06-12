package server;

import chess.ChessMove;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import dataaccess.*;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import service.GenericService;
import websocket.commands.*;
import com.google.gson.Gson;

import java.net.URI;

@WebSocket
public class WebSocketServer {
    private Gson gson = new Gson();
    private MySQLAuthDAO authDAO = GenericService.getAuthDAO();
    private MySQLUserDAO userDAO = GenericService.getUserDAO();
    private MySQLGameDAO gameDAO = GenericService.getGameDAO();


    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        System.out.printf("Received: %s", message);
//        session.getRemote().sendString("WebSocket response: " + message);

        UserGameCommand command = gson.fromJson(message, UserGameCommand.class);
        switch (command.getCommandType()) {
            case CONNECT:
                handleConnect((Connect) command, session);
                break;
            case MAKE_MOVE:
                handleMakeMove((MakeMove) command, session);
                break;
            case LEAVE:
                handleLeave((Leave) command, session);
                break;
            case RESIGN:
                handleResign((Resign) command, session);
                break;
        }
    }

    private void handleConnect(Connect command, Session session) {
        System.out.println("CONNECT");
        String authToken = command.getAuthString();

        try {
            if (authToken != null && authDAO.validateToken(authToken)) {

            } else {
                System.out.println("Authentication failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void handleMakeMove(MakeMove command, Session session) {
        System.out.println("MAKE_MOVE");
        ChessMove move = command.getMove();
    }

    private void handleLeave(Leave command, Session session) {
        System.out.println("LEAVE");
    }

    private void handleResign(Resign command, Session session) {
        System.out.println("RESIGN");
    }
}