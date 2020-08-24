import org.junit.*;

import static org.junit.Assert.*;

import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;

public class BoopingSiteTest {

	private static final String bigData = "hotels_dataset.txt";
	private static final String smallData = "hotels_tst1.txt";
	private static final String emptyData = "hotels_tst2.txt";
	private static final String[] badCities = {"Tel Aviv", "Jerusalem", "madeUpCity"}; //not in the data
	private static final String[] goodCities =
			{"manali", "dwarka", "porbandar", "kutch", "somnath", "malvan", "mumbai"}; //in data

	private BoopingSite bigSite;
	private Hotel[] bigHotels;
	private BoopingSite smallSite;
	private Hotel[] smallHotels;
	private BoopingSite emptySite;
	private Hotel[] emptyHotels;


	@Before
	public void initialize() {
		bigSite = new BoopingSite(bigData);
		smallSite = new BoopingSite(smallData);
		emptySite = new BoopingSite(emptyData);

		bigHotels = HotelDataset.getHotels(bigData);
		smallHotels = HotelDataset.getHotels(smallData);
		emptyHotels = HotelDataset.getHotels(emptyData);
	}

	/**
	 * tests the initialization process
	 */
	@Test
	public void testInitialization() {
		assertNotNull("big site initialization failed", bigSite);
		assertNotNull("small site initialization failed", smallSite);
		assertNotNull("empty site initialization failed", emptySite);
	}

	/**
	 * tests the file with no data (tst2)
	 */
	@Test
	public void testEmpty() {
		for (String city : goodCities) {
			Hotel[] hotels = emptySite.getHotelsInCityByRating(city);
			assertEquals(0, hotels.length);
			hotels = emptySite.getHotelsByProximity(0, 0);
			assertEquals(0, hotels.length);
			hotels = emptySite.getHotelsInCityByProximity(city, 0, 0);
			assertEquals(0, hotels.length);
		}

	}

	private void testHelperGetHotelInCityByRating(BoopingSite site, String city) {
		Hotel[] sorted = site.getHotelsInCityByRating(city);
		Hotel currHotel = sorted[0];
		for (Hotel hotel : sorted) {
			if (!currHotel.equals(hotel)) {
				if (currHotel.getStarRating() == hotel.getStarRating()) {
					String property1 = currHotel.getPropertyName();
					String property2 = hotel.getPropertyName();
					assertTrue("badly sorted small data according to property name",
							   property1.compareTo(property2) <= 0);
				}
				assertTrue("Badly sorted small data according to ratings",
						   currHotel.getStarRating() >= hotel.getStarRating());
			}
			currHotel = hotel;
		}
	}

	/**
	 * tests the GetHotelsInCityByRating method for both big and small data
	 */
	@Test
	public void testGetHotelsInCityByRating() {
		for (String city : badCities) { // testing for cities that arent in the data
			Hotel[] smallSorted = smallSite.getHotelsInCityByRating(city);
			assertEquals(0, smallSorted.length);
			Hotel[] bigSorted = bigSite.getHotelsInCityByRating(city);
			assertEquals(0, bigSorted.length);
		}
		// now checking the small data (city = manali)
		testHelperGetHotelInCityByRating(smallSite, goodCities[0]);

		for (String city : goodCities) { // checking large data, with various cities
			testHelperGetHotelInCityByRating(bigSite, city);
		}




	}


}
