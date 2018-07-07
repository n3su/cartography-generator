package cartography.control

import cartography.shared.Store
import cartography.util.contract
import cartography.util.id
import cartography.util.not_null
import cartography.util.surroundings
import com.runemate.game.api.hybrid.location.Coordinate
import org.graphstream.graph.Edge
import org.graphstream.graph.Node
import org.graphstream.ui.view.Viewer

object Graph
{
    const val DEFAULT_NODE_STYLE = "shape: diamond; size: 4px;"

    /**
     * Create node with required attributes.
     */
    @Suppress("FunctionName")
    fun create_node(coordinate: Coordinate) = contract(Store.visual.getNode<Node>(coordinate.id.toString()) == null) {
        val node = Store.visual.addNode<Node>(coordinate.id.toString())
        node.addAttribute("layout.frozen")
        node.addAttribute("x", coordinate.x)
        node.addAttribute("y", coordinate.y)
        node.addAttribute("self", coordinate)
        node.addAttribute("ui.style", DEFAULT_NODE_STYLE)
    }

    /**
     * Render and display coordinate vertices
     * and their edges.
     */
    fun visualise() {
        if (!Store.cache.isEmpty())
            surroundings()

        display()
    }

    /**
     * Create edges for scanned vertices.
     */
    private fun surroundings() {
        val visual = Store.visual

        if (visual.nodeCount < 1)
            return

        for (node in visual.getNodeSet<Node>())
        {
            val coordinate = node.getAttribute("self", Coordinate::class.java)
            val surroundings = coordinate.surroundings
            val collision = Store.cache[coordinate.id]

            if (!not_null(coordinate, surroundings, collision))
                continue

            // Filter out coordinates that does not have corresponding node
            // or already has edge with center node.
            val filtered = surroundings.filter {
                c -> visual.getNode<Node>(c.id.toString())?.let { !it.hasEdgeBetween(node) } ?: false
            }.map { c -> visual.getNode<Node>(c.id.toString())!! to c }

            if (filtered.isEmpty())
                continue

            for (pair in filtered)
            {
                val (_, target) = pair
                if (collision!!.passable(target)) {
                    visual.addEdge<Edge>("{$coordinate.id} to {${target.id}", coordinate.id.toString(), target.id.toString())
                }
            }
        }
    }

    /**
     * Display graph.
     */
    private fun display() {
        Store.visual.run {
            val viewer = display()
            viewer.enableXYZfeedback(false)
            viewer.closeFramePolicy = Viewer.CloseFramePolicy.CLOSE_VIEWER
        }
    }
}