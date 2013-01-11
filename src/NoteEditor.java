import java.util.ArrayList;
import java.util.HashMap;

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

public class NoteEditor {
	public int numberOfBeats = 64;
	public int pitchRange = 25;
	public int NOTE_WIDTH = 18;
	public int NOTE_HEIGHT = 18;
	//public ArrayList<Rectangle[]> grid = new ArrayList<Rectangle[]>();
	//public ArrayList<MidiEvent[]> eventGrid = new ArrayList<MidiEvent[]>();
	public ArrayList<Note> notes = new ArrayList<Note>();
	public HashMap<Location,Note> noteMap = new HashMap<Location,Note>();
	public Note[][] grid = new Note[numberOfBeats][pitchRange];
	private Rectangle editorBounds;
	private Rectangle bounds;
	public int RESOLUTION = Note.EIGHTH_NOTE;
	Sequence seq;
	Sequencer sequencer;
	int tick = 0;
	Track track;
	Synthesizer s;


	public void init() {
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
			track = seq.createTrack();
		} catch (InvalidMidiDataException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public NoteEditor(int x, int y){
		bounds = new Rectangle(x, y, NOTE_WIDTH * numberOfBeats + 64, 25 * NOTE_HEIGHT + 34);
		editorBounds = new Rectangle(x + 60, y + 30, NOTE_WIDTH * numberOfBeats, 25 * NOTE_HEIGHT);
		/**for(int i = 0; i < numberOfBeats; i++){
			grid.add(new Rectangle[25]);
		}
		for(int i = 0; i < numberOfBeats; i++){
			eventGrid.add(new MidiEvent[25]);
		}**/
		init();
	}

	public void renderLabels(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		String[] noteNames = new String[25];
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
		noteNames[13] = "C#/Db";
		noteNames[14] = "D";
		noteNames[15] = "D#/Eb";
		noteNames[16] = "E";
		noteNames[17] = "F";
		noteNames[18] = "F#/Gb";
		noteNames[19] = "G";
		noteNames[20] = "G#/Ab";
		noteNames[21] = "A";
		noteNames[22] = "A#/Bb";
		noteNames[23] = "B";
		noteNames[24] = "C";
		for(int i = 0; i < 25; i++){
			g.drawString(String.format("%6s",noteNames[24-i]),editorBounds.getX() - 60, editorBounds.getY() + i * NOTE_HEIGHT);
		}
		for(int i = 1; i < numberOfBeats/RESOLUTION + 1; i++){
			g.drawString(""+i, editorBounds.getX() + NOTE_WIDTH * (i - 1) * RESOLUTION, editorBounds.getY() - NOTE_HEIGHT);
		}
		g.drawString(String.format("%-10s%s", "[R]"				, " : Reset"), 100, 10);
		g.drawString(String.format("%-10s%s", "[SPACE]"			, " : Play"), 100, 30);
		g.drawString(String.format("%-10s%s", "[ESC]"			, " : Close"), 100, 50);
		g.drawString(String.format("%-10s%s", "[Left Mouse]"	, " : Add note"), 300, 10);
		g.drawString(String.format("%-10s%s", "[Right Mouse]"	, " : Remove note"), 300, 30);
	}

	public void renderGuidelines(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		for(int i = 1; i < 25; i++){
			g.setColor(Color.gray);
			g.drawLine(editorBounds.getX(), editorBounds.getY() + NOTE_HEIGHT * i, editorBounds.getX() + editorBounds.getWidth() - 1, editorBounds.getY() + NOTE_HEIGHT * i);
		}
		for(int i = 1; i < numberOfBeats; i++){
			float ex = editorBounds.getX();			
			if(i % RESOLUTION == 0){
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
		g.draw(new Rectangle(ex, ey, NOTE_WIDTH * numberOfBeats + 2, 25 * NOTE_HEIGHT + 2));
		Rectangle rect = new Rectangle(0,0,0,0);
		for(Location loc: noteMap.keySet()){
			if(noteMap.get(loc) != null){
				rect.setBounds(editorBounds.getX() + loc.beat * NOTE_WIDTH + 1, editorBounds.getY() + loc.pitch * NOTE_HEIGHT + 1, NOTE_WIDTH, NOTE_HEIGHT);
				g.setColor(Color.cyan);
				g.fill(rect);
				g.setColor(Color.white);
				g.draw(rect);
			}
		}
		/**
		for(int beat = 0; beat < numberOfBeats; beat++){
			for(int pitch = 0; pitch < pitchRange; pitch++){
				if(grid[beat][pitch] != null){
					rect.setBounds(editorBounds.getX() + beat * NOTE_WIDTH + 1, editorBounds.getY() + pitch * NOTE_HEIGHT + 1, NOTE_WIDTH, NOTE_HEIGHT);
					g.setColor(Color.cyan);
					g.fill(rect);
					g.setColor(Color.white);
					g.draw(rect);
				}
			}
		}
		 **/
		/**for(Rectangle[] rs: grid){
			for(int i = 0; i < 25; i++){
				if(rs[i] != null){
					g.setColor(Color.cyan);
					g.fill(rs[i]);
					g.setColor(Color.white);
					g.draw(rs[i]);
				}
			}
		}**/
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
			Location loc = calculateNote(p);

			if(loc != null){
				if(noteMap.get(loc) == null){
					Note note = new Note(25 - loc.pitch + 36, 60, Note.EIGHTH_NOTE, loc.beat * 48);
					/**
					grid[loc.beat][loc.pitch] = note;
					track.add(note.noteOn);
					track.add(note.noteOff);
					 **/
					noteMap.put(loc, note);
					track.add(note.noteOn);
					track.add(note.noteOff);
				}


				//Rectangle[] rs = grid.get(l.x);
				//rs[l.y] = new Rectangle(editorBounds.getX() + l.x * NOTE_WIDTH + 1, editorBounds.getY() + l.y * NOTE_HEIGHT + 1, NOTE_WIDTH, NOTE_HEIGHT);
				//noteMap.put(loc, new Note(25 - loc.pitch + 36, 60, Note.EIGHTH_NOTE, loc.beat * 48));
				/**MidiEvent[] mes = eventGrid.get(l.x);
				try {
					mes[l.y] = new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, (25 - l.y) + 36, 60), l.x * 48);
					t.add(mes[l.y]);
					t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, (25 - l.y) + 36, 60), l.x * 48 + 96));
				} catch (InvalidMidiDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}**/
				System.out.println("Added " + NoteName.getNoteName(25 - loc.pitch + 36) + " @ tick: " + loc.beat * 48);
			}
		}
		if(input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)){
			Circle p = new Circle(input.getMouseX(),input.getMouseY(),0);
			Location loc = calculateNote(p);
			if(loc != null){
				Note note = noteMap.get(loc);
				if(note != null){
					/**
					track.remove(note.noteOn);
					track.remove(note.noteOff);
					grid[loc.beat][loc.pitch] = null;
					 **/
					noteMap.put(loc, null);
					track.remove(note.noteOn);
					track.remove(note.noteOff);
				}
				//Rectangle[] rs = grid.get(l.x);
				//rs[l.y] = null;
				//noteMap.remove(loc);
				/**
				MidiEvent[] mes = eventGrid.get(l.x);
				t.remove(mes[l.y]);
				mes[l.y] = null;**/
				System.out.println("Removed " + loc.beat +" " + loc.pitch);
			}
		}
		if(input.isKeyPressed(Input.KEY_R)){
			/**for(int beat = 0; beat < numberOfBeats; beat++){
				for(int pitch = 0; pitch < pitchRange; pitch++){
					grid[beat][pitch] = null;
				}
			}**/
			for(Note note: noteMap.values()){
				if(note != null){
					track.remove(note.noteOn);
					track.remove(note.noteOff);
				}
			}
			noteMap.clear();
		}
		if(input.isKeyPressed(Input.KEY_SPACE)){
			/**sequencer.close();
			seq.deleteTrack(track);
			System.out.println("Track deleted...");
			track = seq.createTrack();
			System.out.println("New Track created...");
			/**for(int beat = 0; beat < numberOfBeats; beat++){
				for(int pitch = 0; pitch < pitchRange; pitch++){
					Note note = grid[beat][pitch];
					if(note != null){
						try {
							track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, note.getPitch(), note.getVelocity()), note.getTick()));
							track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, note.getPitch(), note.getVelocity()), note.getTick() + 384 / note.getDuration()));
						} catch (InvalidMidiDataException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}**//**
			System.out.println("Track compiled!");
			System.out.println("Playing!");**/
			//seq = sequencer.getSequence();
			/**sequencer.close();
			seq.deleteTrack(t);
			t = seq.createTrack();
			tick = 0;
			System.out.println("Cleared");//**
			for(int i = 0; i < grid.size(); i++){
				Rectangle[] r = grid.get(i);
				for(int j = 0; j < 25; j++){
					if(r[j] != null){
						try {
							t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, (25 - j) + 36, 60), i * 48));
							t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, (25 - j) + 36, 60), i * 48 + 96));
							System.out.println("Added " + NoteName.getNoteName(j));
						} catch (InvalidMidiDataException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}**/
			try {
				sequencer.setSequence(seq);
				sequencer.open();
			} catch (MidiUnavailableException | InvalidMidiDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sequencer.setTickPosition(0);
			sequencer.start();
		}
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			sequencer.close();
			s.close();
			System.exit(0);
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

	public class Location{
		public int beat, pitch;
		public Location(int beat, int pitch){
			this.beat = beat;
			this.pitch = pitch;
		}

		@Override
		public boolean equals(Object obj){
			if(obj instanceof Location){
				Location other = (Location) obj;
				return (this.beat == other.beat) && (this.pitch == other.pitch);
			}
			else{
				return false;
			}
		}

		@Override
		public int hashCode(){
			return beat * 128 + pitch;
		}

	}
}