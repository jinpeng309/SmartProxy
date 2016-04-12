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
    protected void messageReceived(final ChannelHandlerContext channelHandlerContext, final Object message)
            throws Exception {
        if (message instanceof FullHttpRequest) {
            session.fireReceivedMessageFromClient(channelHandlerContext, (FullHttpRequest) message);
        }
    }
}
