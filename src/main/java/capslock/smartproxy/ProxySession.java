package capslock.smartproxy;

import com.google.common.net.HostAndPort;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by capslock.
 */
public final class ProxySession {
    private static final Logger log = LoggerFactory.getLogger(ProxySession.class);
    private final String clientIp;

    public ProxySession(final String clientIp) {
        this.clientIp = clientIp;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void fireReceivedMessageFromClient(final ChannelHandlerContext channelHandlerContext,
            final FullHttpRequest message) {
        final String requestUri = message.getUri();
        final HostAndPort hostAndPort = HostAndPort.fromString(requestUri);
        final Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ProxyToServerConnectionInitializer(this))
                .connect(hostAndPort.getHostText(), hostAndPort.getPort());
    }
}
