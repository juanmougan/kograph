import com.github.juanmougan.kograph.domain.Graph
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe

class NodeTest : ShouldSpec({
    should("throw an exception if trying to get inbound nodes from an undirected graph") {
        val socialMedia = Graph<String>(isUndirected = true)
        val person = socialMedia.addNode("Marieke")
        shouldThrow<IllegalArgumentException> {
            person.getInboundNeighbours()
        }
    }

    should("return all the inbound neighbours for a given node") {
        val socialMedia = Graph<String>()
        val marieke = socialMedia.addNode("Marieke")
        val piet = socialMedia.addNode("Piet")
        socialMedia.addEdge(marieke, piet)

        piet.getInboundNeighbours() shouldContainExactly listOf(marieke)
        marieke.getInboundNeighbours() shouldContainExactly emptyList()
    }

    should("return false if there is not an edge to a direct neighbour") {
        val socialMedia = Graph<String>()
        val marieke = socialMedia.addNode("Marieke")
        val piet = socialMedia.addNode("Piet")

        piet.existsEdgeTo(marieke) shouldBe false
    }

    should("return true if there is an edge to a direct neighbour for a directed graph") {
        val socialMedia = Graph<String>()
        val marieke = socialMedia.addNode("Marieke")
        val piet = socialMedia.addNode("Piet")
        socialMedia.addEdge(piet, marieke)

        piet.existsEdgeTo(marieke) shouldBe true
        marieke.existsEdgeTo(piet) shouldBe false
    }

    should("return true if there is an edge to a direct neighbour for an undirected graph") {
        val socialMedia = Graph<String>(isUndirected = true)
        val marieke = socialMedia.addNode("Marieke")
        val piet = socialMedia.addNode("Piet")
        socialMedia.addEdge(marieke, piet)

        piet.existsEdgeTo(marieke) shouldBe true
        marieke.existsEdgeTo(piet) shouldBe true
    }
})
