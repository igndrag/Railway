package NATrain.UI.scenario;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Scenario extends Thread {
    private ScenarioState state = ScenarioState.CREATED;

    public static Scenario TEST_SCENARIO = new Scenario();

    private Boolean loop = false;
    private Integer step = 0;
    private Queue<ScenarioAction> scenarioActionsQueue;

    public Scenario() {
        super();
        this.scenarioActionsQueue = new ConcurrentLinkedQueue<ScenarioAction>() {
        };
    }

    public void startScenario() {
        //init property change listeners...
    }

    @Override
    public void run() {
        super.run();
        ConcurrentLinkedQueue<ScenarioAction> localQueueCopy = new ConcurrentLinkedQueue<ScenarioAction>(scenarioActionsQueue);
        this.setDaemon(true);

    }
}
