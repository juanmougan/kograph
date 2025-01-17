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

    should("traverse the graph with a recursive DFS") {
        // Given a graph
        val numbers = createGraphWithNumbers()

        // When dfs
//        val path = numbers.buildDepthFirstSearchPathRecursive()
        val path = numbers.depthFirstSearchPath(numbers)
        println("The path through the graph looks like this:\n$path")

        // Then the list should be
    }

    should("traverse the graph with an iterative DFS") {
        // Given a graph
        val numbers = createGraphWithNumbers()

        // When dfs
        val path = numbers.depthFirstSearchIterative(start = numbers.nodes[0])
        println("The path through the graph looks like this:\n$path")

        // Then the list should be
    }
})

private fun createGraphWithNumbers(): Graph<Int> {
    val numbers = Graph<Int>(isUndirected = true)
    repeat(10, { numbers.addNode(it) })
    numbers.addEdge(numbers.nodes[0], numbers.nodes[1])
    numbers.addEdge(numbers.nodes[0], numbers.nodes[5])
    numbers.addEdge(numbers.nodes[0], numbers.nodes[7])
    numbers.addEdge(numbers.nodes[1], numbers.nodes[2])
    numbers.addEdge(numbers.nodes[2], numbers.nodes[3])
    numbers.addEdge(numbers.nodes[2], numbers.nodes[4])
    numbers.addEdge(numbers.nodes[2], numbers.nodes[5])
    numbers.addEdge(numbers.nodes[4], numbers.nodes[9])
    numbers.addEdge(numbers.nodes[5], numbers.nodes[6])
    numbers.addEdge(numbers.nodes[5], numbers.nodes[8])
    numbers.addEdge(numbers.nodes[6], numbers.nodes[8])
    numbers.addEdge(numbers.nodes[8], numbers.nodes[9])
    return numbers
}
