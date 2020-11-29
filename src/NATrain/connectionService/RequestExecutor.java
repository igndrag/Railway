package NATrain.connectionService;

import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RequestExecutor extends Thread{
    private ConnectionService connectionService;

    public void setConnectionService(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    private static final ConcurrentLinkedQueue<String> requestPool = new ConcurrentLinkedQueue<>();
    protected static final ConcurrentLinkedQueue<String> responsePool = new ConcurrentLinkedQueue<>();

    public static ConcurrentLinkedQueue<String> getRequestPool() {
        return requestPool;
    }

    public static ConcurrentLinkedQueue<String> getResponsePool() {
        return responsePool;
    }

    /* Request format COMMANDCODE_CMADDR(2 digit)_CHNUM(2 digit)

            example 20_20_03

            Response format RESCODE_CMADDR(2 digit)_CHNUM(2 digit)

            example  1_11_10
        */
    @Override
    public void run() {
        while (true) {
            if (!requestPool.isEmpty())
                executeRequest(requestPool.poll());
            if (!responsePool.isEmpty())
                processResponse(responsePool.poll());
            while (requestPool.isEmpty() && responsePool.isEmpty())
                Thread.yield();
            }
    }

    private void processResponse(String response) {
        //TODO
    }

    private void executeRequest(String request) {
        //TODO
    }


}
