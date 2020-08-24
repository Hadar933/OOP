import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;

import java.util.HashMap;
import java.util.Map;



public class Locker{

	/**
	 * list of constraints between items
	 */
	private final Item[][] constraints;

	/**
	 * capacity associated with the locker
	 */
	private final int capacity;
	/**
	 * long term storage object associated with the locker
	 */
	private final LongTermStorage lts;

	/**
	 * the items that are stored in the locker
	 */
	private Map<String,Integer> Inventory;


	public Locker(LongTermStorage lts, int capacity, Item[][] constraints){

		this.capacity = capacity;
		this.constraints = constraints;
		this.lts = lts;
		this.Inventory = new HashMap<>();

	}

	/**
	 * adds n items of the given type to the locker
	 * @param item - some Item object to add
	 * @param n - number of items to insert
	 * @return - 0: successfully added n items. -1: could not add n items.
	 *  1: if the action causes to be moved to lts  (yet accomodation is possible)
	 *
	 */
//	public int addItem(Item item, int n){
//
//
//	}
//
//	public int removeItem(Item item, int n){
//
//	}
//
//	public int getItemCount(String type){
//
//	}
//
//	public Map<String,Integer> getInventory(){
//
//	}
//
//	public int getCapacity(){
//
//	}
//
//	public int getAvailableCapacity(){
//
//	}

}
