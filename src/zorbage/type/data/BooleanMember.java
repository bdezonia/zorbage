package zorbage.type.data;

public class BooleanMember {
	boolean v;
	
	public BooleanMember() {
		v = false;
	}
	
	public BooleanMember(boolean value) {
		v = value;
	}
	
	public BooleanMember(BooleanMember value) {
		v = value.v;
	}
	
	public BooleanMember(String value) {
		v = java.lang.Boolean.parseBoolean(value);
	}

	public boolean value() { return v; }
	
	@Override
	public String toString() { return "" + v; }
}
