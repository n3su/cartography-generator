package cartography.control

import cartography.shared.Settings
import cartography.shared.Store
import cartography.util.ERROR
import cartography.util.id
import com.runemate.game.api.hybrid.location.navigation.web.SerializableWeb
import javafx.stage.FileChooser
import org.graphstream.graph.Edge
import java.io.File

object Import
{
    fun load(): FileChooser
    {
        val chooser = FileChooser()
        chooser.title = "[Cartography] Import serializable web..."
        chooser.initialDirectory = Settings.directory
        chooser.extensionFilters.add(FileChooser.ExtensionFilter("RuneMate SerializableWeb", "*.nav"))
        return chooser
    }

    fun selected(load: File)
    {
        val serializable = SerializableWeb.deserialize(load.readBytes())

        if (serializable.vertices.isEmpty()) {
            ERROR("Imported file is invalid or has no vertices.")
            return
        }

        for (vertex in serializable.vertices) {
            Graph.create_node(vertex.position)
        }

        for (vertex in serializable.vertices)
        {
            val coordinate = vertex.position

            for (out in vertex.outputCosts.keys)
            {
                val target = out.position
                Store.visual.addEdge<Edge>("${coordinate.hashCode()} to ${target.hashCode()}", coordinate.id.toString(), target.id.toString())
            }
        }
    }
}