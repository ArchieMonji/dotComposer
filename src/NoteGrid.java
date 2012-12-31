import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

public class NoteGrid {
	public ArrayList<Rectangle[]> grid = new ArrayList<Rectangle[]>();
	
	public NoteGrid(){
		for(int i = 0; i < 4; i++){
			grid.add(new Rectangle[12]);
		}
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.draw(new Rectangle(100,100,92, 134));
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
			if(x > 100 && x < 192 && y > 100 && y < 234){
				int nx = (x - 101)/22;
				int ny = (y - 101)/11;
				if(nx >= 4 || nx <= -1|| ny >= 12 || ny <= -1){
					return;
				}
				Rectangle[] rs = grid.get(nx);
				
				rs[ny] = new Rectangle(nx * 22 + 101, ny * 11 + 101,20,10);
				System.out.println("Added " + nx +" " + ny);
			};
		}
		if(i.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)){
			if(x > 100 && x < 192 && y > 100 && y < 234){
				int nx = (x - 101)/22;
				int ny = (y - 101)/11;
				if(nx >= 4 || nx <= -1|| ny >= 12 || ny <= -1){
					return;
				}
				Rectangle[] rs = grid.get(nx);
				
				rs[ny] = null;
				System.out.println("Removed " + nx +" " + ny);
			};
		}
	}

}
