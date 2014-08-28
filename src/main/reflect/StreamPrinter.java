package reflect;

import java.io.PrintStream;

public class StreamPrinter implements Printer {
	private PrintStream to;
	public StreamPrinter(PrintStream target) {
		to = target;
	}
	public void print(String message) {
		to.print(message);
	}
}
