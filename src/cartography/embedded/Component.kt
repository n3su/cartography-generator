package cartography.embedded

import com.runemate.game.api.hybrid.util.Resources
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.input.MouseEvent
import javafx.stage.Window

@Suppress("LeakingThis")
abstract class Component(resource: String)
{
    private val document = FXMLLoader()
    val property: ObjectProperty<Node>

    init
    {
        val hierarchy: Node = document.load(Resources.getAsStream(resource))
                ?: throw ExceptionInInitializerError("[Interface] Could not create embedded interface.")

        property = SimpleObjectProperty<Node>(hierarchy)
        created()
    }

    /**
     * Function to replace disgusting init function in other components.
     */
    abstract fun created()

    /**
     *
     */
    protected fun window(): Window {
        return property.get().scene.window
    }

    /**
     * Retrieve element [Node] by its' fx:id.
     */
    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> getElement(key: String): T = document.namespace[key] as T?
            ?: throw ExceptionInInitializerError("[Interface] Could not retrieve element named $key.")

    protected fun <T : Any> using(fxid: String): T = getElement(fxid)

    protected infix fun Button.clicked(event: (MouseEvent) -> Unit) = setOnMouseClicked { event.invoke(it) }
}