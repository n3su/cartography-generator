package cartography

import cartography.util.CATCH_STRICT
import cartography.util.DEBUG
import cartography.util.surroundings
import com.runemate.game.api.hybrid.region.Players

/**
 * Application core class controlling
 * storage, settings and initialization.
 */
open class Application
{
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