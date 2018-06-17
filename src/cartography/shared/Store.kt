package cartography.shared

import cartography.Application
import org.graphstream.graph.implementations.MultiGraph

/**
 * Class for storing application session data.
 */
class Store
{
    /**
     * Cache of current application session.
     * Used to cache collected data regarding world map.
     */
    val cache: MutableMap<Int, Collision> = HashMap()

    /**
     * Graph for data visualisation.
     * Used to store collected vertices and edges.
     */
    val visual: MultiGraph = MultiGraph("Cartography")

    companion object
    {
        /**
         * Store class accessor. Uses initialized
         * object in [Application] class.
         */
        operator fun <R : Any?> invoke(body: Store.() -> R?): R? = Application.storage.run(body) ?:
            throw UninitializedPropertyAccessException("Cannot access storage when Application is not initialized.")
    }
}