/**
 * 3. Изобразить в апплете приближающийся издали шар, удаляющийся шар. Шар должен двигаться с постоянной скоростью.
 * @author yaroslav.yermilov
 */
import groovy.beans.Bindable
import groovy.swing.SwingBuilder
import groovy.swing.j2d.GraphicsBuilder
import groovy.swing.j2d.GraphicsPanel

class Circle {
    @Bindable int radius = 0
}

def X_SIZE = 300
def Y_SIZE = 300

def c = new Circle()

def graphicsOperation = new GraphicsBuilder().group {
    circle(cx: X_SIZE / 2, cy: Y_SIZE / 2, radius: bind(source: c, sourceProperty: 'radius'), fill: 'yellow')
}

SwingBuilder.build {
    frame(size: [X_SIZE, Y_SIZE], locationRelativeTo: null, show: true) {
        panel(new GraphicsPanel(), graphicsOperation: graphicsOperation)
    }
}

delta = 1

while (true) {
    c.radius = c.radius + delta

    if (c.radius > X_SIZE * 2/6) {
        delta = -1
    }
    if (c.radius < X_SIZE * 1/6) {
        delta = 1
    }

    Thread.sleep(100)
}