package Game;

import javax.swing.*;

public class GameButton extends JButton {
    private int buttonIndex;  // Номер кнопки
    private GameBoard board;  // Ссылка на игровое поле

    public GameButton(int gameButtonIndex, GameBoard currentGameBoard){
        buttonIndex = gameButtonIndex;
        board = currentGameBoard;

        int rowNum = buttonIndex / GameBoard.dimension;    // Номер ряда, или номер строки
        int cellNum = buttonIndex % GameBoard.dimension;   // Номер столбца

        setSize(GameBoard.cellSize - 5, GameBoard.cellSize - 5);
        addActionListener(new GameActionListener(rowNum, cellNum, this));
    }

    public GameBoard getBoard(){
        return board;               // Возвращает ссылку на игровую доску для кнопки
    }

}
