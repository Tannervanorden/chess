package dataaccess;

import java.util.HashMap;
import java.util.Map;

import model.GameData;

public class GameDAO {
    private static  GameDAO database;
    private Map<Integer, GameDAO> games;

    private GameDAO() {
        this.games = new HashMap<>();
    }

    public Map<Integer,  GameDAO> getGames() {
        return games;
    }

    public void clear() {
        this.games.clear();
    }
}