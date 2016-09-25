package main.resources.david.tang.sms.numberfilter;

public class BlackListFilter extends NumberFilterBase {
	
	public BlackListFilter() {}
	
	@Override
	public boolean Filter(String Number) {
		if (numbers == null) {
			return true;
		}
		else if (numbers.contains(Number)) {
			logger.error(String.format("BlackListFilter :the number %s is not valid, please check smsfactory.xml", Number));
			return false;
		}
		else {
			return true;
		}
	}

}
