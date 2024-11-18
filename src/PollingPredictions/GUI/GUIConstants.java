package PollingPredictions.GUI;

/*
    Holds the constants for Swing/AWT.
 */

import java.awt.*;

public interface GUIConstants {
    // JFrame
    String FRAME_TITLE = "PollingPredictions";
    int FRAME_WIDTH = 1200;
    int FRAME_HEIGHT = 1200;
    LayoutManager DEFAULT_LAYOUT_MANAGER = new BorderLayout();
    Color BACKGROUND_COLOR = new Color(200,162,200); // lilac = mixture of Republican + Democrat
    Font DEFAULT_FONT = new Font("Times New Roman", Font.PLAIN, 15);

    // TABLE PANEL
    Dimension TABLE_PANEL_DIMENSION = new Dimension(200,300);
        Dimension SCROLL_PANE_DIMENSION = new Dimension(1200,400);

    // STATS PANEL
    Dimension STATS_PANEL_DIMENSION = new Dimension(120, 400);
    Dimension JTEXTBOX_DIMENSION = new Dimension(120, 100);

    // CHART PANEL
    Dimension CHART_SIZE = new Dimension(800,600);

    // DETAIL PANEL
    Dimension DETAIL_PANEL_DIMENSION = new Dimension(900,200);
}
