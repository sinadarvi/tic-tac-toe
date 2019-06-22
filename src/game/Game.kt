package game

import board.board
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*

class Game : RComponent<RProps, Game.State>() {

    init {
        state.apply {
            history = arrayOf(
                    squares = arrayOfNulls(9)
            )
            stepNumber = 0
            xIsNext = true
        }
    }

    private fun handleClick(i: Int) {
        val history = state.history.sliceArray(0..state.stepNumber)
        val current = history.last()
        val squares = current.squares.copyOf()
        if (calculateWinner(squares) != null || squares[i] != null)
            return
        squares[i] = if (state.xIsNext) "X" else "O"
        setState {
            this.history = history + Squares(squares)
            stepNumber = history.size
            xIsNext = !state.xIsNext
        }
    }

    private fun calculateWinner(squares: Array<String?>): String? {
        val lines = arrayOf(
                arrayOf(0, 1, 2),
                arrayOf(3, 4, 5),
                arrayOf(6, 7, 8),
                arrayOf(0, 3, 6),
                arrayOf(1, 4, 7),
                arrayOf(2, 5, 8),
                arrayOf(0, 4, 8),
                arrayOf(2, 4, 6))
        for (line in lines) {
            val (a, b, c) = line
            if (squares[a] != null &&
                    squares[a] == squares[b] &&
                    squares[a] == squares[c])
                return squares[a]
        }
        return null
    }


    private fun jumpTo(step: Int) {
        setState {
            stepNumber = step
            xIsNext = (step % 2) == 0
        }
    }

    override fun RBuilder.render() {
        val history = state.history
        val current = history[state.stepNumber]
        val winner = calculateWinner(current.squares)

        fun RBuilder.moves() =
                state.history.mapIndexed { step, move ->
                    val desc =
                            if (step != 0)
                                "Go to move #$step"
                            else
                                "Go to game start"
                    li {
                        key = step.toString()
                        button {
                            attrs.onClickFunction = { jumpTo(step) }
                            +desc
                        }
                    }
                }

        val status =
                if (!winner.isNullOrEmpty())
                    "Winner: $winner"
                else
                    "Next player: ${if (state.xIsNext) "X" else "O"}"

        div(classes = "game") {
            div(classes = "game-board") {
                board(current.squares) {
                    handleClick(it)
                }
            }
            div(classes = "game-info") {
                div { +status }
                ol { moves() }
            }
        }
    }

    interface State : RState {
        var history: Array<Squares>
        var xIsNext: Boolean
        var stepNumber: Int
    }

    class Squares(var squares: Array<String?>)

    private fun arrayOf(squares: Array<String?>): Array<Squares> {
        return arrayOf(Squares(squares))
    }
}

fun RBuilder.game() = child(Game::class) {}