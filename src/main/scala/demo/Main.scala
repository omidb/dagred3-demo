//package demo
//
///**
// * Created by Omid on 5/19/2015.
// */
//
//import Dagre.GraphNode
//import demo.components.{MainComponent, MGraph}
//import japgolly.scalajs.react.{React, vdom, TopNode}
//import org.scalajs.dom
//
//import scala.scalajs.js
//import js.annotation.JSExport
//import org.scalajs.dom.{ document, window}
//
//import demo.components.toplevel._
//import Dagre._
//
//import scala.scalajs.js.JSON
//
//object Main extends js.JSApp {
//  def defer(F: => Unit) = window.setTimeout({ () => F }, 0)
//
//
//  def makeGraph = {
//        val g = js.Dynamic
//          .newInstance(dagre.graphlib.Graph.asInstanceOf[js.Dynamic])()
//          .asInstanceOf[Dagre.Graph]
//
//        // Set an object for the graph label
//        g.setGraph(js.Dynamic.literal())
//
//        // Default to assigning a new object as a label for each new edge.
//        g.setDefaultEdgeLabel(() => js.Dynamic.literal())
//
//
//        // Add nodes to the graph. The first argument is the node id. The second is
//        // metadata about the node. In this case we're going to add labels to each of
//        // our nodes.
////        g.setNode("kspacey",    GraphNode("Kevin Spacey",  144, 100 ))
////        g.setNode("swilliams",  GraphNode( "Saul Williams", 160, 100 ))
////        g.setNode("bpitt",      GraphNode( "Brad Pitt",     108, 100 ))
////        g.setNode("hford",      GraphNode( "Harrison Ford", 168, 100 ))
////        g.setNode("lwilson",    GraphNode( "Luke Wilson",   144, 100 ))
////        g.setNode("kbacon",     GraphNode( "Kevin Bacon",   121, 100 ))
//
//        g.setNode("kspacey",    GraphNode("Kevin Spacey" ))
//        g.setNode("swilliams",  GraphNode( "Saul Williams"))
//        g.setNode("bpitt",      GraphNode( "Brad Pitt"))
//        g.setNode("hford",      GraphNode( "Harrison Ford"))
//        g.setNode("lwilson",    GraphNode( "Luke Wilson"))
//        g.setNode("kbacon",     GraphNode( "Kevin Bacon"))
//
//
//        // Add edges to the graph.
//        g.setEdge("kspacey",   "swilliams", js.Dynamic.literal(label="asas"))
//        g.setEdge("swilliams", "kbacon", js.Dynamic.literal(label="asas"))
//        g.setEdge("bpitt",     "kbacon", js.Dynamic.literal(label="asas"))
//        g.setEdge("hford",     "lwilson", js.Dynamic.literal(label="asas"))
////        g.setEdge("lwilson",   "hford", js.Dynamic.literal(label="asas"))
//        g.setEdge("lwilson",   "kbacon", js.Dynamic.literal(label="asas"))
//        //
//        dagre.layout(g)
//        g
//  }
//
//  def somefunction() = {
//    val nodes = Array(
//      SNode("kspacey",    "Kevin Spacey", List(SNodeAlternative("a1", "note1"))),
//      SNode("swilliams",  "Saul Williams", List(SNodeAlternative("a1", "note1"))),
//      SNode("bpitt",      "Brad Pitt", List(SNodeAlternative("a1", "note1"))),
//      SNode("hford",      "Harrison Ford", List(SNodeAlternative("a1", "note1"))),
//      SNode("lwilson",    "Luke Wilson", List(SNodeAlternative("a1", "note1"))),
//      SNode("kbacon",     "Kevin Bacon", List(SNodeAlternative("a1", "note1")))
//    )
//
//    val edges = List(
//      SEdge(ReactUtil.getEdgeID(nodes(0).id,nodes(1).id),"sm label", nodes(0), nodes(1)),
//      SEdge(ReactUtil.getEdgeID(nodes(1).id,nodes(5).id),"sm label", nodes(1), nodes(5)),
//      SEdge(ReactUtil.getEdgeID(nodes(2).id,nodes(5).id),"sm label", nodes(2), nodes(5)),
//      SEdge(ReactUtil.getEdgeID(nodes(3).id,nodes(4).id),"sm label", nodes(3), nodes(4)),
//      SEdge(ReactUtil.getEdgeID(nodes(1).id,nodes(5).id),"sm label", nodes(1), nodes(5))
//    )
//
//    SGraph(nodes.toList,edges)
//  }
//
//
//  def main = {
////    defer(TopLevel(()).render(document.body))
//   // val simpleG = somefunction()
//
//   // def someF(s:SGraph) = {}
//
////    defer(MainComponent.TopLevel(())
////      .render(document.body))
//
//    React.render(MainComponent.TopLevel(()), dom.document.body)
//  }
//}