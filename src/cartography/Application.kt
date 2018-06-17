package cartography

import cartography.embedded.Controller
import cartography.shared.Settings
import cartography.shared.Store
import cartography.util.CATCH_STRICT

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
     * Embedded interface controller.
     */
    val embedded: Controller = Controller()

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