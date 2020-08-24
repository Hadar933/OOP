import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;
import org.junit.*;
import static org.junit.Assert.*;


public class BoopingSiteTest {

    private BoopingSite TestSite;
    private Hotel[] hotelsArray1;

    @Before
    public void createSite() {
        this.TestSite = new BoopingSite("hotels_tst1.txt");
        this.hotelsArray1 = HotelDataset.getHotels("hotels_tst1.txt");
    }

    /**
     * This method is used to calculate the euclidean distance between a given point and an hotel.
     * @param latitude  - The latitude of the point.
     * @param longitude - The longitude of the point.
     * @param hotel     - The hotel who's distance from the point we would like to find.
     * @return - a double representing the distance.
     */
    private double getDistanceBetween(double latitude, double longitude, Hotel hotel) {
        double latDiffSquared = Math.pow(latitude - hotel.getLatitude(), 2);
        double longDiffSquared = Math.pow(longitude - hotel.getLongitude(), 2);
        return Math.sqrt(latDiffSquared + longDiffSquared);
    }

    /**
     * this method returns a double between a given range. this method is and helper method to improve testing
     * by covering more scenarios.
     * @param min - the minimal value which is allowed.
     * @param max - the maximal value which is allowed.
     * @return - a double between the minimal and maximal values allowed, including.
     */
    private static double getRandomDoubleBetweenRange(double min, double max) {
        return (Math.random() * ((max - min) + 1)) + min;
    }

    @Test
    public void checkList() {
        assertNotNull("TestSite creation has failed", this.TestSite);
    }

    @Test
    public void testGetHotelsInCityByRating() {
        Hotel[] sortedHotelList = this.TestSite.getHotelsInCityByRating("manali");
        if(sortedHotelList == null){
            assertEquals(0, this.TestSite.getHotelsInCityByRating("manali").length);
        }
        else{
        Hotel previousHotel = sortedHotelList[0];
        for (Hotel tempHotel : sortedHotelList) {
            if (previousHotel.equals(tempHotel)) {
                continue;
            }
            else {
                if(previousHotel.getStarRating() == tempHotel.getStarRating()){
                    String hotel1 = previousHotel.getPropertyName();
                    String hotel2 = tempHotel.getPropertyName();
                    assertTrue("List was not subsorted properly", //checks alphabetical sorting.
                            hotel1.compareTo(hotel2) <= 0);
                }
                assertTrue("List by ratings was not sorted properly",
                        previousHotel.getStarRating() >= tempHotel.getStarRating());
                previousHotel = tempHotel; //changes previous hotel before the next comparison;
            }
            }
        }
    }

    @Test
    public void testGetHotelsProximity() {
        double randomLat = getRandomDoubleBetweenRange(-90, 90);
        double randomLong = getRandomDoubleBetweenRange(-180, 180);
        //returns a random location to check against.
        Hotel[] sortedByProximity = this.TestSite.getHotelsByProximity(randomLat, randomLong);
        Hotel previousHotel = sortedByProximity[0];
        for (Hotel tempHotel : sortedByProximity) {
            if (previousHotel.equals(tempHotel)) {
                continue;
            }
            else {
                if (this.getDistanceBetween(randomLat, randomLong, previousHotel) ==
                        this.getDistanceBetween(randomLat, randomLong, tempHotel)) {
                    //in case the distance is the same.
                    assertTrue("Proximity list was not subsorted according to POI",
                            previousHotel.getNumPOI() >= tempHotel.getNumPOI());
                    previousHotel = tempHotel;
                }
                else {
                    assertTrue("Proximity list was not sorted properly",
                            this.getDistanceBetween(randomLat, randomLong, previousHotel) <
                                    this.getDistanceBetween(randomLat, randomLong, tempHotel));
                    previousHotel = tempHotel;
                }
            }
        }
    }

    @Test
    public void testGetHotelsInCityByProximity() {
        double randomLat = getRandomDoubleBetweenRange(-90, 90);
        double randomLong = getRandomDoubleBetweenRange(-180, 180);
        assertEquals(0, //list does not include any hotels in New York.
                TestSite.getHotelsInCityByProximity("New York", 0, 0).length);

        assertEquals(0, //illegal latitude
                TestSite.getHotelsInCityByProximity("manali", -340, 0).length);
        assertEquals(0, //illegal longitude
                TestSite.getHotelsInCityByProximity("manali", 0, 400).length);
        for (int i=0 ; i <= TestSite.getHotelsInCityByProximity("manali", 0,0).length - 1; i++) {
            assertEquals("manali", TestSite.getHotelsInCityByProximity("manali", 0,0)[i].getCity());
        }
        Hotel[] sortedByCityProxArray =
                this.TestSite.getHotelsInCityByProximity("manali", randomLat, randomLong);
        Hotel previousHotel = sortedByCityProxArray[0];
        for (Hotel tempHotel : sortedByCityProxArray) {
            if (previousHotel.equals(tempHotel)) {
                continue;
            } else {
                if (this.getDistanceBetween(randomLat, randomLong, previousHotel)
                        == this.getDistanceBetween(randomLat, randomLong, tempHotel)) {
                    //in case the distance is the same.
                    assertTrue("Proximity in city list was not subsorted according to POI",
                            previousHotel.getNumPOI() >= tempHotel.getNumPOI());
                    previousHotel = tempHotel;

                    assertTrue("Proximity in city list was not sorted properly",
                            this.getDistanceBetween(randomLat, randomLong, previousHotel) <
                                    this.getDistanceBetween(randomLat, randomLong, tempHotel));
                    }
                }
            }
        }
    }

