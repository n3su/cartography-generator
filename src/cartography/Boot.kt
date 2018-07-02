package cartography

import cartography.embedded.components.ApplicationComponent
import cartography.util.DEBUG
import com.runemate.game.api.script.framework.LoopingBot
import com.runemate.game.api.client.embeddable.EmbeddableUI
import com.runemate.game.api.hybrid.RuneScape
import javafx.beans.property.ObjectProperty
import javafx.scene.Node

class Boot : LoopingBot(), EmbeddableUI
{
    override fun onStart(vararg args: String?) {
        Application.launch(this)
        setLoopDelay(1000)
    }

    override fun onLoop() {}

    override fun botInterfaceProperty(): ObjectProperty<out Node> = ApplicationComponent.hierarchy
}