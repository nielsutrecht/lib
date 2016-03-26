package com.nibado.math.linalg;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Vector2Test {

    @Test
    public void testMultiply() throws Exception {
        Vector2 v = new Vector2(0, -10).multiply(Matrix.rotate2d(Math.PI / 2.0));

        assertThat(v.x).isBetween(9.9999, 10.0001);
        assertThat(v.y).isBetween(-0.00001, 0.0001);
    }
}