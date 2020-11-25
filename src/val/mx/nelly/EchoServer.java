package val.mx.nelly;

public class EchoServer extends Server{
    public EchoServer(int pPort) {
        super(pPort);
    }

    @Override
    public void processNewConnection(String pClientIP, int pClientPort) {
        send(pClientIP,pClientPort,"Sie haben mein Land betreten.");

    }

    @Override
    public void processMessage(String pClientIP, int pClientPort, String pMessage) {

        if(pClientIP.equals("10.16.102.13")) return;
        if(pClientIP.equals("10.16.102.3")) return;

        System.out.println(pMessage);
        System.out.println();
        System.out.printf("Von IP: %s ",pClientIP);
        send(pClientIP,pClientPort,pMessage);
        send(pClientIP,pClientPort,"Vielen Dank fuer ihren Einkauf.");
    }

    @Override
    public void processClosingConnection(String pClientIP, int pClientPort) {

    }
}
