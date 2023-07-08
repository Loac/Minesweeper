package joac.minesweeper.game;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private final int width;

    private final int height;

    private final List<Cell> cells = new ArrayList<>();

    public Field(int width, int height) {
        this.width = width;
        this.height = height;

        fillCells();
    }

    /**
     * Установить заданное количество мин, но не больше чем количество клеток поля.
     */
    public void plantMines(int count) {
        if (count > cells.size())
            count = cells.size();
        int planted = 0;
        while (planted < count) {
            int position = (int) (cells.size() * Math.random());
            if (plant(position))
                planted++;
        }
    }

    /**
     * Установить мину по координатам. Вернет `false`, если клетка не найдена,
     * либо в ней уже установлена мина.
     */
    public boolean plant(int x, int y) {
        return plant(position(x, y));
    }

    public boolean plant(int position) {
        Cell cell = get(position);
        if (cell == null || cell.getDanger() == Cell.MINE) {
            return false;
        } else {
            cell.setDanger(Cell.MINE);
            return true;
        }
    }

    /**
     * Получить клетку по координатам. Вернет `null`, если координаты указывают на область вне поля.
     */
    public Cell get(int x, int y) {
        if (outField(x, y))
            return null;

        return cells.get(position(x, y));
    }

    public Cell get(int position) {
        if (outField(position))
            return null;

        return cells.get(position);
    }

    /**
     * Получить клетку соседнюю клетку по координатам.
     * Значение (0,0) вернет слева верхнюю клетку.
     */
    public Cell nearby(Cell mine, int x, int y) {
        return get(mine.getX() + x - 1, mine.getY() + y - 1);
    }

    /**
     * Заполнить массив клеток объектами Cell.
     */
    public void fillCells() {
        for (int i = 0; i < cells.size(); i++) {
            cells.add(new Cell(i % width, i / width, Cell.FREE));
        }
    }

    /**
     * Рассчитать значение всех клеток поля.
     */
    public void calcCells() {
        for (int i = 0; i < cells.size(); i++) {
            Cell cell = get(i);
            if (cell.isMine())
                calcCell(cell);
        }
    }

    /**
     * Пересчитать значения соседних клеток.
     * Цикл обойдет все клетки вокруг указанной. Если соседняя клетка
     * не является миной, то уровень ее опасности увеличится на 1.
     */
    public void calcCell(Cell mine) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                Cell cell = nearby(mine, x, y);
                if (cell == null)
                    continue;

                cell.incDanger();
            }
        }
    }

    /**
     * Вывести данные поля в консоль.
     */
    public void printCells() {
        for (int y = 0; y < height; y++) {
            StringBuilder row = new StringBuilder();
            for (int x = 0; x < width; x++) {
                row.append(get(x, y).getDanger()).append(" ");
            }
            System.out.println(row);
            row.delete(0, row.length());
        }
    }

    /**
     * Преобразовать координаты в позицию клетки.
     */
    public int position(int x, int y) {
        return width * y + x;
    }

    /**
     * Проверка координат, попадают ли они в область поля.
     */
    public boolean inField(int x, int y) {
        return !outField(x, y);
    }

    public boolean outField(int x, int y) {
        return x < 0 || y < 0 || x > width -1 || y > height -1;
    }

    public boolean inField(int position) {
        return !outField(position);
    }

    public boolean outField(int position) {
        return position < 0 || position > cells.size() -1;
    }
}
