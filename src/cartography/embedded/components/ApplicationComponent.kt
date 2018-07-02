package cartography.embedded.components

import cartography.embedded.Component
import cartography.embedded.Invokable
import javafx.beans.property.ObjectProperty
import javafx.fxml.FXML
import javafx.scene.Node

open class ApplicationComponent : Component()
{
    final override val hierarchy: ObjectProperty<Node> = getProperty("layout/Window.fxml")
    companion object : ApplicationComponent()
}