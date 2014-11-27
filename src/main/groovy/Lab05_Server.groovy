/**
 * @author yaroslav.yermilov
 */

def clients = [:]

def server = new ServerSocket(4444)

while(true) {
    server.accept { socket ->
        socket.withStreams { serverInput, serverOutput ->
            def message = serverInput.newReader().readLine()

            if (message.startsWith('HELLO|')) {
                def name = message.split('\\|')[1]
                def clientPort = Integer.parseInt(message.split('\\|')[2])

                clients[name] = clientPort
            }

            if (message.startsWith('MESSAGE|')) {
                clients.each { name, clientPort ->
                    if (message.split('\\|')[1] != name) {
                        client = new Socket("localhost", clientPort)
                        client.withStreams { clientInput, clientOutput ->
                            clientOutput << message
                        }
                    }
                }
            }
        }
    }
}