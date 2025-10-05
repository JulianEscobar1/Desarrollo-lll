package com.yugioh.game;

import com.yugioh.api.YugiohApiClient;
import com.yugioh.model.Card;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController {
    private final YugiohApiClient api;
    private List<Card> playerCards = new ArrayList<>();
    private List<Card> botCards = new ArrayList<>();
    private int playerScore = 0;
    private int botScore = 0;
    private final List<String> logs = new ArrayList<>();
    private final Random rand = new Random();
    private int lastBotChoice = -1;
    private boolean playerTurn = true;

    public GameController() {
        api = new YugiohApiClient();
        playerTurn = rand.nextBoolean();
    }

    public void startNewRound() throws IOException, InterruptedException {
        playerCards = api.getCards();
        botCards = api.getCards();
        lastBotChoice = -1;
        logs.add("Nueva ronda: cartas repartidas.");
    }

    public List<Card> getPlayerCards() {
        return playerCards;
    }

    public List<Card> getBotCards() {
        return botCards;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getBotScore() {
        return botScore;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public String playRound(int playerIndex, String stat) {
        Card playerCard = playerCards.get(playerIndex);
        boolean chosenCard = false;
        while (!chosenCard) {
            lastBotChoice = rand.nextInt(botCards.size());
            if (!botCards.get(lastBotChoice).isUsed()) {
                chosenCard = true;
            }
        }
        Card botCard = botCards.get(lastBotChoice);
        botCards.get(lastBotChoice).setUsed(true);

        boolean usePlayerDef = "def".equalsIgnoreCase(stat);
        int playerValue = usePlayerDef ? playerCard.getDef() : playerCard.getAtk();
        boolean useBotDef = rand.nextBoolean();
        int botValue = useBotDef ? botCard.getDef() : botCard.getAtk();

        String message = String.format("Jugador (%s)=%d vs Bot (%s)=%d. ", stat.toUpperCase(), playerValue, botCard.getName(), botValue);

        if (usePlayerDef && useBotDef) {
            message += "Alguien debe atacar.";
            botCards.get(lastBotChoice).setUsed(false);
            playerTurn = !playerTurn;
        } else if (!usePlayerDef && !useBotDef) {
            if (playerCard.getAtk() > botCard.getAtk()) {
                playerScore++;
                message += "Jugador gana.";
                playerTurn = !playerTurn;
            } else if (playerCard.getAtk() < botCard.getAtk()) {
                botScore++;
                message += "Bot gana.";
                playerTurn = !playerTurn;
            } else {
                message += "Empate.";
                playerTurn = !playerTurn;
            }
        } else {
            if (playerValue > botValue) {
                playerScore++;
                message += "Jugador gana.";
                playerTurn = !playerTurn;
            } else if (playerValue < botValue) {
                botScore++;
                message += "Bot gana.";
                playerTurn = !playerTurn;
            } else {
                message += "Empate.";
                playerTurn = !playerTurn;
            }
        }

        logs.add(message);

        if (playerScore >= 2 || botScore >= 2) {
            message += playerScore > botScore ? " El jugador gana la partida!" : " El bot gana la partida!";
            playerScore = 0;
            botScore = 0;
        }
        return message;
    }

    public int getLastBotChoice() {
        return lastBotChoice;
    }

    public List<String> getLogs() {
        return new ArrayList<>(logs);
    }
}
