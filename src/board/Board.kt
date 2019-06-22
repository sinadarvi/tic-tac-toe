package board

import react.*
import react.dom.div
import square.square

class Board : RComponent<Board.Props, RState>() {

    private fun RBuilder.renderSquare(i: Int) {
        square(props.squares[i]) {
            props.onClickFunction(i)
        }
    }

    override fun RBuilder.render() {
        div {
            div(classes = "board-row") {
                renderSquare(0)
                renderSquare(1)
                renderSquare(2)
            }
            div(classes = "board-row") {
                renderSquare(3)
                renderSquare(4)
                renderSquare(5)
            }
            div(classes = "board-row") {
                renderSquare(6)
                renderSquare(7)
                renderSquare(8)
            }
        }
    }

    interface Props : RProps {
        var squares: Array<String?>
        var onClickFunction: (Int) -> Unit
    }
}

fun RBuilder.board(squares: Array<String?>, onClickFunction: (Int) -> Unit) = child(Board::class) {
    attrs.squares = squares
    attrs.onClickFunction = onClickFunction
}