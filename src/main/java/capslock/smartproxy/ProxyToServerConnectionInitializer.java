package capslock.smartproxy;

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
    private static final int MAX_INACTIVE_TIME = 300;
    private final ProxySession session;

    public ProxyToServerConnectionInitializer(final ProxySession session) {
        this.session = session;
    }

    @Override
    protected void initChannel(final SocketChannel ch) throws Exception {
        final ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(MAX_INACTIVE_TIME, MAX_INACTIVE_TIME, MAX_INACTIVE_TIME));
        pipeline.addLast(new HttpRequestEncoder());
        pipeline.addLast(new HttpResponseDecoder());
        pipeline.addLast(new HttpObjectAggregator(1024 * 8));
    }
}
