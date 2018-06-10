package nuvemplugins.solaryeconomy.util;

public class Numbers {
	
	public Numbers() {
	}

	public double parseDouble(String value) {
		double result = -1;
		value = value.toLowerCase();
		if (!value.contains("nan")) {
			try {
				result = Double.parseDouble(value);
			} catch (Exception e) {
			}
		}		
		return result;
	}

}
