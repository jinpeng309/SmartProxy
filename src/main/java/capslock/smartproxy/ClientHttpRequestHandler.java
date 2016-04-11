package capslock.smartproxy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpRequest;

/**
 * Created by capslock.
 */
public final class ClientHttpRequestHandler extends SimpleChannelInboundHandler {
    @Override
    protected void messageReceived(final ChannelHandlerContext channelHandlerContext, final Object message)
            throws Exception {
        if (message instanceof HttpRequest) {
            System.out.println("http => " + message);
        }
    }
}
