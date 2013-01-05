import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Runner2 extends StateBasedGame{

	public static final int PLAYSTATE = 0;

	public Runner2(String str) throws SlickException{
		super(str);
	}
	
	public static void main(String args[])throws SlickException{
		AppGameContainer app = 
			new AppGameContainer(new Runner2("dotComposer"));
		app.setDisplayMode(1280, 720, false);
		app.setTargetFrameRate(60);
		app.start();
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException
	{
		this.addState(new PlayState(Runner2.PLAYSTATE));
	}
}
