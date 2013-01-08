public class Note {
	public static final int WHOLE_NOTE = 1;
	public static final int HALF_NOTE = 2;
	public static final int QUARTER_NOTE = 4;
	public static final int EIGHTH_NOTE = 8;
	public static final int SIXTEENTH_NOTE = 16;
	
	private int pitch;
	private int velocity;
	private int duration;


	public Note(int pitch, int velocity, int duration){
		this.pitch = pitch;
		this.velocity = velocity;
		this.duration = duration;
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
	
	public void setPitch(int pitch){
		this.pitch = pitch;
	}
	
	public void setVelocity(int velocity){
		this.velocity = velocity;
	}
	
	public void setDuration(int duration){
		this.duration = duration;
	}
}
