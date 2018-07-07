package cartography

import cartography.embedded.components.ApplicationComponent
import cartography.shared.Collision
import cartography.shared.Store
import cartography.control.Graph
import cartography.util.contract
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
        setLoopDelay(1000)
    }

    override fun onLoop()
    {
        if (!Store.active)
            return

        val coordinates = Region.getArea().coordinates
        val collisions = Region.getCollisionFlags()[Region.getCurrentPlane()]

        if (coordinates.isEmpty() || collisions.isEmpty())
            return

        for (x in 0..103)
        {
            for (y in 0..103)
            {
                val flag = collisions[x][y]
                val byIndex = if (x == 0) y else x * 104 + y
                val coordinate = coordinates[byIndex] ?: continue

                val co = Collision().apply {
                    this.coordinate = coordinate
                    this.collision = flag
                }

                if (Store.cache.putIfAbsent(coordinate.id, co) == null && !co.impassable()) {
                    Graph.create_node(coordinate)
                    Store.serializable.addVertex(CoordinateVertex(coordinate, Collections.emptyList()))
                }
            }
        }
    }
}