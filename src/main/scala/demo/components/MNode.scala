package demo.components

/**
 * Created by Omid on 5/23/2015.
 */

import Dagre.GraphNode
import demo.{ReactUtil, RVector, RPoint}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.all.{onClick,onMouseUp,id,className}
import japgolly.scalajs.react.vdom.svg.all._
import paths.mid._

import scala.scalajs.js

object MNode {

  case class UpState(id:String, UPed:Boolean)
  case class State(upState: UpState)


  case class Props(id: String, label:String, pos: RPoint, size: RVector
                   /*, onClick: () => _*/, onMsUp: (UpState) => _)



  class Backend($: BackendScope[Props, _]) {
    def onClick() = {
      println("clicked!")
      //      $.props.onClick()
    }

    def onUp(e:ReactMouseEventI):Unit = {
      println("UP here!  " +  $.props.id)
      $.props.onMsUp(UpState($.props.id, true))
    }
  }

  val mnode = ReactComponentB[Props]("MNodeComponent")
    .stateless
    .backend(new Backend(_))
    .render((props, _, b) => {
    val rec = Rectangle(props.pos.y - props.size.y/2,props.pos.y + props.size.y/2,
      props.pos.x - props.size.x/2,props.pos.x + props.size.x/2)
    g(id := props.id,
      onMouseUp ==> b.onUp,
      path(d := rec.path.print, stroke := "gold", fill := "none"),
      text(
        className := "noselect",
        transform := ReactUtil.move(js.Array(rec.centroid(0),rec.centroid(1)+4.5))
        ,textAnchor := "middle"
        , props.label)
    )
  }
    )
    .build
}
