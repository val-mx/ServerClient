package val.mx.nelly;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("TEST");
        HTTPServer httpServer = new HTTPServer(8000);

        System.out.println(httpServer.isOpen());

        Client client = new EchoClient("10.16.5.4",8000);

        Scanner s = new Scanner(System.in);

        while (true) {
            String str = s.nextLine();

            client.send(str);
        }
//
//        EchoClient client = new EchoClient("nellysachs.de",8000);
//        System.out.println(client.isConnected());




    }
}
