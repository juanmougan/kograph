package com.github.juanmougan.kograph.domain

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class GraphUtilsKtTest : ShouldSpec({
    should("return zero if the path is empty") {
        val emptyPath = emptyList<Edge<String>>()
        computePathCost(emptyPath) shouldBe 0.0
    }

    should("return the path") {
        // TODO reuse this graph
        val trainNetwork = Graph<String>(isUndirected = true)
        val stops = listOf(
            trainNetwork.addNode("Utrecht Centraal"),
            trainNetwork.addNode("Amersfoort Centraal"),
            trainNetwork.addNode("Zwolle"),
            trainNetwork.addNode("Groningen")
        )
        val directConnections = listOf(
            Edge(stops[0], stops[1], 1.0),
            Edge(stops[1], stops[2], 1.5),
            Edge(stops[2], stops[3], 2.0),
        )
        trainNetwork.addEdges(directConnections)

        computePathCost(directConnections) shouldBe 4.5
    }

    should("return infinity if there is not a connection") {
        // TODO reuse this graph
        val trainNetwork = Graph<String>(isUndirected = true)
        val stops = listOf(
            trainNetwork.addNode("Utrecht Centraal"),
            trainNetwork.addNode("Amersfoort Centraal"),
            trainNetwork.addNode("Zwolle"),
            trainNetwork.addNode("Groningen")
        )
        val directConnections = listOf(
            Edge(stops[0], stops[1], 1.0),
            Edge(stops[2], stops[3], 2.0),
        )
        trainNetwork.addEdges(directConnections)

        computePathCost(directConnections) shouldBe Double.POSITIVE_INFINITY
    }
})
