import java.io.File; import java.io.FileNotFoundException; import java.io.PrintWriter; import java.util.ArrayList;
import java.util.Scanner;
public class FlabbyManuel {
	public static void main(String args[]) throws Exception {
		final int jumpHeight = 12;
		final double gravity = .8;
		Graphics.x = new Graphics();
		while (true) {
		double deltaY = 1;
		double xPos = 300;
		double yPos = 300;
		int textureCount = 0, mainCount = 0;
	//FPS Monitoring Variables
		int stTime = (int) System.currentTimeMillis(), fps = 0, frameCount = 0;
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
				frameCount = 0; } }
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
					frameCount = 0; } }
		
		boolean gameRunning = true;
		deltaY = 0;
		ArrayList<Double> twinkiesX = new ArrayList<Double>();
		ArrayList<Double> twinkiesTopThreshold = new ArrayList<Double>();
		int RunningTicksTimer = 0;
		int score = 0;
		
		File inFile = new File("HiScore.dat");
		Scanner in = new Scanner(inFile);
		int hiScore = in.nextInt();
		in.close();
		
		while (gameRunning) {
			//Animated Picture
				mainCount++;
				if (mainCount%5 == 0) textureCount++;
				if (textureCount == 4) textureCount = 0;
				yPos = yPos - deltaY;
				deltaY = deltaY - gravity;
				if (yPos > 630) gameRunning = false;
				if (RunningTicksTimer%100 == 0) {
					twinkiesX.add(0, 1446.0);
					twinkiesTopThreshold.add(0, (double) Math.round(Math.random() * 350 + 100)); }
				for (int i = 0; i < twinkiesX.size(); i++) {
					Graphics.drawPic(twinkiesX.get(i), twinkiesTopThreshold.get(i) - 423, 1, "twinkytop.png");
					if (twinkiesTopThreshold.get(i) > 423) Graphics.drawPic(twinkiesX.get(i), twinkiesTopThreshold.get(i) - 846, 1, "twinkybottom.png");
					Graphics.drawPic(twinkiesX.get(i), twinkiesTopThreshold.get(i) + 225, 1, "twinkybottom.png");
					
					if (twinkiesX.get(i) < 466 && twinkiesX.get(i) > 134) {
						if (twinkiesTopThreshold.get(i) - 15 > yPos) gameRunning = false; //precise 0
						if (twinkiesTopThreshold.get(i) + 240 < yPos) gameRunning = false; } //precise 225
					
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
					//Graphics.writeString(0, 0, 2, fps + " FPS");
					Graphics.writeString(0, 60, 2, "Score: " + score);
					Graphics.writeString(0, 90, 2, "High Score: " + hiScore); }
				if (Graphics.mousePressed) deltaY = jumpHeight;
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
					frameCount = 0; }
				RunningTicksTimer++; }
	//Draws Manuel for Final Frame
		if (textureCount == 0) Graphics.drawPic(xPos, yPos, .3, "sprite_manuel/mid.png");
		else if (textureCount == 1) Graphics.drawPic(xPos, yPos, .3, "sprite_manuel/low.png");
		else if (textureCount == 2) Graphics.drawPic(xPos, yPos, .3, "sprite_manuel/mid.png");
		else if (textureCount == 3) Graphics.drawPic(xPos, yPos, .3, "sprite_manuel/hi.png");
	//Draws Twinkies for Final Frame
		for (int i = 0; i < twinkiesX.size(); i++) {
			Graphics.drawPic(twinkiesX.get(i), twinkiesTopThreshold.get(i) - 423, 1, "twinkytop.png");
			if (twinkiesTopThreshold.get(i) > 423) Graphics.drawPic(twinkiesX.get(i), twinkiesTopThreshold.get(i) - 846, 1, "twinkybottom.png");
			Graphics.drawPic(twinkiesX.get(i), twinkiesTopThreshold.get(i) + 225, 1, "twinkybottom.png"); }
	//Text for Final Frame
		Graphics.writeString(640, 360, 2, "You Lost Your Curves!");
		if (score > hiScore) Graphics.writeString(640, 390, 2, "New High Score!" + score);
		else Graphics.writeString(640, 390, 2, "Check your privilege!");
		Graphics.writeString(640, 420, 2, "Score: " + score + "  High Score: " + hiScore);
		Graphics.writeString(640, 450, 1, "Click Anywhere to Continue...");
		Graphics.makeImage();
	//Saves High Score, if Changed
		if (score > hiScore) {
			try {
				PrintWriter out = new PrintWriter(new File("HiScore.dat"));
				out.print(score);
				out.close(); }
			catch (FileNotFoundException e) {e.printStackTrace(); } }
	//Wait for Input
		while (true) {
			if (Graphics.mousePressed) break;
			Graphics.mousePressed = false;
			Thread.sleep(15); }
		Graphics.clearScreen();
		Graphics.makeImage();
		Thread.sleep(500); } } }
