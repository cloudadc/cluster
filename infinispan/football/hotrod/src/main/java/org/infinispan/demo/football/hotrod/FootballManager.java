package org.infinispan.demo.football.hotrod;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.Configuration;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;

public class FootballManager {

    private static final String JDG_HOST = "jdg.host";
    private static final String HOTROD_PORT = "jdg.hotrod.port";
    private static final String PROPERTIES_FILE = "jdg.properties";
    private static final String msgTeamMissing = "The specified team \"%s\" does not exist, choose next operation\n";
    private static final String msgEnterTeamName = "Enter team name: ";
    private static final String initialPrompt = "Choose action:\n" + "============= \n" + "at  -  add a team\n"
            + "ap  -  add a player to a team\n" + "rt  -  remove a team\n" + "rp  -  remove a player from a team\n"
            + "p   -  print all teams and players\n" + "q   -  quit\n";
    private static final String teamsKey = "teams";

    private Console con;
    private RemoteCacheManager cacheManager;
    private RemoteCache<String, Object> cache;

    public FootballManager(Console con) {
        this.con = con;
        Configuration configuration = new ConfigurationBuilder().addServers(jdgProperty(JDG_HOST) + ":" + jdgProperty(HOTROD_PORT)).build();
        cacheManager = new RemoteCacheManager(configuration);
        cache = cacheManager.getCache("teams");
        if(!cache.containsKey(teamsKey)) {
            List<String> teams = new ArrayList<String>();
            Team t = new Team("Barcelona");
            t.addPlayer("Messi");
            t.addPlayer("Pedro");
            t.addPlayer("Puyol");
            cache.put(t.getName(), t);
            teams.add(t.getName());
            cache.put(teamsKey, teams);
        }
    }

    public void addTeam() {
        String teamName = con.readLine(msgEnterTeamName);
        @SuppressWarnings("unchecked")
        List<String> teams = (List<String>) cache.get(teamsKey);
        if (teams == null) {
            teams = new ArrayList<String>();
        }
        Team t = new Team(teamName);
        cache.put(teamName, t);
        teams.add(teamName);
        // maintain a list of teams under common key
        cache.put(teamsKey, teams);
    }

    public void addPlayers() {
        String teamName = con.readLine(msgEnterTeamName);
        String playerName = null;
        Team t = (Team) cache.get(teamName);
        if (t != null) {
            while (!(playerName = con.readLine("Enter player's name (to stop adding, type \"q\"): ")).equals("q")) {
                t.addPlayer(playerName);
            }
            cache.put(teamName, t);
        } else {
            con.printf(msgTeamMissing, teamName);
        }
    }

    public void removePlayer() {
        String playerName = con.readLine("Enter player's name: ");
        String teamName = con.readLine("Enter player's team: ");
        Team t = (Team) cache.get(teamName);
        if (t != null) {
            t.removePlayer(playerName);
            cache.put(teamName, t);
        } else {
            con.printf(msgTeamMissing, teamName);
        }
    }

    public void removeTeam() {
        String teamName = con.readLine(msgEnterTeamName);
        Team t = (Team) cache.get(teamName);
        if (t != null) {
            cache.remove(teamName);
            @SuppressWarnings("unchecked")
            List<String> teams = (List<String>) cache.get(teamsKey);
            if (teams != null) {
                teams.remove(teamName);
            }
            cache.put(teamsKey, teams);
        } else {
            con.printf(msgTeamMissing, teamName);
        }
    }

    public void printTeams() {
        @SuppressWarnings("unchecked")
        List<String> teams = (List<String>) cache.get(teamsKey);
        if (teams != null) {
            for (String teamName : teams) {
                con.printf(cache.get(teamName).toString());
            }
        }
    }

    public void stop() {
        cacheManager.stop();
    }

    public static void main(String[] args) {
        Console con = System.console();
        FootballManager manager = new FootballManager(System.console());
        con.printf(initialPrompt);

        while (true) {
            String action = con.readLine(">");
            if ("at".equals(action)) {
                manager.addTeam();
            } else if ("ap".equals(action)) {
                manager.addPlayers();
            } else if ("rt".equals(action)) {
                manager.removeTeam();
            } else if ("rp".equals(action)) {
                manager.removePlayer();
            } else if ("p".equals(action)) {
                manager.printTeams();
            } else if ("q".equals(action)) {
                manager.stop();
                break;
            }
        }
    }

    public static String jdgProperty(String name) {
        Properties props = new Properties();
        try {
            props.load(FootballManager.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        return props.getProperty(name);
    }
}
