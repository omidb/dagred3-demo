package demo

import Dagre._

import scala.scalajs.js

/**
 * Created by Omid on 5/25/2015.
 */

case class SNodeAlternative(id:String, label:String)
case class SNode(id:String, label:String, alternatives: List[SNodeAlternative])
case class SEdge(id:String, label:String, from:SNode, to:SNode)
case class SGraph(nodes: List[SNode], edges: List[SEdge])

object SGraph{
  def modifyEdge(g: SGraph, oldEdge: SEdge, fromID: String, toID: String) = {
//    val es = g.edges.zipWithIndex.filter(e => e._1.from.id == fromID && e._1.to.id == toID)
    val es = g.edges.zipWithIndex.filter(_._1 == oldEdge)
    val n1L = g.nodes.filter(_.id == fromID)
    val n2L = g.nodes.filter(_.id == toID)

    if(es.size > 0 && n1L.size > 0 && n2L.size > 0){
//      println(fromID, toID)
//      println(es.head._1.from.label + "-->" + es.head._1.to.label)
//      println("--------------")
      val enwE = es.head._1
        .copy(id = ReactUtil.getEdgeID(fromID,toID), from = n1L.head, to = n2L.head)
      val newG = g.copy(edges = g.edges.updated(es.head._2, enwE))
//      newG.edges.map(ne => println(ne.id))
      newG
    }
    else{
      println("Didn't find any node or edge")
      g
    }
  }

  def makeGraph(gr: SGraph) = {
    val g = js.Dynamic
      .newInstance(dagre.graphlib.Graph.asInstanceOf[js.Dynamic])()
      .asInstanceOf[Dagre.Graph]

    g.setGraph(js.Dynamic.literal())
    g.setDefaultEdgeLabel(() => js.Dynamic.literal())

    // Add nodes to the graph.
    gr.nodes.map(n => g.setNode(n.id, GraphNode(n.label)))
    // Add edges to the graph.
    gr.edges.map(e => g.setEdge(e.from.id, e.to.id, GraphNode(e.label)))
    g
  }

  def layoutGraph(g: Graph) = {
    dagre.layout(g)
    g
  }
}
