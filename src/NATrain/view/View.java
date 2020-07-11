package NATrain.view;

import NATrain.RedactorFxController;
import NATrain.quads.Quad;

import java.util.Arrays;

public class View {
        private static Quad[][] mainGrid;
        private static RedactorFxController controller;


        public static void setSize (int raws, int columns) {
                mainGrid = new Quad[columns][raws];
        }

        public static Quad[][] getMainGrid() {
                return mainGrid;
        }

        public static RedactorFxController getController() {
                return controller;
        }

        public void setController (RedactorFxController controller) {
                controller = controller;
        }

        public void refreshAll() {
                Arrays.stream(mainGrid).flatMap(Arrays::stream).forEach(Quad::refresh);
        }


}
