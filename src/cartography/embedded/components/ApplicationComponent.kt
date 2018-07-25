package cartography.embedded.components

import cartography.control.Export
import cartography.control.Graph
import cartography.control.Import
import cartography.embedded.Component
import cartography.shared.Settings
import cartography.shared.Store
import cartography.util.*
import javafx.application.Platform
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.input.MouseEvent
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Main application component.
 */
@Suppress("UNUSED_PARAMETER")
open class ApplicationComponent : Component("layout/Window.fxml")
{
    private val dtformat = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")

    override fun created() {
        events()
        message("Application started.")
        message("Using default refresh rate (500ms).")
    }

    private fun events() {
        using<Button>("ApplicationStart") clicked { started(it) }
        using<Button>("ApplicationHalt") clicked { halted(it) }
        using<Button>("ApplicationClearCache") clicked { cleared(it) }
        using<Button>("VertexWriteOutput") clicked { writeTo(it) }
        using<Button>("VertexLoadInput") clicked { loadFrom(it) }
        using<Button>("VertexVisualisation") clicked { visualise(it) }
    }

    private fun started(event: MouseEvent) = contract(!Store.active) {

        if (!Store.cleared()) {
            message("Clear cached data before starting.")
            return@contract
        }

        message("Application started reading coordinates.")
        Store.active = true
    }

    private fun halted(event: MouseEvent) = contract(Store.active) {
        Store.active = false
        message("Application stopped reading coordinates.")
        message("Collision object cache contains ${Store.cache.size} elements.")
    }

    private fun cleared(event: MouseEvent) {
        using<ListView<String>>("ApplicationLog").apply {
            this.items.clear()
        }

        Store.reset()
        message("Application cache cleared.")
    }

    private fun writeTo(event: MouseEvent) {
        val chooser = Export.save()
        val to = chooser.showSaveDialog(window())

        if (to == null) {
            message("Export canceled.")
            return
        }

        Export.selected(to)

        // Setting selected directory as default.
        Settings.directory = File(to.parent)
        message("Exported (${to.parent}).")
    }

    private fun loadFrom(event: MouseEvent) {

        if (Store.active) {
            message("Stop application process before importing file.")
            return
        }

        if (!Store.cleared()) {
            message("Clear cached data before importing.")
            return
        }

        val chooser = Import.load()
        val imported = chooser.showOpenDialog(window())

        if (imported == null) {
            message("Import canceled.")
            return
        }

        try {
            Import.selected(imported)
            message("Imported from ${imported.parent}.")
        }
        catch (e: Exception) {
            message("Failed to open file.")
        }
    }

    private fun visualise(event: MouseEvent) = Platform.runLater {
        Graph.visualise()
    }

    fun message(content: String) = Platform.runLater {
        DEBUG(content)
        using<ListView<String>>("ApplicationLog").apply {
            val ct = LocalDateTime.now().format(dtformat)
            items.add("[$ct] $content")
        }
    }

    companion object : ApplicationComponent()
}