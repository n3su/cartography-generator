package cartography.embedded

import cartography.Application
import cartography.util.DEBUG
import com.runemate.game.api.hybrid.util.Resources
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.layout.StackPane
import java.net.URL
import java.util.*

/**
 * Embedded interface loader.
 */
open class Interface : StackPane(), Initializable
{
    fun create(): ObjectProperty<Node>
    {
        val using = FXMLLoader().apply {
            setController(Application.embedded)
        }

        val hierarchy: Node = using.load(Resources.getAsStream("layout/Window.fxml")) ?:
            throw ExceptionInInitializerError("[Interface] Could not create embedded interface.")

        DEBUG("[Interface] Hierarchy node loaded.")
        return SimpleObjectProperty<Node>(hierarchy)
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        isVisible = true
    }

    companion object : Interface()
}