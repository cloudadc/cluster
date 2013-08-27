package org.infinispan.demo.football.hotrod;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable {

    private static final long serialVersionUID = -181403229462007401L;

    private String teamName;
    private List<String> players;

    public Team(String teamName) {
        this.teamName = teamName;
        players = new ArrayList<String>();
    }

    public void addPlayer(String name) {
        players.add(name);
    }

    public void removePlayer(String name) {
        players.remove(name);
    }

    public List<String> getPlayers() {
        return players;
    }

    public String getName() {
        return teamName;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("=== Team: " + teamName + " ===\n");
        b.append("Players:\n");
        for (String player : players) {
            b.append("- " + player + "\n");
        }
        return b.toString();
    }
}