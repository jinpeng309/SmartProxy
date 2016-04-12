package capslock.smartproxy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by capslock.
 */
public final class ProxyToServerConnectionInitializer extends ChannelInitializer<SocketChannel> {
    private static final int MAX_INACTIVE_TIME = 30000;
    private final ProxySession session;
    private final ChannelHandlerContext clientConnectionContext;

    public ProxyToServerConnectionInitializer(final ProxySession session,
            final ChannelHandlerContext clientConnectionContext) {
        this.session = session;
        this.clientConnectionContext = clientConnectionContext;
    }

    @Override
    protected void initChannel(final SocketChannel ch) throws Exception {
        final ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(MAX_INACTIVE_TIME, MAX_INACTIVE_TIME, MAX_INACTIVE_TIME));
        pipeline.addLast(new HttpRequestEncoder());
        pipeline.addLast(new HttpResponseDecoder());
        pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
        pipeline.addLast(new ServerHttpResponseHandler(session, clientConnectionContext));
    }
}
