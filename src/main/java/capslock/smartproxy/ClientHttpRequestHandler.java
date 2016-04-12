package capslock.smartproxy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * Created by capslock.
 */
public final class ClientHttpRequestHandler extends SimpleChannelInboundHandler {
    private final ProxySession session;

    public ClientHttpRequestHandler(final ProxySession session) {
        this.session = session;
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        System.out.println("exception in client " + cause);
    }

    @Override
    protected void messageReceived(final ChannelHandlerContext channelHandlerContext, final Object message)
            throws Exception {
        if (message instanceof FullHttpRequest) {
            final FullHttpRequest request = (FullHttpRequest) message;
            request.retain();
            session.fireReceivedMessageFromClient(channelHandlerContext, request);
        }
    }
}
