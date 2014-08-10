package ca.kanoa.rodstwo.helpers;

import java.util.Comparator;

import ca.kanoa.rodstwo.rods.Rod;

public class RodComparator implements Comparator<Rod> {
	
	@Override
	public int compare(Rod rod1, Rod rod2) {
		int chosen = rod1.getName().equalsIgnoreCase(rod2.getName()) ? 0 : 2;
		int index = 0;
		while (chosen == 2) {
			char c1 = rod1.getName().charAt(index), c2 = rod2.getName().charAt(index);
			if (Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
				chosen = Character.getNumericValue(Character.toLowerCase(c1)) < Character.getNumericValue(Character.toLowerCase(c2)) ? -1 : 1;
			}
			index++;
			if (index > rod1.getName().length() - 1) {
				chosen = -1;
			} else if (index > rod2.getName().length() - 1) {
				chosen = 1;
			}
		}
		return chosen;
	}
	
}
