package cartography

import cartography.embedded.Interface
import com.runemate.game.api.script.framework.LoopingBot
import com.runemate.game.api.client.embeddable.EmbeddableUI
import javafx.beans.property.ObjectProperty
import javafx.scene.Node

class Boot : LoopingBot(), EmbeddableUI
{
    override fun onStart(vararg args: String?) {
        Application.launch(this)
    }

    override fun onLoop() { }
    override fun botInterfaceProperty(): ObjectProperty<out Node> = Interface.create()
}