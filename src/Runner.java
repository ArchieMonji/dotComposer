import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Runner extends StateBasedGame{

	public static final int MAINSTATE = 0;

	public Runner(String str) throws SlickException{
		super(str);
	}
	
	public static void main(String args[])throws SlickException{
		AppGameContainer app = 
			new AppGameContainer(new Runner("dotComposer"));
		app.setDisplayMode(1280, 720, false);
		app.setTargetFrameRate(60);
		app.start();
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException
	{
		this.addState(new MainState(Runner.MAINSTATE));
	}

}
