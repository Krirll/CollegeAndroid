package com.example.practicandroid7_8

import org.junit.Test

import org.junit.Assert.*

class TestResultOfEquation {
    @Test
    fun result_isCorrect() {
        assertEquals(-1.5, Equation.getResultOfEquation("2", "3"), .000)
        assertEquals(2.0, Equation.getResultOfEquation("0", "30.125"), .000)
        assertEquals(1.0, Equation.getResultOfEquation("0", "-3"), .000)
        assertEquals(4.0, Equation.getResultOfEquation("2d", "3a"), .000)
        assertEquals(4.0, Equation.getResultOfEquation("", ""), .000)
        assertEquals(4.0, Equation.getResultOfEquation("1", ""), .000)
        assertEquals(4.0, Equation.getResultOfEquation("", "2"), .000)
        assertEquals(2.0, Equation.getResultOfEquation("0", "0"), .000)
        assertEquals(0.0, Equation.getResultOfEquation("-1", "0"), .000)
        assertEquals(-0.0, Equation.getResultOfEquation("1", "0"), .000)
    }
}