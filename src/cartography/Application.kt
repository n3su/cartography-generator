package cartography

import cartography.embedded.Component
import cartography.embedded.components.ApplicationComponent
import cartography.shared.Settings
import cartography.shared.Store
import cartography.util.CATCH_STRICT
import cartography.util.DEBUG
import javafx.beans.property.ObjectProperty
import javafx.fxml.FXMLLoader
import javafx.scene.Node

/**
 * Application core class controlling
 * storage, settings and initialization.
 */
open class Application
{
    /**
     * Session data.
     */
    val storage: Store = Store()

    /**
     * Session settings.
     */
    val settings: Settings = Settings()

    /**
     * Application initialization function called
     * after successful injection into game.
     */
    fun launch(robot: Boot) = CATCH_STRICT {
        robot.embeddableUI = robot
    }

    /**
     * Companion object with class initializer.
     */
    companion object : Application()
}