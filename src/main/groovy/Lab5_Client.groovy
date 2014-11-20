/**
 * @author yaroslav.yermilov
 */

def clientPort = 7777
def client = new ServerSocket(clientPort)

new Thread({
    while (true) {
        client.accept { socket ->
            socket.withStreams { clientInput, clientOutput ->
                def buffer = clientInput.newReader().readLine()

                println ">>> ${buffer}"
            }
        }
    }
}).start()


BufferedReader.metaClass.readInt = {
    Integer.parseInt(delegate.readLine())
}
def console = new BufferedReader(new InputStreamReader(System.in))


while(true) {
    def message = console.readLine()
    new Socket("localhost", 4444).withStreams { serverInput, serverOutput ->
        serverOutput << message
    }
}