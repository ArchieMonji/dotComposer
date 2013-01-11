import java.util.HashMap;


public class j {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HashMap map = new HashMap();
		K k = new K(3,3);
		map.put(k, 2 );
		map.put(new K(3, 3), null );
		System.out.println(map.get(k));
		System.out.println();
	}
	
	public static class K{
		int x, y;
		K(int x, int y){
			this.x = x;
			this.y = y;
		}
		@Override
		public int hashCode(){
			return 1;
		}
		@Override
		public boolean equals(Object o){
			K o2 = (K) o;
			return x == o2.x && y == o2.y;
		}
	}

}
