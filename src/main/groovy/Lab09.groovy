/**
 * @author yaroslav.yermilov
 */

def random = new Random()
def powers = []
256.times {
    powers << random.nextInt(1000)
}

while (powers.size() > 1) {
    println powers

    def threads = []
    def winners = new int[powers.size() / 2]
    (powers.size() / 2).times { index ->
        threads << new Thread({
            winners[index] = Math.max(powers[2*index], powers[2*index + 1])
        })
    }
    threads.each { thread -> thread.start() }
    threads.each { thread -> thread.join() }

    powers = winners
}

println powers[0]
