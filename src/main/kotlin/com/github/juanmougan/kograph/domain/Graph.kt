package com.github.juanmougan.kograph.domain

class Graph<T>(
    val isUndirected: Boolean = false
) {
    val nodes: MutableList<Node<T>> = mutableListOf()
    private val adjacencyList: MutableMap<Node<T>, MutableList<Node<T>>> = mutableMapOf()

    fun addNode(data: T): Node<T> {
        val newNode = Node(index = nodes.size, data = data, graph = this)
        nodes.add(newNode)
        adjacencyList[newNode] = mutableListOf()
        return newNode
    }

    fun addEdge(from: Node<T>, to: Node<T>, weight: Double = 1.0) {
        val edge = Edge(from, to, weight)
        from.edges.add(edge)
        adjacencyList[from]?.add(to)

        if (isUndirected) {
            val reverseEdge = Edge(to, from, weight)
            to.edges.add(reverseEdge)
            adjacencyList[to]?.add(from)
        }
    }

    fun getAdjacencyList(): Map<Node<T>, List<Node<T>>> = adjacencyList
}
