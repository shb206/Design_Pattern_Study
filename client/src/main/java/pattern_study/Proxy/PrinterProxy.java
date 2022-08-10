package pattern_study.Proxy;

public class PrinterProxy implements Printable {
	
	private String name;
	private Printer real;
	public PrinterProxy() {
		
	}
	public PrinterProxy(String name) {
		this.name = name;
	}

	@Override
	public synchronized void setPrintName(String name) {
		if(real != null) {
			real.setPrintName(name);
		}
		this.name = name;
	}

	@Override
	public String getPrinterName() {
		return name;
	}

	@Override
	public void print(String string) {
		realize();
		real.print(string);
	}
	private synchronized void realize() {
		if(real == null) {
			real = new Printer(name);
		}
	}

}
