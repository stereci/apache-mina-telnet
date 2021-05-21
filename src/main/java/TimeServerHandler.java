
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class TimeServerHandler extends IoHandlerAdapter {

// Logger logger = LoggerFactory.getLogger(TimeServerHandler.class);

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
    }

    @Override
    public void messageSent(IoSession ioSession, Object obj) throws Exception {
        System.out.println("\nSent message :" + obj.toString());
    }

    @Override
    public void messageReceived(IoSession session, Object obj) throws Exception {
        String message = obj.toString();
        System.out.println("\nNew Message Received:" + message);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        System.out.println("\nIDLE " + session.getIdleCount(status));
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        System.out.println("\nSession closed.  Session ID:" + session.getId() + " - Local address:" + session.getLocalAddress() + " - Remote Address:"
                + session.getRemoteAddress() + " - Service Address:" + session.getServiceAddress() + " - TransportMetadata:" + session.getTransportMetadata());

// connector.setSessionState(SessionState.closed);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        System.out.println("\nSession created .. ");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
        System.out.println("\nSession opened. Session ID:" + session.getId() + " - Local address:" + session.getLocalAddress() + " - Remote Address:"
                + session.getRemoteAddress() + " - Service Address:" + session.getServiceAddress() + " - TransportMetadata:" + session.getTransportMetadata());

// connector.setSessionState(SessionState.open);
    }

}