import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;

public class Note {
	public static final int WHOLE_NOTE = 1;
	public static final int HALF_NOTE = 2;
	public static final int QUARTER_NOTE = 4;
	public static final int EIGHTH_NOTE = 8;
	public static final int SIXTEENTH_NOTE = 16;
	
	private int pitch;
	private int velocity;
	private int duration;
	private long tick;
	public MidiEvent noteOn;
	public MidiEvent noteOff;

	public Note(int pitch, int velocity, int duration, long tick){
		this.pitch = pitch;
		this.velocity = velocity;
		this.duration = duration;
		this.tick = tick;
		try {
			noteOn = new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, pitch, velocity), tick);
			noteOff = new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, pitch, velocity), tick + 384 / duration);
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getPitch(){
		return pitch;
	}
	
	public int getVelocity(){
		return velocity;
	}
	
	public long getDuration(){
		return duration;
	}
	
	public long getTick(){
		return tick;
	}
	
	public void setPitch(int pitch){
		this.pitch = pitch;
	}
	
	public void setVelocity(int velocity){
		this.velocity = velocity;
	}
	
	public void setDuration(int duration){
		this.duration = duration;
	}
	
	public void setTick(long tick){
		this.tick = tick;
	}
}
