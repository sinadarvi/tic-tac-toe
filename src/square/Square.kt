package square

import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.button

fun RBuilder.square(value: String?, onClickFunction: (Event) -> Unit) =
        button(classes = "square") {
            attrs.onClickFunction = onClickFunction
            +(value ?: "")
        }