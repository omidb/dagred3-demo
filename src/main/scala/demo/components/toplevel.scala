package demo.components

/**
 * Created by Omid on 5/19/2015.
 */


import japgolly.scalajs.react._
import org.w3c.dom.html.HTMLInputElement

//import japgolly.scalajs.react.vdom.all._
import japgolly.scalajs.react.vdom.all.{div,className,p,input,onMouseMove,onChange,ref}
import fr.iscpif.scaladget.d3._
import panel._
import japgolly.scalajs.react.vdom.svg.all._


object toplevel {

  case class State(color: String, arrowState: ArrowState)
  case class ArrowState(p1:(Double,Double), p2:(Double,Double), readyMove:Boolean)

  class Backend($: BackendScope[Unit, State]) {
    val ref = Ref[org.scalajs.dom.html.Input]("input")
    val svgref = Ref("svg")


    def onChange(): Unit = {
      changeColor()
    }

    def getPos(x:Double, y:Double) = {
      val r = svgref($).get.getDOMNode().asInstanceOf[TopNode].getBoundingClientRect()
      (x - r.left, y - r.top)
    }

    def onMouseDown(e:ReactMouseEvent): Unit = {
      $.modState(s => s.copy(arrowState = s.arrowState.copy(readyMove = true)))
//      $.modState(s => s.copy(arrowState = s.arrowState.copy(p1 = getPos(e.clientX,e.clientY))))
    }

    def onMouseUp(e:ReactMouseEvent): Unit = {
      $.modState(s => s.copy(arrowState = s.arrowState.copy(readyMove = false)))
    }

    def onMouseMove(e:ReactMouseEvent): Unit = {
//      val pageOffset = $.getDOMNode().getBoundingClientRect()
//      val r2 = svgref($).get.getDOMNode().asInstanceOf[TopNode].getBoundingClientRect()
//      println("-----")
//      println("ref" + r2.left + "--" + r2.top)
//      println(pageOffset.left + "--" + pageOffset.top)
//
//      println("client", e.clientX+ "-"+e.clientY)
      if($.state.arrowState.readyMove)
        $.modState(s => s.copy(arrowState = s.arrowState.copy(p1 = getPos(e.clientX,e.clientY))))
      //println("name", $.getDOMNode().className)


//      if($.state.arrowState.readyMove)
//        $.modState(s => s.copy(arrowState = s.arrowState.copy(p1 = (e.clientX,e.clientY))))
    }

//    def onMouseMove2(e: SyntheticMouseEvent[org.scalajs.dom.html.Div]): Unit ={
//      e.mo
//    }

    def onClick(): Unit = {
      changeColor()
      ref($).get.getDOMNode().value += "hi"
    }


    def changeColor(): Unit = {
      val current = $.state.color
      val newColor = $.state.color match {
        case "blue" => "red"
        case _ => "blue"
      }
      $.modState(_.copy(color = newColor))
    }

  }


  val TopLevel = ReactComponentB[Unit]("Top level component")
    .initialState((State("blue",ArrowState((10, 10), (100, 100),false))))
    .backend(new Backend(_))
    .render((_, state, backend) => {

    div(id:="topsvg",className := "container",
      svg(ref:=backend.svgref, width := 800, height := 1000, pointerEvents := "none",
       // onMouseMove ==> backend.onMouseMove,
        Arrow.arrow(
          Arrow.Props("red", state.arrowState.p1, state.arrowState.p2,
            backend.onMouseDown _,backend.onMouseUp _,backend.onMouseMove _))
       // mcircle.circ(mcircle.Props(state.color, backend.onClick _))
      ),
      input(ref := backend.ref, onChange --> backend.onChange)

    )
  }).build
}