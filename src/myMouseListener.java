import java.awt.event.MouseEvent; import java.awt.event.MouseListener; import java.awt.event.MouseMotionListener;
class myMouseListener implements MouseListener, MouseMotionListener {
	@Override
	public void mouseClicked(MouseEvent arg0) { }
	@Override
	public void mouseEntered(MouseEvent arg0) { }
	@Override
	public void mouseExited(MouseEvent arg0) { }
	@Override
	public void mousePressed(MouseEvent arg0) {Graphics.mousePressed = true; }
	@Override
	public void mouseReleased(MouseEvent arg0) {Graphics.mousePressed = false; }
	@Override
	public void mouseDragged(MouseEvent arg0) { }
	@Override
	public void mouseMoved(MouseEvent arg0) {
		Graphics.userMouseX = arg0.getX();
		Graphics.userMouseY = arg0.getY(); } }
