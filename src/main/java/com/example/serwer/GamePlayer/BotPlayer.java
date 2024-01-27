package com.example.serwer.GamePlayer;


import com.example.serwer.ClientMessages.Move;
import com.example.serwer.ClientMessages.Pass;
import com.example.serwer.MessagefromServer.MoveInfo;
import com.example.serwer.MessagefromServer.OpponentPass;
import com.example.serwer.MessagefromServer.Server_ServerMessage;

import java.io.IOException;

public class BotPlayer extends GamePlayer
{

    private final int boardSize;

    public BotPlayer(int number, int boardSize) {
        this.numberOfPoints = 0;
        this.number = number;
        this.boardSize = boardSize;
    }
    @Override
    public void sendMessage(Server_ServerMessage message) throws IOException
    {
        if (message instanceof OpponentPass) {
            this.game.getMessage(new Pass(), this);
        } else if (message instanceof MoveInfo moveInfo) {

            if (moveInfo.getPlayer() == 1 && moveInfo.isCorrectMove()) {
                this.doMove();
            }
        }
    }

    private void doMove() {
        boolean foundMove = false;

        for (int i = 0; i < this.boardSize && !foundMove; i++) {
            for (int j = 0; j < this.boardSize && !foundMove; j++) {
                if (this.game.getGameLogic().validateMove(i, j, 2) && this.checkOpponentAdjacent(i, j)) {
                    this.game.getMessage(new Move(i, j), this);
                    foundMove = true;
                }
            }
        }

        if (!foundMove) {
            for (int i = 0; i < this.boardSize && !foundMove; i++) {
                for (int j = 0; j < this.boardSize && !foundMove; j++) {
                    if (this.game.getGameLogic().validateMove(i, j, 2)) {
                        this.game.getMessage(new Move(i, j), this);
                        foundMove = true;
                    }
                }
            }
        }

        if (!foundMove) {
            this.game.getMessage(new Pass(), this);
        }
    }

    private boolean checkOpponentAdjacent(int x, int y) {
        return this.game.getGameLogic().checkPlayer(x + 1, y) == 1 ||
                this.game.getGameLogic().checkPlayer(x - 1, y) == 1 ||
                this.game.getGameLogic().checkPlayer(x, y + 1) == 1 ||
                this.game.getGameLogic().checkPlayer(x, y - 1) == 1;
    }

    @Override
    public void run()
    {

    }
}
