package NATrain.trackSideObjects;

import NATrain.UI.AppConfigController;

public enum ControlAction {
    ALLOCATE_MOVABLE_MODEl,

    SET_ROUT_FROM,
    SET_ROUT_TO,
    SET_ROUTE_TO_TRACK,
    SET_ROUTE_TO_TRACK_LINE,

    SET_AUTO_MODE,
    CHANGE_SWITCH_POSITION,
    CHANGE_TRACK_LINE_DIRECTION,
    OPEN_ON_BLINKED_WHITE,

    OPEN_GATE,
    CLOSE_GATE,
    ;

    public String getDescription() {
        switch (this) {
                       case ALLOCATE_MOVABLE_MODEl:
                switch (AppConfigController.getLanguage()) {
                    case RU:
                        return "Разместить подвижной состав";
                    case ENG:
                        return "Allocate movable model";
                }
            case SET_ROUT_FROM:
                switch (AppConfigController.getLanguage()) {
                    case RU:
                        return "Задать маршрут от";
                    case ENG:
                        return "Set rout from";
                }
            case SET_ROUT_TO:
                switch (AppConfigController.getLanguage()) {
                    case RU:
                        return "Задать маршрут на";
                    case ENG:
                        return "Set rout to";
                }
            case SET_ROUTE_TO_TRACK:
                switch (AppConfigController.getLanguage()) {
                    case RU:
                        return "Задать маршрут на путь";
                    case ENG:
                        return "Set route to track";
                }
            case SET_ROUTE_TO_TRACK_LINE:
                switch (AppConfigController.getLanguage()) {
                    case RU:
                        return "Задать маршрут на перегон";
                    case ENG:
                        return "Set route to trackline";
                }
            case SET_AUTO_MODE:
                switch (AppConfigController.getLanguage()) {
                    case RU:
                        return "Автодействие";
                    case ENG:
                        return "Auto mode";
                }
            case CHANGE_SWITCH_POSITION:
                switch (AppConfigController.getLanguage()) {
                    case RU:
                        return "Перевести";
                    case ENG:
                        return "Change switch position";
                }
            case CHANGE_TRACK_LINE_DIRECTION:
                switch (AppConfigController.getLanguage()) {
                    case RU:
                        return "Сменить направление на перегоне";
                    case ENG:
                        return "Change track line direction";
                }
            case OPEN_ON_BLINKED_WHITE:
                switch (AppConfigController.getLanguage()) {
                    case RU:
                        return "Пригласительный сигнал";
                    case ENG:
                        return "Open on blinked white";
                }
            case OPEN_GATE:
                switch (AppConfigController.getLanguage()) {
                    case RU:
                        return "Открыть ворота";
                    case ENG:
                        return "Open gate";
                }
            case CLOSE_GATE:
                switch (AppConfigController.getLanguage()) {
                    case RU:
                        return "Закрыть ворота";
                    case ENG:
                        return "Close gate";
                }
            default: return "None";
        }
    }


    @Override
    public String toString() {
        return getDescription();
    }
}
