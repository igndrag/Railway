package NATrain.UI.scenario;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TimerAction extends ScenarioAction{
    private Integer timerVal_1 = 0;
    private Integer timerVal_2 = 0;
    private Boolean random = false;
    private Random randomGenerator;

    public TimerAction(int timerVal_1, int timerVal_2,  boolean random) {
        super();
        this.timerVal_1 = timerVal_1;
        this.timerVal_2 = timerVal_2;
        if (random) {
            this.randomGenerator = new Random(timerVal_1);
        }
    }

    @Override
    void execute() {
        int sleepTime = timerVal_1;
        if (random) {
            sleepTime = randomGenerator.nextInt(timerVal_2 + 1);
        }
        try {
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
