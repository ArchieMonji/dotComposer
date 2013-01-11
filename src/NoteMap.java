import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class NoteMap {
	HashMap<Integer,Note> beatMap = new HashMap<Integer,Note>();
	HashMap<Integer,Note> pitchMap = new HashMap<Integer,Note>();
	HashSet<NoteEditor.Location> keySet = new HashSet<NoteEditor.Location>();
	
	public void put(NoteEditor.Location location, Note note){
		beatMap.put(location.beat, note);
		pitchMap.put(location.pitch, note);
		keySet.add(location);
	}
	
	public void remove(NoteEditor.Location location){
		beatMap.remove(location.beat);
		pitchMap.remove(location.pitch);
		keySet.remove(location);
	}
	
	public Set<NoteEditor.Location> keySet(){
		return keySet;
	}
	
	public Collection<Note> values(){
		return beatMap.values();
	}
	
	public void clear(){
		beatMap.clear();
		pitchMap.clear();
		keySet.clear();
	}
}
