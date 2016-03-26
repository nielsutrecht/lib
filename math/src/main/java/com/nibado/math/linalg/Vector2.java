package com.nibado.math.linalg;

import java.util.Locale;

public class Vector2 {
    public final double x;
    public final double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 multiply(Matrix matrix) {
        Matrix rot = matrix.multiply(new Matrix(2, x, y));

        return new Vector2(rot.get(0, 0), rot.get(0, 1));
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "[%s, %s]", x, y);
    }
}
