/**
 * LayoutExt
 * Extension functions and helpers for interface controlling.
 */
package cartography.util

import cartography.embedded.Component
import javafx.beans.property.ObjectProperty
import javafx.scene.Node
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Delegate for retrieving object hierarchy from FXML document.
 */
fun property(resource: String) = object: ReadOnlyProperty<Component, ObjectProperty<Node>>
{
    override fun getValue(thisRef: Component, property: KProperty<*>): ObjectProperty<Node> {
        return thisRef.getProperty(resource)
    }
}

/**
 * Delegate for retrieving FXML elements by their fx:id.
 */
fun <T : Any> selector(fxid: String? = null) = object : ReadOnlyProperty<Component, T>
{
    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Component, property: KProperty<*>): T {
        val key = fxid ?: property.name
        return thisRef.getElement(key) ?: throw IllegalArgumentException("Cannot find element with corresponding fx:id ($key).")
    }
}