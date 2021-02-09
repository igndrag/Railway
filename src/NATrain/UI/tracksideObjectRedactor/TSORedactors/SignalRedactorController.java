package NATrain.UI.tracksideObjectRedactor.TSORedactors;

import NATrain.UI.tracksideObjectRedactor.TracksideObjectRedactorController;
import NATrain.model.Model;
import NATrain.trackSideObjects.*;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.signals.SignalLampType;
import NATrain.trackSideObjects.signals.SignalState;
import NATrain.trackSideObjects.signals.SignalType;
import NATrain.сontrolModules.OutputChannel;
import NATrain.сontrolModules.OutputChannelType;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class SignalRedactorController extends TracksideObjectRedactorController {

    @FXML
    private CheckBox yellowLampCheckBox;
    @FXML
    private CheckBox greenLampCheckBox;
    @FXML
    private CheckBox redLampCheckBox;
    @FXML
    private CheckBox secondYellowLampCheckBox;
    @FXML
    private CheckBox secondGreenLampCheckBox;
    @FXML
    private CheckBox whiteLampCheckBox;
    @FXML
    private CheckBox greenLineCheckBox;
    @FXML
    private CheckBox trimmerRedLampCheckBox;
    @FXML
    private ToggleButton trimmerToggleButton;
    @FXML
    private ToggleButton stationToggleButton;
    @FXML
    private Pane signalPreview;

    private Shape yellowLampElement;
    private Shape greenLampElement;
    private Shape redLampElement;
    private Shape secondYellowLampElement;
    private Shape secondGreenLampElement;
    private Shape whiteLampElement;
    private Shape greenLineElement;

    private Signal signal;

    @Override
    public void init(TracksideObject tracksideObject, TableView<TracksideObject> tableView, ObservableList<TracksideObject> observableList) {
        signal = (Signal) tracksideObject;
        this.tableView = tableView;
        this.observableList = observableList;
        initTextField(Model.getSignals(), signal);

        ToggleGroup toggleGroup = new ToggleGroup();
        trimmerToggleButton.setToggleGroup(toggleGroup);
        stationToggleButton.setToggleGroup(toggleGroup);

        stationToggleButton.setSelected(true);

        switch (signal.getSignalType()) {
            case TRIMMER:
                trimmerToggleButton.setSelected(true);
                yellowLampCheckBox.setDisable(true);
                greenLampCheckBox.setDisable(true);
                redLampCheckBox.setDisable(true);
                secondYellowLampCheckBox.setDisable(true);
                secondGreenLampCheckBox.setDisable(true);
                whiteLampCheckBox.setDisable(true);
                greenLineCheckBox.setDisable(true);
                break;
            case STATION:
                stationToggleButton.setSelected(true);
                trimmerRedLampCheckBox.setDisable(true);
                break;
        }
        trimmerToggleButton.setOnAction(event -> {
            trimmerRedLampCheckBox.setDisable(false);
            signal.getLamps().clear();
            yellowLampCheckBox.setSelected(false);
            yellowLampCheckBox.setDisable(true);
            setNotActive(yellowLampElement);
            greenLampCheckBox.setSelected(false);
            greenLampCheckBox.setDisable(true);
            setNotActive(greenLampElement);
            redLampCheckBox.setSelected(false);
            redLampCheckBox.setDisable(true);
            setNotActive(redLampElement);
            secondYellowLampCheckBox.setSelected(false);
            secondYellowLampCheckBox.setDisable(true);
            setNotActive(secondYellowLampElement);
            secondGreenLampCheckBox.setSelected(false);
            secondGreenLampCheckBox.setDisable(true);
            setNotActive(secondGreenLampElement);
            whiteLampCheckBox.setSelected(false);
            whiteLampCheckBox.setDisable(true);
            setNotActive(whiteLampElement);
            greenLineCheckBox.setSelected(false);
            greenLineCheckBox.setDisable(true);
            setNotActive(greenLineElement);

            signal.getLamps().put(SignalLampType.WHITE_LAMP, new OutputChannel(OutputChannelType.SIGNAL_LAMP_OUTPUT, signal, SignalLampType.WHITE_LAMP));
            if (trimmerRedLampCheckBox.isSelected()) {
                signal.getLamps().put(SignalLampType.RED_LAMP, new OutputChannel(OutputChannelType.SIGNAL_LAMP_OUTPUT, signal, SignalLampType.RED_LAMP));
            } else {
                signal.getLamps().put(SignalLampType.BLUE_LAMP, new OutputChannel(OutputChannelType.SIGNAL_LAMP_OUTPUT, signal, SignalLampType.BLUE_LAMP));
            }
        });
        stationToggleButton.setOnAction(event -> {
            signal.getLamps().clear();
            trimmerRedLampCheckBox.setDisable(true);
            yellowLampCheckBox.setDisable(false);
            greenLampCheckBox.setDisable(false);
            redLampCheckBox.setDisable(false);
            secondYellowLampCheckBox.setDisable(false);
            secondGreenLampCheckBox.setDisable(false);
            whiteLampCheckBox.setDisable(false);
            greenLineCheckBox.setDisable(false);
        });

        if (signal.getClosedSignalState() == SignalState.RED) {
            trimmerRedLampCheckBox.setSelected(true);
        }

        trimmerRedLampCheckBox.setOnAction(event -> {
            if (trimmerRedLampCheckBox.isSelected()) {
                signal.getLamps().put(SignalLampType.RED_LAMP, new OutputChannel(OutputChannelType.SIGNAL_LAMP_OUTPUT, signal, SignalLampType.RED_LAMP));
            } else {
                signal.getLamps().put(SignalLampType.BLUE_LAMP, new OutputChannel(OutputChannelType.SIGNAL_LAMP_OUTPUT, signal, SignalLampType.BLUE_LAMP));
            }
        });

        if (!signal.getId().equals(Signal.INITIAL_SIGNAL_NAME)) {
            trimmerToggleButton.setDisable(true);
            stationToggleButton.setDisable(true);
        }
        // init preview pane
        createPreview();

        if (signal.getSignalType() != SignalType.TRIMMER) {
            if (signal.getLamps().containsKey(SignalLampType.YELLOW_LAMP)) {
                yellowLampCheckBox.setSelected(true);
            } else {
                setNotActive(yellowLampElement);
            }

            if (signal.getLamps().containsKey(SignalLampType.GREEN_LAMP)) {
                greenLampCheckBox.setSelected(true);
            } else {
                setNotActive(greenLampElement);
            }

            if (signal.getLamps().containsKey(SignalLampType.RED_LAMP)) {
                redLampCheckBox.setSelected(true);
            } else {
                setNotActive(redLampElement);
            }

            if (signal.getLamps().containsKey(SignalLampType.SECOND_YELLOW_LAMP)) {
                secondYellowLampCheckBox.setSelected(true);
            } else {
                setNotActive(secondYellowLampElement);
            }

            if (signal.getLamps().containsKey(SignalLampType.SECOND_GREEN_LAMP)) {
                secondGreenLampCheckBox.setSelected(true);
            } else {
                setNotActive(secondGreenLampElement);
            }

            if (signal.getLamps().containsKey(SignalLampType.WHITE_LAMP)) {
                whiteLampCheckBox.setSelected(true);
            } else {
                setNotActive(whiteLampElement);
            }

            if (signal.getLamps().containsKey(SignalLampType.GREEN_LINE)) {
                greenLineCheckBox.setSelected(true);
            } else {
                setNotActive(greenLineElement);
            }
        }

        //init check boxes
        yellowLampCheckBox.setOnAction(event -> {
            selectElement(yellowLampElement, yellowLampCheckBox.isSelected());
            if (yellowLampCheckBox.isSelected()) {
                signal.getLamps().put(SignalLampType.YELLOW_LAMP, new OutputChannel(OutputChannelType.SIGNAL_LAMP_OUTPUT, signal, SignalLampType.YELLOW_LAMP));
            } else {
                OutputChannel outputChannel = signal.getLamps().get(SignalLampType.YELLOW_LAMP);
                if (outputChannel.getModule() != null) {
                    outputChannel.getModule().getOutputChannels().remove(outputChannel.getChNumber());
                }
                signal.getLamps().remove(SignalLampType.YELLOW_LAMP);
            }
        });

        greenLampCheckBox.setOnAction(event -> {
            selectElement(greenLampElement, greenLampCheckBox.isSelected());
            if (greenLampCheckBox.isSelected()) {
                signal.getLamps().put(SignalLampType.GREEN_LAMP, new OutputChannel(OutputChannelType.SIGNAL_LAMP_OUTPUT, signal, SignalLampType.GREEN_LAMP));
            } else {
                OutputChannel outputChannel = signal.getLamps().get(SignalLampType.GREEN_LAMP);
                if (outputChannel.getModule() != null) {
                    outputChannel.getModule().getOutputChannels().remove(outputChannel.getChNumber());
                }
                signal.getLamps().remove(SignalLampType.GREEN_LAMP);
            }
        });

        redLampCheckBox.setOnAction(event -> {
            selectElement(redLampElement, redLampCheckBox.isSelected());
            if (redLampCheckBox.isSelected()) {
                signal.getLamps().put(SignalLampType.RED_LAMP, new OutputChannel(OutputChannelType.SIGNAL_LAMP_OUTPUT, signal, SignalLampType.RED_LAMP));
            } else {
                OutputChannel outputChannel = signal.getLamps().get(SignalLampType.RED_LAMP);
                if (outputChannel.getModule() != null) {
                    outputChannel.getModule().getOutputChannels().remove(outputChannel.getChNumber());
                }
                signal.getLamps().remove(SignalLampType.RED_LAMP);
            }
        });

        secondYellowLampCheckBox.setOnAction(event -> {
            selectElement(secondYellowLampElement, secondYellowLampCheckBox.isSelected());
            if (secondYellowLampCheckBox.isSelected()) {
                signal.getLamps().put(SignalLampType.SECOND_YELLOW_LAMP, new OutputChannel(OutputChannelType.SIGNAL_LAMP_OUTPUT, signal, SignalLampType.SECOND_YELLOW_LAMP));
            } else {
                OutputChannel outputChannel = signal.getLamps().get(SignalLampType.SECOND_YELLOW_LAMP);
                if (outputChannel.getModule() != null) {
                    outputChannel.getModule().getOutputChannels().remove(outputChannel.getChNumber());
                }
                signal.getLamps().remove(SignalLampType.SECOND_YELLOW_LAMP);
            }
        });

        secondGreenLampCheckBox.setOnAction(event -> {
            selectElement(secondGreenLampElement, secondGreenLampCheckBox.isSelected());
            if (secondGreenLampCheckBox.isSelected()) {
                signal.getLamps().put(SignalLampType.SECOND_GREEN_LAMP, new OutputChannel(OutputChannelType.SIGNAL_LAMP_OUTPUT, signal, SignalLampType.SECOND_GREEN_LAMP));
            } else {
                OutputChannel outputChannel = signal.getLamps().get(SignalLampType.SECOND_GREEN_LAMP);
                if (outputChannel.getModule() != null) {
                    outputChannel.getModule().getOutputChannels().remove(outputChannel.getChNumber());
                }
                signal.getLamps().remove(SignalLampType.SECOND_GREEN_LAMP);
            }
        });

        whiteLampCheckBox.setOnAction(event -> {
            selectElement(whiteLampElement, whiteLampCheckBox.isSelected());
            if (whiteLampCheckBox.isSelected()) {
                signal.getLamps().put(SignalLampType.WHITE_LAMP, new OutputChannel(OutputChannelType.SIGNAL_LAMP_OUTPUT, signal, SignalLampType.WHITE_LAMP));
            } else {
                OutputChannel outputChannel = signal.getLamps().get(SignalLampType.WHITE_LAMP);
                if (outputChannel.getModule() != null) {
                    outputChannel.getModule().getOutputChannels().remove(outputChannel.getChNumber());
                }
                signal.getLamps().remove(SignalLampType.WHITE_LAMP);
            }
        });

        greenLineCheckBox.setOnAction(event -> {
            selectElement(greenLineElement, greenLineCheckBox.isSelected());
            if (greenLineCheckBox.isSelected()) {
                signal.getLamps().put(SignalLampType.GREEN_LINE, new OutputChannel(OutputChannelType.SIGNAL_LAMP_OUTPUT, signal, SignalLampType.GREEN_LINE));
            } else {
                OutputChannel outputChannel = signal.getLamps().get(SignalLampType.GREEN_LINE);
                if (outputChannel.getModule() != null) {
                    outputChannel.getModule().getOutputChannels().remove(outputChannel.getChNumber());
                }
                signal.getLamps().remove(SignalLampType.GREEN_LINE);
            }
        });


    }

    private void createPreview() {
        Group signalView = new Group();
        Shape yellowLampBorder = new Circle(20, 10, 10);
        yellowLampElement = new Circle(20, 10, 8);
        yellowLampElement.setFill(Color.YELLOW);
        signalView.getChildren().add(yellowLampBorder);
        signalView.getChildren().add(yellowLampElement);

        Shape greenLampBorder = new Circle(20, 40, 10);
        greenLampElement = new Circle(20, 40, 8);
        greenLampElement.setFill(Color.GREEN);

        signalView.getChildren().add(greenLampBorder);
        signalView.getChildren().add(greenLampElement);

        Shape redLampBorder = new Circle(20, 70, 10);
        redLampElement = new Circle(20, 70, 8);
        redLampElement.setFill(Color.RED);
        signalView.getChildren().add(redLampBorder);
        signalView.getChildren().add(redLampElement);

        Shape secondYellowLampBorder = new Circle(20, 100, 10);
        secondYellowLampElement = new Circle(20, 100, 8);
        secondYellowLampElement.setFill(Color.YELLOW);
        signalView.getChildren().add(secondYellowLampBorder);
        signalView.getChildren().add(secondYellowLampElement);

        Shape secondGreenLampBorder = new Circle(20, 130, 10);
        secondGreenLampElement = new Circle(20, 130, 8);
        secondGreenLampElement.setFill(Color.GREEN);
        signalView.getChildren().add(secondGreenLampBorder);
        signalView.getChildren().add(secondGreenLampElement);

        Shape whiteLampBorder = new Circle(20, 160, 10);
        whiteLampElement = new Circle(20, 160, 8);
        whiteLampElement.setFill(Color.WHITE);
        signalView.getChildren().add(whiteLampBorder);
        signalView.getChildren().add(whiteLampElement);

        Shape greenLineBorder = new Rectangle(10, 180, 20, 10);
        greenLineElement = new Rectangle(12, 182, 16, 6);
        greenLineElement.setFill(Color.GREEN);
        signalView.getChildren().add(greenLineBorder);
        signalView.getChildren().add(greenLineElement);

        signalPreview.getChildren().add(signalView);

    }

    private void selectElement(Shape shape, boolean select) {
        if (select) {
            setActive(shape);
        } else {
            setNotActive(shape);
        }
    }

    private void setActive(Shape shape) {
        shape.setOpacity(1.0);
    }

    private void setNotActive(Shape shape) {
        shape.setOpacity(0.6);
    }

    @FXML
    @Override
    public void saveAndClose(ActionEvent actionEvent) {
        signal.setId(textField.getText());
        if (!isNameValid(Model.getSignals(), Signal.INITIAL_SIGNAL_NAME))
            return;
        if (trimmerToggleButton.isSelected())
            signal.setSignalType(SignalType.TRIMMER);
        else
            signal.setSignalType(SignalType.STATION);

        if (trimmerRedLampCheckBox.isSelected()) {
            signal.setClosedSignalState(SignalState.RED);
        }
        updateModelAndClose(Model.getSignals(), signal);
    }
}
