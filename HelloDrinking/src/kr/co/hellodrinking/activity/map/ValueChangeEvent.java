package kr.co.hellodrinking.activity.map;

public class ValueChangeEvent {
	private Object _source;
	private int _state;
	private Object _object;

	public ValueChangeEvent(Object source){
		_source = source;
		_state = 0;
	}
	public ValueChangeEvent(Object source, int state){
		_source = source;
		_state = state;
	}
	public ValueChangeEvent(Object source, Object object){
		_source = source;
		setObject(object);
	}
	
	public void setState(int state){
		_state = state;
	}
	
	public int getState(){
		return _state;
	}
	
	public Object getSource(){
		return _source;
	}
	
	public void setObject(Object object) {
		this._object = object;
	}
	public Object getObject() {
		return _object;
	}

}
