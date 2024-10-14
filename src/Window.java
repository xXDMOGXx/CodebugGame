import javax.swing.*;
import java.awt.*;

public class Window {
    private static final JFrame frame = new JFrame("Simulation");
    private static DrawPanel canvas;
    public static int width = 1720;
    public static int height = 980;

    public static void createWindow() {
        canvas = new DrawPanel();
        canvas.setPreferredSize(new Dimension(width, height));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(canvas);
        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void renderSimulation() {
        if (canvas != null) {
            canvas.repaint();
        }
    }

    public static void closeWindow() {
        frame.dispose();
    }

    static class DrawPanel extends JPanel {
        public DrawPanel() {
            initUI();
        }
        private void initUI() {
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            for (Simulation.CommunityHandler ch : Simulation.communities) {
                for (Creatures.Ant m : ch.members) {
                    g.fillPolygon(m.points[0], m.points[1], m.points[0].length);
                }
            }
            Simulation.renderReady = false;
        }
    }
}