import javafx.stage.Stage
import tornadofx.*

class MyApp: App(MyView::class) {
    override fun start(stage: Stage) {
        stage.height = 500.0
        stage.width = 800.0
        stage.title = "view"
        super.start(stage)
    }
}

fun main(args: Array<String>) {
    launch<MyApp>(args)
}