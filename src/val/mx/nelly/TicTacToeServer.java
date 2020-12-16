package val.mx.nelly;

import java.util.HashMap;

public class TicTacToeServer extends Server {

    static int MAX_PLAYERS = 2;
    private GameInfo lastGame;
    private HashMap<String, GameInfo> games;

    public TicTacToeServer() {
        super(16785);

        lastGame = null;
        games = new HashMap<>();
    }



    @Override
    public void processNewConnection(String pClientIP, int pClientPort) {

        if (lastGame == null) {
            lastGame = new GameInfo(this);
        }

        lastGame.addPlayer(pClientIP, pClientPort);
        games.put(pClientIP, lastGame);

        if (!lastGame.isAllPlayersConnected()) {
            lastGame.sendToAll("+OK Spieler verbunden. Bitte warten Sie!");
        } else {
            lastGame.sendToAll("+OK Das Spiel geht los! Geben sie Ihren Zug ein!");
            lastGame = null;
        }

    }

    @Override
    public void processMessage(String pClientIP, int pClientPort, String pMessage) {
        GameInfo game = games.get(pClientIP);
        String[] args = pMessage.split(" ");
        System.out.println("[INCOMING : " + pClientIP + "] " + pMessage);
        send(pClientIP,pClientPort,"daw");

//        if (game == null) return;
        GameInfo.PlayerInfo player = game.getPlayerThroughIP(pClientIP);

        System.out.println("ZUG".equals(args[0]));

        if (args[0].equals("ZUG")) {
            if (!game.isAllPlayersConnected()) {
                send(pClientIP, pClientPort, "-ERR Sie spielen gar nicht!");
                return;
            }

            if (args.length < 2) {
                send(pClientIP, pClientPort, "-ERR Ungueltiger Zug!");
                return;
            }

            try {

                SpielZug zug = SpielZug.valueOf(args[1]);
                player.setSpielZug(zug);

                send(pClientIP, pClientPort, String.format("+Ok Zug %s erhalten!", zug.toString()));

                if (game.isGameInputDone()) {
                    game.sendToAll("STARTE AUSWERTUNG");

                    GameResult result = game.getResults();

                    GameInfo.PlayerInfo player1 = result.getLoser();
                    GameInfo.PlayerInfo player2 = result.getWinner();

                    SpielZug zug1 = player1.getSpielZug();
                    SpielZug zug2 = player2.getSpielZug();

                    if (zug1.equals(zug2)) {
                        game.sendToAll("+Ok Unentschieden!");
                    } else if (zug1.spielZugs.contains(zug2)) {
                        send(player1.getIP(), player1.getPort(), "+OK Sie haben gewonnen!");
                        send(player2.getIP(), player2.getPort(), "+OK Sie haben verloren!");
                    } else {
                        send(player1.getIP(), player1.getPort(), "+OK Sie haben verloren!");
                        send(player2.getIP(), player2.getPort(), "+OK Sie haben gewonnen!");
                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
                send(pClientIP, pClientPort, "-ERR Ungueltiger Zug!");
            }
        }

    }

    @Override
    public void processClosingConnection(String pClientIP, int pClientPort) {

    }
//
//    @Override
//    public void send(String pClientIP, int i, String pMessage) {
//        super.send(pClientIP, i, pMessage);
//        System.out.println("[LOG : " + pClientIP + "] " + pMessage);
//    }
}
