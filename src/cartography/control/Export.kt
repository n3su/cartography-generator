package cartography.control

import cartography.shared.Collision
import cartography.shared.Settings
import cartography.shared.Store
import cartography.util.DEBUG
import cartography.util.surroundings
import com.runemate.game.api.hybrid.location.navigation.web.WebVertex
import javafx.stage.FileChooser
import java.io.File
import java.io.ObjectOutputStream

object Export
{
    fun save(): FileChooser
    {
        val chooser: FileChooser = FileChooser()
        chooser.title = "[Cartography] Export serializable web..."
        chooser.initialDirectory = Settings.directory
        chooser.extensionFilters.add(FileChooser.ExtensionFilter("RuneMate SerializableWeb", "*.nav"))
        return chooser
    }

    fun selected(write: File)
    {
        val serializable = Store.serializable
        val invalidated = mutableListOf<WebVertex>()

        if (serializable.vertices.isEmpty()) {
            DEBUG("Serializable web is empty and there is nothing to export.")
            return
        }

        for (vertex in serializable.vertices)
        {
            val center = vertex.position
            val surroundings = center.surroundings
            val collision = Collision.get(center)

            if (!collision.valid() || surroundings.isEmpty())
                continue

            for (surrounding in surroundings)
            {
                if (collision.passable(surrounding)) {
                    val vsur = serializable.getFirstVertexOn(surrounding) ?: continue
                    vertex.addBidirectionalEdge(vsur)
                }
            }
        }

        for (vertex in serializable.vertices)
        {
            if (vertex.outputCosts.isEmpty() && vertex.inputCosts.isEmpty())
                invalidated.add(vertex)
        }

        serializable.vertices.removeAll(invalidated)
        serializable.writeExternal(ObjectOutputStream(write.outputStream()))
    }
}