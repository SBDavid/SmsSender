package sms.numberfilter;

import java.util.ArrayList;
import java.util.List;

import sms.logger.NormalLoggerI;

public class NumberFilterBase implements NumberFilterI{

	protected List<String> numbers;
	
	protected NormalLoggerI logger;
	
	public void setLogger(NormalLoggerI logger) {
		this.logger = logger;
	}
	
	public void addNumber(String number) {
		if (numbers == null) {
			numbers = new ArrayList<String>();
		}
		numbers.add(number);
	}
	
	@Override
	public boolean Filter(String Number) {
		
		return false;
	}
	
}
