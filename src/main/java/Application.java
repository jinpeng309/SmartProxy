import capslock.smartproxy.SmartProxyServer;

import java.util.concurrent.ExecutionException;

/**
 * Created by capslock.
 */
public class Application {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new SmartProxyServer(80).start();
    }
}
