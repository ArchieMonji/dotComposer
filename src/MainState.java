import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainState extends BasicGameState {
	private int stateID;
	NoteEditor noteEditor = new NoteEditor(25,100);
	public MainState(int id) {
		stateID = id;
	}
	

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {	
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		noteEditor.render(container, game, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		noteEditor.update(container, game, delta);
	}

	@Override
	public int getID() {
		return stateID;
	}
}
