import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

public class NoteGrid {
	public int numberOfBeats = 24;
	public int NOTE_WIDTH = 40;
	public int NOTE_HEIGHT = 20;
	public ArrayList<Rectangle[]> grid = new ArrayList<Rectangle[]>();
	private int x, y;
	private Rectangle bounds;
	public NoteGrid(int x, int y){
		this.x = x;
		this.y = y;
		bounds = new Rectangle(x, y, NOTE_WIDTH * numberOfBeats, 13 * NOTE_HEIGHT);
		for(int i = 0; i < numberOfBeats; i++){
			grid.add(new Rectangle[13]);
		}
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.setColor(Color.white);
		g.draw(new Rectangle(x,y, NOTE_WIDTH * numberOfBeats + 2, 13 * NOTE_HEIGHT + 2));
		//g.draw(new Rectangle(100,100,92, 134));
		for(int i = 1; i < 13; i++){
			g.setColor(Color.gray);
			g.drawLine(x, y + NOTE_HEIGHT * i, x + bounds.getWidth() - 1, y + NOTE_HEIGHT * i);
		}
		String[] noteNames = new String[13];
		noteNames[0] = "C";
		noteNames[1] = "C#/Db";
		noteNames[2] = "D";
		noteNames[3] = "D#/Eb";
		noteNames[4] = "E";
		noteNames[5] = "F";
		noteNames[6] = "F#/Gb";
		noteNames[7] = "G";
		noteNames[8] = "G#/Ab";
		noteNames[9] = "A";
		noteNames[10] = "A#/Bb";
		noteNames[11] = "B";
		noteNames[12] = "C";
		for(int i = 0; i < 13; i++){
			g.drawString(String.format("%6s",noteNames[12-i]),x - 60, y + i * NOTE_HEIGHT);
		}
		for(Rectangle[] rs: grid){
			for(int i = 0; i < 13; i++){
				if(rs[i] != null){
					g.setColor(Color.cyan);
					g.fill(rs[i]);
					g.setColor(Color.white);
					g.draw(rs[i]);
				}
			}
		}
		Input i = container.getInput();
		g.drawString(i.getMouseX() + ", " + i.getMouseY(), 300,300);
	}

	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input i = container.getInput();
		if(i.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			Circle p = new Circle(i.getMouseX(),i.getMouseY(),0);
			Location l = calculateNote(p);
			if(l != null){
				Rectangle[] rs = grid.get(l.x);
				rs[l.y] = new Rectangle(x + l.x * NOTE_WIDTH + 1, y + l.y * NOTE_HEIGHT + 1, NOTE_WIDTH, NOTE_HEIGHT);
				System.out.println("Added " + l.x +" " + l.y);
			}
		}
		if(i.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)){
			Circle p = new Circle(i.getMouseX(),i.getMouseY(),0);
			Location l = calculateNote(p);
			if(l != null){
				Rectangle[] rs = grid.get(l.x);
				
				rs[l.y] = null;
				System.out.println("Removed " + l.x +" " + l.y);
			}
		}
		if(i.isKeyPressed(Input.KEY_R)){
			for(Rectangle[] r: grid){
				for(int j = 0; j < 13; j++){
					r[j] = null;
				}
			}
		}
	}
	
	public Location calculateNote(Circle point){
		System.out.println("H");
		if(bounds.intersects(point)){
			int x = (int) (point.getX() - bounds.getX() - 1);
			int nx = x/NOTE_WIDTH;
			int y = (int) (point.getY() - bounds.getY() - 1);
			int ny = y/NOTE_HEIGHT;
			System.out.println(nx + " " + ny);
			return new Location(nx,ny);
		}
		return null;
	}

}
