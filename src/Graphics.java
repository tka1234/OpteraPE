import java.awt.*; import java.awt.event.*; import java.awt.geom.Line2D; import java.awt.image.*; import java.io.*; import java.util.*; import javax.imageio.ImageIO; import javax.swing.*;
public class Graphics extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;
	public static boolean AntiAliasingOn = false;
	
	private static Graphics2D image;
	private static BufferedImage imageB;
	
	private static Object keyLock = new Object();
	private static TreeSet<Integer> keysDown = new TreeSet<Integer>();
	private static LinkedList<Character> keysTyped = new LinkedList<Character>();
	
	public static Graphics x;
	public JFrame drawing;
	
	public static boolean mousePressed;
	public static int userMouseX, userMouseY;
	
	public Graphics() {
		super("Optera");
		drawing = new JFrame();
		imageB = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);
		image = imageB.createGraphics();
		image.setColor(Color.WHITE);
		if (AntiAliasingOn) {
			RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			image.addRenderingHints(hints); }
		image.fillRect(0, 0, 1280, 720);
		image.setColor(Color.BLACK);
		ImageIcon icon = new ImageIcon(imageB);
        JLabel draw = new JLabel(icon);
		drawing.setContentPane(draw);
		drawing.setResizable(false);
		drawing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		drawing.setTitle("Game Engine Alpha 0.2");
		drawing.pack();
		drawing.requestFocusInWindow();
		drawing.setVisible(true);
		myMouseListener mouseFunction = new myMouseListener();
		drawing.addMouseListener(mouseFunction);
		drawing.addMouseMotionListener(mouseFunction); }
	
	public static void drawLine(double x1, double y1, double x2, double y2) {image.draw(new Line2D.Double(x1, y1, x2, y2)); }
	
	/**Repaints the image displayed.**/
	public static void makeImage() {x.drawing.repaint(); }
	
	/**Sets the current pen color.**/
	public static void setPen(Color color) {image.setColor(color); }
	
	/**Sets entire screen to current pen color, or a defined color. Pen color is not modified at all.**/
	public static void clearScreen() {image.fillRect(0, 0, 1280, 720); }
	public static void clearScreen(Color color) {
		Color orig = image.getColor();
		image.setColor(color);
		image.fillRect(0, 0, 1280, 720);
		image.setColor(orig); }
	
	/**Writes a string to the display at (xPos, yPos), scaled to scale.**/
	public static void writeString(double xPos, double yPos, double scale, String type) {
		int count = 0;
		while (count < type.length()) {
			if (type.charAt(count) == " ".charAt(0)) {
				xPos = xPos + (9 * scale);
				count++; }
			else if (type.charAt(count) == ".".charAt(0)) {
				Image character = new ImageIcon("FontChars/period.png").getImage();
				image.drawImage(character, (int) Math.round(xPos), (int) (Math.round(yPos)), (int) Math.round(character.getWidth(null) * scale), (int) Math.round(character.getHeight(null) * scale), null);
				xPos = xPos + (9 * scale);
				count++; }
			else if (Character.isLowerCase(type.charAt(count))) {
				Image character = new ImageIcon("FontChars/lc_" + type.charAt(count) + ".png").getImage();
				image.drawImage(character, (int) Math.round(xPos), (int) (Math.round(yPos) + (3 * scale)), (int) Math.round(character.getWidth(null) * scale), (int) Math.round(character.getHeight(null) * scale), null);
				xPos = xPos + (9 * scale);
				count++; }
			else {
				Image character = new ImageIcon("FontChars/" + type.charAt(count) + ".png").getImage();
				image.drawImage(character, (int) Math.round(xPos), (int) Math.round(yPos), (int) Math.round(character.getWidth(null) * scale), (int) Math.round(character.getHeight(null) * scale), null);
				xPos = xPos + (9 * scale);
				count++; } } }
	
	public static void drawPixel(double xPos, double yPos, Color a) {
		Color orig = image.getColor();
		setPen(a);
		image.fillRect((int) Math.round(xPos), (int) Math.round(yPos), 1, 1);
		setPen(orig); }
	
	@Deprecated
	public static void YeOldeDistortedPicture(double xPos, double yPos, double scale, String fname, double propLTop, double propLBot, double propRTop, double propRBot) {
		BufferedImage img = null;
		try {img = ImageIO.read(new File(fname)); }
		catch (IOException e) {e.printStackTrace(); }
		Raster raster = img.getData();
		double startingYPos = ((1 - propLTop) * raster.getHeight());
		double currentTProp = propLTop;
		double currentBProp = propLBot;
		for (int x = 0; x < raster.getWidth(); x++) {
			currentTProp = currentTProp + ((propRTop - propLTop) / raster.getWidth());
			currentBProp = currentBProp + ((propRBot - propLBot) / raster.getWidth());
			double InstantaneousProportionSize = currentTProp - currentBProp;
			double countForPersistentPixel = 0;
			double positionY = startingYPos;
			
			for (int y = 0; y < raster.getHeight(); y++) {
				if (countForPersistentPixel >= 1) {
					drawPixel(x + xPos, positionY + yPos, new Color(img.getRGB(x, y)));
					positionY++;
					countForPersistentPixel--; }
				else countForPersistentPixel = countForPersistentPixel + InstantaneousProportionSize + .50;
			}
			startingYPos = startingYPos - ((propRTop - propLTop) / raster.getWidth()) * raster.getHeight();
		}
	}
	
	public static void drawDistortedPicture(double xPos, double yPos, double scale, String fname, double propLTop, double propLBot, double propRTop, double propRBot) {
		try {
			BufferedImage img = ImageIO.read(new File(fname));
			Raster raster = img.getData();
			double topPropThisCol = propLTop;
			double botPropThisCol = propLBot;
			double RequiredPixelsThisCol;
			for (int x = 0; x < raster.getWidth(); x++) {
				
				RequiredPixelsThisCol = (botPropThisCol - topPropThisCol) * raster.getHeight();
				double newY = yPos + (topPropThisCol * raster.getHeight());
				for (int y = 0; y < RequiredPixelsThisCol; y++) {
					drawPixel(x + xPos, newY, new Color(img.getRGB(x, y)));
					newY++;
				}
				topPropThisCol = topPropThisCol - ((propRTop - propLTop) / raster.getWidth());
				botPropThisCol = botPropThisCol + ((propRBot - propLBot) / raster.getWidth());
			}
			
			
		}
		catch (IOException E) {E.printStackTrace(); }
	}
	
	/**Draw a picture to the display at (xPos, yPos), scaled to scale.**/
	public static void drawPic(double xPos, double yPos, double scale, String fname) {
		Image createdImage = new ImageIcon(fname).getImage();
		image.drawImage(createdImage, (int) Math.round(xPos), (int) Math.round(yPos), (int) Math.round(createdImage.getWidth(null) * scale), (int) Math.round(createdImage.getHeight(null) * scale), null); }
	
	/**Checks for a pressed key. Find Keycodes at: http://docs.oracle.com/javase/6/docs/api/constant-values.html#java.awt.event.KeyEvent.VK_NUM_LOCK**/
	public static boolean isKeyPressed(int keycode) {synchronized (keyLock) {return keysDown.contains(keycode); } }
	public void keyTyped(KeyEvent e) {synchronized (keyLock) {keysTyped.addFirst(e.getKeyChar()); } }
	public void keyPressed(KeyEvent e) {synchronized (keyLock) {keysDown.add(e.getKeyCode()); } }
	public void keyReleased(KeyEvent e) {synchronized (keyLock) {keysDown.remove(e.getKeyCode()); } }
	
	public static void main(String args[]) throws InterruptedException {
		x = new Graphics();
		
		/*int count = 0;
		while (count < 250) {
			drawLine(Math.random() * 400, Math.random() * 400, Math.random() * 400, Math.random() * 400);
			count++; }
		writeString(10, 500, 2, "My beetus grandplanets were enfuriated at the outrageous mobility of");
		writeString(10, 530, 2, "Thin Privilege.");
		writeString(10, 590, 3, "V.L.P. Very Large Person Club");
		writeString(10, 640, 2, "Special Event. Doors Widen at 10:30");
		drawPic(50, 50, 1, "testManuel.jpg");
		drawDistortedPicture(50, 50, 1, "Manuel.jpg", .2, .9, .3, .7);
		makeImage(); */
		
		int mouseCount = 0;
		while (true) {
			Thread.sleep(16);
			clearScreen(Color.WHITE);
			if (mousePressed) mouseCount++;
			writeString(10, 470, 1, "myMouseListener Class Diagnostics");
			writeString(10, 500, 2, "Mouse Clicked " + mouseCount + " times.");
			writeString(10, 530, 2, "Mouse Pos: X=" + userMouseX + ", Y=" + userMouseY);
			makeImage();
			mousePressed = false;
			
			
		}
		}
	}
