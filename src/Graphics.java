import java.awt.*; import java.awt.event.*; import java.awt.geom.Line2D; import java.awt.image.*; import java.util.*; import javax.swing.*;
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
			else if (type.charAt(count) == ":".charAt(0)) {
				Image character = new ImageIcon("FontChars/colon.png").getImage();
				image.drawImage(character, (int) Math.round(xPos), (int) (Math.round(yPos)), (int) Math.round(character.getWidth(null) * scale), (int) Math.round(character.getHeight(null) * scale), null);
				xPos = xPos + (9 * scale);
				count++; }
			else if (type.charAt(count) == "!".charAt(0)) {
				Image character = new ImageIcon("FontChars/exclamation.png").getImage();
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
	
	/**Draws a pixel at xPos, yPos with color a**/
	public static void drawPixel(double xPos, double yPos, Color a) {
		Color orig = image.getColor();
		setPen(a);
		image.fillRect((int) Math.round(xPos), (int) Math.round(yPos), 1, 1);
		setPen(orig); }

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
		int mouseCount = 0;
		while (true) {
			Thread.sleep(16);
			clearScreen(Color.WHITE);
			if (mousePressed) mouseCount++;
			writeString(10, 470, 1, "myMouseListener Class Diagnostics");
			writeString(10, 500, 2, "Mouse Clicked " + mouseCount + " times.");
			writeString(10, 530, 2, "Mouse Pos: X=" + userMouseX + ", Y=" + userMouseY);
			makeImage();
			mousePressed = false; } } }
