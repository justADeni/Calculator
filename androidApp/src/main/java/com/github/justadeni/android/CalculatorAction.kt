package com.github.justadeni.android

sealed class CalculatorAction {

    data class Number(val number: Int): CalculatorAction()
    data object Clear: CalculatorAction()
    data object Delete: CalculatorAction()
    data object Decimal: CalculatorAction()
    data object Calculate: CalculatorAction()
    data class Operation(val operation: OperationType): CalculatorAction()

}

enum class OperationType(val symbol: Char) {
    ADD('+'),
    SUBTRACT('-'),
    MULTIPLY('*'),
    DIVIDE('/')
}