package com.github.juanmougan.kograph.domain


fun <T> computePathCost(edges: List<Edge<T>>): Double {
    if (edges.isEmpty()) return 0.0
    var cost = 0.0
    var previousNode = edges[0].fromNode
    for (edge in edges) {
        if (edge.fromNode != previousNode) {
            cost = Double.POSITIVE_INFINITY
        } else {
            cost += edge.weight
        }
        previousNode = edge.toNode
    }
    return cost
}
