package com.github.juanmougan.kograph.domain

data class Edge<T>(
    val fromNode: Node<T>,
    val toNode: Node<T>,
    val weight: Double = 1.0
) {
    override fun toString(): String {
        return "Edge(from=${fromNode.index}, to=${toNode.index}, weight=$weight)"
    }
}
