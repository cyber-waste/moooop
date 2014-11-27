import groovy.beans.Bindable
import groovy.swing.SwingBuilder

def battlefield = new Battlefield9()

battlefield.decThread.daemon = true
battlefield.decThread.start()

battlefield.incThread.daemon = true
battlefield.incThread.start()

new SwingBuilder().edt {
    frame(title:'Lab 08', size:[300, 300], show: true) {
        gridLayout(columns:  1, rows: 3)

        button (text: 'START DECREMENT', actionPerformed: { battlefield.startDecrement() })

        slider(minimum: 0, maximum: 100, value: bind(source: battlefield, sourceProperty: 'value'))

        button (text: 'START INCREMENT', actionPerformed: { battlefield.startIncrement() })
    }
}

class Battlefield9 {

    Object mutex = new Object()

    boolean runDecrement = false
    boolean runIncrement = false

    @Bindable
    int value = 50

    Thread decThread = new Thread({
        synchronized (mutex) {
            while (true) {
                while (!runDecrement) { mutex.wait() }

                value = Math.max(value-1, 0)

                Thread.sleep(50)
                Thread.yield()
            }
        }
    })

    Thread incThread = new Thread({
        synchronized (mutex) {
            while (true) {
                while (!runIncrement) { mutex.wait() }

                value = Math.min(value+1, 100)

                Thread.sleep(50)
                Thread.yield()
            }
        }
    })

    def startDecrement() {
        runDecrement = true
        runIncrement = false

        synchronized (mutex) {
            mutex.notifyAll()
        }
    }

    def startIncrement() {
        runDecrement = false
        runIncrement = true

        synchronized (mutex) {
            mutex.notifyAll()
        }
    }
}