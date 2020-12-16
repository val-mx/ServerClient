package val.mx.nelly;

public class GameResult {

    enum Result {
        UNENTSCHIEDEN,
        OK
    }

    private GameInfo.PlayerInfo loser;
    private GameInfo.PlayerInfo winner;
    private Result result;

    public GameResult(GameInfo.PlayerInfo loser, GameInfo.PlayerInfo winner, Result result) {
        this.loser = loser;
        this.winner = winner;
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public GameInfo.PlayerInfo getLoser() {
        return loser;
    }

    public GameInfo.PlayerInfo getWinner() {
        return winner;
    }
}
