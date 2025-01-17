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
        addEdge(edge = edge, weight = weight)
    }

    fun addEdges(edges: List<Edge<T>>) {
        edges.forEach { addEdge(it) }
    }

    private fun addEdge(edge: Edge<T>, weight: Double = 1.0) {
        val from = edge.fromNode
        val to = edge.toNode
        from.edges.add(edge)
        adjacencyList[from]?.add(to)

        if (isUndirected) {
            val reverseEdge = Edge(to, from, weight)
            to.edges.add(reverseEdge)
            adjacencyList[to]?.add(from)
        }
    }

    fun getAdjacencyList(): Map<Node<T>, List<Node<T>>> = adjacencyList

    fun checkValidPath(path: List<Node<T>>): Boolean {
        if (path.isEmpty()) return true    // An empty path is valid
        var previousNode = path.first()
        for (i in 1 until path.size) {
            val nextNode = path[i]
            if (!previousNode.existsEdgeTo(nextNode)) return false
            previousNode = nextNode
        }
        return true
    }

    // TODO move these search methods elsewhere
    fun buildDepthFirstSearchPathRecursive(): List<Node<T>> {
        val visited = MutableList(this.nodes.size, { false })
        val lastVisited = MutableList<Node<T>?>(this.nodes.size, { null })

        for (i in 0..this.nodes.size) {
            if (!visited[i]) {
                dfsRecursive(i, visited, lastVisited)
            }
        }

        return lastVisited.toList().filterNotNull()
    }

    private fun dfsRecursive(i: Int, visited: MutableList<Boolean>, lastVisited: MutableList<Node<T>?>) {
        visited[i] = true
        val current = this.nodes[i]

        for (edge in current.edges) {
            val neighbor = edge.toNode
            if (!visited[neighbor.index]) {
                lastVisited[neighbor.index] = this.nodes[i]
                dfsRecursive(i, visited, lastVisited)
            }
        }
    }

    // TODO throwaway code
    private fun dfsRecursivePath(g: Graph<T>, ind: Int, seen: MutableList<Boolean>, last: MutableList<Int>) {
        seen[ind] = true
        val current: Node<T> = g.nodes[ind]

        for (edge in current.edges) {
            val neighbor: Int = edge.toNode.index
            if (!seen[neighbor]) {
                last[neighbor] = ind
                dfsRecursivePath(g, neighbor, seen, last)
            }
        }
    }

    fun depthFirstSearchPath(g: Graph<T>): List<Int> {
        val seen = MutableList(g.nodes.size) { false }
        val last = MutableList(g.nodes.size) { -1 }

        for (ind in 0 until g.nodes.size) {
            if (!seen[ind]) {
                dfsRecursivePath(g, ind, seen, last)
            }
        }
        return last
    }
    // TODO end throwaway

    //def depth_first_search_stack(g: Graph, start: int) -> list:
    //    seen: list = [False] * g.num_nodes
    //    last: list = [-1] * g.num_nodes
    //    to_explore: list = []
    //
    //  ❶ to_explore.append(start)
    //  ❷ while to_explore:
    //      ❸ ind = to_explore.pop()
    //        if not seen[ind]:
    //            current: Node = g.nodes[ind]
    //            seen[ind] = True
    //          ❹ all_edges: list = current.get_sorted_edge_list()
    //          ❺ all_edges.reverse()
    //            for edge in all_edges:
    //                neighbor: int = edge.to_node
    //                if not seen[neighbor]:
    //                    last[neighbor] = ind
    //                    to_explore.append(neighbor)
    //    return last

    fun depthFirstSearchIterative(start: Node<T>): List<Node<T>> {
        val seen = MutableList(this.nodes.size, { false })
        val previous = MutableList<Node<T>?>(this.nodes.size, { null })
        val toExplore: ArrayDeque<Node<T>> = ArrayDeque()

        toExplore.add(start)
        while (toExplore.isNotEmpty()) {
            val last = toExplore.removeLast()
            val ind = last.index
            if (!seen[ind]) {
                val current = this.nodes[ind]
                seen[ind] = true
                val allEdges = current.edges
                allEdges.reverse()
                for (edge in allEdges) {
                    val neighbor = edge.toNode
                    if (!seen[neighbor.index]) {
                        previous[neighbor.index] = last
                        toExplore.addLast(neighbor)
                    }
                }
            }
        }

        return previous.toList().filterNotNull()
    }
}
