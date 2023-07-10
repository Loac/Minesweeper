package joac.minesweeper.game;

import java.util.ArrayList;
import java.util.List;

public class Minefield {

    private final int width;

    private final int height;

    private final int size;

    private final List<Cell> cells = new ArrayList<>();

    public Minefield(int width, int height) {
        this.width = width;
        this.height = height;
        this.size = width * height;
    }

    /**
     * Установить заданное количество мин, но не больше чем количество клеток поля -1.
     * Во внимание принимается одна клетка, с которой начинается игра.
     * Она никогда не должна быть заминирована.
     */
    public void plantMines(int count, Cell startCell) {
        plantMines(count, position(startCell.getX(), startCell.getY()));
    }

    public void plantMines(int count, int exclude) {
        if (count > size -1)
            count = size -1;
        int planted = 0;
        while (planted < count) {
            int position = (int) (size * Math.random());
            if (position == exclude)
                continue;
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
     * Заполнить массив клеток объектами Cell.
     */
    public void fillCells() {
        for (int i = 0; i < size; i++) {
            cells.add(new Cell(i % width, i / width, Cell.CLEAR));
        }
    }

    /**
     * Рассчитать значение всех клеток поля.
     */
    public void calcCells() {
        for (int i = 0; i < size; i++) {
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
    private void calcCell(Cell cell) {
        nearbyPositions(cell).forEach(position -> get(position).incDanger());
    }

    /**
     * Установить или снять отметку с клетки.
     */
    public void switchMarker(Cell cell) {
        cell.switchMarker();
    }

    /**
     * Открыть клетку. Если клетка пустая, открыть соседние клетки.
     */
    public boolean openCell(Cell cell) {
        if (cell.canOpened()) {
            cell.open();

            if (cell.isClear())
                nearbyPositions(cell).forEach(this::openCell);

            return true;
        } else {
            return false;
        }
    }

    public void openCell(int position) {
        Cell cell = get(position);
        if (null == cell)
            return;

        openCell(cell);
    }

    public void openCells() {
        cells.forEach(Cell::open);
    }

    public void markMines() {
        cells.stream()
                .filter(Cell::isMine)
                .forEach(Cell::markFlag);
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
        return inField(x, y) ? width * y + x : -1;
    }

    /**
     * Получить список позиций соседних клеток.
     */
    public List<Integer> nearbyPositions(Cell cell) {
        List<Integer> positions = new ArrayList<>();
        for (int y = -1; y < 2; y++) {
            for (int x = -1; x < 2; x++) {
                if (x == 0 && y == 0)
                    continue;
                int position = position(cell.getX() + x, cell.getY() + y);
                if (inField(position))
                    positions.add(position);
            }
        }

        return positions;
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
        return position < 0 || position > size -1;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSize() {
        return size;
    }
}
