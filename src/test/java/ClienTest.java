import java.util.Date;

class ClientTest {

    public static void main(String[] args) {

        try {

            MinaClient client = new MinaClient();
            client.createSocketConnector();
            client.openServerConnection();

            while (true) {
                Date date = new Date();
                client.sendMessage("Client message [ " + date.getTime() + " ]");

                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}