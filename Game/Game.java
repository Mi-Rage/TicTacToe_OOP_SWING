package Game;

import javax.swing.*;

public class Game {
    private GameBoard board;   // Ссылка на игровое поле
    private GamePlayer[] gamePlayers = new GamePlayer[2];  // Массив игроков
    private int playerTurn = 0;  // Индекс текущего игрока


    public Game(){
        this.board = new GameBoard(this);
    }

    public void initGame(){
        gamePlayers[0] = new GamePlayer(true, 'X');
        gamePlayers[1] = new GamePlayer(false, 'O');
    }

    /**
     * Метод передачи хода
     */
    void passTurn(){
        if(playerTurn == 0){
            playerTurn = 1;
        } else {
            playerTurn = 0;
        }
    }

    /**
     * Метод переопределения хода. Нужен для реализации правильной очередности ходов после выигрыша игрока.
     */
    void setPlayersTurn(){
        playerTurn = 0;
    }

    /**
     * Получение объекта текущего игрока
     * @return GamePlayer объект игрока
     */
    GamePlayer getCurrentPlayer(){
        return gamePlayers[playerTurn];
    }
    /**
     * Получение объекта следующего игрока
     * @return GamePlayer объект игрока
     */
    GamePlayer getAnotherPlayer(){
        if (playerTurn == 0){
            return gamePlayers[1];
        } else {
            return gamePlayers[0];
        }
    }

    /**
     * Метод показа всплывающих сообщения для пользователя
     * @param messageText - текс тообщений
     */
    void showMessage(String messageText){
        JOptionPane.showMessageDialog(board, messageText); //Вызываем диалоговое окно с привязкой к игровому полю с передаваемым сообщением
    }
}
