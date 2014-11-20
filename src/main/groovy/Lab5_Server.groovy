/**
 * @author yaroslav.yermilov
 */

def clients = [5555, 6666, 7777]

def server = new ServerSocket(4444)

while(true) {
    server.accept { socket ->
        socket.withStreams { serverInput, serverOutput ->
            def buffer = serverInput.newReader().readLine()

            clients.each { clientPort ->
                client = new Socket("localhost", clientPort)
                client.withStreams { clientInput, clientOutput ->
                    clientOutput << buffer
                }
            }
        }
    }
}