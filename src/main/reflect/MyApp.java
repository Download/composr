package reflect;

public interface MyApp extends Printer, Message {
	
	static Composite<MyApp> IMPL = new Composite<MyApp>(
		new InstanceDelegate<Message>(){
			@Override protected Object initialValue() {
				return new TextMessage("Hello, World!");
			}
		}, 
		new InstanceDelegate<Printer>(){
			@Override protected Object initialValue() {
				return new StreamPrinter(System.out);
			}
		}
	){};
	
	
	
	static class Main {
		public static void main(String ... args) {
			MyApp app = MyApp.IMPL.create();
			app.print(app.message());
		}
	}
}
