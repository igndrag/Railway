package NATrain.view;

import NATrain.FxController;
import NATrain.quads.Quad;

import java.util.Arrays;

public class View {
        private static Quad[][] mainGrid;
        private static FxController controller;


        public static void setSize (int raws, int columns) {
                mainGrid = new Quad[columns][raws];
        }

        public static Quad[][] getMainGrid() {
                return mainGrid;
        }

        public static FxController getController() {
                return controller;
        }

        public void setController (FxController controller) {
                controller = controller;
        }

        public void refreshAll() {
                Arrays.stream(mainGrid).flatMap(Arrays::stream).forEach(Quad::refresh);
        }


}
