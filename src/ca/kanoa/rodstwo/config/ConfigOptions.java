package ca.kanoa.rodstwo.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigOptions {

	private List<String> options;
	private List<Object> defaultValues;

	public ConfigOptions(String[] options, Object[] defaultValues) {

		if (options != null && defaultValues != null && options.length == defaultValues.length) {
			this.options = new ArrayList<String>(Arrays.asList(options));
			this.defaultValues = new ArrayList<Object>(Arrays.asList(defaultValues));
		}
		else {
			this.options = new ArrayList<String>();
			this.defaultValues = new ArrayList<Object>();
		}
	}

	public ConfigOptions() {
		this.options = new ArrayList<String>();
		this.defaultValues = new ArrayList<Object>();
	}

	/*
	 * This will return a list of all the options like so: option name = [0] and defaultvalue = [1] :)
	 */
	public List<Object[]> getAllOptionsAsArray(){
		List<Object[]> list = new ArrayList<Object[]>();
		for(int x = 0; (x < options.size())||(x < defaultValues.size()); x++)
			list.add(new Object[]{options.get(x), defaultValues.get(x)});
		return list;
	}
}
