import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class mainView extends Container {
    String mode = "blue-yellow";
    String view = "DayView";


    public mainView() throws IOException {

        JFrame frame = new JFrame("Weather App");
        frame.setMinimumSize(new Dimension(800, 500));

        int h = frame.getHeight();
        int w = frame.getWidth();

        Dimension prefSize = new Dimension(w/4, h/6);
        JButton day = new JButton("Day");
        JButton week = new JButton("Week");
        JButton location = new JButton("location");
        day.setPreferredSize(prefSize);
        week.setPreferredSize(prefSize);
        location.setPreferredSize(prefSize);

        JPanel bottombar = new JPanel();
        bottombar.add(day);
        bottombar.add(week);
        bottombar.add(location);

        JPanel middlebar = new JPanel();

        if (view == "DayView") {
            middlebar = new DayView(w, h);
        } else if (view == "WeekView") {
            // middlebar = new WeekView(w, h);
        }

        JButton settings = new JButton("Settings");
        settings.setPreferredSize(new Dimension(w/8, h/6));
        JLabel ta = new JLabel("Weather App");
        ta.setBackground(Color.GREEN);
        ta.setOpaque(true);
        ta.setPreferredSize(new Dimension(5*w/8, h/6));
        JPanel topbar = new JPanel();
        topbar.add(ta);
        topbar.add(settings);

        frame.getContentPane().add(BorderLayout.CENTER, middlebar);
        frame.getContentPane().add(BorderLayout.NORTH, topbar);
        frame.getContentPane().add(BorderLayout.SOUTH, bottombar);

        frame.setVisible(true);
        add(frame);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                repaint();
            }
        });

    }

    public static void main(String[] args) {
        try {
            mainView m = new mainView();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    }
