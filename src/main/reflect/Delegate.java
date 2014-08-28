package reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

public abstract class Delegate<T> {
	private InvocationHandler handler;
	
	public Delegate(InvocationHandler handler) {
		this.handler = handler;
	}
	
	public InvocationHandler getHandler() {
		return handler;
	}
	
	/**
	 * Gets the class of the target of this element.
	 * 
	 * <p>This method returns the type of the generic type argument {@code T}.
	 * This is only possible if the generic type argument is filled in on
	 * the delegate {@code class}. E.G. This will work
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Class<T> getClassTarget() {
		Type t = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		if (! (t instanceof Class)) throw new IllegalStateException("Unable to determine generic type argument for " + getClass().getSimpleName() + ".");
		return (Class<T>) t;
	}
	
	@SuppressWarnings("unchecked")
	public T create() {
		Class<T> t = getClassTarget();
		return (T) Proxy.newProxyInstance(t.getClassLoader(), new Class[]{t}, this.handler);
	}
}
