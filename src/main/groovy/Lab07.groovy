import groovy.beans.Bindable
import groovy.swing.SwingBuilder

def battlefield = new Battlefield()

new SwingBuilder().edt {
    frame(title:'Lab 07', size:[300, 300], show: true) {
        gridLayout(columns:  1, rows: 4)

        slider(minimum: Thread.MIN_PRIORITY, maximum: Thread.MAX_PRIORITY, value: bind(source: battlefield, sourceProperty: 'decPriority'))

        slider(minimum: 0, maximum: 100, value: bind(source: battlefield, sourceProperty: 'value'))

        slider(minimum: Thread.MIN_PRIORITY, maximum: Thread.MAX_PRIORITY, value: bind(source: battlefield, sourceProperty: 'incPriority'))

        button (text: 'START', actionPerformed: { battlefield.start() })
    }
}

class Battlefield {

    @Bindable
    int value = 50

    @Bindable
    int decPriority = Thread.NORM_PRIORITY

    @Bindable
    int incPriority = Thread.NORM_PRIORITY

    Thread decThread = new Thread({
        while (true) {
            value = Math.max(value-1, 0)

            Thread.currentThread().setPriority(decPriority);
            Thread.sleep(50)
            Thread.yield()
        }
    })

    Thread incThread = new Thread({
        while (true) {
            value = Math.min(value+1, 100)

            Thread.currentThread().setPriority(incPriority);
            Thread.sleep(50)
            Thread.yield()
        }
    })

    def start() {
        decThread.daemon = true
        decThread.start()

        incThread.daemon = true
        incThread.start()
    }
}