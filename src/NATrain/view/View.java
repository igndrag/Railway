package NATrain.view;

import NATrain.RedactorFxController;
import NATrain.quads.Quad;

import java.util.Arrays;

public class View {
        private static Quad[][] mainGrid;

        public static void setSize (int raws, int columns) {
                mainGrid = new Quad[columns][raws];
        }

        public static Quad[][] getMainGrid() {
                return mainGrid;
        }

        public void setController (RedactorFxController controller) {
                controller = controller;
        }

        public void refreshAll() {
                Arrays.stream(mainGrid).flatMap(Arrays::stream).forEach(Quad::refresh);
        }


}
