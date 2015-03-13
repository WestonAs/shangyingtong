package gnete.card.web.user;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ValidateImage {
	public BufferedImage create(String s) {
		if (s == null || s.trim().length() != 4) {
			throw new IllegalArgumentException("必须是4位字符");
		}

		int width = 60, height = 20;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		Graphics g = image.getGraphics();

		Random random = new Random();

		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("宋体", Font.BOLD, 18));
		g.setColor(getRandColor(160, 200));
		
		for (int i = 0; i < 10; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			
			g.drawLine(x, y, x * xl, y * yl);
		}

		for (int i = 0; i < 4; i++) {
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(s.substring(i, i + 1), 13 * i + 6, 16 + random.nextInt(4));
		}

		g.dispose();

		return image;
	}

	// 给定范围获得随机颜色
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		
		return new Color(r, g, b);
	}
}
