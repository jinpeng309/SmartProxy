package capslock.smartproxy;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.LastHttpContent;

/**
 * Created by capslock.
 */
public final class ServerHttpResponseHandler extends SimpleChannelInboundHandler {
    private final ProxySession session;
    final ChannelHandlerContext clientConnectionContext;

    public ServerHttpResponseHandler(final ProxySession session, final ChannelHandlerContext clientConnectionContext) {
        this.session = session;
        this.clientConnectionContext = clientConnectionContext;
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        System.out.println("exception in server response");
    }

    @Override
    protected void messageReceived(final ChannelHandlerContext channelHandlerContext, final Object message)
            throws Exception {
        if (message instanceof FullHttpResponse) {
            System.out.println("server response " + message);
            final FullHttpResponse response = (FullHttpResponse) message;
            response.retain();
            clientConnectionContext.writeAndFlush(response).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(final ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        clientConnectionContext.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
                    }
                }
            });
        }
    }
}
