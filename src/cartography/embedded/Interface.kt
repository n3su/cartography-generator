package cartography.embedded

import cartography.Application
import cartography.util.DEBUG
import com.runemate.game.api.client.embeddable.EmbeddableUI
import com.runemate.game.api.hybrid.util.Resources
import com.runemate.game.api.script.Execution
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
class Interface : StackPane(), Initializable
{
    /**
     * Controller that will be assigned to
     * [FXMLLoader].
     */
    private val controller: Controller

    /**
     * Object property of Node.
     */
    var property: ObjectProperty<Node>? = null

    init
    {
        val using = FXMLLoader()
        controller = Controller()
        using.setController(controller)

        val hierarchy: Node? = using.load(Resources.getAsStream("layout/Window.fxml")) ?:
                throw ExceptionInInitializerError("[Interface] Could not create embedded interface.")

        DEBUG("[Interface] Hierarchy node loaded.")
        property = SimpleObjectProperty<Node>(hierarchy)
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        isVisible = true

    }
}