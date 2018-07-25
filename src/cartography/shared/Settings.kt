package cartography.shared

import com.runemate.game.api.hybrid.Environment
import java.io.File
import kotlin.properties.Delegates

/**
 * Class for storing settings adjustable
 * in user interface.
 */
open class Settings
{
    /**
     * File chooser default directory for saving
     * or opening SerializableWeb files.
     *
     * Overwrite every time new location is chosen.
     */
    var directory = File(".")

    companion object : Settings()
}