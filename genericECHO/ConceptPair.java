package genericECHO;

public class ConceptPair {
	public ConceptPair(String concept, String value) {
		this.concept=concept;
		this.value=value;
	}
	public String concept;
	public String value;
	public void println() {
		System.out.println("concept="+this.concept+"\t"+"value="+this.value);
	}
}
