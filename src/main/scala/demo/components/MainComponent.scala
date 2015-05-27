package demo.components


/**
 * Created by Omid on 5/25/2015.
 */


import demo._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.all.{div,className,onMouseMove,onMouseUp,ref}
import japgolly.scalajs.react.vdom.svg.all._

import scala.scalajs.js



case class State(graphStateInfo: GraphStateInfo)

case class GraphStateInfo(g:Dagre.Graph, sGraph: SGraph)


object MainComponent {

  def somefunction() = {
    val nodes = Array(
      SNode("kspacey",    "Kevin Spacey", List(SNodeAlternative("a1", "note1"))),
      SNode("swilliams",  "Saul Williams", List(SNodeAlternative("a1", "note1"))),
      SNode("bpitt",      "Brad Pitt", List(SNodeAlternative("a1", "note1"))),
      SNode("hford",      "Harrison Ford", List(SNodeAlternative("a1", "note1"))),
      SNode("lwilson",    "Luke Wilson", List(SNodeAlternative("a1", "note1"))),
      SNode("kbacon",     "Kevin Bacon", List(SNodeAlternative("a1", "note1")))
    )

    val edges = List(
      SEdge(ReactUtil.getEdgeID(nodes(0).id,nodes(1).id),"sm label", nodes(0), nodes(1)),
      SEdge(ReactUtil.getEdgeID(nodes(1).id,nodes(5).id),"sm label", nodes(1), nodes(5)),
      SEdge(ReactUtil.getEdgeID(nodes(2).id,nodes(5).id),"sm label", nodes(2), nodes(5)),
      SEdge(ReactUtil.getEdgeID(nodes(3).id,nodes(4).id),"sm label", nodes(3), nodes(4)),
      SEdge(ReactUtil.getEdgeID(nodes(4).id,nodes(5).id),"sm label", nodes(4), nodes(5))
    )

    SGraph(nodes.toList,edges)
  }


  class Backend($: BackendScope[Unit, State]) {
//    val dvRef = Ref[org.scalajs.dom.html.Div]("dvRef")

    def updateGraph(oldEdgeId:String, newEdge:(String,String)):Unit = {

      val newSGr = SGraph.modifyEdge(
        $.state.graphStateInfo.sGraph,
        $.state.graphStateInfo.sGraph.edges.filter(_.id == oldEdgeId).head,
        newEdge._1,
        newEdge._2
      )


      val gsi = GraphStateInfo(SGraph.layoutGraph(SGraph.makeGraph(newSGr)),newSGr)
      $.setState($.state.copy(graphStateInfo = gsi))


//      println("******************")
////      s.edges.map(ne => println(ne.id))
//      println("******************")
//      $.state.graphStateInfo.sGraph.edges.map(ne => println(ne.id))


    }
    def onUp(e:ReactMouseEventI) = {

    }

  }
  val simpleG = somefunction()




  def getFirstState():State = {
    State(GraphStateInfo(SGraph.layoutGraph(SGraph.makeGraph(simpleG)), simpleG))
  }

  val TopLevel = ReactComponentB[Unit]("Top level component")
    .initialState(getFirstState())
    .backend(new Backend(_))
    .render((_, state, backend) => {
   // div(ref:=backend.dvRef, id:="topDiv",className := "container",
//    svg( width := 800, height := 1000,
      //onMouseUp ==> backend.onUp,
     MGraph.mGraph(
       MGraph.GraphProps(
             state.graphStateInfo.g, state.graphStateInfo.sGraph, backend.updateGraph)
        )
    //)
  }).buildU

}
