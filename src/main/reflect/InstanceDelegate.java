package reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

public abstract class InstanceDelegate<T> extends Delegate<T> {
	private AtomicReference<Object> value = new AtomicReference<Object>();

	public InstanceDelegate() {
		super(null);
	}

	/**
	 * Gets the instance this delegate is delegating calls to.
	 * 
	 * @return The object, which will be created by calling {@code initialValue}
	 * if it did not exist yet, never {@code null}.
	 * 
	 * @see #set
	 * @see #initialValue
	 */
	public final Object get() {
		Object result = value.get();
		if (result == null) {
			value.compareAndSet((Object) null, initialValue());
			result = value.get();
		}
		return result;
	}

	/** 
	 * (Re)sets the instance this delegate is delegating calls to.
	 * 
	 * <p>If this method is called with a non-{@code null} value
	 * prior to calling {@code get}, the {@code initialValue} 
	 * method will not be invoked.</p>
	 * 
	 * <p>If on the other hand {@code set} is called with a
	 * {@code null} value, the next invocation of {@code get}
	 * will again invoke {@code initialValue}.</p>
	 * 
	 * @param value The value to set, possibly {@code null}.
	 * 
	 * @see #get
	 * @see #initialValue
	 */
	public final void set(Object value) {
		this.value.set(value);
	}
	
	@Override public InvocationHandler getHandler() {
		return new InvocationHandler() {
			@Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				return method.invoke(get(), args);
			}
		};
	}
	
	/**
	 * Creates the initial value for the instance this delegate delegates to. 
	 *
	 * <p>This method will be invoked when the 
	 * {@code get} method is invoked and the object this delegate is delegating to
	 * is (still) {@code null}.</p> 
	 * 
	 * <p>If {@code set} had been called with a non-{@code null} value prior to
	 * calling {@code get}, this method might never be invoked. On the other hand
	 * if {@code set} is called with a {@code null} argument, or multiple threads
	 * are accessing this delegate simultaneously, this method might end up being 
	 * called multiple times.</p>
	 * 
	 * @return The new instance to be delegated to, never {@code null}.
	 */
	protected abstract Object initialValue();
}
