package com.github.justadeni.android

import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.PrimitiveElement
import java.math.RoundingMode
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
        val bd = e.calculate().toString().toBigDecimal()
        bd.setScale(8, RoundingMode.HALF_EVEN)
        string = bd.toPlainString()

        // remove trailing zeroes
        if (string.length >= 3 && string.contains('.'))
            if (string.takeLast(1) == "0")
                string = string.dropLast(1)
            else if (string.takeLast(2) == ".0")
                string = string.dropLast(2)

        return this
    }

}