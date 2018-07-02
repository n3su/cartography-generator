package cartography.embedded

import com.runemate.game.api.hybrid.util.Resources
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.fxml.FXMLLoader
import javafx.scene.Node

abstract class Component
{
    protected val document = FXMLLoader()
    abstract val hierarchy: ObjectProperty<Node>

    fun getProperty(resource: String): ObjectProperty<Node> {
        val hierarchy: Node = document.load(Resources.getAsStream(resource))
            ?: throw ExceptionInInitializerError("[Interface] Could not create embedded interface.")

        return SimpleObjectProperty<Node>(hierarchy)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getElement(key: String): T? {
        document.controllerFactory
        return document.namespace[key] as T?
    }
}