package com.github.juanmougan.kograph.domain

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.maps.shouldContainExactly

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
})
