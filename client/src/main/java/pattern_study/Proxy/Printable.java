package pattern_study.Proxy;

public interface Printable {
	public abstract void setPrintName(String name);
	public abstract String getPrinterName();
	public abstract void print(String string);
}
