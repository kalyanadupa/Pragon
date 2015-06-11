package genericECHO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ECHOStructurer {

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		PrintStream out = new PrintStream(new FileOutputStream("output.txt"));       //     This line is to write output in text file
		ECHOStructurer es = new ECHOStructurer();
		ArrayList<ConceptPair> concPairs = es.getConcPairs("The left ventricle is normal in  size");
		          String report = "Qualitative LV systolic function is at the lower end of normal, with a visually estimated LVEF of 50 - 55% (quantitative analysis precluded by motion artifact).";
//			report = report.toLowerCase();
            report = report.replaceAll("\\.{3,}", ". ");
            report = report.replaceAll("\\.{2}", " ");
            report = report.replaceAll("\\.{1}(?=(\\D))", ". ");
//			report = report.replaceAll("(?!=(\\d+\\.*\\d*\\s\\w+\\/*\\?*\\w*\\%*))\\s{4,}", ". ");
            report = report.replaceAll("(?<=(mmHg|cm\\/s|cm\\?|m\\?))\\s{2,}(?=(\\w+))", ". ");
            report = report.replaceAll("(?<=(\\w+))\\s{4,}", ": ");

            String sentences[] = es.sentenceDetector.sentDetect(report);
            for (String sentence : sentences) {
                System.out.println("\t\t" + sentence);
                concPairs = es.getConcPairs(sentence);
                for (ConceptPair conceptPair : concPairs) {
                    conceptPair.println();                                							   //    CHECK THIS LINE FOR GETING ALL VALUES
                    System.setOut(out);														    		//     This line is to write output in text file
                }
            }
                
                
	//	 for (ConceptPair conceptPair : concPairs) { conceptPair.println(); }
                

//		String fileName = "testD.xls";
//		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(fileName));
//		HSSFSheet sheet = wb.getSheetAt(0);
//		int rows = sheet.getPhysicalNumberOfRows();
//
//		for (int r = 0; r < rows; r++) {
//			HSSFRow row = sheet.getRow(r);
//			if (row == null || r == 0) {
//				continue;
//			}
//			// if(r!=95) continue;
//
//			int cells = row.getPhysicalNumberOfCells();
//			if (cells < 2)
//				continue;
//			HSSFCell cell = row.getCell(2);
//			String report = cell.getStringCellValue();
////			report = report.toLowerCase();
//			report = report.replaceAll("\\.{3,}", ". ");
//			report = report.replaceAll("\\.{2}", " ");
//			report = report.replaceAll("\\.{1}(?=(\\D))", ". ");
////			report = report.replaceAll("(?!=(\\d+\\.*\\d*\\s\\w+\\/*\\?*\\w*\\%*))\\s{4,}", ". ");
//			report = report.replaceAll("(?<=(mmHg|cm\\/s|cm\\?|m\\?))\\s{2,}(?=(\\w+))", ". ");
//			report = report.replaceAll("(?<=(\\w+))\\s{4,}", ": ");
//			
//			String sentences[] = es.sentenceDetector.sentDetect(report);
//			for (String sentence : sentences) {
//			System.out.println("\t\t" + sentence);
//				concPairs = es.getConcPairs(sentence);
//				for (ConceptPair conceptPair : concPairs) {
//					conceptPair.println();                                							   //    CHECK THIS LINE FOR GETING ALL VALUES
//					System.setOut(out);														    		//     This line is to write output in text file
//				}
//			}
//
//			// System.out.println("\n\n\n\n");
//		}
	}

	SentenceDetectorME sentenceDetector;
	Tokenizer tokenizer;
	ChunkerME chunker;
	POSTaggerME posTagger;

	public ECHOStructurer() {
		InputStream modelIn = null;
		try {
			modelIn = new FileInputStream("models/en-sent.bin");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			SentenceModel model = new SentenceModel(modelIn);
			sentenceDetector = new SentenceDetectorME(model);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (modelIn != null) {
				try {
					modelIn.close();
				} catch (IOException e) {
				}
			}
		}

		TokenizerModel model1 = null;
		ChunkerModel model2 = null;
		POSModel model3 = null;
		try {
			modelIn = new FileInputStream("models/en-token.bin");
			model1 = new TokenizerModel(modelIn);
			modelIn.close();

			modelIn = new FileInputStream("models/en-chunker.bin");
			model2 = new ChunkerModel(modelIn);
			modelIn.close();

			modelIn = new FileInputStream("models/en-pos-maxent.bin");
			model3 = new POSModel(modelIn);
			modelIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		tokenizer = new TokenizerME(model1);
		chunker = new ChunkerME(model2);
		posTagger = new POSTaggerME(model3);
	}

	String qualVal = "abnormal|grossly\\s*normal|severely\\s*dilated|mildly\\s*dilated|not\\s*dilated|no\\s*evidence|no\\s*significant|\\s*normal|Mild-moderate|mild-moderate|mild to moderate|mild|Mild|moderate|moderately|Moderate|severe|Severe|trace|trivial|concentric|no|No";
	String units = "(\\%|cm\\s*2|cm\\^2|cm\\?|cm\\?\\?|cm\\s*sq|sq\\s*cm|sq\\.cm|cm per square|cm\\/sq|cm\\s*squared|mm2|mm squared|sq\\.mm|vti|mmHg|mmhg|mm hg|mm Hg|m\\/s|cm\\/s|mm\\/s|ms|cm2|mm2|mm\\?*|cm\\?*|sq\\smm|ml per square meter|ml)?";
	String condition ="thickened\\s*and\\s*calcified|calcified|calcification|thickned|thickened|thickening|all\\s*normal|normal|increased|elevated|indeterminate|decreased|no\\schange";
	String grade ="Grade\\sI|Grade\\sII|Grade\\sIII|moderate\\s*to\\s*severe|mild\\s*to\\s*moderate|mild\\s*additional|Mild-moderate|mild-moderate|Moderate to severe|mild|Mild|moderate|moderately|Moderate|severe|Severe";
	
//	Pattern p1 = Pattern.compile("The ([a-z ]+) is .*(" + qualVal + ").*");
	Pattern p1 = Pattern.compile("The ([a-z ]+) (is|\\=|was|measured|were) .*(" + qualVal + ").*");
	Pattern p2 = Pattern.compile("\\s*(" + qualVal + ")" + " ([a-z ]+)");
	Pattern p3 = Pattern.compile("([a-zA-Z]+.*) (is|\\=|was|measured) " + "([0-9\\-\\.]+\\s*"+units+")"+".*");
	Pattern p4 = Pattern.compile("([a-zA-Z]+.*) (appear|are|appear|appears|appeared|structurally) " + "\\s*("+condition+")"+".*");
	Pattern p5 = Pattern.compile("(" + grade + ")" + " (.*[a-z ]+)"+"(is|are)\\s*present");
	Pattern p6 = Pattern.compile("This is consistent with.*("+grade+").*([a-z ]+)");
	
	private ArrayList<ConceptPair> getConcPairs(String input) {
		String chunk2="";
		String chunk3="";
		
		ArrayList<ConceptPair> res = new ArrayList<ConceptPair>();
		Matcher m1 = p1.matcher(input);
		if (m1.find()) {
			res.add(new ConceptPair(m1.group(1), m1.group(3)));
		}
		
		Matcher m6 = p6.matcher(input);
		if (m6.find()) {
			System.out.println("found");
			res.add(new ConceptPair(m6.group(1), m6.group(2)));
		}
		
		Matcher m3 = p3.matcher(input);
		if (m3.find()) {
//			System.out.println("found");
			res.add(new ConceptPair(m3.group(1), m3.group(3)));
		}
		
		Matcher m5 = p5.matcher(input);
		if (m5.find()) {
//			System.out.println("found");
			res.add(new ConceptPair(m5.group(2),m5.group(1)));
		}
		

		String[] toks = this.tokenizer.tokenize(input);
		String[] tags = this.posTagger.tag(toks);
		Span[] spans = this.chunker.chunkAsSpans(toks, tags);

		
		for (Span span : spans) {
			if (span.getType().equals("NP")) {				
	//		if (span.getType().contains("")) {
			
				String chunk = "";
				for (int i = span.getStart(); i < span.getEnd(); i++) chunk += toks[i] + " ";
				chunk = chunk.trim();
				System.out.println(span.getType()+"chunk="+chunk);
				Matcher m2 = p2.matcher(chunk);
				if (m2.find()) {
//              System.out.println("found-type2");
					res.add(new ConceptPair(m2.group(2), m2.group(1)));
				}

				Matcher m4 = p4.matcher(input);
				if (m4.find()) {
//					System.out.println("found");
					res.add(new ConceptPair(chunk, m4.group(3)));
				}
			}
				
			
			if (Arrays.toString(spans).contains("NP") && !Arrays.toString(spans).contains("VP") && !Arrays.toString(spans).contains("PP")&& !Arrays.toString(spans).contains("ADVP")&& !Arrays.toString(spans).contains("ADJP")){
        			String chunk1="";	
    				for(int i=span.getStart();i<span.getEnd();i++) chunk1+=toks[i]+"  ";	
    				chunk1=chunk1.trim();

    				if (chunk1.matches("[a-zA-Z].*")) {
    					chunk2=chunk1;
    					}

    				if (chunk1.matches("[0-9].*") ){
    					chunk3=chunk1;
    					 res.add(new ConceptPair(chunk2, chunk3));

    				}
						    				
			}

		}
		return res;
	}
}
