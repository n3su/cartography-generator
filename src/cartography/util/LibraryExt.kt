/**
 * LibraryExt
 * Extension functions for RuneMate dependency.
 */
package cartography.util

import com.runemate.game.api.hybrid.Environment
import com.runemate.game.api.hybrid.location.Coordinate

/* ------------------------------ ------------------------------ */
// RuneMate logging functions.
/* ------------------------------ ------------------------------ */

@Suppress("FunctionName")
fun DEBUG(message: String): Unit = Environment.getLogger().run { debug(message) }

@Suppress("FunctionName")
fun ERROR(message: String): Unit = Environment.getLogger().run { severe(message) }

@Suppress("FunctionName")
fun EXIT(message: String = "Bot terminated."): Unit = Environment.getBot().run { stop(message) }

/* ------------------------------ ------------------------------ */
// RuneMate environment functions.
/* ------------------------------ ------------------------------ */



/* ------------------------------ ------------------------------ */
// Area and coordinate extensions.
/* ------------------------------ ------------------------------ */

/**
 * Unique identifier for [Coordinate] object.
 */
val Coordinate.id: Int
    get() = hashCode()

/**
 * Getter for neighbors of specific [Coordinate] object.
 */
val Coordinate.surroundings: List<Coordinate>
    get() = area.surroundingCoordinates