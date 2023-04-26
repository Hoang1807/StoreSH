/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author TIEN SY
 */
public class XImage {

    static String url = "jdbc:sqlserver://localhost:1433;databaseName=EDUSYS;encrypt=true;trustServerCertificate=true";
    static String username = "sa";
    static String password = "";

    public static void save(File src) throws IOException {
        File file = new File("Logos", src.getName());
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(file.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ImageIcon read(byte[] fileName) throws IOException {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileName);
            BufferedImage bfi = ImageIO.read(byteArrayInputStream);
            Image img = bfi.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            return null;
        }

    }

    public static ImageIcon readChon(File fileName, int Width, int Height) throws IOException {
        BufferedImage bfi = ImageIO.read(fileName);
        Image img = bfi.getScaledInstance(Width, Height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
    
    public static Image toScaleImage(byte[] buf) {
        ByteArrayInputStream bais = new ByteArrayInputStream(buf);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(bais);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bi.getScaledInstance(180, 150, BufferedImage.SCALE_SMOOTH);
    }


}
