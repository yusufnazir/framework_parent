package software.simple.solutions.framework.core.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;

public class TextToImage {

	public byte[] createPngImage(String text) {
		int imgWidth = 250;
		int imgHeight = 250;
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);

		Graphics2D g = img.createGraphics();

		int R = (int) (Math.random() * 256);
		int G = (int) (Math.random() * 256);
		int B = (int) (Math.random() * 256);
		Color backgroundColor = new Color(R, G, B);
		// Color backgroundColor = new Color(0, 150, 100);
		g.setPaint(backgroundColor);
		g.fillRect(0, 0, imgWidth, imgHeight);

		HSLColor hslColor = new HSLColor(backgroundColor);
		Color fontColor = hslColor.getComplementary();
		Font font = new Font("Arial", Font.PLAIN, 80);
		g.setFont(font);
		g.setPaint(fontColor);

		TextLayout textLayout = new TextLayout(text, g.getFont(), g.getFontRenderContext());
		double textHeight = textLayout.getBounds().getHeight();
		double textWidth = textLayout.getBounds().getWidth();

		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// Draw the text in the center of the image
		g.drawString(text, imgWidth / 2 - (int) textWidth / 2, imgHeight / 2 + (int) textHeight / 2);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageOutputStream ios = new MemoryCacheImageOutputStream(out);
		String imgFormat = "PNG";
		try {
			if (!ImageIO.write(img, imgFormat, ios)) {
				throw new IOException("ImageIO.write failed");
			}
			ios.close();
		} catch (IOException ex) {
			throw new RuntimeException("saveImage: " + ex.getMessage());
		}
		// ImageIO.write(img, imgFormat, new File("C:\\Temp\\myimage." +
		// imgFormat));
		return out.toByteArray();
	}
}
