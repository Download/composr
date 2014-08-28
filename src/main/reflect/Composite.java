package reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Composite<T> extends Delegate<T> {
	public Composite(final Delegate<?> ... delegates) {
		super(
			new InvocationHandler() {
				@Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					for (Delegate<?> delegate : delegates) {
						try {
							Method handler = delegate.getClassTarget().getMethod(method.getName(), method.getParameterTypes());
							return delegate.getHandler().invoke(proxy, handler, args);
						}
						catch(NoSuchMethodException ex) {
							// too bad
						}
					}
					throw new NoSuchMethodException(method.toString());
				}
			}
		);
	}
}
