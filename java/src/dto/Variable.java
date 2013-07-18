package dto;

public class Variable {
	
	public static final int NUMERIC = 1 ;
	public static final int BINARY = 2 ;
	public static final int STRING = 3 ;
	
	private String name ;
	private String unit ;
	private int type ;
	private String symbol ;
	
	public Variable() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public static int getNumeric() {
		return NUMERIC;
	}

	public static int getBinary() {
		return BINARY;
	}

	public static int getString() {
		return STRING;
	} 
	
}
