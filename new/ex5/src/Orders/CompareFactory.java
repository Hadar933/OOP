package Orders;

import java.io.File;
import java.util.Comparator;

/**
 * a factory class that generates comparators
 */
public class CompareFactory {

    /**
     * generates the comparators based on order value
     * @param order - order value as specified in the command file
     * @return - either a new abs, type or size comparator (default is abs)
     */
    public Comparator<File> generateComparator(String order){
        switch(order){
        case "type":
            return new TypeCompare();
        case "size":
            return new SizeCompare();
        default:
            return new AbsCompare();
        }
    }
}
