package com.yugioh.game;

import com.yugioh.api.YugiohApiClient;
import com.yugioh.model.Card;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Controlador de la lógica del juego: reparto de cartas, resolución de rondas y puntuaciones.
 * Mantiene un registro (logs) de las jugadas para que la vista pueda mostrarlas.
 */
public class GameController {
    private final YugiohApiClient api;
    private List<Card> playerCards = new ArrayList<>();
    private List<Card> botCards = new ArrayList<>();
    private int playerScore = 0;
    private int botScore = 0;
    private final List<String> logs = new ArrayList<>();
    private final Random rand = new Random();
    private int lastBotChoice = -1;

    public GameController() {
        api = new YugiohApiClient();
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


    public String playRound(int playerIndex, String stat) {
        lastBotChoice = rand.nextInt(botCards.size());
        Card playerCard = playerCards.get(playerIndex);
        Card botCard = botCards.get(lastBotChoice);

        boolean useDef = "def".equalsIgnoreCase(stat);
        int playerValue = useDef ? playerCard.getDef() : playerCard.getAtk();
        int botValue = useDef ? botCard.getDef() : botCard.getAtk();

        String message = String.format("Jugador (%s)=%d vs Bot (%s)=%d. ", stat.toUpperCase(), playerValue, botCard.getName(), botValue);
        if (playerValue > botValue) {
            playerScore++;
            message += "Jugador gana.";
        } else if (playerValue < botValue) {
            botScore++;
            message += "Bot gana.";
        } else {
            message += "Empate.";
        }
        logs.add(message);

        if(playerScore >= 2 || botScore >= 2) {
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

