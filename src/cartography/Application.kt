package cartography

import cartography.embedded.Interface
import cartography.shared.Settings
import cartography.shared.Store
import cartography.util.CATCH_STRICT
import com.runemate.game.api.client.ClientUI
import com.runemate.game.api.client.embeddable.EmbeddableUI
import com.runemate.game.api.hybrid.Environment
import com.runemate.game.api.script.Execution
import com.runemate.game.api.script.framework.AbstractBot
import javafx.beans.property.ObjectProperty
import javafx.scene.Node

/**
 * Application core class controlling
 * storage, settings and initialization.
 */
open class Application
{
    /**
     * Current bot instance.
     */
    val robot: AbstractBot
        get() = Environment.getBot()

    /**
     * Session data.
     */
    val storage: Store = Store()

    /**
     * Session settings.
     */
    val settings: Settings = Settings()

    /**
     * Embedded interface handle.
     */
    lateinit var embedded: Interface

    /**
     * Application initialization function called
     * after successful injection into game.
     */
    fun launch() = CATCH_STRICT {
        embedded = Interface()
    }

    fun getInterfaceProperty(): ObjectProperty<Node>? = embedded.property

    companion object : Application()
}