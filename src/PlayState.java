import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PlayState extends BasicGameState {
	private int stateID;
	Sequence seq;
	Sequencer sequencer;
	int tick = 0;
	Track t;
	Synthesizer s;
	public PlayState(int id) {
		stateID = id;

	}
	

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {	
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

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		for(int i = 1; i < 36; i++){
			g.drawLine(0, i*container.getHeight()/36, container.getWidth(), i*container.getHeight()/36);
			g.drawString(NoteName.getNoteName(36 - i) + "",0,(i - 1)*container.getHeight()/36);
		}
		g.drawString(NoteName.getNoteName(0) + "",0,35*container.getHeight()/36);
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			System.exit(0);
		}
		if(input.isKeyPressed(Input.KEY_F)){
			//sequence = new Sequence(delta, delta);
			//sequencer.setTickPosition(sequencer.getTickPosition());
			//sequencer.setTrackSolo(1, true);
			int pitch = (int) (Math.random() * 36) + 48;
			try {
				t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, pitch, 60), tick += 48L));
				t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, pitch, 60), tick + 48L));
				System.out.println("added: " + (pitch + 36) + " " + NoteName.getNoteName(pitch)  + " tick: " + tick);
			} catch (InvalidMidiDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			s.getChannels()[1].noteOn(pitch, 60);
		}
		if(input.isKeyPressed(Input.KEY_SPACE)){
			//sequencer.setTrackSolo(1, false);
			int pitch =  (container.getHeight() - input.getMouseY())/(container.getHeight()/36) % 36;
			try {
				t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, pitch + 48, 60), tick += 48L));
				t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, pitch + 48, 60), tick + 48L));
				System.out.println("added: " + (pitch + 48L) + " " + NoteName.getNoteName(pitch)  + " tick: " + tick);
			} catch (InvalidMidiDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			s.getChannels()[1].noteOn(pitch + 48, 60);
		}
		if(input.isKeyPressed(Input.KEY_A)){
			if(sequencer.getTickPosition() >= t.ticks()){
				sequencer.setTickPosition(0);
			}
			sequencer.start();
			System.out.println("Started");
		}
		if(input.isKeyPressed(Input.KEY_S)){
			sequencer.stop();
			System.out.println("Stopped");
			//sequencer.setTickPosition(0);
		}
		if(input.isKeyPressed(Input.KEY_C)){
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
		}
		if(input.isKeyPressed(Input.KEY_Q)){
			sequencer.setTickPosition(0);
			System.out.println("Set to beginning");
		
		}
		if(sequencer.isRunning()){
			System.out.println(	sequencer.getTickPosition());
		}
	}


	@Override
	public int getID() {
		return stateID;
	}
}