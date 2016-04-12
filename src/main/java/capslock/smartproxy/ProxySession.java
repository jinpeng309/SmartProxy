package capslock.smartproxy;

import com.google.common.net.HostAndPort;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

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
        final String noHttpPrefixUri = ProxyUtils.extractHostAndPort(requestUri);
        final HostAndPort hostAndPort = HostAndPort.fromString(noHttpPrefixUri);
        try {
            final InetSocketAddress address = ProxyUtils.getAddress(hostAndPort);
            final Bootstrap bootstrap = new Bootstrap();
            final Channel channel = bootstrap
                    .group(new NioEventLoopGroup())
                    .channel(NioSocketChannel.class)
                    .handler(new ProxyToServerConnectionInitializer(this, channelHandlerContext))
                    .connect(address).sync().channel();
            channel.writeAndFlush(message).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(final ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        channel.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
                    }
                }
            });
        } catch (UnknownHostException e) {
            log.error(e.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return "ProxySession[" + "clientIp='" + getClientIp() + "]";
    }
}
