package demo.components

/**
 * Created by Omid on 5/19/2015.
 */

import japgolly.scalajs.react._
//import japgolly.scalajs.react.vdom.all._
import japgolly.scalajs.react.vdom.all.{div,className,p,onClick,onKeyPress,select}
import fr.iscpif.scaladget.d3._
import panel._
import japgolly.scalajs.react.vdom.svg.all._


object mcircle {

  case class State(current: String)


  case class Props(color: String, onClick: () => _)

  //  case class Props(color: String, onClick: Unit => Unit)

  class Backend($: BackendScope[Props, _]) {
    def onClick() = {
      println("clicked!")
      $.props.onClick()
    }
  }

  val circ = ReactComponentB[Props]("CircleComponent")
    .stateless
    .backend(new Backend(_))
    .render((props, _, b) =>
    g(
      circle(
        onClick --> b.onClick,
        cx := "25", cy := "25", r := "25", fill := props.color
      )
    )
    )
    .build
}
