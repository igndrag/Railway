package NATrain.UI.scenario;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Scenario extends Thread {

    private final String id;

    public static Scenario TEST_SCENARIO = new Scenario("TEST_SCENARIO");

    private Boolean loop = false;
    private ScenarioState state;
    private Integer step = 0;
    private Queue<ScenarioAction> scenarioActionsQueue;

    public Scenario(String id) {
        super();
        this.id = id;
        this.state = ScenarioState.CREATED;
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
        state = ScenarioState.ACTIVE;
        while (loop) {

        }
    }

    @Override
    public String toString() {
        return id;
    }
}
