package Game;

import java.awt.Image;
import javax.swing.ImageIcon;

public class ImageUtils {

    public static ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
}
