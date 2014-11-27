import groovy.transform.EqualsAndHashCode

import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 * @author yaroslav.yermilov
 */

def random = new Random()
def busmap = new BusMap()

new Thread({
    while (true) {
        busmap.lock.writeLock().lock()

        if (!busmap.roads.isEmpty()) {
            def index = random.nextInt(busmap.roads.size())
            def newCost = random.nextInt(100)
            busmap.roads[index].cost = newCost
            println "Cost of road between ${busmap.roads[index].station1.name} and ${busmap.roads[index].station2.name} changed to ${newCost}"
        }

        busmap.lock.writeLock().unlock()
        Thread.sleep(random.nextInt(10000))
    }
}).start()

new Thread({
    while (true) {
        busmap.lock.writeLock().lock()

        if (!busmap.stations.isEmpty()) {
            Station station1 = busmap.stations[random.nextInt(busmap.stations.size())]
            Station station2 = busmap.stations[random.nextInt(busmap.stations.size())]
            def cost = random.nextInt(100)
            busmap.roads << new Road(station1: station1, station2: station2, cost: cost)
            println "New road added: between ${station1.name} and ${station2.name} with cost ${cost}"
        }

        busmap.lock.writeLock().unlock()
        Thread.sleep(random.nextInt(10000))
    }
}).start()

new Thread({
    while (true) {
        busmap.lock.writeLock().lock()

        if (!busmap.roads.isEmpty()) {
            int index = random.nextInt(busmap.roads.size())
            println "Road removed: between ${busmap.roads[index].station1.name} and ${busmap.roads[index].station2.name} with cost ${busmap.roads[index].cost}"
            busmap.roads.remove(index)
        }

        busmap.lock.writeLock().unlock()
        Thread.sleep(random.nextInt(50000))
    }
}).start()

new Thread({
    while (true) {
        busmap.lock.writeLock().lock()

        Station newStation = new Station()
        busmap.stations << newStation
        println "New station added: ${newStation.name}"

        busmap.lock.writeLock().unlock()
        Thread.sleep(random.nextInt(10000))
    }
}).start()

new Thread({
    while (true) {
        busmap.lock.writeLock().lock()

        if (!busmap.stations.isEmpty()) {
            int index = random.nextInt(busmap.stations.size())
            println "Station ${busmap.stations[index].name} removed"
            def i = 0
            while (i < busmap.roads.size){
                if (busmap.roads[i].station1.name == busmap.stations[index].name ||  busmap.roads[i].station2.name == busmap.stations[index].name) {
                    busmap.roads.remove(i)
                } else {
                    i++
                }
            }
            busmap.stations.remove(index)
        }

        busmap.lock.writeLock().unlock()
        Thread.sleep(random.nextInt(50000))
    }
}).start()

new Thread({
    while (true) {
        busmap.lock.readLock().lock()

        int[][] floyd = new int[busmap.stations.size()][busmap.stations.size()]
        for (int i = 0; i < busmap.stations.size(); i++) {
            for (int j = 0; j < busmap.stations.size(); j++) {
                floyd[i][j] = Integer.MAX_VALUE / 3
            }
            floyd[i][i] = 0
        }

        busmap.roads.forEach { road ->
            int i = busmap.stations.indexOf(new Station(road.station1.name))
            int j = busmap.stations.indexOf(new Station(road.station2.name))

            floyd[i][j] = Math.min(floyd[i][j], road.cost)
            floyd[j][i] = Math.min(floyd[j][i], road.cost)
        }

        for (int i = 0; i < busmap.stations.size(); i++) {
            for (int j = 0; j < busmap.stations.size(); j++) {
                for (int k = 0; k < busmap.stations.size(); k++) {
                    floyd[i][j] = Math.min(floyd[i][k] + floyd[k][j], floyd[i][j])
                }
            }
        }

        print " ".padLeft(15)
        for (int i = 0; i < busmap.stations.size(); i++) {
            print "${busmap.stations[i].name} ".padLeft(15)
        }
        println()

        for (int i = 0; i < busmap.stations.size(); i++) {
            print "${busmap.stations[i].name} ".padLeft(15)
            for (int j = 0; j < busmap.stations.size(); j++) {
                if (floyd[i][j] > Integer.MAX_VALUE / 4) {
                    print "INF ".padLeft(15)
                } else {
                    print "${floyd[i][j]} ".padLeft(15)
                }
            }
            println()
        }

        busmap.lock.readLock().unlock()
        Thread.sleep(random.nextInt(10000))
    }
}).start()

@EqualsAndHashCode
class BusMap {
    List<Station> stations = []
    List<Road> roads = []
    ReadWriteLock lock = new ReentrantReadWriteLock()
}

@EqualsAndHashCode
class Station {
    static INDEX = 0

    String name

    Station() {
        this("Station #${INDEX++}")
    }

    Station(String name) {
        this.name = name
    }
}

@EqualsAndHashCode
class Road {
    Station station1
    Station station2
    int cost
}
