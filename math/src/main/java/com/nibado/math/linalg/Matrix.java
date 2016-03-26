package com.nibado.math.linalg;

import java.util.Arrays;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.util.Arrays.copyOf;

public class Matrix {
    private final double[] values;
    public final int rows;
    public final int columns;

    public Matrix(double... values) {
        this(assertSquare(values.length) , values);
    }

    public Matrix(Matrix other) {
        this.values = copyOf(other.values, other.values.length);
        this.columns = other.columns;
        this.rows = other.rows;
    }

    public Matrix(int rows, double... values) {
        this.rows = rows;
        this.columns = values.length / rows;
        this.values = values;
    }

    private static int assertSquare(int length) {
        int sq = (int)sqrt(length);
        if(sq * sq != length) {
            throw new IllegalArgumentException("Assuming square matrix");
        }

        return sq;
    }

    public double get(int row, int col) {
        return values[toIndex(row, col)];
    }

    public double[] values() {
        return copyOf(values, values.length);
    }

    private int toIndex(int row, int col) {
        return col + row * columns;
    }

    public Matrix add(Matrix other) {
        return add(this, other);
    }

    public static Matrix add(Matrix m1, Matrix m2) {
        assertSameSize(m1, m2);
        Matrix m = new Matrix(m1);
        for(int i = 0;i < m.values.length;i++) {
            m.values[i] += m2.values[i];
        }

        return m;
    }

    public Matrix subtract(Matrix other) {
        return subtract(this, other);
    }

    public static Matrix subtract(Matrix m1, Matrix m2) {
        assertSameSize(m1, m2);
        Matrix m = new Matrix(m1);
        for(int i = 0; i < m.values.length; i++) {
            m.values[i] -= m2.values[i];
        }

        return m;
    }

    public Matrix negative() {
        return negative(this);
    }

    public static Matrix negative(Matrix m) {
        return m.multiply(-1.0);
    }

    public Matrix multiply(double scalar) {
        return multiply(this, scalar);
    }

    public static Matrix multiply(Matrix m, double scalar) {
        Matrix newM = new Matrix(m);
        for(int i = 0;i < m.values.length;i++) {
            newM.values[i] *= scalar;
        }

        return newM;
    }

    public Matrix multiply(Matrix other) {
        return multiply(this, other);
    }

    public static Matrix multiply(Matrix m1, Matrix m2) {
        if(m1.columns != m2.rows) {
            throw new IllegalArgumentException("Coumns of M1 must match rows in M2");
        }

        int newCol = m2.columns;
        int newRow = m1.rows;

        double[] values = new double[newRow * newCol];

        for(int c = 0;c < newCol;c++) {
            for(int r = 0;r < newRow;r++) {
                values[c + r * newCol] = dotProduct(m1, r, m2, c);
            }
        }

        return new Matrix(newRow, values);
    }

    public static Matrix identity(int rows) {
        double[] values = new double[rows * rows];
        for(int i = 0;i < rows;i++) {
            values[i + i * rows] = 1.0;
        }

        return new Matrix(rows, values);
    }

    public static double dotProduct(Matrix m1, int row, Matrix m2, int column) {
        double dp = 0.0;
        for(int i = 0;i < m1.columns;i++) {
            dp += m1.get(row, i) * m2.get(i, column);
        }

        return dp;
    }

    private static void assertSameSize(Matrix m1, Matrix m2) {
        if(m1.rows != m2.rows || m1.columns != m2.columns) {
            throw new IllegalArgumentException("Matrices don't match in size");
        }
    }

    public static Matrix rotate2d(double radians) {
        return new Matrix(cos(radians), -sin(radians), sin(radians), cos(radians));
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Matrix)) {
            return false;
        }
        if(o == this) {
            return true;
        }
        Matrix m = (Matrix)o;
        return m.rows == rows && Arrays.equals(values, m.values);
    }

    @Override
    public int hashCode() {
        int result = 31 * rows;
        long temp;
        for(int i = 0;i < values.length;i++) {
            temp = Double.doubleToLongBits(values[i]);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int y = 0;y < rows;y++) {
            if(y > 0) {
                builder.append(",");
            }
            builder.append("[");
            for (int x = 0; x < columns; x++) {
                if(x > 0) {
                    builder.append(",");
                }
                builder.append(get(y, x));
            }
            builder.append("]");
        }

        return builder.toString();
    }
}
