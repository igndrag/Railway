package NATrain.utils;

import NATrain.model.Model;
import NATrain.quads.EmptyQuad;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.signals.SignalLampType;
import NATrain.trackSideObjects.signals.SignalType;
import NATrain.trackSideObjects.switches.Switch;
import NATrain.trackSideObjects.switches.SwitchState;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.сontrolModules.ControlModule;
import NATrain.сontrolModules.OutputChannel;
import NATrain.сontrolModules.OutputChannelType;
import NATrain.сontrolModules.UniversalMQTTModule;

public class ModelMock {

    public static void MockModel() {

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Model.getMainGrid()[j][i] = new EmptyQuad(i, j);
            }
        }

        ControlModule controlModule = new UniversalMQTTModule("testModule");
        Model.getControlModules().add(controlModule);

        // topic name: NATrain/controlModules/testModule

        Signal testSignal = new Signal("S1", SignalType.STATION);
        OutputChannel outputChannel0 = new OutputChannel(OutputChannelType.SIGNAL_LAMP_OUTPUT, testSignal, SignalLampType.GREEN_LINE);
        testSignal.getLamps().put(SignalLampType.GREEN_LAMP, outputChannel0);
        outputChannel0.setChNumber(0);
        outputChannel0.setModule(controlModule);
        controlModule.getOutputChannels().put(0, outputChannel0);
        testSignal.getLamps().put(SignalLampType.RED_LAMP, outputChannel0);
        OutputChannel outputChannel1 = new OutputChannel(OutputChannelType.SIGNAL_LAMP_OUTPUT, testSignal, SignalLampType.RED_LAMP);
        testSignal.getLamps().put(SignalLampType.RED_LAMP, outputChannel1);
        outputChannel1.setChNumber(1);
        outputChannel1.setModule(controlModule);
        controlModule.getOutputChannels().put(1, outputChannel1);
        testSignal.getLamps().put(SignalLampType.GREEN_LAMP, outputChannel1);

        Model.getSignals().put("S1", testSignal);
        Model.getSignals().put("M1", new Signal("M1", SignalType.TRIMMER));

        Switch oneSwitch = new Switch("1");
        Switch twoSwitch = new Switch("2");
        Switch threeSwitch = new Switch("3");

        oneSwitch.getPlusInputChannel().setChNumber(2);
        oneSwitch.getPlusInputChannel().setModule(controlModule);
        oneSwitch.getMinusInputChannel().setChNumber(3);
        oneSwitch.getMinusInputChannel().setModule(controlModule);
        oneSwitch.getPlusOutputChannel().setChNumber(2);
        oneSwitch.getPlusOutputChannel().setModule(controlModule);
        oneSwitch.getMinusInputChannel().setChNumber(3);
        oneSwitch.getMinusOutputChannel().setModule(controlModule);
        controlModule.getInputChannels().put(2, oneSwitch.getPlusInputChannel());
        controlModule.getInputChannels().put(3, oneSwitch.getMinusInputChannel());
        controlModule.getOutputChannels().put(2, oneSwitch.getPlusOutputChannel());
        controlModule.getOutputChannels().put(3, oneSwitch.getMinusOutputChannel());
        threeSwitch.setParedSwitch(twoSwitch);
        twoSwitch.setParedSwitch(threeSwitch);
        threeSwitch.setNormalState(SwitchState.MINUS);


        Model.getSwitches().put("1", oneSwitch);
        Model.getSwitches().put("2", twoSwitch);
        Model.getSwitches().put("3", threeSwitch);
        Model.getSwitches().put("4", new Switch("4"));

        TrackSection oneTrackSection = new TrackSection ("1-3SP");
        TrackSection twoTrackSection = new TrackSection ("SP");
        oneSwitch.setTrackSection(oneTrackSection);
        controlModule.getInputChannels().put(0, oneTrackSection.getInputChannel());

        Model.getTrackSections().put("1-3SP", oneTrackSection);
        Model.getTrackSections().put("SP", twoTrackSection);
        Model.getTrackSections().put("2-4SP", new TrackSection("2-4SP"));
        Model.getTrackSections().put("6-8SP", new TrackSection("6-8SP"));
    }
}