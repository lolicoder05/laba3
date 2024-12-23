package bsu.rfe.java.group8.lab3.Chernov.var5;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class GornerTableModel extends AbstractTableModel {

    private Double[] coefficients; // Коэффициенты многочлена
    private Double from;           // Левая граница интервала
    private Double to;             // Правая граница интервала
    private Double step;           // Шаг табулирования

    public GornerTableModel(Double from, Double to, Double step, Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }

    public Double getStep() {
        return step;
    }

    @Override
    public int getColumnCount() {
        // В данной модели три столбца: X, значение многочлена и проверка дробной части
        return 3;
    }

    @Override
    public int getRowCount() {
        return (int) (Math.ceil((to - from) / step)) + 1;
    }

    @Override
    public Object getValueAt(int row, int col) {
        // Вычислить значение X как НАЧАЛО_ОТРЕЗКА + ШАГ * НОМЕР_СТРОКИ
        double x = from + step * row;
        if (col == 0) {
            // Если запрашивается значение 1-го столбца, то это X
            return x;
        } else if (col == 1) {
            // Если запрашивается значение 2-го столбца, то это значение многочлена
            double result = coefficients[0];
            // Вычисление значения многочлена по схеме Горнера
            for (int i = 1; i < coefficients.length; i++) {
                result = result * x + coefficients[i];
            }
            return result;
        } else {
            // Если запрашивается значение 3-го столбца, то это проверка дробной части
            double result = coefficients[0];
            for (int i = 1; i < coefficients.length; i++) {
                result = result * x + coefficients[i];
            }
            double fractionalPart = result - Math.floor(result);
            double sqrt = Math.sqrt(fractionalPart * 100000000);
            return sqrt == Math.round(sqrt);
        }
    }

    @Override
    public String getColumnName(int col) {
        switch (col) {
            case 0:
                // Название 1-го столбца
                return "Значение X";
            case 1:
                // Название 2-го столбца
                return "Значение многочлена";
            default:
                // Название 3-го столбца
                return "Дробная часть является квадратом?";
        }
    }

    @Override
    public Class<?> getColumnClass(int col) {
        // В 1-ом и 2-ом столбцах находятся значения типа Double, в 3-ем - Boolean
        return col == 2 ? Boolean.class : Double.class;
    }
}
