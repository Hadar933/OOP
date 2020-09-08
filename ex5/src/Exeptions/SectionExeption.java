package Exeptions;

/**
 * a class that represents exeption that corresponds to a bad section format
 */
public class SectionExeption extends Exception{
	/**
	 * a constructor that throws
	 * @param type - either FILTER or ORDER
	 */
	public SectionExeption(String type){
		super(type+" missing");
	}
}
