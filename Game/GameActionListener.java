package Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameActionListener implements ActionListener {
    private int row;
    private int cell;
    private GameButton button;

    public GameActionListener(int row, int cell, GameButton gButton) {
        this.row = row;
        this.cell = cell;
        this.button = gButton;  // Передаем номер ряда, номер столбца и ссылку на кнопку, к которой привязыаем наш GameActionListener
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameBoard board = button.getBoard();

        if (board.isTurnable(row, cell)) {
            updateByPlayersData(board);

            if (board.isFull()) {
                board.getGame().showMessage("Ничья");
                board.emptyField();
            } else {
                if (!board.isEmpty()) {     // Комп сможет сходить только если уже кто то ходил
                    updateByAiData(board);
                }
            }
        } else {
            board.getGame().showMessage("Некорректный ход");
        }

    }

    /**
     * Ход человека
     *
     * @param board GameBoard() - ссылка на игровое поле
     */
    private void updateByPlayersData(GameBoard board) {
        // Обновляем матрицу игры
        board.updateGameField(row, cell);
        // Обновляем содержимое кнопки
        button.setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));
        // После хода проверим состояние победы
        if (board.checkWin()) {
            button.getBoard().getGame().showMessage("Вы выиграли!");
            board.emptyField();
        } else {
            board.getGame().passTurn();
        }
    }

    /**
     * Ход умного комппьютера
     *
     * @param board GameBoard() - ссылка на игровое поле
     */
    private void updateByAiData(GameBoard board) {
        // Все поиски ходов умного компа реализуются в методе aiTurn() для удобства дальнейшей доработки

        board.getButton(aiTurn(board)).setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));

        // Проверим победу
        if (board.checkWin()) {
            button.getBoard().getGame().showMessage("Компьютер выиграл!");
            board.emptyField();
        } else {
            board.getGame().passTurn();
        }
    }

    /**
     * Метод поиска хода умного компа
     * @param board - состояние игрового поля
     * @return индекс кнопки, куда комп решил сходить
     */
    private int aiTurn(GameBoard board){
        boolean foundWin = false; // Флаг нахождения любой выигрышной комбинации
        int x = -1;               // Эти переменные пригодятся для умного хода компа
        int y = -1;


        for(int i = 0; i < (GameBoard.dimension * GameBoard.dimension); i++){ // Обходим массив игрового поля

            int tmpX = i / GameBoard.dimension;
            int tmpY = i % GameBoard.dimension;

            if(board.isTurnable(tmpX, tmpY)){ // Если ячейка пустая и в неё можно сходить
                board.aiCell(tmpX, tmpY);     // Подставим в неё символ хода компа и
                if (board.checkWinAi()){      // Проверим есть ли выигрыш
                    foundWin = true;          // Если выигрыш есть - поднимем флаг находжения и запомним координаты
                    x = tmpX;
                    y = tmpY;
                    break;                    // Больше ничего не ищем - уже нашли победу
                }
                board.playerCell(tmpX, tmpY); // Если победа в той клетке не нашлась - подставим символ противника
                if (board.checkWinPlayer()){  // Проверим выиграет ли в этой ячейке противник
                    foundWin = true;          // Если противник может выиграть - поднимем флаг и запомним координаты
                    x = tmpX;
                    y = tmpY;
                    break;                     // Больше ничего не ищем - спасаем ситуацию
                }
                board.emptyCell(tmpX, tmpY);  // Ничего не нашли? Обнулим до этого пустую ячейку
            }
        }
        if(!foundWin){ // Если во всем поле флаг не поднялся - значит ищем рандомные координаты
            // Генерация координат хода компьютера
            Random rnd = new Random();

            do {
                x = rnd.nextInt(GameBoard.dimension);
                y = rnd.nextInt(GameBoard.dimension);
            } while (!board.isTurnable(x, y));
        }
        // С полученными координатами фиксируем ход
        // Обновим матрицу игры
        board.updateGameField(x, y);
        // Возвращаем индекс кнопки с выбранным ходом
        return GameBoard.dimension * x + y;

    }

}
