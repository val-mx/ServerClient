package val.mx.nelly;

import java.util.HashMap;

public class HTTPServer extends Server {
    public HTTPServer(int pPort) {
        super(pPort);
    }

    private final HashMap<String, BuyRequest> actions = new HashMap<>();

    @Override
    public void processNewConnection(String pClientIP, int pClientPort) {
        System.out.println(pClientIP);
        send(pClientIP,pClientPort,"User wurde verbunden");
    }

    @Override
    public void processMessage(String pClientIP, int pClientPort, String pMessage) {

        String[] befehle = pMessage.split(":");

        System.out.println(pMessage);
        if(befehle.length == 0) return;

        switch (befehle[0].toLowerCase()) {

            case "anmeldung":
                if(actions.containsKey(pClientIP)) {
                    send(pClientIP,pClientPort,"User ist schon angemeldet");
                    return;
                }

                actions.put(pClientIP,null);
                send(pClientIP,pClientPort,"User wurde angemeldet");
                return;
            case "abmeldung":
                if(actions.containsKey(pClientIP)) {
                    send(pClientIP,pClientPort,"User war nicht angemeldet");
                    return;
                }
                actions.remove(pClientIP);
                send(pClientIP,pClientPort,"User wurde erfolgreich abgemeldet");
                return;
            case "bestaetigung":
                if(actions.containsKey(pClientIP)) {
                    if(befehle.length == 2) {

                        BuyRequest request = actions.get(pClientIP);

                        if (request != null)

                        switch (befehle[1]) {
                            case "ja":
                                send(pClientIP,pClientPort,String.format("Vielen dank fuer ihren Einkauf: T shirt Farbe %s und Groesse %s wurde bestellt! lIEFERUNG IN 200 STUNDEN",request.getColor(),request.getSize()));
                                actions.put(pClientIP,null);
                                break;

                            case "nein":
                                actions.put(pClientIP,null);
                                send(pClientIP,pClientPort,":( Kein Einkauf");
                        }

                        else                 send(pClientIP,pClientPort,"User hat sein Tshirt noch nicht ausgewaehlt");

                    }else {
                        send(pClientIP,pClientPort,"Nicht genug? zu viele parameter");
                    }

                }else {
                    send(pClientIP,pClientPort,"User ist noch nicht angemeldet!");
                }
                return;
            case "tshirt":
                if(actions.containsKey(pClientIP))
                if(befehle.length == 3) {
                    BuyRequest request = new BuyRequest();

                    request.setColor(befehle[1]);
                    request.setSize(befehle[2]);

                    actions.put(pClientIP,request);

                } else send(pClientIP,pClientPort,"Nicht genug? zu viele parameter");
                else send(pClientIP,pClientPort,"User ist noch nicht angemeldet!");
                return;
            default:
                send(pClientIP,pClientPort,"Unbekannter Befehl");
        }



    }

    @Override
    public void processClosingConnection(String pClientIP, int pClientPort) {
        actions.remove(pClientIP);
    }

    public class BuyRequest {

        String size,color;

        public String getSize() {
            return size;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getColor() {
            return color;
        }
    }
}