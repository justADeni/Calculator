package com.github.justadeni.android

import org.mariuszgromada.math.mxparser.Expression
import kotlin.math.round


data class CalculatorState(var string: String = "") {

    fun clear(): CalculatorState {
        string = ""
        return this
    }

    fun pop(): CalculatorState {
        string = string.dropLast(1)
        return this
    }

    fun add(vararg char: Char): CalculatorState {
        for(i in char)
            string += i

        return this
    }

    fun evaluate(): CalculatorState {
        val e = Expression(string)
        string = e.calculate().round(3).toString()
        if (string.length >= 3 && string.contains('.') && string.takeLast(2) == ".0")
            string = string.dropLast(2)
        return this
    }

    private fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }

}