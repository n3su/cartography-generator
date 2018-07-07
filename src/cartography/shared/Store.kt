package cartography.shared

import com.runemate.game.api.hybrid.location.navigation.web.SerializableWeb
import org.graphstream.graph.implementations.MultiGraph

/**
 * Class for storing application session data.
 */
open class Store
{
    /**
     * Cache of current application session.
     * Used to cache collected data regarding world map.
     */
    var cache: MutableMap<Int, Collision> = HashMap()

    /**
     * Graph for data visualisation.
     * Used to store collected vertices and edges.
     */
    var visual: MultiGraph = MultiGraph("Cartography")

    /**
     * RuneMate serializable web object.
     */
    var serializable: SerializableWeb = SerializableWeb()

    /**
     * Boolean flag to check if application
     * should gather coordinates.
     */
    var active: Boolean = false

    /**
     * Completely reset storage to default
     * values.
     */
    fun reset() {
        Store.active = false
        Store.cache = HashMap()
        Store.visual = MultiGraph("Cartography")
        Store.serializable = SerializableWeb()
    }

    fun cleared(): Boolean {
        return Store.cache.isEmpty() && visual.nodeCount == 0 && serializable.vertices.isEmpty()
    }

    companion object : Store()
}