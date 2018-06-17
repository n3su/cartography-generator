package cartography

import com.runemate.game.api.script.framework.LoopingBot
import com.runemate.game.api.client.embeddable.EmbeddableUI
import com.runemate.game.api.script.Execution
import javafx.beans.property.ObjectProperty
import javafx.scene.Node

class Boot() : LoopingBot(), EmbeddableUI
{
    override fun onStart(vararg _arguments: String?) {
        Application.launch()
        embeddableUI = this
    }

    override fun onLoop() { }

    override fun botInterfaceProperty(): ObjectProperty<out Node> = Application.getInterfaceProperty() ?:
        throw ExceptionInInitializerError("[Interface] Error occurred.")
}