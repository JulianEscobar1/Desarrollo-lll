package com.yugioh.view;

import com.yugioh.game.GameController;
import com.yugioh.model.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import javax.swing.Timer;

public class Init {
    private JPanel background;
    private JPanel playerPanel;
    private JPanel botPlayer;
    private JPanel panelCard1;
    private JLabel imageCard1;
    private JLabel nameCard1;
    private JPanel panelCardBot;
    private JLabel image_card_bot;
    private JLabel atkCard1;
    private JLabel defCard1;
    private JPanel panelCard2;
    private JLabel imageCard2;
    private JLabel nameCard2;
    private JLabel atkCard2;
    private JLabel defCard2;
    private JPanel panelCard3;
    private JLabel imageCard3;
    private JLabel nameCard3;
    private JLabel atkCard3;
    private JLabel defCard3;
    private JLabel name_card_bot;
    private JLabel atk_card_bot;
    private JLabel def_card_bot;
    private JButton atkButton;
    private JButton defButton;
    private JPanel logPanel;
    private JLabel botScore;
    private JTextArea logTextArea;
    private JButton startButton;
    private JLabel scorePlayer;

    private List<Card> playerCards;
    private List<Card> botCards;

    private GameController controller;
    private int selectedPlayerCard = -1;

    public Init() throws IOException, InterruptedException {
        setScroller();
        panelCard1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelCard2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelCard3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panelCard1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectPlayerCard(0);
            }
        });

        panelCard2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectPlayerCard(1);
            }
        });

        panelCard3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectPlayerCard(2);
            }
        });

        controller = new GameController();

        atkButton.addActionListener(e -> playSelectedCard("atk"));

        defButton.addActionListener(e -> playSelectedCard("def"));

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    initCards();
                    logTextArea.setText("");
                    appendLog("Nueva ronda iniciada.");
                    image_card_bot.setIcon(null);
                    name_card_bot.setText("Nombre");
                    atk_card_bot.setText("ATK: ");
                    def_card_bot.setText("DEF: ");
                    scorePlayer.setText("0");
                    botScore.setText("0");
                    panelCard1.setVisible(true);
                    panelCard2.setVisible(true);
                    panelCard3.setVisible(true);
                    atkButton.setEnabled(false);
                    defButton.setEnabled(false);
                } catch (IOException | InterruptedException ex) {
                    appendLog("Error iniciando nueva ronda: " + ex.getMessage());
                }
            }
        });
        panelCard1.setVisible(false);
        panelCard2.setVisible(false);
        panelCard3.setVisible(false);
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                logTextArea.setText("");
                appendLog("Cargando partida, porfavor espere!.");
            }
        });
    }

    private void initCards() throws IOException, InterruptedException {
        controller.startNewRound();
        playerCards = controller.getPlayerCards();
        botCards = controller.getBotCards();
        setCardValues();
        scorePlayer.setText(String.valueOf(controller.getPlayerScore()));
        botScore.setText(String.valueOf(controller.getBotScore()));
        selectedPlayerCard = -1;
    }

    private void setCardValues() throws MalformedURLException {
        setCardsImages(imageCard1, playerCards.get(0).getImageUrl());
        setCardsImages(imageCard2, playerCards.get(1).getImageUrl());
        setCardsImages(imageCard3, playerCards.get(2).getImageUrl());
        nameCard1.setText(playerCards.get(0).getName());
        nameCard2.setText(playerCards.get(1).getName());
        nameCard3.setText(playerCards.get(2).getName());
        atkCard1.setText("ATK: " + playerCards.get(0).getAtk());
        atkCard2.setText("ATK: " + playerCards.get(1).getAtk());
        atkCard3.setText("ATK: " + playerCards.get(2).getAtk());
        defCard1.setText("DEF: " + playerCards.get(0).getDef());
        defCard2.setText("DEF: " + playerCards.get(1).getDef());
        defCard3.setText("DEF: " + playerCards.get(2).getDef());
    }

    private void setBotCardValues(int choosedCard) throws MalformedURLException {
        setCardsImages(image_card_bot, botCards.get(choosedCard).getImageUrl());
        name_card_bot.setText(botCards.get(choosedCard).getName());
        atk_card_bot.setText("ATK: " + botCards.get(choosedCard).getAtk());
        def_card_bot.setText("DEF: " + botCards.get(choosedCard).getDef());
    }


    private void setCardsImages(JLabel imageCard, String imageUrl) throws MalformedURLException {
        java.net.URL urlImagen = new java.net.URL(imageUrl);
        ImageIcon icon = new ImageIcon(urlImagen);
        Image image = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // ajusta tamaño a tu gusto
        imageCard.setIcon(new ImageIcon(image));
    }

    private void setScroller() {
        if (logTextArea == null) {
            logTextArea = new JTextArea();
        }
        logTextArea.setLineWrap(true);
        logTextArea.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(
                logTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scroll.setPreferredSize(new Dimension(400, 50));

        if (logPanel != null) {
            logPanel.setLayout(new BorderLayout());
            logPanel.removeAll();
            logPanel.add(scroll, BorderLayout.CENTER);
            logPanel.revalidate();
            logPanel.repaint();
        }
    }

    private void selectPlayerCard(int index) {
        selectedPlayerCard = index;
        panelCard1.setBorder(index == 0 ? BorderFactory.createLineBorder(Color.BLUE, 4) : null);
        panelCard2.setBorder(index == 1 ? BorderFactory.createLineBorder(Color.BLUE, 4) : null);
        panelCard3.setBorder(index == 2 ? BorderFactory.createLineBorder(Color.BLUE, 4) : null);
        appendLog("Carta del jugador seleccionada: " + (index + 1));
        atkButton.setEnabled(true);
        defButton.setEnabled(true);
    }

    private void playSelectedCard(String stat) {
        if (selectedPlayerCard < 0) {
            appendLog("Selecciona primero una carta del jugador.");
            return;
        }
        String result = controller.playRound(selectedPlayerCard, stat);
        appendLog(result);

        int botChoice = controller.getLastBotChoice();
        try {
            setBotCardValues(botChoice);
        } catch (MalformedURLException e) {
            appendLog("Error cargando imagen del bot: " + e.getMessage());
        }

        scorePlayer.setText(String.valueOf(controller.getPlayerScore()));
        botScore.setText(String.valueOf(controller.getBotScore()));
        if (!result.contains("Empate.") && !result.contains(" Alguien debe atacar.")) {
            if (selectedPlayerCard == 0) {
                hideCardAfterWithTimer(panelCard1, 2000);
            }
            if (selectedPlayerCard == 1) {
                hideCardAfterWithTimer(panelCard2, 2000);
            }
            if (selectedPlayerCard == 2) {
                hideCardAfterWithTimer(panelCard3, 2000);
            }
        }
        atkButton.setEnabled(false);
        defButton.setEnabled(false);
        selectedPlayerCard = -1;
        panelCard1.setBorder(null);
        panelCard2.setBorder(null);
        panelCard3.setBorder(null);

        if(result.contains("El jugador gana la partida!") || result.contains("El bot gana la partida!")) {
            JOptionPane.showMessageDialog(null,result.contains("El jugador gana la partida!") ? " El jugador gana la partida!" : " El bot gana la partida!");
            panelCard1.setVisible(false);
            panelCard2.setVisible(false);
            panelCard3.setVisible(false);
        }
    }

    private void appendLog(String message) {
        if (logTextArea != null) {
            logTextArea.append(message + "\n");
        }
    }

    private void hideCardAfterWithTimer(JPanel cardPanel, int millis) {
        Timer timer = new Timer(millis, e -> cardPanel.setVisible(false));
        timer.setRepeats(false);
        timer.start();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        JFrame frame = new JFrame("YuGiOh Duel");
        frame.setContentPane(new Init().background);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1100, 700);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }
}
