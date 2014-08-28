package reflect;

public class ReflectedClass<T> {
	private Delegate[] delegates;
	public ReflectedClass(Delegate ... delegates) {
		this.delegates = delegates;
	}
	public T create() {
		return new Composite<T>(delegates){}.create();
	}

}
