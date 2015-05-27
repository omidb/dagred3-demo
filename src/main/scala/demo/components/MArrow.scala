package demo.components

/**
 * Created by Omid on 5/23/2015.
 */

import Dagre.{GraphEdge, GraphNode}
import demo.{ReactUtil, RLine, RVector, RPoint}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.all.{onClick,onMouseUp,onMouseDown,id,onMouseOver,onMouseOut}
import japgolly.scalajs.react.vdom.svg.all._
import paths.mid.Bezier

import scala.scalajs.js

object MArrow {

  case class State(startOver:Boolean, endOver:Boolean, startDrag:Boolean, endDraf:Boolean)
  case class DraggingState(id:String, startDraging:Boolean)


  case class Props(id: String,
                   points: js.Array[RPoint]
                   , drgState: DraggingState
                   , onDown: (DraggingState) => _)


  class Backend($: BackendScope[Props, State]) {
    def onClick() = {
      println("clicked!")
      //$.props.onClick()
    }

    def onUp() = {
      println("UP!")
      //      $.props.onClick()
    }

    def onDownStart() = {
      println("Down!")
      $.props.onDown(DraggingState($.props.id, true))
      $.modState(_.copy(startDrag = true))
    }

    def onOverStart() = $.modState(_.copy(startOver = true))

    def onOutStart() = if(!$.state.startDrag) $.modState(_.copy(startOver = false))

    def onOverEnd() = $.modState(_.copy(endOver = true))

    def onOutEnd() = $.modState(_.copy(endOver = false))
  }

  def bezierPath(points: js.Array[RPoint]) = {
    val ps = points.map(p => (p.x, p.y))
    val bez = Bezier(ps)
    val ab = bez.path.points.last
    val arrow = bez.path
      .lineto(ab(0) - 3, ab(1) - 3)
      .moveto(ab(0), ab(1))
      .lineto(ab(0) + 3, ab(1) - 3)
    path(d := arrow.print, stroke := "red", fill := "none")
  }

  val marrow = ReactComponentB[Props]("MArrowComponent")
//    .stateless
    .initialState(State(false,false,false,false))
    .backend(new Backend(_))
    .render((props, state, b) =>
    g(id := props.id,

//      props.points.map(point =>
//        circle(transform := ReactUtil.move(js.Array(point.x, point.y)), fill := "blue", r := 2)
//      ),
      bezierPath(props.points),
      circle(
        onMouseOver --> b.onOverStart,
        onMouseOut --> b.onOutStart,
        onMouseDown --> b.onDownStart,
        stroke := "purple",fill:= "blue", if(state.startOver) opacity := 0.5 else opacity := 0.01,
        transform := ReactUtil.move(js.Array(props.points.head.x, props.points.head.y)),
        r := 5,
        pointerEvents := "all"),
      circle(
        onMouseOver --> b.onOverEnd,
        onMouseOut --> b.onOutEnd,
        stroke := "purple",fill:= "blue", if(state.endOver) opacity := 0.5 else opacity := 0.01,
        transform := ReactUtil.move(js.Array(props.points.last.x, props.points.last.y)),
        r := 5,
        pointerEvents := "all"
      )

    )
    )
    .build
}
