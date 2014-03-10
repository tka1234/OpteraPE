import java.util.ArrayList;
public class FlabbyManuel {
	public static void main(String args[]) throws InterruptedException {
		Graphics.x = new Graphics();
		while (true) {
		double deltaY = 1;
		double xPos = 300;
		double yPos = 300;
		int textureCount = 0, mainCount = 0;
	//FPS Monitoring Variables
		int stTime = (int) System.currentTimeMillis(), fps = 0, frameCount = 0, nOfValues = 0, sumOfFps = 0;
	//Introduction Game Loop
		boolean introRunning = true;
		while (introRunning) {
		//Animated Picture
			mainCount++;
			if (mainCount%5 == 0) textureCount++;
			if (textureCount == 4) textureCount = 0;
		//Oscillating Position
			yPos = yPos + deltaY;
			if (yPos > 315) deltaY = -1;
			if (yPos < 285) deltaY = 1;
		//Display Animated Picture
			if (textureCount == 0) Graphics.drawPic(xPos, yPos, .3, "sprite_manuel/mid.png");
			else if (textureCount == 1) Graphics.drawPic(xPos, yPos, .3, "sprite_manuel/low.png");
			else if (textureCount == 2) Graphics.drawPic(xPos, yPos, .3, "sprite_manuel/mid.png");
			else if (textureCount == 3) Graphics.drawPic(xPos, yPos, .3, "sprite_manuel/hi.png");
			Graphics.drawPic(453, 320, 4, "logo.png");
			Graphics.drawPic(499, 450, .3, "start.png");
		//Debugging Output
			if (fps != 0) {
				Graphics.writeString(0, 0, 2, fps + " FPS");
				if (nOfValues != 0) Graphics.writeString(0, 30, 2, Math.round(sumOfFps / nOfValues) + " avg");
				Graphics.writeString(0, 60, 2, textureCount + " tex");
				Graphics.writeString(0, 90, 1, "Mouse Pos: X=" + Graphics.userMouseX + ", Y=" + Graphics.userMouseY); }
		//Is the button pressed
			if (Graphics.mousePressed && Graphics.userMouseX > 504 && Graphics.userMouseY > 478 && Graphics.userMouseX < 781 && Graphics.userMouseY < 548) introRunning = false;
			Graphics.makeImage();
			Graphics.mousePressed = false;
		//Preparation for Next Frame
			Thread.sleep(15);
			Graphics.drawPic(0, 0, 1, "background_overlay.png");
		//FPS System
			frameCount++;
			if (((int) System.currentTimeMillis()) > (stTime + 1000)) {
				stTime = (int) System.currentTimeMillis();
				fps = frameCount;
				frameCount = 0;
				sumOfFps = sumOfFps + fps;
				nOfValues++; } }
		Graphics.clearScreen();
		Graphics.makeImage();
		Thread.sleep(500);
		boolean tutorialRunning = true;
		Graphics.drawPic(0, 0, 1, "background_overlay.png");
		while (tutorialRunning) {
			//Animated Picture
				mainCount++;
				if (mainCount%5 == 0) textureCount++;
				if (textureCount == 4) textureCount = 0;
			//Oscillating Position
				yPos = yPos + deltaY;
				if (yPos > 315) deltaY = -1;
				if (yPos < 285) deltaY = 1;
			//Display Animated Picture
				if (textureCount == 0) Graphics.drawPic(xPos, yPos, .3, "sprite_manuel/mid.png");
				else if (textureCount == 1) Graphics.drawPic(xPos, yPos, .3, "sprite_manuel/low.png");
				else if (textureCount == 2) Graphics.drawPic(xPos, yPos, .3, "sprite_manuel/mid.png");
				else if (textureCount == 3) Graphics.drawPic(xPos, yPos, .3, "sprite_manuel/hi.png");
				Graphics.drawPic(508, 277, .5, "tutorial.png");
			//Debugging Output
				if (fps != 0) {
					Graphics.writeString(0, 0, 2, fps + " FPS");
					if (nOfValues != 0) Graphics.writeString(0, 30, 2, Math.round(sumOfFps / nOfValues) + " avg");
					Graphics.writeString(0, 60, 2, textureCount + " tex");
					Graphics.writeString(0, 90, 1, "Mouse Pos: X=" + Graphics.userMouseX + ", Y=" + Graphics.userMouseY); }
			//Is the button pressed
				if (Graphics.mousePressed) tutorialRunning = false;
				Graphics.makeImage();
				Graphics.mousePressed = false;
			//Preparation for Next Frame
				Thread.sleep(15);
				Graphics.drawPic(0, 0, 1, "background_overlay.png");
			//FPS System
				frameCount++;
				if (((int) System.currentTimeMillis()) > (stTime + 1000)) {
					stTime = (int) System.currentTimeMillis();
					fps = frameCount;
					frameCount = 0;
					sumOfFps = sumOfFps + fps;
					nOfValues++; } }
		boolean gameRunning = true;
		deltaY = 0;
		ArrayList<Double> twinkiesX = new ArrayList<Double>();
		ArrayList<Double> twinkiesTopThreshold = new ArrayList<Double>();
		int RunningTicksTimer = 0;
		int score = 0;
		while (gameRunning) {
			//Animated Picture
				mainCount++;
				if (mainCount%5 == 0) textureCount++;
				if (textureCount == 4) textureCount = 0;
				yPos = yPos - deltaY;
				deltaY = deltaY - 0.8;
				if (yPos > 630) gameRunning = false;
				if (RunningTicksTimer%100 == 0) {
					twinkiesX.add(0, 1446.0);
					twinkiesTopThreshold.add(0, (double) Math.round(Math.random() * 350 + 100)); }
				for (int i = 0; i < twinkiesX.size(); i++) {
					Graphics.drawPic(twinkiesX.get(i), twinkiesTopThreshold.get(i) - 423, 1, "twinkytop.png");
					if (twinkiesTopThreshold.get(i) > 423) Graphics.drawPic(twinkiesX.get(i), twinkiesTopThreshold.get(i) - 846, 1, "twinkybottom.png");
					Graphics.drawPic(twinkiesX.get(i), twinkiesTopThreshold.get(i) + 225, 1, "twinkybottom.png");
					twinkiesX.set(i, twinkiesX.get(i) - 5);
					if (twinkiesX.get(i) < -200) {
						twinkiesX.remove(i);
						twinkiesTopThreshold.remove(i);
						score++;
						i--; } }
				if (twinkiesX.size() == 3 && score == 0) score++;
			//Display Animated Picture
				if (textureCount == 0) Graphics.drawPic(xPos, yPos, .3, "sprite_manuel/mid.png");
				else if (textureCount == 1) Graphics.drawPic(xPos, yPos, .3, "sprite_manuel/low.png");
				else if (textureCount == 2) Graphics.drawPic(xPos, yPos, .3, "sprite_manuel/mid.png");
				else if (textureCount == 3) Graphics.drawPic(xPos, yPos, .3, "sprite_manuel/hi.png");
			//Debugging Output
				if (fps != 0) {
					Graphics.writeString(0, 0, 2, fps + " FPS");
					if (nOfValues != 0) Graphics.writeString(0, 30, 2, Math.round(sumOfFps / nOfValues) + " avg");
					Graphics.writeString(0, 60, 2, textureCount + " tex");
					Graphics.writeString(0, 90, 1, "Mouse Pos: X=" + Graphics.userMouseX + ", Y=" + Graphics.userMouseY);
					Graphics.writeString(0, 120, 1, RunningTicksTimer + " ticks");
					Graphics.writeString(0, 150, 1, twinkiesX.size() + ", " + twinkiesTopThreshold.size() + " entities");
					Graphics.writeString(0, 180, 1, score + " points"); }
				if (Graphics.mousePressed) deltaY = 12; 
				Graphics.makeImage();
				Graphics.mousePressed = false;
			//Preparation for Next Frame
				Thread.sleep(15);
				Graphics.drawPic(0, 0, 1, "background_overlay.png");
			//FPS System
				frameCount++;
				if (((int) System.currentTimeMillis()) > (stTime + 1000)) {
					stTime = (int) System.currentTimeMillis();
					fps = frameCount;
					frameCount = 0;
					sumOfFps = sumOfFps + fps;
					nOfValues++; }
				RunningTicksTimer++; }
		Graphics.writeString(640, 360, 2, "You got The Beetus.");
		Graphics.writeString(640, 390, 2, "Score " + score);
		Graphics.writeString(640, 420, 1, "Click Anywhere to Continue");
		if (textureCount == 0) Graphics.drawPic(xPos, yPos, .3, "sprite_manuel/mid.png");
		else if (textureCount == 1) Graphics.drawPic(xPos, yPos, .3, "sprite_manuel/low.png");
		else if (textureCount == 2) Graphics.drawPic(xPos, yPos, .3, "sprite_manuel/mid.png");
		else if (textureCount == 3) Graphics.drawPic(xPos, yPos, .3, "sprite_manuel/hi.png");
		Graphics.makeImage();
		while (true) {
			if (Graphics.mousePressed) break;
			Graphics.mousePressed = false;
			Thread.sleep(15); }
		Graphics.clearScreen();
		Graphics.makeImage();
		Thread.sleep(500); } } }
