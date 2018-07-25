package cartography

import cartography.shared.Store
import cartography.util.CATCH_STRICT
import com.runemate.game.api.hybrid.region.Region

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
        Region.cacheCollisionFlags(true)
        robot.embeddableUI = robot
        Store.reset()
    }

    /**
     * Companion object with class initializer.
     */
    companion object : Application()
}