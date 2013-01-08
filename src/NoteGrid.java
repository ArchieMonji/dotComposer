import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

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
	private Rectangle editorBounds;
	private Rectangle bounds;

	private int stateID;
	Sequence seq;
	Sequencer sequencer;
	int tick = 0;
	Track t;
	Synthesizer s;
	

	public void init() {	
		Sequence seq;
		try {
			sequencer = MidiSystem.getSequencer();
			//seq = MidiSystem.getSequence(new File("test.mid"));
			//sequencer.setSequence(seq);
			//System.out.println(	seq);
			s = MidiSystem.getSynthesizer();
			s.open();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			seq = new Sequence(Sequence.PPQ, 96);
			sequencer.setSequence(seq);
			t = seq.createTrack();
		} catch (InvalidMidiDataException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	

		if(!sequencer.isOpen())
			try {
				sequencer.open();
			} catch (MidiUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(!sequencer.isRunning())
			sequencer.start();
		
	}

	public NoteGrid(int x, int y){
		bounds = new Rectangle(x, y, NOTE_WIDTH * numberOfBeats + 64, 13 * NOTE_HEIGHT + 34);
		editorBounds = new Rectangle(x + 60, y + 30, NOTE_WIDTH * numberOfBeats, 13 * NOTE_HEIGHT);
		for(int i = 0; i < numberOfBeats; i++){
			grid.add(new Rectangle[13]);
		}
		init();
	}
	
	public void renderLabels(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
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
			g.drawString(String.format("%6s",noteNames[12-i]),editorBounds.getX() - 60, editorBounds.getY() + i * NOTE_HEIGHT);
		}
		for(int i = 1; i < numberOfBeats/4 + 1; i++){
			g.drawString(""+i, editorBounds.getX() + NOTE_WIDTH * (i - 1) * 4, editorBounds.getY() - NOTE_HEIGHT);
		}
	}
	
	public void renderGuidelines(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		for(int i = 1; i < 13; i++){
			g.setColor(Color.gray);
			g.drawLine(editorBounds.getX(), editorBounds.getY() + NOTE_HEIGHT * i, editorBounds.getX() + editorBounds.getWidth() - 1, editorBounds.getY() + NOTE_HEIGHT * i);
		}
		for(int i = 1; i < numberOfBeats; i++){
			float ex = editorBounds.getX();			
			if(i % 4 == 0){
				g.setColor(Color.white);
			}
			else{
				g.setColor(Color.gray);	
			}
			g.drawLine(ex + NOTE_WIDTH * i, editorBounds.getY() , ex + NOTE_WIDTH * i - 1, editorBounds.getY() + editorBounds.getHeight());
		}
	}
	
	public void renderEditor(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		float ex = editorBounds.getX();
		float ey = editorBounds.getY();
		g.setColor(Color.white);
		g.draw(new Rectangle(ex, ey, NOTE_WIDTH * numberOfBeats + 2, 13 * NOTE_HEIGHT + 2));
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
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.setColor(Color.red);
		g.draw(bounds);
		this.renderGuidelines(container, game, g);
		this.renderLabels(container, game, g);
		this.renderEditor(container, game, g);
	}

	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			Circle p = new Circle(input.getMouseX(),input.getMouseY(),0);
			Location l = calculateNote(p);
			if(l != null){
				Rectangle[] rs = grid.get(l.x);
				rs[l.y] = new Rectangle(editorBounds.getX() + l.x * NOTE_WIDTH + 1, editorBounds.getY() + l.y * NOTE_HEIGHT + 1, NOTE_WIDTH, NOTE_HEIGHT);
				System.out.println("Added " + l.x +" " + l.y);
			}
		}
		if(input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)){
			Circle p = new Circle(input.getMouseX(),input.getMouseY(),0);
			Location l = calculateNote(p);
			if(l != null){
				Rectangle[] rs = grid.get(l.x);
				
				rs[l.y] = null;
				System.out.println("Removed " + l.x +" " + l.y);
			}
		}
		if(input.isKeyPressed(Input.KEY_R)){
			for(Rectangle[] r: grid){
				for(int j = 0; j < 13; j++){
					r[j] = null;
				}
			}
		}
		if(input.isKeyPressed(Input.KEY_X)){
			seq = sequencer.getSequence();
			sequencer.close();
			seq.deleteTrack(t);
			t = seq.createTrack();
			tick = 0;
			try {
				sequencer.setSequence(seq);
				sequencer.open();
			} catch (MidiUnavailableException | InvalidMidiDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Cleared");
			for(int i = 0; i < grid.size(); i++){
				Rectangle[] r = grid.get(i);
				for(int j = 0; j < 13; j++){
					if(r[j] != null){
						try {
							t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, (13 - j) + 48, 60), i * 48));
							t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, (13 - j) + 48, 60), i * 48 + 48));
						} catch (InvalidMidiDataException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			sequencer.start();
		}
	}
	
	public Location calculateNote(Circle point){
		//System.out.println("H");
		if(editorBounds.intersects(point)){
			int x = (int) (point.getX() - editorBounds.getX() - 1);
			int nx = x/NOTE_WIDTH;
			int y = (int) (point.getY() - editorBounds.getY() - 1);
			int ny = y/NOTE_HEIGHT;
			//System.out.println(nx + " " + ny);
			return new Location(nx,ny);
		}
		return null;
	}

}
