package file.pic;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by johnny on 2016/9/15.
 * 生成图片水印
 */
public class WaterMarkIconTest {

    //http://www.phpxs.com/code/1001578/
    public static void main(String[] args) {

        File srcImageFile = new File("D:\\Java_ex\\test\\src\\test\\resources\\KatyPerry.jpg");
        File logoImageFile = new File("D:\\Java_ex\\test\\src\\test\\resources\\logo.png");

        File outputImageFile = new File("D:\\Java_ex\\test\\src\\test\\resources\\KatyPerry_water_icon.jpg");
        File outputRotateImageFile = new File("D:\\Java_ex\\test\\src\\test\\resources\\KatyPerry_water_icon_rotate.jpg");

        createWaterMarkByIcon(srcImageFile, logoImageFile, outputImageFile);

        createWaterMarkByIcon(srcImageFile, logoImageFile, outputRotateImageFile, 45);
    }

    private static void createWaterMarkByIcon(File srcImageFile, File logoImageFile,
                                              File outputImageFile) {
        createWaterMarkByIcon(srcImageFile, logoImageFile, outputImageFile, 0);
    }

    //with parameter of degree, realize rotate
    private static void createWaterMarkByIcon(File srcImageFile, File logoImageFile,
                                              File outputImageFile, double degree) {

        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(srcImageFile);
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = buffImg.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(
                    srcImg.getScaledInstance(srcImg.getWidth(null),
                            srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
            ImageIcon logoImgIcon = new ImageIcon(ImageIO.read(logoImageFile));
            Image logoImg = logoImgIcon.getImage();
            //rotate
            if (degree > 0) {
                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2,
                        (double) buffImg.getWidth() / 2);
            }
            float alpha = 0.3f; // 透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            //location of water mark
            g.drawImage(logoImg, buffImg.getWidth() / 15, buffImg.getHeight() / 15, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            g.dispose();
            os = new FileOutputStream(outputImageFile);
            //generate pic
            ImageIO.write(buffImg, "JPG", os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
