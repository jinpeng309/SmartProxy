package capslock.smartproxy;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by capslock.
 */
public final class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(final SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new IdleStateHandler(300, 300, 300));
    }
}
