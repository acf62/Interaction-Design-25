import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DayView extends JPanel {


    public DayView(int w, int h) throws IOException {
        JPanel top = new JPanel();
        JPanel bottom = new JPanel();
        BufferedImage myPicture = ImageIO.read(new File("img.jpg"));
        JLabel pic = new JLabel(new ImageIcon(myPicture));
        pic.setPreferredSize(new Dimension(w/3, h/3));
        JLabel description = new JLabel("Description");
        description.setPreferredSize(new Dimension(w/2, h/3));
        JLabel time1 = new JLabel("Time 1");
        time1.setPreferredSize(new Dimension(w/6, h/6));
        time1.setOpaque(true);
        time1.setBackground(Color.ORANGE);
        JLabel time2 = new JLabel("Time 2");
        time2.setPreferredSize(new Dimension(w/6, h/6));
        time2.setOpaque(true);
        time2.setBackground(Color.ORANGE);
        JLabel time3 = new JLabel("Time 3");
        time3.setPreferredSize(new Dimension(w/6, h/6));
        time3.setOpaque(true);
        time3.setBackground(Color.ORANGE);
        JLabel time4 = new JLabel("Time 4");
        time4.setPreferredSize(new Dimension(w/6, h/6));
        time4.setOpaque(true);
        time4.setBackground(Color.ORANGE);
        JLabel time5 = new JLabel("Time 5");
        time5.setPreferredSize(new Dimension(w/6, h/6));
        time5.setOpaque(true);
        time5.setBackground(Color.ORANGE);
        top.add(pic);
        top.add(description);
        bottom.add(time1);
        bottom.add(time2);
        bottom.add(time3);
        bottom.add(time4);
        bottom.add(time5);
        add(top);
        add(bottom);

    }

}
