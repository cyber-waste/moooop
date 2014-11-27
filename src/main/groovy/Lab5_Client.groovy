/**
 * @author yaroslav.yermilov
 */
def clientName = 'five'
def clientPort = 5555
def client = new ServerSocket(clientPort)

new Thread({
    while (true) {
        client.accept { socket ->
            socket.withStreams { clientInput, clientOutput ->
                def message = clientInput.newReader().readLine()
                if (message.startsWith('MESSAGE|')) {
                    def name = message.split('\\|')[1]
                    def text = message.split('\\|')[2]

                    println "$name>>> ${text}"
                }
            }
        }
    }
}).start()

new Socket("localhost", 4444).withStreams { serverInput, serverOutput ->
    serverOutput << "HELLO|$clientName|$clientPort"
}

def console = new BufferedReader(new InputStreamReader(System.in))
while(true) {
    def message = console.readLine()
    new Socket("localhost", 4444).withStreams { serverInput, serverOutput ->
        serverOutput << "MESSAGE|$clientName|$message"
    }
}