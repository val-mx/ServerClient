package val.mx.nelly;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        TicTacToeServer httpServer = new TicTacToeServer();

        System.out.println(httpServer.isOpen());

        EchoClient client = new EchoClient("10.16.5.4",16785);

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
