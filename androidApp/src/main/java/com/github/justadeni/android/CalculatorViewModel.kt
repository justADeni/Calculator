package com.github.justadeni.android

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class CalculatorViewModel: ViewModel() {

    var state by mutableStateOf(CalculatorState())
        private set

    companion object {
        private val operators = OperationType.entries.map { it.symbol }
        private fun Char.isOperator() = operators.contains(this)
        private fun Char.isDot() = this == '.'
        private fun String.allZero() = this.isNotBlank() && this.all { it == '0' }
    }

    fun onAction(action: CalculatorAction) {
        when(action) {
            CalculatorAction.Calculate -> {
                if (state.string.isNotBlank() && !state.string.last().isDot() && !state.string.last().isOperator())
                    state = state.evaluate()
            }
            CalculatorAction.Clear -> state.clear()
            CalculatorAction.Decimal -> {
                if (state.string.isBlank()) {
                    state.add('0','.')
                } else if (!state.string.last().isDot() && !state.string.last().isOperator()) {
                    state.add('.')
                }
            }
            CalculatorAction.Delete -> {
                if (state.string.isNotBlank())
                    state.pop()
            }
            is CalculatorAction.Number -> {
                if (state.string.length < 6) {
                    if (action.number != 0) {
                        if (state.string.length == 1 && state.string[0] == '0')
                            state.pop()

                        state.add(action.number.digitToChar())
                    } else if (!state.string.allZero()) {
                        state.add('0')
                    }
                }
            }
            is CalculatorAction.Operation -> {
                if (state.string.length < 6) {
                    if (state.string.isNotBlank()) {
                        if (state.string.last().isDot() || state.string.last().isOperator())
                            state.pop()

                        state.add(action.operation.symbol)
                    }
                }
            }
        }

        // Requires this workaround because it won't change state otherwise
        val temp = state.string
        state = state.copy(null.toString())
        state = state.copy(temp)
    }

}