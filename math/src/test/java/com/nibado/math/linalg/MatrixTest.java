package com.nibado.math.linalg;

import org.junit.Test;

import static com.nibado.math.linalg.Matrix.dotProduct;
import static com.nibado.math.linalg.Matrix.identity;
import static com.nibado.math.linalg.Matrix.rotate2d;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class MatrixTest {
    private static final Matrix M1_4 = new Matrix(1.0, 2.0, 3.0, 4.0);
    private static final Matrix ROT_90 = new Matrix(0.0, -1.0, 1.0, 0.0);
    private static final Matrix ROT_180 = new Matrix(-1.0, 0.0, 0.0, -1.0);
    private static final Matrix ROT_270 = ROT_90.negative();
    private static final Matrix ROT_360 = ROT_180.negative();

    @Test
    public void testGet() throws Exception {
        for(int i = 1;i <= 5; i++) {
            double[] values = new double[i * i];
            for(int j = 0;j < values.length;j++) {
                values[j] = j + 1;
            }
            Matrix m = new Matrix(values);
            for(int j = 0;j < values.length;j++) {
                int row = j / i;
                int col = j % i;

                assertThat(m.get(row,col)).isEqualTo(j + 1);
            }
        }
    }

    @Test
    public void testToString() throws Exception {
        assertThat(M1_4.toString()).isEqualTo("[1.0,2.0],[3.0,4.0]");
    }

    @Test
    public void testEquals() {
        Matrix m2 = new Matrix(1.0, 2.0, 3.0, 4.0);
        Matrix m3 = new Matrix(1, 1.0, 2.0, 3.0, 4.0);
        Matrix m4 = new Matrix(1.0);

        assertThat(M1_4.equals(m2)).isTrue();
        assertThat(m2.equals(M1_4)).isTrue();
        assertThat(M1_4.equals(M1_4)).isTrue();
        assertThat(M1_4.equals(null)).isFalse();
        assertThat(M1_4.equals("bla")).isFalse();
        assertThat(M1_4.equals(m3)).isFalse();
        assertThat(M1_4.equals(m4)).isFalse();
    }

    @Test
    public void testMultiply() {
        Matrix m1 = new Matrix(2, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0);
        Matrix m2 = new Matrix(3, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0);
        Matrix r = new Matrix(58.0, 64.0, 139.0, 154.0);

        assertThat(dotProduct(m1, 0, m2, 0)).isEqualTo(r.get(0, 0));
        assertThat(dotProduct(m1, 0, m2, 1)).isEqualTo(r.get(0, 1));
        assertThat(dotProduct(m1, 1, m2, 0)).isEqualTo(r.get(1, 0));
        assertThat(dotProduct(m1, 1, m2, 1)).isEqualTo(r.get(1, 1));

        assertThat(m1.multiply(m2)).isEqualTo(r);

        assertThat(r.multiply(identity(r.rows))).isEqualTo(r);
    }

    @Test
    public void testMultiplyScalar() {
        assertThat(M1_4.multiply(2.0)).isEqualTo(new Matrix(2.0, 4.0, 6.0, 8.0));
    }

    @Test
    public void testAddSubstract() {
        Matrix one = new Matrix(1.0, 1.0, 1.0, 1.0);
        Matrix m2 = new Matrix(2.0, 3.0, 4.0, 5.0);
        Matrix m3 = new Matrix(0.0, 1.0, 2.0, 3.0);

        assertThat(M1_4.add(one)).isEqualTo(m2);
        assertThat(M1_4.subtract(one)).isEqualTo(m3);
    }

    @Test
    public void testNegative() {
        assertThat(M1_4.negative()).isEqualTo(new Matrix(-1.0, -2.0, -3.0, -4.0));
    }

    @Test
    public void testRotate2d() {
        assertNear(rotate2d(Math.PI / 2.0), ROT_90, 6);
        assertNear(rotate2d(Math.PI), ROT_180, 6);
        assertNear(rotate2d(Math.PI * 1.5), ROT_270, 6);
        assertNear(rotate2d(Math.PI * 2.0), ROT_360, 6);
    }

    private void assertNear(Matrix m1, Matrix m2, int precision) {
        assertThat(m1.columns).isEqualTo(m2.columns);
        assertThat(m1.rows).isEqualTo(m2.rows);

        double delta = 1.0 / Math.pow(10, precision);

        double[] v1 = m1.values();
        double[] v2 = m2.values();

        for(int i = 0;i < v1.length;i++) {
            if(Math.abs(v1[i] - v2[i]) > delta) {
                fail(String.format("Delta too large at position %s: %s vs %s", i, v1[i], v2[i]));
            }
        }
    }


}