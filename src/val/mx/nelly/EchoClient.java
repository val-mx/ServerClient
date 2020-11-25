package val.mx.nelly;

public class EchoClient extends Client {


    public EchoClient(String pServerIP, int pServerPort) {
        super(pServerIP, pServerPort);
    }

    @Override
    public void processMessage(String pMessage) {
        System.out.println(pMessage);
    }

}
