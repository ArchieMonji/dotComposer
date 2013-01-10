import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;


public class TrackManager {
	ArrayList<MidiEvent> events = new ArrayList<MidiEvent>();
	public TrackManager(){
		init();
	}
	
	public void compileTrack(){

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
		for(MidiEvent ev: events){
			t.add(ev);
		}
	}
	
	public void add(MidiEvent event){
		t.add(event);
	}
	
	
	public void remove(MidiEvent event){
		t.remove(event);
	}
	
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
	
	public void play(){
		sequencer.start();
	}
	
}
