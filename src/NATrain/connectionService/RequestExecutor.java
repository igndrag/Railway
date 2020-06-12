package NATrain.connectionService;

import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RequestExecutor extends Thread{
    private ConnectionService connectionService;

    public void setConnectionService(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    private static ConcurrentLinkedQueue<String> requestPool = new ConcurrentLinkedQueue<>();
    protected static ConcurrentLinkedQueue<String> responsePool = new ConcurrentLinkedQueue<>();

    public static ConcurrentLinkedQueue<String> getRequestPool() {
        return requestPool;
    }

    public static ConcurrentLinkedQueue<String> getResponsePool() {
        return responsePool;
    }

    /* Request format REQCODE(1 digit)_CMADDR(3 digit)_CHNUM(2 digit)
                            1 - global request
                            2 - check input
                            3 - command
            example 2_002_03

            Response format RESCODE(1 digit)_CMADDR(3 digit)_CHNUM(2 digit)
                            1 - OK
                            2 - wrong request
                            3 - fail or timeout
            example  1_101_10
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
