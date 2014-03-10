import java.awt.Color;
public class BouncyManuel {
	public static void main(String args[]) throws InterruptedException {
		Graphics.x = new Graphics();
		int xPos = 0;
		int yPos = 0;
		int xDir = 1;
		int yDir = 1;
		int stTime = (int) System.currentTimeMillis();
		int fps = 0;
		int frameCount = 0;
		int nOfValues = 0;
		int sumOfFps = 0;
		while (true) {
			Graphics.drawPic((double) xPos, (double) yPos, 1, "Manuel.jpg");
			Graphics.writeString(500, 50, 1, fps + " Frames Per Second");
			if (nOfValues != 0) Graphics.writeString(500, 70, 1, Math.round(sumOfFps / nOfValues) + " Average");
			Graphics.makeImage();
			Thread.sleep(16);
			Graphics.clearScreen(Color.WHITE);
			frameCount++;
			if (((int) System.currentTimeMillis()) > (stTime + 1000)) {
				stTime = (int) System.currentTimeMillis();
				fps = frameCount;
				frameCount = 0;
				sumOfFps = sumOfFps + fps;
				nOfValues++;
				System.out.println("Average " + Math.round(sumOfFps / nOfValues) + " FPS"); }
			if (xPos > 1211) xDir = -1;
			if (yPos > 629) yDir = -1;
			if (xPos < 0) xDir = 1;
			if (yPos < 0) yDir = 1;
			xPos = xPos + xDir;
			yPos = yPos + yDir;
		}
	}
}
