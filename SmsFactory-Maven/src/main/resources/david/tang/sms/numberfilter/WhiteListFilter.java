package main.resources.david.tang.sms.numberfilter;

public class WhiteListFilter extends NumberFilterBase {
	
	public WhiteListFilter() {}
	
	@Override
	public boolean Filter(String Number) {
		if (numbers == null) {
			logger.error("WhiteListFilter ： fail to filterate the number, the number is empty !");
			return false;
		}
		else if (numbers.contains(Number)) {
			return true;
		}
		else {
			logger.error(String.format("WhiteListFilter ：the number %s is not valid, please check smsfactory.xml", Number));
			return false;
		}
	}

}
