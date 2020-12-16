package val.mx.nelly;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

import static val.mx.nelly.TicTacToeServer.MAX_PLAYERS;

public class GameInfo {


    private final TicTacToeServer server;
    private final LinkedList<PlayerInfo> players;

    public GameInfo(TicTacToeServer server) {
        players = new LinkedList<>();
        this.server = server;
    }

    public void sendToAll(String message) {
        players.forEach(playerInfo ->
                server.send(playerInfo.IP
                        , playerInfo.port, message)
        );
    }

    public void addPlayer(String IP, int port) {
        players.add(new PlayerInfo(IP, port));
    }

    public LinkedList<PlayerInfo> getPlayers() {
        return players;
    }

    public PlayerInfo getPlayerThroughIP(String IP) {

        AtomicReference<PlayerInfo> result = new AtomicReference<>();

        players.forEach(playerInfo -> {
            if(playerInfo.getIP().equals(IP)) result.set(playerInfo);
        });

        return result.get();
    }

    public void addResult(String IP, SpielZug zug) {
        PlayerInfo info = getPlayerThroughIP(IP);
        info.setSpielZug(zug);
    }

    public boolean isGameInputDone() {
        int playersToDoActions = (int) players.stream().filter(playerInfo -> playerInfo.spielZug != null).count();

        return playersToDoActions == MAX_PLAYERS;
    }

    public boolean isAllPlayersConnected() {
        return players.size() == MAX_PLAYERS;
    }

    public PlayerInfo getPlayer(int index) {
        return players.get(index);
    }

    public void sendToPlayer(int index, String message) {
        PlayerInfo info = players.get(index);
        server.send(info.IP, info.port, message);
    }

    public GameResult getResults() {
        GameResult result = new GameResult(getPlayer(0),getPlayer(1), GameResult.Result.OK);

        return result;
    }

    static class PlayerInfo {
        private String IP;
        private int port;
        private SpielZug spielZug;

        public PlayerInfo(String IP, int port) {
            this.IP = IP;
            this.port = port;
        }

        public void setSpielZug(SpielZug spielZug) {
            this.spielZug = spielZug;
        }



        public SpielZug getSpielZug() {
            return spielZug;
        }

        public String getIP() {
            return IP;
        }

        public int getPort() {
            return port;
        }
    }

}
