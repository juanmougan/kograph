package com.github.juanmougan.kograph.domain

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.maps.shouldContainExactly
import io.kotest.matchers.shouldBe

class GraphTest : ShouldSpec({
    should("add a new node to the graph") {
        val trainNetwork = Graph<String>()
        val station = trainNetwork.addNode("Utrecht Centraal")
        trainNetwork.nodes shouldContainExactly listOf(station)
    }

    should("add an edge directed") {
        val friends = Graph<String>(isUndirected = false)
        val matthew = friends.addNode("Matthew")
        val luke = friends.addNode("Luke")
        friends.addEdge(matthew, luke)      // Sucks to be Matthew 🤷‍♂️
        val expectedAdjacencyList: MutableMap<Node<String>, MutableList<Node<String>>> = mutableMapOf(
            matthew to mutableListOf(luke),
            luke to mutableListOf()
        )
        friends.getAdjacencyList() shouldContainExactly expectedAdjacencyList
        matthew.getNeighbours() shouldContainExactly listOf(luke)
    }

    should("add an edge undirected") {
        val trainNetwork = Graph<String>(isUndirected = true)
        val fromStation = trainNetwork.addNode("Utrecht Centraal")
        val toStation = trainNetwork.addNode("Amersfoort Centraal")
        trainNetwork.addEdge(fromStation, toStation)
        val expectedRoutes: MutableMap<Node<String>, MutableList<Node<String>>> = mutableMapOf(
            fromStation to mutableListOf(toStation),
            toStation to mutableListOf(fromStation)
        )
        trainNetwork.getAdjacencyList() shouldContainExactly expectedRoutes
    }

    should("validate an empty path") {
        val emptyPath = emptyList<Node<String>>()
        // TODO reuse this graph
        val trainNetwork = Graph<String>(isUndirected = true)
        trainNetwork.addNode("Utrecht Centraal")
        trainNetwork.addNode("Amersfoort Centraal")

        trainNetwork.checkValidPath(emptyPath) shouldBe true
    }

    should("return false if an edge is missing") {
        // TODO reuse this graph
        val trainNetwork = Graph<String>(isUndirected = true)
        val stops = listOf(
            trainNetwork.addNode("Utrecht Centraal"),
            trainNetwork.addNode("Amersfoort Centraal"),
            trainNetwork.addNode("Groningen")
        )
        trainNetwork.addEdge(
            stops[0],
            stops[1]
        )  // So, not (directly) connected from Amersfoort Centraal to Groningen

        trainNetwork.checkValidPath(stops) shouldBe false
    }

    should("return true if there is a path") {
        // TODO reuse this graph
        val trainNetwork = Graph<String>(isUndirected = true)
        val stops = listOf(
            trainNetwork.addNode("Utrecht Centraal"),
            trainNetwork.addNode("Amersfoort Centraal"),
            trainNetwork.addNode("Zwolle")
        )
        trainNetwork.addEdge(
            stops[0],
            stops[1]
        )
        trainNetwork.addEdge(
            stops[1],
            stops[2]
        )

        trainNetwork.checkValidPath(stops) shouldBe true
    }
})
