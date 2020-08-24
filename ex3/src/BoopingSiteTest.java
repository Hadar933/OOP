import org.junit.*;
import static org.junit.Assert.*;
import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;

public class BoopingSiteTest {

	private static final String data = "hotels_dataset.txt";
	private static final String data1 = "hotels_tst1.txt";
	private static final String data2 = "hotels_tst2.txt";

	private BoopingSite site;
	private Hotel[] hotels;
	private BoopingSite site1;
	private Hotel[] hotels1;
	private BoopingSite site2;
	private Hotel[] hotels2;


	@Before
	public void initialize(){
		site = new BoopingSite(data);
		site1 = new BoopingSite(data1);
		site2 = new BoopingSite(data2);

		hotels = HotelDataset.getHotels(data);
		hotels1 = HotelDataset.getHotels(data1);
		hotels2 = HotelDataset.getHotels(data2);
	}

	@Test
	public void testInitialization(){
		assertNotNull("site initialization failed",site);
		assertNotNull("site1 initialization failed",site1);
		assertNotNull("site2 initialization failed",site2);
	}



}
