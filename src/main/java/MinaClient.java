import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;


public class MinaClient {

    private NioSocketConnector socketConnector = null;// socket connector
    private IoSession socketSession = null; // minanın bize atadıgı session
    private final String SERVER_ADRESS = "127.0.0.1"; // server ip(deneme amaçlı localhost)
    private final int SERVER_PORT = 5003; // server port

    public boolean isServerConnectionAlive() {
        return socketSession != null ? socketSession.isConnected() : null; // session null degil ve hala baglı?
    }

    public void createSocketConnector() {
        try {
            //connector olustur ve filterları ekle
            socketConnector = new NioSocketConnector();
            socketConnector.getFilterChain().addLast("logger", new LoggingFilter());
            socketConnector.getFilterChain().addLast("protocol", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
            //socket handler set
            socketConnector.setHandler(new TimeServerHandler());
            socketConnector.getSessionConfig().setReadBufferSize(2048);
            socketConnector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 60);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeServerConnection() {
        //senkron kapat
        if (socketSession != null) {
            socketSession.close(false);
        }
        if (socketConnector != null) {
            socketConnector.dispose();
        }
    }

    public void openServerConnection() {

        try {
            // session baglı degilse once bir connect olalım sonrasında session alalım.
            if (socketSession == null || socketSession != null ? !socketSession.isConnected() : null) {
                ConnectFuture future = socketConnector.connect(new InetSocketAddress(SERVER_ADRESS, SERVER_PORT));
                future.awaitUninterruptibly();
                socketSession = future.getSession();
            } else {
                System.out.println("Session already active.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        // server alive degilse once acmayi deneyeliö
        if (!isServerConnectionAlive()) {
            openServerConnection();
        }
        // server alive , o zaman sessiona yazalim mesajimizi.
        if (isServerConnectionAlive()) {
            System.out.println("session is open, sending message : " + message);
            socketSession.write(message);
        }
    }
}