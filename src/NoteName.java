public enum NoteName {
	
	NOTE_C(0),
	NOTE_C_SHARP(1), 
	NOTE_D_FLAT(1), 
	NOTE_D(2), 
	NOTE_D_SHARP(3), 
	NOTE_E_FLAT(3), 
	NOTE_E(4), 
	NOTE_F(5), 
	NOTE_F_SHARP(6), 
	NOTE_G_FLAT(6), 
	NOTE_G(7), 
	NOTE_G_SHARP(8), 
	NOTE_A_FLAT(8), 
	NOTE_A(9), 
	NOTE_A_SHARP(10), 
	NOTE_B_FLAT(10), 
	NOTE_B(11);
	
	private int noteValue;
	
	private NoteName(int noteValue){
		this.noteValue = noteValue;
	}
	
	public static NoteName getNoteName(int pitchValue){
		pitchValue %= 12;
		if(pitchValue < 0){
			pitchValue += 12;
		}
		switch(pitchValue){
			case 0: return NOTE_C;
			case 1: return NOTE_C_SHARP;
			case 2: return NOTE_D;
			case 3: return NOTE_D_SHARP;
			case 4: return NOTE_E;
			case 5: return NOTE_F;
			case 6: return NOTE_F_SHARP;
			case 7: return NOTE_G;
			case 8: return NOTE_G_SHARP;
			case 9: return NOTE_A;
			case 10: return NOTE_A_SHARP;
			case 11: return NOTE_B;
			default:  return null;
		}
	}
	
	public int getValue(){
		return noteValue;
	}
	
}
