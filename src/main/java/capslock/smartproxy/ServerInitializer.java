package capslock.smartproxy;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by capslock.
 */
public final class ServerInitializer extends ChannelInitializer<SocketChannel> {
    private static final int MAX_INACTIVE_TIME = 300;

    @Override
    protected void initChannel(final SocketChannel ch) throws Exception {
        final ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(MAX_INACTIVE_TIME, MAX_INACTIVE_TIME, MAX_INACTIVE_TIME));
        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpRequestEncoder());
        pipeline.addLast(new HttpResponseEncoder());
        pipeline.addLast(new HttpResponseDecoder());
        pipeline.addLast(new ClientHttpRequestHandler());
    }
}
