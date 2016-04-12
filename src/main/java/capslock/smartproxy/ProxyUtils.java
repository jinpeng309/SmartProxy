package capslock.smartproxy;

import com.google.common.net.HostAndPort;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by capslock.
 */
public final class ProxyUtils {
    private static final Pattern HTTP_PREFIX = Pattern.compile("^https?://(.*)", Pattern.CASE_INSENSITIVE);

    /**
     * Don't let anyone instantiate this class.
     */
    private ProxyUtils() {
        // This constructor is intentionally empty.
    }

    public static String extractHostAndPort(final String uri) {
        final Matcher matcher = HTTP_PREFIX.matcher(uri);
        String noHttpPrefixUri = uri;
        if (matcher.find()) {
            noHttpPrefixUri = matcher.group(1);
        }
        if (noHttpPrefixUri.indexOf("/") > 0) {
            noHttpPrefixUri = noHttpPrefixUri.substring(0, noHttpPrefixUri.indexOf("/"));
        }
        return noHttpPrefixUri;
    }

    public static InetSocketAddress getAddress(final HostAndPort hostAndPort) throws UnknownHostException {
        final InetAddress address = InetAddress.getByName(hostAndPort.getHostText());
        return new InetSocketAddress(address, hostAndPort.getPortOrDefault(80));
    }

    public static void main(String[] args) {
        System.out.println(extractHostAndPort("https://www.baidu.com/asfafa/fsfs"));
    }
}
