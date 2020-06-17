package scs.mpp.exam.client.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import scs.mpp.exam.entites.Player;
import scs.mpp.exam.entites.Round;
import scs.mpp.exam.services.Observer;
import scs.mpp.exam.services.Services;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

public class GameController extends UnicastRemoteObject implements Observer, Serializable {

    private Services services;
    private Player player;

    @FXML
    private Button gameButton;

    @FXML
    private Pane quizPane;

    @FXML
    private Label currentLetterLabek;

    @FXML
    private TextField countryTextFiled;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField seaTextField;

    @FXML
    private Button sendResponseButton;

    public GameController() throws RemoteException {
    }

    @FXML
    public void initialize() {
        quizPane.setVisible(false);
        gameButton.setDisable(true);
    }

    @FXML
    void gameButtonOnAction(ActionEvent event) {
        gameButton.setVisible(false);
        quizPane.setVisible(true);
    }

    @FXML
    void sendResponseOnAction(ActionEvent event) {
        Round round = new Round();
        round.setAnswer1(countryTextFiled.getText());
        round.setAnswer2(cityTextField.getText());
        round.setAnswer3(seaTextField.getText());
        round.setPlayerName(player.getName());

        sendResponseButton.setDisable(true);
        services.sendQuizResponse(round);
    }

    @Override
    public void startGame(String letter) throws RemoteException {
        Platform.runLater(() -> {
            gameButton.setDisable(false);
            currentLetterLabek.setText(letter);
        });
    }

    @Override
    public void nextRound(Map<String, Integer> points, String letter) throws RemoteException {
        System.out.println("acii");
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            String data = "";
            for (String x : points.keySet()) {
                data += "Player: " + x + " with points " + points.get(x) + "\n";
            }
            alert.setContentText("Round finished with points:\n" + data);
            alert.showAndWait();
            currentLetterLabek.setText(letter);
            nextRound();
        });
    }

    @Override
    public void top(List<String> top) throws RemoteException {
        System.out.println("top");
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            String data = "";
            for (int i = 0; i < top.size(); i += 2) {
                data += "Player: " + top.get(i) + " with points " + top.get(i + 1) + "\n";
            }
            alert.setContentText("Game finished with points:\n" + data);
            alert.showAndWait();
        });

    }

    public void setServices(Services services, Player player) {
        this.services = services;
        this.player = player;
    }

    private void nextRound() {
        cityTextField.clear();
        countryTextFiled.clear();
        seaTextField.clear();
        sendResponseButton.setDisable(false);

    }
}
