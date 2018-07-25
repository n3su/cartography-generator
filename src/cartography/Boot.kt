package cartography

import cartography.control.Graph
import cartography.embedded.components.ApplicationComponent
import cartography.shared.Collision
import cartography.shared.Store
import cartography.util.id
import com.runemate.game.api.client.embeddable.EmbeddableUI
import com.runemate.game.api.hybrid.location.navigation.web.vertex_types.CoordinateVertex
import com.runemate.game.api.hybrid.region.Region
import com.runemate.game.api.script.framework.LoopingBot
import javafx.beans.property.ObjectProperty
import javafx.scene.Node
import java.util.*

class Boot : LoopingBot(), EmbeddableUI
{
    override fun botInterfaceProperty(): ObjectProperty<out Node> = ApplicationComponent.property

    override fun onStart(vararg args: String?) {
        Application.launch(this)
        setLoopDelay(600)
    }

    override fun onLoop()
    {
        if (!Store.active)
            return

        val coordinates = Region.getArea().coordinates
        val collisions = Region.getCollisionFlags()[Region.getCurrentPlane()]

        if (coordinates.isEmpty() || collisions.isEmpty())
            return

        val cache = Store.cache

        for (x in 0..103)
        {
            for (y in 0..103)
            {
                val flag = collisions[x][y]
                val byIndex = if (x == 0) y else x * 104 + y
                val coordinate = coordinates[byIndex] ?: break

                if (cache.containsKey(coordinate.id))
                {
                    val retrieve = cache[coordinate.id]!!

                    if (retrieve.impassable())
                    {
                        retrieve.collision = flag
                        cache.replace(coordinate.id, retrieve)
                        if (!retrieve.impassable()) {
                            Graph.create_node(coordinate)
                            Store.serializable.addVertex(CoordinateVertex(coordinate, Collections.emptyList()))
                        }
                    }
                }
                else
                {
                    val collide = Collision().apply {
                        this.coordinate = coordinate
                        collision = flag
                    }

                    cache.putIfAbsent(
                        coordinate.id,
                        collide
                    )

                    if (!collide.impassable()) {
                        Graph.create_node(coordinate)
                        Store.serializable.addVertex(CoordinateVertex(coordinate, Collections.emptyList()))
                    }
                }
            }
        }
    }


}