import java.util.ArrayList;

public class UnboundedGrid<E>{
	private ArrayList<ArrayList<E>> data;
	
	
	public UnboundedGrid(int x, int y){
		if(x < 0){
			throw new IllegalArgumentException("UnboundedGrid(int x, int y): Illegal Capacity: x = " + x);
		}
		if(y < 0){
			throw new IllegalArgumentException("UnboundedGrid(int x, int y): Illegal Capacity: y = " + y);
		}
		data = new ArrayList<ArrayList<E>>(x);
		while(x > 0){
			data.add(new ArrayList<E>(y));
			x--;
		}
	}
	
	public boolean add(int x, int y, E e){
		if(x < 0){
			throw new IndexOutOfBoundsException("UnboundedGrid.add(int x, int y): Index out of bounds: x = " + x);
		}
		if(y < 0){
			throw new IndexOutOfBoundsException("UnboundedGrid.add(int x, int y): Index out of bounds: y = " + y);
		}
		if(x >= data.size()){
			ArrayList<E> list = new ArrayList<E>();
			list.add(y, e);
			data.set(x, list);	
		}
		else{
			ArrayList<E> bucket = data.get(x); 
			if(bucket == null){
				bucket = new ArrayList<E>();
				data.set(x, bucket);
			}
			bucket.set(y, e);
		}
		return true;
		
	}
	
	public E get(int x, int y){
		if(x < 0 || x >= data.size()){
			throw new IndexOutOfBoundsException("UnboundedGrid.get(int x, int y): Index out of bounds: x = " + x);
		}
		if(y < 0 || y >= data.get(x).size()){
			throw new IndexOutOfBoundsException("UnboundedGrid.get(int x, int y): Index out of bounds: y = " + y);
		}
		return data.get(x).get(y);
	}
	
	public E set(int x, int y, E e){
		if(x < 0 || x >= data.size()){
			throw new IndexOutOfBoundsException("UnboundedGrid.set(int x, int y): Index out of bounds: x = " + x);
		}
		if(y < 0 || y >= data.get(x).size()){
			throw new IndexOutOfBoundsException("UnboundedGrid.set(int x, int y): Index out of bounds: y = " + y);
		}
		ArrayList<E> bucket = data.get(x); 
		if(bucket == null){
			bucket = new ArrayList<E>();
			data.add(x, bucket);
		}
		return bucket.set(y, e);
	}
}
