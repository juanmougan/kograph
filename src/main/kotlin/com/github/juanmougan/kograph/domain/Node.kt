package com.github.juanmougan.kograph.domain

data class Node<T>(
    val index: Int, val data: T, val edges: MutableList<Edge<T>> = mutableListOf(), val graph: Graph<T>
) {
    fun getNeighbours(): List<Node<T>>? {
        return graph.getAdjacencyList()[this]
    }

    override fun toString(): String {
        return "Node(index=$index, data=$data)"
    }

    // Override equals and hashCode to ensure comparisons are based on index and data
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Node<*>) return false
        return index == other.index && data == other.data
    }

    override fun hashCode(): Int {
        return 31 * index + (data?.hashCode() ?: 0)
    }
}
