package val.mx.nelly;

public class GameResult {

    enum Result {
        UNENTSCHIEDEN,
        OK
    }

    private final GameInfo.PlayerInfo player1;
    private final GameInfo.PlayerInfo player2;
    private final Result result;

    public GameResult(GameInfo.PlayerInfo player2, GameInfo.PlayerInfo player1, Result result) {
        this.player1 = player1;
        this.player2 = player2;
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public GameInfo.PlayerInfo getPlayer1() {
        return player1;
    }

    public GameInfo.PlayerInfo getPlayer2() {
        return player2;
    }
}
