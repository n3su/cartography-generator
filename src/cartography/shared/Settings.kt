package cartography.shared

import cartography.Application
import java.io.File

/**
 * Class for storing settings adjustable
 * in user interface.
 */
class Settings
{
    /*
    * Scan radius.
    * */
    val radius: Int = 52

    /**
     * File chooser default directory for saving
     * SerializableWeb files.
     *
     * Overwrite every time new location is chosen.
     */
    val directory = File("C:\\")

    companion object
    {
        /**
         * Settings class accessor. Uses initialized
         * object in [Application] class.
         */
        operator fun <R : Any?> invoke(body: Settings.() -> R?): R? = Application.settings.run(body) ?:
            throw UninitializedPropertyAccessException("Cannot access settings when Application is not initialized.")
    }
}