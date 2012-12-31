import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

public class NoteGrid {
	public ArrayList<Rectangle[]> grid = new ArrayList<Rectangle[]>();
	private int x, y;
	private Rectangle bounds;
	public NoteGrid(int x, int y){
		this.x = x;
		this.y = y;
		bounds = new Rectangle(x,y,300,30);
		for(int i = 0; i < 12; i++){
			grid.add(new Rectangle[12]);
		}
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		Rectangle border = new Rectangle(x,y,240 + 30, 120 + 28);
		g.draw(border);
		//g.draw(new Rectangle(100,100,92, 134));
		for(Rectangle[] rs: grid){
			for(int i = 0; i < 12; i++)
				if(rs[i] != null)
					g.draw(rs[i]);
		}
		Input i = container.getInput();
		g.drawString(i.getMouseX() + ", " + i.getMouseY(), 300,300);
	}

	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input i = container.getInput();
		int x = i.getMouseX();
		int y = i.getMouseY();	
		if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			int nx = (x - 101)/22;
			int ny = (y - 101)/11;
			if(nx >= 4 || nx <= -1|| ny >= 12 || ny <= -1){
				return;
			}
			Rectangle[] rs = grid.get(nx);
			
			rs[ny] = new Rectangle(nx * 22 + 101, ny * 11 + 101,20,10);
			System.out.println("Added " + nx +" " + ny);
		}
		if(i.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)){
			int nx = (x - 101)/22;
			int ny = (y - 101)/11;
			if(nx >= 4 || nx <= -1|| ny >= 12 || ny <= -1){
				return;
			}
			Rectangle[] rs = grid.get(nx);
			
			rs[ny] = null;
			System.out.println("Removed " + nx +" " + ny);
		}
	}
	
	public Location calculateNote(Circle point){
		if(bounds.contains(point)){
			int x = (int) point.getX();
			int y = (int) point.getY();
			
		}
	}

}
