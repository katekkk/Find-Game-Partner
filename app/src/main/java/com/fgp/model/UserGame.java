package com.fgp.model;

public class UserGame {

    private String userId;
    private int gameId;
    private int duration;
    private Game game;
    private User user;

    public UserGame(String userId, int gameId, int duration, Game game, User user) {
        this.userId = userId;
        this.gameId = gameId;
        this.duration = duration;
        this.game = game;
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserGame{" +
                "userId='" + userId + '\'' +
                ", gameId=" + gameId +
                ", duration=" + duration +
                ", game=" + game +
                ", user=" + user +
                '}';
    }
}
