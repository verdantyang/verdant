import boofcv.abst.fiducial.QrCodeDetector;
import boofcv.alg.fiducial.qrcode.QrCode;
import boofcv.alg.fiducial.qrcode.QrCodeEncoder;
import boofcv.alg.fiducial.qrcode.QrCodeGeneratorImage;
import boofcv.factory.fiducial.FactoryFiducial;
import boofcv.io.UtilIO;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * <p>文件名称：ZxingTest.java</p>
 * <p>文件描述：</p>
 * <p>其他说明： </p>
 * <p>版权所有： 版权所有(C)2016-2099</p>
 * <p>公   司： 新华智云 </p>
 * <p>完成日期：2018-09-05</p>
 *
 * @author yangcongyu@shuwen.com
 * @version 1.0
 */
public class ZxingTest {
    private static String decodeQRCode(File qrCodeimage) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(qrCodeimage);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            System.out.println("There is no QR code in the image");
            return null;
        }
    }

//    public static void main(String[] args) {
//        try {
//            File file = new File("/Users/verdant/Downloads/QR-CODE-1.png");
//            String decodedText = decodeQRCode(file);
//            if(decodedText == null) {
//                System.out.println("No QR Code found in the image");
//            } else {
//                System.out.println("Decoded text = " + decodedText);
//            }
//        } catch (IOException e) {
//            System.out.println("Could not decode QR Code, IOException :: " + e.getMessage());
//        }
//    }

    public static void main(String[] args) {

        BufferedImage input = UtilImageIO.loadImage(UtilIO.pathExample("/Users/verdant/Downloads/QR-CODE-1.jpg"));
        GrayU8 gray = ConvertBufferedImage.convertFrom(input, (GrayU8) null);

        QrCodeDetector<GrayU8> detector = FactoryFiducial.qrcode(null, GrayU8.class);

        detector.process(gray);

        // Get's a list of all the qr codes it could successfully detect and decode
        List<QrCode> detections = detector.getDetections();

        Graphics2D g2 = input.createGraphics();
        int strokeWidth = Math.max(4, input.getWidth() / 200); // in large images the line can be too thin
        g2.setColor(Color.GREEN);
        g2.setStroke(new BasicStroke(strokeWidth));
        for (QrCode qr : detections) {
            // The message encoded in the marker
            System.out.println("message: " + qr.message);

            // Visualize its location in the image
        }

        // List of objects it thinks might be a QR Code but failed for various reasons
        List<QrCode> failures = detector.getFailures();
        g2.setColor(Color.RED);
        for (QrCode qr : failures) {
            // If the 'cause' is ERROR_CORRECTION or later then it's probably a real QR Code that
            if (qr.failureCause.ordinal() < QrCode.Failure.ERROR_CORRECTION.ordinal())
                continue;

//            VisualizeShapes.drawPolygon(qr.bounds,true,1,g2);
        }

//        ShowImages.showWindow(input,"Example QR Codes", true);
    }
}
