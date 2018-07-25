package cartography.shared

import cartography.util.BLOCK_MOVEMENT_FULL
import cartography.util.Direction
import cartography.util.direction
import cartography.util.id
import com.runemate.game.api.hybrid.location.Coordinate
import com.runemate.game.api.hybrid.region.Region.CollisionFlags.HelperFlags.BLOCKED_OFF

class Collision
{
    /**
     * Assigned [Coordinate] object.
     */
    lateinit var coordinate: Coordinate

    /**
     * Collision flag.
     */
    var collision: Int = 0

    /**
     * Function that calculates collision based on given coordinate.
     *
     * @return false if object's coordinate is completely blocked off.
     * @return false if calculated direction was invalid value or if there
     * was collision on that direction.
     * @return true if calculated direction was valid and if there was
     * no collision on that direction.
     */
    fun passable(to: Coordinate): Boolean {
        if (impassable())
            return false

        val cf = Direction.MAPPED_DIRECTIONS[coordinate.direction(to)] ?: return false
        return collision.and(cf) == 0
    }

    fun impassable(): Boolean = collision.and(BLOCKED_OFF) != 0
    fun valid(): Boolean = collision != INVALID_COLLISION.collision

    companion object
    {
        private val INVALID_COLLISION = Collision().apply {
            collision = -1
            coordinate = Coordinate(-1, -1, -1)
        }

        fun get(coordinate: Coordinate) = Store.cache[coordinate.id] ?: INVALID_COLLISION

        fun create(coordinate: Coordinate, cf: Int): Collision
        {
            return Collision().apply {
                this.coordinate = coordinate
                this.collision = cf
            }
        }
    }
}