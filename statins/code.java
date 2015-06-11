package statins; //23 March 2015//09 April 2015

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class code {

	public static void main(String[] args) {

		// +++++++++++++++++++++++++++++++++++++++++++(VARIABLE
		// DECLARATION)++++++++++++++++++++++++++++++++++++++++++++++//

		int numOfRows = 60;
		int numOfInputCol = 4;
		int numOfPheno = 61;
		int startIndexCol = 4;
		int IndexCol = 4;
		int addColperPheno = 4;
		int count = 0;
		int numOfOutputColumns = numOfPheno * addColperPheno + IndexCol;

		String fileName = "/Users/cnx471/Desktop/testpr.xls";
		String WritefileName = "/Users/cnx471/Desktop/testPOIWriteTrialone.xls";
		String completelist = "a";
		String check = "";
		String check1 = "";
		String check1a = "";
		String check2 = "";
		// +++++++++++++++++++++++++++++++++++++++++++(METHOD
		// CALLING)++++++++++++++++++++++++++++++++++++++++++++++++++//
		Echo_ReadExcelFile method = new Echo_ReadExcelFile();
		Vector recordlist = method.readExcelFile(fileName);
		String[][] arrRecords = new String[recordlist.size()][numOfInputCol];
		String[][] excelData = new String[numOfRows][numOfOutputColumns];

		// +++++++++++++++++++++++++++++++++++++++++++(INPUT / READING EXCEL
		// SHEET)++++++++++++++++++++++++++++++++++++++//
		for (int i = 0; i < recordlist.size(); i++) {
			Vector cellStoreVector = (Vector) recordlist.elementAt(i);
			for (int j = 0; j < cellStoreVector.size(); j++) {
				HSSFCell myCell = (HSSFCell) cellStoreVector.elementAt(j);
				String stringCellValue = myCell.toString();
				String stringCellValue1 = stringCellValue.toLowerCase();
				completelist = stringCellValue1 + "\t";
				arrRecords[i][j] = completelist;
				// excelData[i][j] = arrRecords[i][j]; // copy data from old
				// data
			}
		}
		// +++++++++++++++++++++++++++++++++++++++++++(OUTPUT EXCEL CELL
		// DECLARATION)++++++++++++++++++++++++++++++++++++//
		for (int r = 0; r < numOfRows; r++) {
			for (int c = 1; c < numOfOutputColumns; c++) {
				excelData[r][c] = "";
			}
		}

		excelData[0][4] = "AVA";
		excelData[0][8] = "MVA";
		excelData[0][12] = "AORTIC LEAFLET";
		excelData[0][16] = "MITRAL LEAFLET";
		excelData[0][20] = "AV PEAK GRADIENT";
		excelData[0][24] = "MV PEAK GRADIENT";
		excelData[0][28] = "MR PEAK GRADIENT";
		excelData[0][32] = "TR PEAK GRADIENT";
		excelData[0][36] = "TV PEAK GRADIENT";
		excelData[0][40] = "PV PEAK GRADIENT";
		excelData[0][44] = "LVOT PEAK GRADIENT";
		excelData[0][48] = "RVOT PEAK GRADIENT";
		excelData[0][52] = "AV MEAN GRADIENT";
		excelData[0][56] = "MV MEAN GRADIENT";
		excelData[0][60] = "PV MEAN GRADIENT";
		excelData[0][64] = "TRICUPSID MEAN GRADIENT";
		excelData[0][68] = "TRANSVALVULAR MEAN GRADIENT";
		excelData[0][72] = "BIOPROSTHESIS MEAN GRADIENT";
		excelData[0][76] = "RVOT PEAK VELOCITY";
		excelData[0][80] = "LVOT PEAK VELOCITY";
		excelData[0][84] = "TV PEAK VELOCITY";
		excelData[0][88] = "MV PEAK VELOCITY";
		excelData[0][92] = "PV PEAK VELOCITY";
		excelData[0][96] = "AV PEAK VELOCITY";
		excelData[0][100] = "TR PEAK VELOCITY";
		excelData[0][104] = "MR PEAK VELOCITY";
		excelData[0][108] = "LVOT MEAN VELOCITY";
		excelData[0][112] = "PV MEAN VELOCITY";
		excelData[0][116] = "MR MEAN VELOCITY";
		excelData[0][120] = "AV MEAN VELOCITY";
		excelData[0][124] = "MV MEAN VELOCITY";
		excelData[0][128] = "TV MEAN VELOCITY";
		excelData[0][132] = "AORTIC STENOSIS";
		excelData[0][136] = "MITRAL STENOSIS";
		excelData[0][140] = "DIMENSIONAL INDEX";
		excelData[0][144] = "MVA(P1/2T)";
		excelData[0][148] = "PULMONARY ARTERY PRESSURE";
		excelData[0][152] = "RIGHT ARTERY PRESSURE";
		excelData[0][156] = "AORTIC REGURGITATION";
		excelData[0][160] = "MITRAL REGURGITATION";
		excelData[0][164] = "VENA CONTRACTA";
		excelData[0][168] = "REGURGITANT FRACTION";
		excelData[0][172] = "REGURGITANT ORIFICE AREA";
		excelData[0][176] = "PRESSURE HALF";
		excelData[0][180] = "AORTIC FLOW REVERSAL";
		excelData[0][184] = "LVEF";
		excelData[0][188] = "DIASTOLIC FUNCTION";
		excelData[0][192] = "E/A RATIO";
		excelData[0][196] = "LV FILLING PRESSURE";
		excelData[0][200] = "E/e' Ratio";
		excelData[0][204] = "LVEDD";
		excelData[0][208] = "LVESD";
		excelData[0][212] = "CONCENTRIC TYPE";
		excelData[0][216] = "BASAL TYPE";
		excelData[0][220] = "DEGREE OF LV HYPERTROPHY";
		excelData[0][224] = "DEGREE OF BS HYPERTROPHY";
		excelData[0][228] = "SEPTAL THICKNESS";
		excelData[0][232] = "ATRIAL ENLARGEMENT";
		excelData[0][236] = "LA DIMENSION";
		excelData[0][240] = "VOLUMN INDEX";
		excelData[0][244] = "BODY SURFACE AREA";
		for (int col = 1; col < numOfPheno + 1; col++) {
			excelData[0][IndexCol * col + 1] = "Result";
			excelData[0][IndexCol * col + 2] = "Max Value";
			excelData[0][IndexCol * col + 3] = "Last Value";

		}

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++(PATTERN
		// DECLARATION)++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

		    Pattern p = Pattern.compile(".{3}(\\sava\\s|aortic\\s*valve\\s*area|\\sav\\sarea).{100}");
	        Pattern Px = Pattern.compile("ava\\s*index.{1,15}\\d*\\.\\d*|av\\s*area\\s*index.{1,15}\\d*\\.\\d*|aortic\\s*valve\\s*area\\s*index.{1,15}\\d*\\.\\d*");
	        Pattern p1 = Pattern.compile("((?<=(ava.{1,20}))|(?<=(aortic\\s{1,4}valve\\s{1,4}area.{1,20}))|(?<=(av\\s{1,4}area.{1,20}))|(?<=(((ava)|(aortic\\s{1,4}valve\\s{1,4}area)|(av\\s{1,4}area)).{1,20}((continuity\\sequation)|(planimetry)).{1,20})))\\s*((\\d*\\.*\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+)|(\\[\\*\\*\\d+(\\-|to)\\d+\\*\\*\\]))(?=(\\s*((cm\\s*2)|(cm\\^2)|(cm\\?)|(cm\\?\\?)|(sq\\/cm)|(cm\\s*sq)|(sq\\s*cm)|(sq\\.cm)|(cm\\/sq)|(cm\\s*squared)|(mm2)|(mm\\s*squared)|(sq\\.mm)|(vti))))");
	        Pattern p4 = Pattern.compile(".{3}(mva\\s|mitral\\s*valve\\s*area|\\smv\\sarea|(mva(\\(traced\\)\\:))).{100}");
	        Pattern p5 = Pattern.compile("((?<=(mva(\\(traced\\)\\:)))|(?<=(mva.{1,20}))|(?<=(mitral\\s{1,4}valve\\s{1,4}area.{1,20}))|(?<=(mv\\s{1,4}area.{1,20}))|(?<=(((mva)|(mitral\\s{1,4}valve\\s{1,4}area)|(mv\\s{1,4}area)).{1,20}((pressure\\shalf\\stime)|(pht)).{1,20})))\\s*((\\d*\\.*\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+)|(\\[\\*\\*\\d+(\\-|to)\\d+\\*\\*\\]))(?=(\\s*((cm\\s*2)|(cm\\^2)|(cm\\?)|(cm\\?\\?)|(sq\\/cm)|(cm\\s*sq)|(sq\\s*cm)|(sq\\.cm)|(cm\\/sq)|(cm)|(cm\\s*squared)|(mm2)|(mm\\s*squared)|(sq\\.mm)|(vti))))");
	       
	        Pattern p8 = Pattern.compile("(.{1,10}((aortic)\\s*valve.{1,20}(is|was|are|(\\.it\\s*is)|(leaflets)).{1,40}((thickened\\s*and\\s*calcified)|(sclerotic\\s*and\\s*calcified)|(calcified)|(thickned)|(thickened)|(thickening)|(\\snormal)|(no\\schange))))|(.{1,10}((aortic)\\s*valve.{1,10}(leaflet|leaflets|trileaflet|trileaflets).{1,40}((calcified)|(thickned)|(thickened)|(thickening)|(sclerotic\\s*and\\s*calcified)|(\\snormal)|(no\\schange))).{1,10})|(aortic\\s*(valve)*.{1,5}calcification)|(aortic\\s*(thickening|sclerosis|calcification))|((calcification|thickening)[^\\.].{1,10}(aortic\\s*(valve)))");
	        Pattern p9 = Pattern.compile("(thickened\\s*and\\s*calcified)|(sclerotic\\s*and\\s*calcified)|(calcified)|(calcification)|(thickned)|(thickened)|(thickening)|(normal)|(sclerotic)|(no\\schange)|(sclerosis)");
	        Pattern p8a = Pattern.compile("((((mitral)\\s*(valve).{1,20}((is)|(was)|(are)|(\\.it\\s*is)))|(mitral\\s*leaflet(s)*))[^.].{1,40}((calcified)|(thickned)|(thickened)|(thickening)|(\\snormal)|(no\\schange)))|((leaflet|leaflets|trileaflet|trileaflets).{1,30}(mitral)\\s*valve[^.].{1,40}((calcified)|(thickned)|(thickened)|(thickening)|(normal)|(no\\schange)))|(mitral\\s*(valve|annular).{1,20}(calcification|thickening))|((calcification|thickening).{1,20}leaflets.{1,10}(mitral\\s*(valve)))|(mitral\\svalve\\sleaflets[^.].{1,40}(thickened|calcified))");
	        Pattern p9a = Pattern.compile("(thickened\\s*and\\s*calcified)|(calcified)|(calcification)|(thickned)|(thickened)|(thickening)|(normal)|(sclerotic)|(no\\schange)|(sclerosis)");
	       
	        Pattern p10a = Pattern.compile("(ao\\s{1,6}max\\s{1,6}pg.{1,20}(mmhg|mm\\s{1,4}hg))|((av|aortic\\s*valve)\\s{1,6}peak\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|(peak\\s{1,6}((pressure)|(forward\\s{1,6}flow))*\\s*gradient.{1,30}across.{1,30}(aortic\\s*(valve|prosthesis)).{1,20}(mmhg|mm\\s*hg))|(((across.{1,30}(aortic\\s*valve))|((bioprosthetic\\s{1,6}valve).{1,90}aortic\\s{1,6}pos(i)*tion)).{1,80}peak\\s{1,6}(pressure)*(forward\\s{1,6}flow)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|(peak\\s{1,6}(trans)*(aortic|av)(\\svalve)*\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|(((((bioprosthetic)|(prosthetic)|(bioprosthetic\\svalve)).{1,90}aortic\\s*(valve|pos(i)*tion))|(((aortic)|(ao)|(av))\\s*((bioprosthetic\\s(valve)*)|(prosthesis)|(bioprosthesis)|(pos(i)*tion)))).{1,140}((this\\scorresponds)|with|calculating|the).{1,140}(peak\\s{1,4}(gradient|(pressure\\s)*difference)).+?{1,40}(mmhg|mm\\s*hg))|(aortic\\svalve.{1,100}((this\\scorresponds)|with|calculating|the|consistent).{1,100}(peak\\s{1,4}(gradient|(pressure\\s)*difference)).+?{1,140}(mmhg|mm\\s*hg))");
	        Pattern p11a = Pattern.compile("((?<=(ao\\s{1,6}max\\s{1,6}pg.{1,20}))|(?<=(peak\\s{1,6}pressure\\s{1,6}(gradient|difference).{1,40}))|(?<=(peak\\s{1,6}gradient.{1,40}))|(?<=(peak\\s{1,6}((transaortic)|(aortic\\svalve)|(av)|(aortic)|(ao)|(forward\\s{1,6}flow))\\s{1,6}gradient.{1,20})))\\s*(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))((?=(\\s{1,20}mmhg))|(?=(mmhg))|(?=(\\s{1,20}mm\\shg))))");
	        Pattern p10b = Pattern.compile("(mv\\s{1,6}max\\s{1,6}pg.{1,20}(mmhg|mm\\s{1,4}hg))|((mv|mitral\\s*valve)\\s{1,6}peak\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\shg))|(peak\\s{1,6}(pressure)*\\s*gradient.{1,30}(across\\s(the)*\\s*mitral\\s*valve).{1,20}(mmhg|mm\\shg))|((across\\s*(the)*\\s*mitral\\s*valve).{1,40}peak\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\shg))|(peak\\s{1,6}(trans)*mitral(\\svalve)*\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\shg))");
	        Pattern p11b = Pattern.compile("((?<=(mv\\s{1,6}max\\s{1,6}pg.{1,20}))|(?<=(peak\\spressure\\sgradient.{1,40}))|(?<=(peak\\sgradient.{1,40})))(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))((?=(\\s*mmhg))|(?=(mmhg))|(?=(\\s*mm\\shg))))");
	        Pattern p10c = Pattern.compile("(mr\\s{1,6}max\\s{1,6}pg.{1,20}(mmhg|mm\\s{1,4}hg))|(mr\\s{1,6}peak\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|(mr\\s{1,6}max\\s{1,6}pg.{1,20}(mmhg|mm\\s{1,4}hg))");
	        Pattern p11c = Pattern.compile("((?<=(mr\\s{1,6}max\\s{1,6}pg.{1,20}))|(?<=(peak\\sgradient.{1,40}))|(?<=(mr\\s{1,6}max\\s{1,6}pg.{1,20}))|)(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))((?=(\\s*mmhg))|(?=(mmhg))|(?=(\\s*mm\\s*hg))))");
	        Pattern p10d = Pattern.compile("(tr\\s{1,6}max\\s{1,6}pg.{1,20}(mmhg|mm\\s{1,4}hg))|(tr\\s{1,6}peak\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|(tr\\s{1,6}max\\s{1,6}pg.{1,20}(mmhg|mm\\s{1,4}hg))");
	        Pattern p11d = Pattern.compile("((?<=(tr\\s{1,6}max\\s{1,6}pg.{1,20}))|(?<=(peak\\sgradient.{1,40}))|(?<=(tr\\s{1,6}max\\s{1,6}pg.{1,20}))|)(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))((?=(\\s*mmhg))|(?=(mmhg))|(?=(\\s*mm\\s*hg))))");
	        Pattern p10e = Pattern.compile("(tv\\s{1,6}max\\s{1,6}pg.{1,20}(mmhg|mm\\s{1,4}hg))|(tv\\s{1,6}peak\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|(tv\\s{1,6}max\\s{1,6}pg.{1,20}(mmhg|mm\\s{1,4}hg))");
	        Pattern p11e = Pattern.compile("((?<=(tv\\s{1,6}max\\s{1,6}pg.{1,20}))|(?<=(peak\\sgradient.{1,40}))|(?<=(tv\\s{1,6}max\\s{1,6}pg.{1,20}))|)(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))((?=(\\s*mmhg))|(?=(mmhg))|(?=(\\s*mm\\s*hg))))");
	        Pattern p10f = Pattern.compile("(pv\\s{1,6}max\\s{1,6}pg.{1,20}(mmhg|mm\\s{1,4}hg))|(pv\\s{1,6}peak\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|(pv\\s{1,6}max\\s{1,6}pg.{1,20}(mmhg|mm\\s{1,4}hg))");
	        Pattern p11f = Pattern.compile("((?<=(pv\\s{1,6}max\\s{1,6}pg.{1,20}))|(?<=(peak\\sgradient.{1,40}))|(?<=(pv\\s{1,6}max\\s{1,6}pg.{1,20}))|)(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))((?=(\\s*mmhg))|(?=(mmhg))|(?=(\\s*mm\\s*hg))))");
	        Pattern p10g = Pattern.compile("(lvot\\s{1,6}peak\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|(lvot.{1,40}((with)|(and)).{1,40}peak\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))");
	        Pattern p11g = Pattern.compile("((?<=(peak\\sgradient.{1,40}))|(?<=(peak\\sgradient\\sof.{1,40})))(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))((?=(\\s*mmhg))|(?=(mmhg))|(?=(\\s*mm\\s*hg))))");
	        Pattern p10h = Pattern.compile("(rvot\\s{1,6}peak\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|(rvot.{1,40}((with)|(and)).{1,40}peak\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))");
	        Pattern p11h = Pattern.compile("((?<=(peak\\sgradient.{1,40}))|(?<=(peak\\sgradient\\sof.{1,40})))(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))((?=(\\s*mmhg))|(?=(mmhg))|(?=(\\s*mm\\s*hg))))");
	        
	        Pattern p10aa = Pattern.compile("(ao\\s{1,6}mean\\s{1,6}pg.{1,20}(mmhg|mm\\s{1,4}hg))|((av|aortic\\s*valve)\\s{1,6}mean\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|(mean\\s{1,6}((pressure)|(forward\\s{1,6}flow))*\\s*gradient.{1,30}across.{1,30}(aortic\\s*(valve|prosthesis)).{1,20}(mmhg|mm\\s*hg))|(((across.{1,30}(aortic\\s*valve))|((bioprosthetic\\s{1,6}valve).{1,90}aortic\\s{1,6}pos(i)*tion)).{1,80}mean\\s{1,6}(pressure)*(forward\\s{1,6}flow)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|(mean\\s{1,6}(trans)*(aortic|av)(\\svalve)*\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|(ao\\s{1,6}mean\\s{1,6}pg.{1,20}(mmhg|mm\\s{1,4}hg))|(((((bioprosthetic)|(prosthetic)|(bioprosthetic\\svalve)).{1,90}aortic\\s*(valve|pos(i)*tion))|(((aortic)|(ao)|(av))\\s*((bioprosthetic\\s(valve)*)|(prosthesis)|(bioprosthesis)|(pos(i)*tion)))).{1,140}((this\\scorresponds)|with|calculating|the).{1,140}(mean\\s{1,4}(gradient|(pressure\\s)*difference)).+?{1,40}(mmhg|mm\\s*hg))|(aortic\\svalve.{1,100}((this\\scorresponds)|with|calculating|the|consistent).{1,100}(mean\\s{1,4}(gradient|(pressure\\s)*difference)).+?{1,140}(mmhg|mm\\s*hg))");
	        Pattern p11aa = Pattern.compile("((?<=(ao\\s{1,6}mean\\s{1,6}pg.{1,20}))|(?<=(aortic\\s{1,6}valve\\s{1,6}.{1,30}))|(?<=(ao\\s{1,6}mean\\s{1,6}pg.{1,20}))|(?<=(mean\\s{1,6}pressure\\s{1,6}(gradient|difference).{1,40}))|(?<=(mean\\s{1,6}gradient.{1,40}))|(?<=(mean\\s{1,6}((transaortic)|(aortic\\svalve)|(av)|(aortic)|(ao)|(forward\\s{1,6}flow))\\s{1,6}gradient.{1,20})))\\s*(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))((?=(\\s{1,20}mmhg))|(?=(mmhg))|(?=(\\s{1,20}mm\\shg))))");
	        Pattern p10ab = Pattern.compile("(mv\\s{1,6}mean\\s{1,6}pg.{1,20}(mmhg|mm\\s{1,4}hg))|((mv|mitral\\s*valve)\\s{1,6}mean\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|(mean\\s{1,6}(pressure)*\\s*gradient.{1,30}(across\\s(the)*\\s*mitral\\s*valve).{1,20}(mmhg|mm\\s*hg))|((across\\s*(the)*\\s*mitral\\s*valve).{1,40}mean\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|(mean\\s{1,6}(trans)*(mitral|mv)(\\svalve)*\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|(mv\\s{1,6}mean\\s{1,6}pg.{1,20}(mmhg|mm\\s{1,4}hg))");
	        Pattern p11ab = Pattern.compile("((?<=(mv\\s{1,6}mean\\s{1,6}pg.{1,20}))|(?<=(mv\\s{1,6}mean\\s{1,6}pg.{1,20}))|(?<=(mean\\s{1,6}pressure\\s{1,6}gradient.{1,40}))|(?<=(mean\\s{1,6}((transmitral)|(mitral\\svalve)|(mv)|(mitral)|(forward\\s{1,6}flow))\\s{1,6}gradient.{1,20}))|(?<=(mean\\s{1,6}gradient.{1,40})))(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))((?=(\\s*mmhg))|(?=(mmhg))|(?=(\\s*mm\\s*hg))))");
	        Pattern p10ac = Pattern.compile("(((pulmonic|pulmonary)\\s*valve)\\s{1,6}mean\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|(mean\\s*(pressure)*\\s*gradient.{1,30}(across\\s(the)*\\s*pulmonic\\s*valve).{1,20}(mmhg|mm\\s*hg))|((across\\s*(the)*\\s*pulmonic\\s*valve).{1,40}mean\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|(mean\\s{1,6}(trans)*pulmonary(\\svalve)*\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))");
	        Pattern p11ac = Pattern.compile("((?<=(mean\\s{1,6}pressure\\s{1,6}gradient.{1,40}))|(?<=(mean\\s{1,6}((transpulmonary)|(pulmonary\\svalve)|(pulmonary))\\s{1,6}gradient.{1,20}))|(?<=(mean\\s{1,6}gradient.{1,40})))(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))((?=(\\s*mmhg))|(?=(mmhg))|(?=(\\s*mm\\s*hg))))");
	        Pattern p10ad = Pattern.compile("(t`v\\s{1,6}mean\\s{1,6}pg.{1,20}(mmhg|mm\\s{1,4}hg))|((mean\\s{1,6}(pressure)*\\s*gradient.{1,30}(across\\s(the)*\\s*tricuspid\\s*valve).{1,20}(mmhg|mm\\s*hg))|(tricuspid\\s*valve).{1,40}mean\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|((across\\s*(the)*\\s*tricuspid\\s*valve).{1,40}mean\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|(mean\\s{1,6}(trans)*tricuspid(\\svalve)*\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|(tv\\s{1,6}mean\\s{1,6}pg.{1,20}(mmhg|mm\\s{1,4}hg))");
	        Pattern p11ad = Pattern.compile("((?<=(tv\\s{1,6}mean\\s{1,6}pg.{1,20}))|(?<=(tv\\s{1,6}mean\\s{1,6}pg.{1,20}))|(?<=(mean\\s{1,6}pressure\\s{1,6}gradient.{1,40}))|(?<=(mean\\s{1,6}((transtricupsid)|(tricuspid\\svalve)|(tricuspid))\\s{1,6}gradient.{1,20}))|(?<=(mean\\s{1,6}gradient.{1,40})))(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))((?=(\\s*mmhg))|(?=(mmhg))|(?=(\\s*mm\\s*hg))))");
	        Pattern p10ae = Pattern.compile("(((transvalvular))\\s{1,20}mean\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|(mean\\s{1,6}(pressure)*\\s{1,6}gradient.{1,30}(across\\s(the)*\\s*transvalvular\\s*valve).{1,20}(mmhg|mm\\s*hg))|((across\\s*(the)*\\s*transvalvular\\s*valve).{1,40}mean\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|(mean\\s{1,6}transvalvular\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))");
	        Pattern p11ae = Pattern.compile("((?<=(mean\\s{1,6}pressure\\s{1,6}gradient.{1,40}))|(?<=(mean\\s{1,6}((transvalvular))\\s{1,6}gradient.{1,20}))|(?<=(mean\\s{1,6}gradient.{1,40})))(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))((?=(\\s*mmhg))|(?=(mmhg))|(?=(\\s*mm\\s*hg))))");
	        Pattern p10af = Pattern.compile("(((bioprosthetic\\s*valve))\\s{1,20}mean\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|(mean\\s{1,6}(pressure)*\\s*gradient.{1,30}(across\\s(the)*\\s*bioprosthetic\\s*valve).{1,20}(mmhg|mm\\s*hg))|((across\\s*(the)*\\s*bioprosthetic\\s*valve).{1,40}mean\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))|(mean\\s{1,6}bioprosthetic\\svalve\\s{1,6}(pressure)*\\s*gradient.{1,20}(mmhg|mm\\s*hg))");
	        Pattern p11af = Pattern.compile("((?<=(mean\\spressure\\sgradient.{1,40}))|(?<=(mean\\s{1,6}bioprosthetic\\svalve\\s{1,6}gradient.{1,20}))|(?<=(mean\\sgradient.{1,40})))(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))((?=(\\s*mmhg))|(?=(mmhg))|(?=(\\s*mm\\s*hg))))");
	       
	        
	        Pattern p12a = Pattern.compile("((rvot)\\s{1,6}peak\\s{1,6}(recorded)*\\s*velocity.{1,20}(((cm)|m)\\/(s|second|sec)))|(peak\\s{1,6}(recorded)*\\s*velocity.{1,30}(across\\s(the)*\\s*rvot).{1,20}(((cm)|m)\\/(s|second|sec)))|((across\\s*(the)*\\s*rvot).{1,40}peak\\s{1,6}(recorded)*\\s*velocity.{1,20}(((cm)|m)\\/(s|second|sec)))");
	        Pattern p13a = Pattern.compile("((?<=(peak\\s{1,6}(recorded)\\s{1,6}velocity.{1,40}))|(?<=(peak\\s{1,6}velocity.{1,40})))(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\_*\\s*(((cm)|m)\\/(s|second|sec)))");
	        Pattern p12b = Pattern.compile("((lvot)\\s{1,6}peak\\s{1,6}(recorded)*\\s*velocity.{1,20}(((cm)|m)\\/(s|second|sec)))|((lvot.{1,40}((with)|(and)).{1,40}peak\\s{1,6}(recorded)*\\s*velocity.{1,20}(((cm)|m)\\/(s|second|sec))))|(peak\\s{1,6}(recorded)*\\s*velocity.{1,30}(across\\s(the)*\\s*lvot).{1,20}(((cm)|m)\\/(s|second|sec)))|((across\\s*(the)*\\s*lvot).{1,40}peak\\s{1,6}(recorded)*\\s*velocity.{1,20}(((cm)|m)\\/(s|second|sec)))");
	        Pattern p13b = Pattern.compile("((?<=(peak\\s{1,6}(recorded)\\s{1,6}velocity.{1,40}))|(?<=(peak\\s{1,6}velocity.{1,40})))(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\_*\\s*(((cm)|m)\\/(s|second|sec)))");
	        Pattern p12c = Pattern.compile("((tv)\\s{1,6}peak\\s{1,6}(recorded)*\\s*velocity.{1,20}(((cm)|m)\\/(s|second|sec)))|(peak\\s{1,6}(recorded)*\\s*velocity.{1,30}(across\\s(the)*\\s*tricuspid\\s*valve).{1,20}(((cm)|m)\\/(s|second|sec)))|((across\\s*(the)*\\s*tricuspid\\s*valve).{1,40}peak\\s{1,6}(recorded)*\\s*velocity.{1,20}(((cm)|m)\\/(s|second|sec)))");
	        Pattern p13c = Pattern.compile("((?<=(peak\\s{1,6}(recorded)\\s{1,6}velocity.{1,40}))|(?<=(peak\\s{1,6}velocity.{1,40})))(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\_*\\s*(((cm)|m)\\/(s|second|sec)))");
	        
	        
	        Pattern p12d = Pattern.compile("((mv|mitral\\s*valve)\\s{1,6}peak\\s{1,6}(recorded)*\\s*velocity.{1,20}(((cm)|m)\\/(s|second|sec)))|(peak\\s{1,6}(recorded)*\\s*velocity.{1,30}(across\\s(the)*\\s*(bioprosthetic\\s)*mitral\\s*valve).{1,20}((with)|(and)).{1,20}(((cm)|m)\\/(s|second|sec)))|((across\\s*(the)*\\s*(bioprosthetic\\s)*mitral\\s*valve).{1,40}peak\\s{1,6}(recorded)*\\s*velocity.{1,20}(((cm)|m)\\/(s|second|sec)))|(peak\\s{1,6}(trans)*mitral(\\svalve)*\\s{1,6}velocity.{1,20}(((cm)|m)\\/(s|second|sec)))");
	        Pattern p13d = Pattern.compile("((?<=(peak\\s{1,6}(recorded|transmitral)\\s{1,6}velocity.{1,40}))|(?<=(peak\\s{1,6}mitral\\s{1,6}velocity.{1,40}))|(?<=(peak\\s{1,6}velocity.{1,80})))(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\_*\\s*(((cm)|m)\\/(s|second|sec)))");
	       
	        
	        Pattern p12e = Pattern.compile("((pv)\\s{1,6}peak\\s{1,6}(recorded)*\\s*velocity.{1,20}(((cm)|m)\\/(s|second|sec)))|(peak\\s{1,6}(recorded)*\\s*velocity.{1,30}(across\\s(the)*\\s*pulmonary\\s*valve).{1,20}(((cm)|m)\\/(s|second|sec)))|((across\\s*(the)*\\s*pulmonary\\s*valve).{1,40}peak\\s{1,6}(recorded)*\\s*velocity.{1,20}(((cm)|m)\\/(s|second|sec)))");
	        Pattern p13e = Pattern.compile("((?<=(peak\\s{1,6}(recorded)\\s{1,6}velocity.{1,40}))|(?<=(peak\\s{1,6}velocity.{1,40})))(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\_*\\s*(((cm)|m)\\/(s|second|sec)))");
	        
	        
	        Pattern p12f = Pattern.compile("((av|aortic\\s*valve)\\s{1,6}peak\\s{1,6}(recorded)*\\s*velocity.{1,20}(((cm)|m)\\/(s|second|sec)))|(peak\\s{1,6}(recorded)*\\s*velocity.{1,30}(across\\s(the)*\\s*(bioprosthetic\\s)*aortic\\s*valve).{1,20}(((cm)|m)\\/(s|second|sec)))|((across\\s*(the)*\\s*(bioprosthetic\\s)*aortic\\s*valve).{1,40}peak\\s{1,6}(recorded)*\\s*velocity.{1,20}(((cm)|m)\\/(s|second|sec)))|(peak\\s{1,6}(trans)*aortic(\\svalve)*\\s{1,6}velocity.{1,20}(((cm)|m)\\/(s|second|sec)))|(((((bioprosthetic)|(prosthetic)|(bioprosthetic\\svalve)).{1,90}aortic\\s*(valve|pos(i)*tion))|(((aortic)|(ao)|(av))\\s*((bioprosthetic\\s(valve)*)|(prosthesis)|(bioprosthesis)|(pos(i)*tion)))).{1,140}((this\\scorresponds)|with|calculating|the).{1,140}(peak\\s{1,4}(velocity)).+?{1,40}(((cm)|m)\\/(s|second|sec)))");
	        Pattern p13f = Pattern.compile("((?<=(peak\\s{1,6}(recorded|transaortic)\\s{1,6}velocity.{1,40}))|(?<=(peak\\s{1,6}aortic\\s{1,6}velocity.{1,40}))|(?<=(peak\\s{1,6}velocity.{1,40})))(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\_*\\s*(((cm)|m)\\/(s|second|sec)))");
	        
	        
	        
	        Pattern p12g = Pattern.compile("(tr\\s{1,6}peak\\s{1,6}velocity.{1,20}(((cm)|m)\\/(s|second|sec)))|(tr\\s{1,6}max\\s{1,6}vel.{1,20}(((cm)|m)\\/(s|second|sec)))");
	        Pattern p13g = Pattern.compile("((?<=(peak\\s{1,6}(recorded)\\s{1,6}velocity.{1,40}))|(?<=(tr\\s{1,6}max\\s{1,6}vel.{1,20}))|(?<=(peak\\s{1,6}velocity.{1,40})))(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\_*\\s*(((cm)|m)\\/(s|second|sec)))");
	        Pattern p12h = Pattern.compile("(mr\\s{1,6}peak\\s{1,6}velocity.{1,20}(((cm)|m)\\/(s|second|sec)))");
	        Pattern p13h = Pattern.compile("((?<=(peak\\s{1,6}(recorded)\\s{1,6}velocity.{1,40}))|(?<=(peak\\s{1,6}velocity.{1,40})))(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\_*\\s*(((cm)|m)\\/(s|second|sec)))");
	       
	        Pattern p12aa = Pattern.compile("((lvot)\\s{1,6}mean\\s{1,6}(recorded)*\\s*velocity.{1,20}(((cm)|m)\\/(s|second|sec)))|(mean\\s{1,6}(recorded)*\\s*velocity.{1,30}(across\\s(the)*\\s*lvot).{1,20}(((cm)|m)\\/(s|second|sec)))|((across\\s*(the)*\\s*lvot).{1,40}mean\\s{1,6}(recorded)*\\s*velocity.{1,20}(((cm)|m)\\/(s|second|sec)))");
	        Pattern p13aa = Pattern.compile("((?<=(mean\\s{1,6}(recorded)\\s{1,6}velocity.{1,40}))|(?<=(mean\\s{1,6}velocity.{1,40})))(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\_*\\s*(((cm)|m)\\/(s|second|sec)))");
	        Pattern p12ab = Pattern.compile("((pv)\\s{1,6}mean\\s{1,6}(recorded)*\\s*velocity.{1,20}(((cm)|m)\\/(s|second|sec)))|(mean\\s{1,6}(recorded)*\\s*velocity.{1,30}(across\\s(the)*\\s*pulmonary\\s*valve).{1,20}(((cm)|m)\\/(s|second|sec)))|((across\\s*(the)*\\s*pulmonary\\s*valve).{1,40}mean\\s{1,6}(recorded)*\\s*velocity.{1,20}(((cm)|m)\\/(s|second|sec)))");
	        Pattern p13ab = Pattern.compile("((?<=(mean\\s{1,6}(recorded)\\s{1,6}velocity.{1,40}))|(?<=(mean\\s{1,6}velocity.{1,40})))(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\_*\\s*(((cm)|m)\\/(s|second|sec)))");
	        Pattern p12ac = Pattern.compile("(mr\\s{1,6}mean\\s{1,6}velocity.{1,20}(((cm)|m)\\/(s|second|sec)))");
	        Pattern p13ac = Pattern.compile("((?<=(mean\\s{1,6}(recorded)\\s{1,6}velocity.{1,40}))|(?<=(mean\\s{1,6}velocity.{1,40})))(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\_*\\s*(((cm)|m)\\/(s|second|sec)))");
	        Pattern p12ad = Pattern.compile("((av|aortic\\s*valve)\\s{1,6}mean\\s{1,6}(recorded)*\\s*velocity.{1,20}(((cm)|m)\\/(s|second|sec)))|(mean\\s{1,6}(recorded)*\\s*velocity.{1,30}(across\\s(the)*\\s*(bioprosthetic\\s)*aortic\\s*valve).{1,20}(((cm)|m)\\/(s|second|sec)))|((across\\s*(the)*\\s*(bioprosthetic\\s)*aortic\\s*valve).{1,40}mean\\s{1,6}(recorded)*\\s*velocity.{1,20}(((cm)|m)\\/(s|second|sec)))|(mean\\s{1,6}(trans)*aortic(\\svalve)*\\s{1,6}velocity.{1,20}(((cm)|m)\\/(s|second|sec)))");
	        Pattern p13ad = Pattern.compile("((?<=(mean\\s{1,6}(recorded)\\s{1,6}velocity.{1,40}))|(?<=(mean\\s{1,6}velocity.{1,40})))(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\_*\\s*(((cm)|m)\\/(s|second|sec)))");
	        Pattern p12ae = Pattern.compile("((mv|mitral\\s*valve)\\s{1,6}mean\\s{1,6}(recorded)*\\s*velocity.{1,20}(((cm)|m)\\/(s|second|sec)))|(mean\\s{1,6}(recorded)*\\s*velocity.{1,30}(across\\s(the)*\\s*(bioprosthetic\\s)*mitral\\s*valve).{1,20}(((cm)|m)\\/(s|second|sec)))|((across\\s*(the)*\\s*(bioprosthetic\\s)*mitral\\s*valve).{1,40}mean\\s{1,6}(recorded)*\\s*velocity.{1,20}(((cm)|m)\\/(s|second|sec)))|(mean\\s{1,6}(trans)*mitral(\\svalve)*\\s{1,6}velocity.{1,20}(((cm)|m)\\/(s|second|sec)))");
	        Pattern p13ae = Pattern.compile("((?<=(mean\\s{1,6}(recorded)\\s{1,6}velocity.{1,40}))|(?<=(mean\\s{1,6}velocity.{1,40})))(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\_*\\s*(((cm)|m)\\/(s|second|sec)))");
	        Pattern p12af = Pattern.compile("(tv\\s{1,6}mean\\s{1,6}velocity.{1,20}(((cm)|m)\\/(s|second|sec)))");
	        Pattern p13af = Pattern.compile("((?<=(mean\\s{1,6}(recorded)\\s{1,6}velocity.{1,40}))|(?<=(mean\\s{1,6}velocity.{1,40})))(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\_*\\s*((cm|m)\\/(s|second|sec)))");
	      
	        Pattern p14 = Pattern.compile("((mildly\\s*dialated\\s*(moderate|severe))|(moderately\\s*severe\\s*to\\s*severe)|(mild\\s*\\-*to\\s*\\-*moderate)|(moderate\\s*\\-*to\\s*\\-*severe)|(mild-moderate)|(moderate-severe)|(mild)|(moderate)|(severe)|(no\\s*significant)|(significant)|(no\\s+evidence\\s*of*(\\sbioprosthetic)*)|(no\\s*))[\\s\\w]{1,30}(((aortic.{1,5}stenosis))|(aortic\\svalve\\sstenosis))");
	        Pattern p15 = Pattern.compile("((mildly\\s*dialated\\s*(moderate|severe))|(moderately\\s*severe\\s*to\\s*severe)|(mild\\s*\\-*to\\s*\\-*moderate)|(moderate\\s*\\-*to\\s*\\-*severe)|(mild-moderate)|(moderate-severe)|(mild)|(moderate)|(severe)|(no\\s*significant)|(significant)|(no\\s+evidence\\s)|(no\\s))");
	        Pattern p14a = Pattern.compile("((mildly\\s*dialated\\s*(moderate|severe))|(moderately\\s*severe\\s*to\\s*severe)|(mild\\s*\\-*to\\s*\\-*moderate)|(moderate\\s*\\-*to\\s*\\-*severe)|(mild-moderate)|(moderate-severe)|(mild)|(moderate)|(severe)|(no\\s*significant)|(significant)|(no\\s+evidence\\s*of*(\\sbioprosthetic)*)|(no\\s*))[\\s\\w]{1,30}((mitral.{1,15}stenosis)|(mitral\\svalve\\sstenosis))");
	        Pattern p15a = Pattern.compile("((mildly\\s*dialated\\s*(moderate|severe))|(moderately\\s*severe\\s*to\\s*severe)|(mild\\s*\\-*to\\s*\\-*moderate)|(moderate\\s*\\-*to\\s*\\-*severe)|(mild-moderate)|(moderate-severe)|(mild)|(moderate)|(severe)|(no\\s*significant)|(significant)|(no\\s+evidence\\s)|(no\\s))");
	      
	        Pattern p16 = Pattern.compile("(dimensionless\\s*index)\\s*(is|are|was|of|\\=)*\\s*((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))(?=(\\s|\\,|\\(|\\)|\\w+|(\\s\\d)|(\\.\\s*\\w+)))");
	        Pattern p17 = Pattern.compile("(?<=(dimensionless\\s{1,6}index.{1,5}))(\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+)");
	       
	        Pattern p18 = Pattern.compile("mva\\s*\\(p1\\/2t\\)(.){1,10}((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))");
	        Pattern p19 = Pattern.compile("(?<=(\\(p1\\/2t\\)\\:))((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))");
	      
	        Pattern p20 = Pattern.compile("((pulmonary\\s*artery\\s*systolic\\s*pressure.{1,20}(estimated)*.{1,10})|(pa\\s*systolic\\s*pressure)).{1,15}(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))((\\s*mmhg)|(\\s*mm\\s*hg)))");
	        Pattern p21 = Pattern.compile("(\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+)");
	       
	        Pattern p22 = Pattern.compile("((right\\s*atrial\\s*pressure)|(ra\\s*pressure)).{1,40}(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+)).{1,8}((\\s*mmhg)|(\\s*mm\\s*hg)))");
	        Pattern p23 = Pattern.compile("((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d+\\s*(\\-|to)\\s*\\d*\\.\\d+)|(\\d*\\.\\d+)|(\\d+))");
	      
	        Pattern p24 = Pattern.compile("((no\\s(valvular|perivalvular))|(trace\\sto\\smild)|(mildly\\s*dialated\\s*(moderate|severe))|(moderately\\s*severe\\s*to\\s*severe)|(mild\\s*\\-*to\\s*\\-*moderate)|(moderate\\s*\\-*to\\s*\\-*severe)|(mild-moderate)|(moderate-severe)|(mild)|(trace)|(moderate)|(severe)|(no\\s*significant)|(significant)|(no\\s+evidence\\s*of*)|(no\\s*))[\\s\\w]{1,35}((aortic)\\s*(insufficiency|regurgitation))");
	        Pattern p25 = Pattern.compile("((no\\s(valvular|perivalvular))|(trace\\sto\\smild)|(mildly\\s*dialated\\s*(moderate|severe))|(moderately\\s*severe\\s*to\\s*severe)|(mild\\s*\\-*to\\s*\\-*moderate)|(moderate\\s*\\-*to\\s*\\-*severe)|(mild-moderate)|(moderate-severe)|(mild)|(trace)|(moderate)|(severe)|(no\\s*significant)|(significant)|(no\\s+evidence\\s*of*)|(no\\s*))((?=(\\s*(\\w+)*\\s*aortic\\s*(insufficiency|regurgitation)))|((?=(\\s*(\\w+)*\\s*transvalvular|perivalvular)\\s*aortic\\s*(insufficiency|regurgitation))))");
	        Pattern p24a = Pattern.compile("((no\\s(valvular|perivalvular))|(trace\\sto\\smild)|(mildly\\s*dialated\\s*(moderate|severe))|(moderately\\s*severe\\s*to\\s*severe)|(mild\\s*\\-*to\\s*\\-*moderate)|(moderate\\s*\\-*to\\s*\\-*severe)|(mild-moderate)|(moderate-severe)|(mild)|(trace)|(moderate)|(severe)|(no\\s*significant)|(significant)|(no\\s+evidence\\s*of*)|(no\\s*))[\\s\\w]{1,35}((mitral)\\s*(insufficiency|regurgitation))");
	        Pattern p25a = Pattern.compile("((no\\s(valvular|perivalvular))|(trace\\sto\\smild)|(mildly\\s*dialated\\s*(moderate|severe))|(moderately\\s*severe\\s*to\\s*severe)|(mild\\s*\\-*to\\s*\\-*moderate)|(moderate\\s*\\-*to\\s*\\-*severe)|(mild-moderate)|(moderate-severe)|(mild)|(trace)|(moderate)|(severe)|(no\\s*significant)|(significant)|(no\\s+evidence\\s*of*)|(no\\s*))((?=(\\s*(\\w+)*\\s*mitral\\s*(insufficiency|regurgitation)))|((?=(\\s*(\\w+)*\\s*transvalvular|perivalvular)\\s*mitral\\s*(insufficiency|regurgitation))))");
	      
	        Pattern p26 = Pattern.compile("vena\\s*contracta(.){1,20}((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\s*(cm|mm)");
	        Pattern p27 = Pattern.compile("((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\s*(cm|mm)");
	       
	        Pattern p28 = Pattern.compile("regurgitant\\s*fraction(.){1,20}((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\s*(\\%)");
	        Pattern p29 = Pattern.compile("(\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d+\\.\\d+)|(\\d+)");
	      
	        Pattern p30 = Pattern.compile("((effective\\s*regurgitant\\s*orifice.{1,8}area)|(effective\\s*orifice\\s*area)|(effective\\s*regurgitant\\s*orifice)|(regurgitant\\s*orifice\\s*area))(.){1,50}((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\s*(cm2|mm2|mm\\?*|cm\\?*|sq\\scm|sq\\smm|m2)");
	        Pattern p31 = Pattern.compile("((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\s*(cm2|mm2|mm\\?*|cm\\?*|sq\\scm|sq\\smm|m2)");
	       
	        Pattern p32 = Pattern.compile("((ar)|(ai)|(aortic\\s*(insufficiency|regurgitation))).{1,5}pressure\\s*half.{1,5}time(.){1,10}(((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\s*(ms|msec|millisecond|millisec))");
	        Pattern p33 = Pattern.compile("((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))(?=(\\s*(ms|msec|millisecond|millisec)))");
	       
	        Pattern p34 = Pattern.compile("(.{1,30}(holodiastolic|diastolic|aorta).{1,20}(flow.{1,30}reversal).{1,20})|(.{1,30}(reversal\\s*of\\s*flow).{1,30}(aorta|diastole))");
	        Pattern p35 = Pattern.compile("(there\\s*is\\s*no\\s)|(there\\s*is\\s*(evidence)*)|(is\\s*not\\s*seen)|(there\\s*was)|(show)|(reveal)|(no\\s)|(no\\s*spectral*\\s*evidence)|(has)|(demonstrate(sd)*)|(with\\s*(evidence)*)|(with)");
	      
	        Pattern p36 = Pattern.compile("(lvef|(ef)|(lv\\s*ejection\\s*fraction)|(left\\s*(ventricular|ventricle)\\s*ejection\\s*fraction)|(ejection\\s*fraction)).{1,85}((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\s*(\\%)");
	        Pattern p37 = Pattern.compile("((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))(?=(\\s*(\\%)))");
	       
	        Pattern p38 = Pattern.compile("(diastolic\\s*(dysfunction|function).{1,30}grade\\s*(\\d|(i\\-)|(i\\s)))|(grade.{1,40}diastolic\\s*(dysfunction|function)(\\s\\w+\\s\\w+)*)");
	        Pattern p39 = Pattern.compile("grade\\s*[123iii]");
	      
	        Pattern p40 = Pattern.compile("(((mv)|(mitral\\s*valve))\\s*e(\\/a\\:)\\s*((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+)))|((mitral\\s*e\\s*to\\s*a\\s*ratio).{1,10}((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+)))"); //
	        Pattern p41 = Pattern.compile("(\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+)");
	      
	        Pattern p42 = Pattern.compile("((lv)|(left\\s*(ventricular|ventricle)))\\s*filling\\s*(pressure|pressures).{1,40}(increased|elevated|indeterminate|normal)"); //
	        Pattern p43 = Pattern.compile("(increased|elevated|indeterminate|normal)");
	       
	        Pattern p44 = Pattern.compile("((e\\/e\\')|(e\\:e\\'))[\\s*\\=\\w+]{1,50}((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))"); //
	        Pattern p45 = Pattern.compile("((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))");
	       
	        Pattern p46 = Pattern.compile("lvedd\\s*\\=*\\s*((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\s*((cm)|(mm)|(ccm))*|(lvedd\\s*(is|at|\\:)[\\s*\\w+]{1,50}((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\s*((cm)|(mm)|(ccm))*)");
	        Pattern p47 = Pattern.compile("((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\s*((cm)|(mm)|(ccm))*");
	        Pattern p48 = Pattern.compile("(lvsd|lvesd)\\s*\\=*\\s*(is\\s)*((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\s*((cm)|(mm)|(ccm))*");
	        Pattern p49 = Pattern.compile("((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\s*((cm)|(mm)|(ccm))*");
	       
	        Pattern p50 = Pattern.compile("concentric.{1,15}((lv)|((left\\s*(ventricular|ventricle)))\\s*hypertrophy)|(((lv)|(left\\s*(ventricular|ventricle)))\\s*concentric\\s*hypertrophy)"); //
	        Pattern p51 = Pattern.compile("concentric");
	        Pattern p50a = Pattern.compile("basal\\s*septal\\s*hypertrophy"); //
	        Pattern p51a = Pattern.compile("basal\\s*septal");
	       
	        Pattern p52 = Pattern.compile("((mild\\s*\\-*to*\\s*\\-*moderate)|(moderate\\s*\\-*to*\\s*\\-*severe)|(mild)|(severe)|(moderate)).{1,10}concentric.{1,15}((lv)|((left\\s*(ventricular|ventricle)))\\s*hypertrophy)");
	        Pattern p53 = Pattern.compile("((mild\\s*\\-*to*\\s*\\-*moderate)|(moderate\\s*\\-*to*\\s*\\-*severe)|(mild)|(severe)|(moderate))");
	        Pattern p52a = Pattern.compile("(((mild\\s*\\-*to*\\s*\\-*moderate)|(moderate\\s*\\-*to*\\s*\\-*severe)|(mild\\s*additiona*l)|(severe)|(moderate)|(mild))\\s{1,4}basal\\s{1,4}septal\\s{1,4}hypertrophy)");
	        Pattern p53a = Pattern.compile("((mild\\s*\\-*to*\\s*\\-*moderate)|(moderate\\s*\\-*to*\\s*\\-*severe)|(mild)|(severe)|(moderate))");
	       
	        Pattern p54 = Pattern.compile("septal\\s*thickness.{1,40}((\\d+\\s*\\-\\s*\\d+)|(\\d*\\.\\d*\\s*\\-\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\s(cm|mm)"); // |(.{1,100}septal\\s*w.{1,100})
	        Pattern p55 = Pattern.compile("(\\d+\\s*\\-\\s*\\d+)|(\\d*\\.\\d*\\s*\\-\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+)\\s(cm|mm)"); // |septal\\s*wall\\s*thick[\\s*\\-]diastole.{1,30}(\\d+|\\d*\\.\\d+)\\s*(cm|ccm|mm)
	      
	        Pattern p56 = Pattern.compile("((moderately\\s*severe\\s*to\\s*severe)|(mild\\s*\\-*to\\s*\\-*moderate)|(moderate\\s*\\-*to\\s*\\-*severe)|(mild)|(moderate)|(severe))((.{1,50}and\\s*left\\s*atrial\\s*enlargement)|(atrial\\s*enlargement)|(\\s*left\\s*atrial\\s*enlargement))");
	        Pattern p57 = Pattern.compile("(moderately\\s*severe\\s*to\\s*severe)|(mild\\s*\\-*to\\s*\\-*moderate)|(moderate\\s*\\-*to\\s*\\-*severe)|(mild)|(moderate)|(severe)");
	       
	        Pattern p58 = Pattern.compile("la\\s*dimension\\s*\\:\\s*((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\s*(cm|mm)"); //
	        Pattern p59 = Pattern.compile("((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))");
	        
	        Pattern p60 = Pattern.compile("volume\\s*index(.){1,20}((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))\\s{1,4}((ml\\s*per\\s*square\\s*meter)|(ml\\/m(2|\\?)*)|(ml\\/sq\\/m))"); //
	        Pattern p61 = Pattern.compile("((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))(?=(\\s*((ml\\s*per\\s*square\\s*meter)|(ml\\/m(2|\\?)*)|(ml\\/sq\\/m))))");
	       
	        Pattern p62 = Pattern.compile("(bsa).{12}"); //
			Pattern p63 = Pattern.compile("((\\d+\\s*(\\-|to)\\s*\\d+)|(\\d*\\.\\d*\\s*(\\-|to)\\s*\\d*\\.\\d*)|(\\d*\\.\\d+)|(\\d+))(?=(.*(\\(cm\\))|.*cm|.*m2|.*m\\?*))");

		// +++++++++++++++++++++++++++++++++++++++++++(LOOP OF CONCEPT
		// EXTRACTION)++++++++++++++++++++++++++++++++++++++++++//
		for (int i = 1; i < recordlist.size(); i++) {

			check = arrRecords[i][2];
			check1a = check.replaceAll("\\.{2,}", "");
			check1 = check1a.replaceAll("\\s{3,}", " ");
			// String ID = arrRecords[i][0];
			// String OC = arrRecords[i][3];

			// 1 //////////////////////////First Concept Start//
			// Here//////////////////////////////////
			// ////////////// Find concept (AVA) /////////////////////////

			if (check1.matches("(.*)((ava)|(av\\s*area)|(aortic\\s*valve\\s*area))(.*)")) {
				// System.out.println("for ID:" + ID + " and for OC " + OC);
				Matcher m = p.matcher(check1);
				while (m.find()) {
					check2 = check2 + " " + m.group();
					excelData[i][IndexCol] = excelData[i][IndexCol] + "\n"+ "<OUTPUT>" + m.group();
				}
				Matcher mx = Px.matcher(check2);
				check2 = mx.replaceAll("IndexRemoved");

				Matcher m1 = p1.matcher(check2);
				List<String> matchstring1 = new ArrayList<String>();
				while (m1.find()) {
					calculate(matchstring1, m1);
				}
				if (matchstring1 != null && !matchstring1.isEmpty()) {
					excelData[i][IndexCol + 1] = matchstring1.toString()+"@"+Integer.toString(matchstring1.size());
					excelData[i][IndexCol + 2] = Collections.max(matchstring1)+ "," + Collections.min(matchstring1);
					excelData[i][IndexCol + 3] = matchstring1.get(matchstring1.size() - 1);
					
				}
			}
			startIndexCol = IndexCol + (4 * 1);
			check2 = " ";

			// 2 //////////////////////////Next Concept Start//
			// Here//////////////////////////////////
			// ////////////// Find concept (MVA) /////////////////////////

			 if (check1.matches("(.*)((mva)|(mv\\s*area)|(mitral\\s*valve\\s*area))(.*)")) {
				Matcher m4 = p4.matcher(check1);
				while (m4.find()) {
					check2 = check2 + "^^ " + m4.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m4.group();
				}

				Matcher m5 = p5.matcher(check2);
				List<String> matchstring5 = new ArrayList<String>();
				while (m5.find()) {
					calculate(matchstring5, m5);
				}
				if (matchstring5 != null && !matchstring5.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring5.toString()+"@"+Integer.toString(matchstring5.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring5)+ ","+ Collections.min(matchstring5);
					excelData[i][startIndexCol + 3] = matchstring5.get(matchstring5.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 2);
			check2 = " ";

			// 3 //////////////////////////Next Concept Start//
			// Here//////////////////////////////////
			// ////////////// Find concept (AORTIC Leaflet)
			// //////////////////////
			if (check1.matches("(.*)(aortic)(.*)")) {
				Matcher m8 = p8.matcher(check1);
				while (m8.find()) {
					check2 = check2 + "^^ " + m8.group();
					
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m8.group();
				}
				Matcher m9 = p9.matcher(check2);
				List<String> matchstring9 = new ArrayList<String>();
				while (m9.find()) {
					matchstring9.add(m9.group());
					
				}
				if (matchstring9 != null && !matchstring9.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring9.toString()+"@"+Integer.toString(matchstring9.size());
					excelData[i][startIndexCol + 3] = matchstring9.get(matchstring9.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 3);
			check2 = " ";
			// 4 //////////////////////////Next Concept Start//
			// Here//////////////////////////////////
			// ////////////// Find concept (MITRAL Leaflet)
			// //////////////////////
			if (check1.matches("(.*)(mitral)(.*)")) {
				Matcher m8a = p8a.matcher(check1);
				while (m8a.find()) {
					check2 = check2 + "^^ " + m8a.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m8a.group();
				}
				Matcher m9a = p9a.matcher(check2);
				List<String> matchstring9a = new ArrayList<String>();
				while (m9a.find()) {
					matchstring9a.add(m9a.group());
				}
				if (matchstring9a != null && !matchstring9a.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring9a.toString()+"@"+Integer.toString(matchstring9a.size());
					excelData[i][startIndexCol + 3] = matchstring9a.get(matchstring9a.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 4);
			check2 = " ";
			// 5 //////////////////////////Next Concept Start//
			// Here//////////////////////////////////
			// ////////////// Find concept (AV Peak Gradient) /////////////////
			if (check1.matches("(.*)((peak)|(pg)|(max))(.*)")) {
				Matcher m10a = p10a.matcher(check1);
				while (m10a.find()) {
					check2 = check2 + "^^ " + m10a.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m10a.group();
				}
				Matcher m11a = p11a.matcher(check2);
				List<String> matchstring11a = new ArrayList<String>();
				while (m11a.find()) {
					calculate(matchstring11a, m11a);
				}
				if (matchstring11a != null && !matchstring11a.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring11a.toString()+"@"+Integer.toString(matchstring11a.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring11a)+ ","+ Collections.min(matchstring11a);
					excelData[i][startIndexCol + 3] = matchstring11a.get(matchstring11a.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 5);
			check2 = " ";
			// 6 //////////////////////////Next Concept Start//
			// Here//////////////////////////////////
			// ////////////// Find concept (MV Peak Gradient) /////////////////
			if (check1.matches("(.*)((peak)|(pg)|(max))(.*)")) {
				Matcher m10b = p10b.matcher(check1);
				while (m10b.find()) {
					check2 = check2 + "^^ " + m10b.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m10b.group();
				}
				Matcher m11b = p11b.matcher(check2);
				List<String> matchstring11b = new ArrayList<String>();
				while (m11b.find()) {
					calculate(matchstring11b, m11b);
				}
				if (matchstring11b != null && !matchstring11b.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring11b.toString()+"@"+Integer.toString(matchstring11b.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring11b)+ ","+ Collections.min(matchstring11b);
					excelData[i][startIndexCol + 3] = matchstring11b.get(matchstring11b.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 6);
			check2 = " ";
			// 7 //////////////////////////Next Concept Start//
			// Here//////////////////////////////////
			// ////////////// Find concept (MR Peak Gradient) /////////////////
			if (check1.matches("(.*)((peak)|(pg)|(max))(.*)")) {
				Matcher m10c = p10c.matcher(check1);
				while (m10c.find()) {
					check2 = check2 + "^^ " + m10c.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m10c.group();
				}
				Matcher m11c = p11c.matcher(check2);
				List<String> matchstring11c = new ArrayList<String>();
				while (m11c.find()) {
					calculate(matchstring11c, m11c);
				}
				if (matchstring11c != null && !matchstring11c.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring11c.toString()+"@"+Integer.toString(matchstring11c.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring11c)+ ","+ Collections.min(matchstring11c);
					excelData[i][startIndexCol + 3] = matchstring11c.get(matchstring11c.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 7);
			check2 = " ";
			// 8 //////////////////////////Next Concept Start//
			// Here//////////////////////////////////
			// ////////////// Find concept (TR Peak Gradient) /////////////////
			if (check1.matches("(.*)((peak)|(pg)|(max))(.*)")) {
				Matcher m10d = p10d.matcher(check1);
				while (m10d.find()) {
					check2 = check2 + "^^ " + m10d.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m10d.group();
				}
				Matcher m11d = p11d.matcher(check2);
				List<String> matchstring11d = new ArrayList<String>();
				while (m11d.find()) {
					calculate(matchstring11d, m11d);
				}
				if (matchstring11d != null && !matchstring11d.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring11d.toString()+"@"+Integer.toString(matchstring11d.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring11d)+ ","+ Collections.min(matchstring11d);
					excelData[i][startIndexCol + 3] = matchstring11d.get(matchstring11d.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 8);
			check2 = " ";
			// 9 //////////////////////////Next Concept Start//
			// Here//////////////////////////////////
			// ////////////// Find concept (TV Peak Gradient) /////////////////
			if (check1.matches("(.*)((peak)|(pg)|(max))(.*)")) {
				Matcher m10e = p10e.matcher(check1);
				while (m10e.find()) {
					check2 = check2 + "^^ " + m10e.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m10e.group();
				}
				Matcher m11e = p11e.matcher(check2);
				List<String> matchstring11e = new ArrayList<String>();
				while (m11e.find()) {
					calculate(matchstring11e, m11e);
				}
				if (matchstring11e != null && !matchstring11e.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring11e.toString()+"@"+Integer.toString(matchstring11e.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring11e)+ ","+ Collections.min(matchstring11e);
					excelData[i][startIndexCol + 3] = matchstring11e.get(matchstring11e.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 9);
			check2 = " ";
			// 10 //////////////////////////Next Concept Start//
			// Here//////////////////////////////////
			// ////////////// Find concept (PV Peak Gradient) /////////////////
			if (check1.matches("(.*)((peak)|(pg)|(max))(.*)")) {
				Matcher m10f = p10f.matcher(check1);
				while (m10f.find()) {
					check2 = check2 + "^^ " + m10f.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m10f.group();
				}
				Matcher m11f = p11f.matcher(check2);
				List<String> matchstring11f = new ArrayList<String>();
				while (m11f.find()) {
					calculate(matchstring11f, m11f);
				}
				if (matchstring11f != null && !matchstring11f.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring11f.toString()+"@"+Integer.toString(matchstring11f.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring11f)+ ","+ Collections.min(matchstring11f);
					excelData[i][startIndexCol + 3] = matchstring11f.get(matchstring11f.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 10);
			check2 = " ";
			// 11 //////////////////////////Next Concept Start//
			// Here//////////////////////////////////
			// ////////////// Find concept (LVOT Peak Gradient)
			// /////////////////
			if (check1.matches("(.*)((peak)|(pg)|(max))(.*)")) {
				Matcher m10g = p10g.matcher(check1);
				while (m10g.find()) {
					check2 = check2 + "^^ " + m10g.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m10g.group();
				}
				Matcher m11g = p11g.matcher(check2);
				List<String> matchstring11g = new ArrayList<String>();
				while (m11g.find()) {
					calculate(matchstring11g, m11g);
				}
				if (matchstring11g != null && !matchstring11g.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring11g.toString()+"@"+Integer.toString(matchstring11g.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring11g)+ ","+ Collections.min(matchstring11g);
					excelData[i][startIndexCol + 3] = matchstring11g.get(matchstring11g.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 11);
			check2 = " ";
			// 12 //////////////////////////Next Concept Start//
			// Here//////////////////////////////////
			// ////////////// Find concept (RVOT Peak Gradient)
			// /////////////////
			if (check1.matches("(.*)((peak)|(pg)|(max))(.*)")) {
				Matcher m10h = p10h.matcher(check1);
				while (m10h.find()) {
					check2 = check2 + "^^ " + m10h.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m10h.group();
				}
				Matcher m11h = p11h.matcher(check2);
				List<String> matchstring11h = new ArrayList<String>();
				while (m11h.find()) {
					calculate(matchstring11h, m11h);
				}
				if (matchstring11h != null && !matchstring11h.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring11h.toString()+"@"+Integer.toString(matchstring11h.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring11h)+ ","+ Collections.min(matchstring11h);
					excelData[i][startIndexCol + 3] = matchstring11h.get(matchstring11h.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 12);
			check2 = " ";
			// 13 //////////////////////////Next Concept Start//
			// Here//////////////////////////////////
			// ////////////// Find concept (AV Mean Gradient) /////////////////
			if (check1.matches("(.*)((mean)|(pg))(.*)")) {
				Matcher m10aa = p10aa.matcher(check1);
				while (m10aa.find()) {
					check2 = check2 + "^^ " + m10aa.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m10aa.group();
				}
				Matcher m11aa = p11aa.matcher(check2);
				List<String> matchstring11aa = new ArrayList<String>();
				while (m11aa.find()) {
					calculate(matchstring11aa, m11aa);
				}
				if (matchstring11aa != null && !matchstring11aa.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring11aa.toString()+"@"+Integer.toString(matchstring11aa.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring11aa)+ ","+ Collections.min(matchstring11aa);
					excelData[i][startIndexCol + 3] = matchstring11aa.get(matchstring11aa.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 13);
			check2 = " ";
			// 14 //////////////////////////Next Concept Start//
			// Here//////////////////////////////////
			// ////////////// Find concept (MV Mean Gradient) /////////////////
			if (check1.matches("(.*)((mean)|(pg))(.*)")) {
				Matcher m10ab = p10ab.matcher(check1);
				while (m10ab.find()) {
					check2 = check2 + "^^ " + m10ab.group();
					
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m10ab.group();
				}
				Matcher m11ab = p11ab.matcher(check2);
				List<String> matchstring11ab = new ArrayList<String>();
				while (m11ab.find()) {
					calculate(matchstring11ab, m11ab);
				}
				if (matchstring11ab != null && !matchstring11ab.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring11ab.toString()+"@"+Integer.toString(matchstring11ab.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring11ab)+ ","+ Collections.min(matchstring11ab);
					excelData[i][startIndexCol + 3] = matchstring11ab.get(matchstring11ab.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 14);
			check2 = " ";
			// 15 //////////////////////////Next Concept Start//
			// Here//////////////////////////////////
			// ////////////// Find concept (PV Mean Gradient) /////////////////
			if (check1.matches("(.*)((mean)|(pg))(.*)")) {
				Matcher m10ac = p10ac.matcher(check1);
				while (m10ac.find()) {
					check2 = check2 + "^^ " + m10ac.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m10ac.group();
				}
				Matcher m11ac = p11ac.matcher(check2);
				List<String> matchstring11ac = new ArrayList<String>();
				while (m11ac.find()) {
					calculate(matchstring11ac, m11ac);
				}
				if (matchstring11ac != null && !matchstring11ac.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring11ac.toString()+"@"+Integer.toString(matchstring11ac.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring11ac)+ ","+ Collections.min(matchstring11ac);
					excelData[i][startIndexCol + 3] = matchstring11ac.get(matchstring11ac.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 15);
			check2 = " ";
			// 16 //////////////////////////Next Concept Start//
			// Here//////////////////////////////////
			// ////////////// Find concept (Tricuspid Mean Gradient)
			// /////////////////
			if (check1.matches("(.*)((mean)|(pg))(.*)")) {
				Matcher m10ad = p10ad.matcher(check1);
				while (m10ad.find()) {
					check2 = check2 + "^^ " + m10ad.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m10ad.group();
				}
				Matcher m11ad = p11ad.matcher(check2);
				List<String> matchstring11ad = new ArrayList<String>();
				while (m11ad.find()) {
					calculate(matchstring11ad, m11ad);
				}
				if (matchstring11ad != null && !matchstring11ad.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring11ad.toString()+"@"+Integer.toString(matchstring11ad.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring11ad)+ ","+ Collections.min(matchstring11ad);
					excelData[i][startIndexCol + 3] = matchstring11ad.get(matchstring11ad.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 16);
			check2 = " ";
			// 17 //////////////////////////Next Concept Start//
			// Here//////////////////////////////////
			// ////////////// Find concept (TV Mean Gradient) /////////////////
			if (check1.matches("(.*)((mean)|(pg))(.*)")) {
				Matcher m10ae = p10ae.matcher(check1);
				while (m10ae.find()) {
					check2 = check2 + "^^ " + m10ae.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m10ae.group();
				}
				Matcher m11ae = p11ae.matcher(check2);
				List<String> matchstring11ae = new ArrayList<String>();
				while (m11ae.find()) {
					calculate(matchstring11ae, m11ae);
				}
				if (matchstring11ae != null && !matchstring11ae.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring11ae.toString()+"@"+Integer.toString(matchstring11ae.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring11ae)+ ","+ Collections.min(matchstring11ae);
					excelData[i][startIndexCol + 3] = matchstring11ae.get(matchstring11ae.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 17);
			check2 = " ";
			// 18 //////////////////////////Next Concept Start//
			// Here//////////////////////////////////
			// ////////////// Find concept (Bioprosthesis Mean Gradient)
			// /////////////////
			if (check1.matches("(.*)((mean)|(pg))(.*)")) {
				Matcher m10af = p10af.matcher(check1);
				while (m10af.find()) {
					check2 = check2 + "^^ " + m10af.group();

					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m10af.group();
				}
				Matcher m11af = p11af.matcher(check2);
				List<String> matchstring11af = new ArrayList<String>();
				while (m11af.find()) {
					calculate(matchstring11af, m11af);
				}
				if (matchstring11af != null && !matchstring11af.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring11af.toString()+"@"+Integer.toString(matchstring11af.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring11af)+ ","+ Collections.min(matchstring11af);
					excelData[i][startIndexCol + 3] = matchstring11af.get(matchstring11af.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 18);
			check2 = " ";
			// 19 ///////////////// Next Concept Start Here ///////////////
			// ///////////////////////////////////Find concept// (RVOT Peak
			// Velocity)/////////////
			if (check1.matches("(.*)((peak)|(max))(.*)")) {
				Matcher m12a = p12a.matcher(check1);
				while (m12a.find()) {
					check2 = check2 + "^^ " + m12a.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m12a.group();
				}
				Matcher m13a = p13a.matcher(check2);
				List<String> matchstring13a = new ArrayList<String>();
				while (m13a.find()) {
					calculate_z(matchstring13a, m13a);
				}
				if (matchstring13a != null && !matchstring13a.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring13a.toString()+"@"+Integer.toString(matchstring13a.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring13a)+ ","+ Collections.min(matchstring13a);
					excelData[i][startIndexCol + 3] = matchstring13a.get(matchstring13a.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 19);
			check2 = " ";
			// 20 ///////////////// Next Concept Start Here ///////////////
			// ///////////////////////////////////Find concept// (LVOT Peak
			// Velocity)/////////////
			if (check1.matches("(.*)((peak)|(max))(.*)")) {
				Matcher m12b = p12b.matcher(check1);
				while (m12b.find()) {
					check2 = check2 + "^^ " + m12b.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m12b.group();
				}
				Matcher m13b = p13b.matcher(check2);
				List<String> matchstring13b = new ArrayList<String>();
				while (m13b.find()) {
					calculate_z(matchstring13b, m13b);
				}
				if (matchstring13b != null && !matchstring13b.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring13b.toString()+"@"+Integer.toString(matchstring13b.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring13b)+ ","+ Collections.min(matchstring13b);
					excelData[i][startIndexCol + 3] = matchstring13b.get(matchstring13b.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 20);
			check2 = " ";
			// 21 ///////////////// Next Concept Start Here ///////////////
			// ///////////////////////////////////Find concept// (TV Peak
			// Velocity)/////////////
			if (check1.matches("(.*)((peak)|(max))(.*)")) {
				Matcher m12c = p12c.matcher(check1);
				while (m12c.find()) {
					check2 = check2 + "^^ " + m12c.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m12c.group();
				}
				Matcher m13c = p13c.matcher(check2);
				List<String> matchstring13c = new ArrayList<String>();
				while (m13c.find()) {
					calculate_z(matchstring13c, m13c);
				}
				if (matchstring13c != null && !matchstring13c.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring13c.toString()+"@"+Integer.toString(matchstring13c.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring13c)+ ","+ Collections.min(matchstring13c);
					excelData[i][startIndexCol + 3] = matchstring13c.get(matchstring13c.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 21);
			check2 = " ";
			// 22 ///////////////// Next Concept Start Here ///////////////
			// ///////////////////////////////////Find concept// (MV Peak
			// Velocity)/////////////
			if (check1.matches("(.*)((peak)|(max))(.*)")) {
				Matcher m12d = p12d.matcher(check1);
				while (m12d.find()) {
					check2 = check2 + "^^ " + m12d.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m12d.group();
				}
				Matcher m13d = p13d.matcher(check2);
				List<String> matchstring13d = new ArrayList<String>();
				while (m13d.find()) {
					calculate_z(matchstring13d, m13d);
				}
				if (matchstring13d != null && !matchstring13d.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring13d.toString()+"@"+Integer.toString(matchstring13d.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring13d)+ ","+ Collections.min(matchstring13d);
					excelData[i][startIndexCol + 3] = matchstring13d.get(matchstring13d.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 22);
			check2 = " ";
			// 23 ///////////////// Next Concept Start Here ///////////////
			// ///////////////////////////////////Find concept// (PV Peak
			// Velocity)/////////////
			if (check1.matches("(.*)((peak)|(max))(.*)")) {
				Matcher m12e = p12e.matcher(check1);
				while (m12e.find()) {
					check2 = check2 + "^^ " + m12e.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m12e.group();
				}
				Matcher m13e = p13e.matcher(check2);
				List<String> matchstring13e = new ArrayList<String>();
				while (m13e.find()) {
					calculate_z(matchstring13e, m13e);
				}
				if (matchstring13e != null && !matchstring13e.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring13e.toString()+"@"+Integer.toString(matchstring13e.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring13e)+ ","+ Collections.min(matchstring13e);
					excelData[i][startIndexCol + 3] = matchstring13e.get(matchstring13e.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 23);
			check2 = " ";
			// 24 ///////////////// Next Concept Start Here ///////////////
			// ///////////////////////////////////Find concept// (AV Peak
			// Velocity)/////////////
			if (check1.matches("(.*)((peak)|(max))(.*)")) {
				Matcher m12f = p12f.matcher(check1);
				while (m12f.find()) {
					check2 = check2 + "^^ " + m12f.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m12f.group();
				}
				Matcher m13f = p13f.matcher(check2);
				List<String> matchstring13f = new ArrayList<String>();
				while (m13f.find()) {
//					calculate(matchstring13f, m13f);
					calculate_z(matchstring13f, m13f);
				}
				if (matchstring13f != null && !matchstring13f.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring13f.toString()+"@"+Integer.toString(matchstring13f.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring13f)+ ","+ Collections.min(matchstring13f);
					excelData[i][startIndexCol + 3] = matchstring13f.get(matchstring13f.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 24);
			check2 = " ";
			// 25 ///////////////// Next Concept Start Here ///////////////
			// ///////////////////////////////////Find concept// (TR Peak
			// Velocity)/////////////
			if (check1.matches("(.*)((peak)|(max))(.*)")) {
				Matcher m12g = p12g.matcher(check1);
				while (m12g.find()) {
					check2 = check2 + "^^ " + m12g.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m12g.group();
				}
				Matcher m13g = p13g.matcher(check2);
				List<String> matchstring13g = new ArrayList<String>();
				while (m13g.find()) {
					calculate_z(matchstring13g, m13g);
				}
				if (matchstring13g != null && !matchstring13g.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring13g.toString()+"@"+Integer.toString(matchstring13g.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring13g)+ ","+ Collections.min(matchstring13g);
					excelData[i][startIndexCol + 3] = matchstring13g.get(matchstring13g.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 25);
			check2 = " ";
			// 26 ///////////////// Next Concept Start Here ///////////////
			// ///////////////////////////////////Find concept// (MR Peak
			// Velocity)/////////////
			if (check1.matches("(.*)((peak)|(max))(.*)")) {
				Matcher m12h = p12h.matcher(check1);
				while (m12h.find()) {
					check2 = check2 + "^^ " + m12h.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m12h.group();
				}
				Matcher m13h = p13h.matcher(check2);
				List<String> matchstring13h = new ArrayList<String>();
				while (m13h.find()) {
					calculate_z(matchstring13h, m13h);
				}
				if (matchstring13h != null && !matchstring13h.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring13h.toString()+"@"+Integer.toString(matchstring13h.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring13h)+ ","+ Collections.min(matchstring13h);
					excelData[i][startIndexCol + 3] = matchstring13h.get(matchstring13h.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 26);
			check2 = " ";
			// 27 ///////////////// Next Concept Start Here ///////////////
			// ///////////////////////////////////Find concept // (LVOT Mean
			// Velocity)/////////////
			if (check1.matches("(.*)(mean.{1,25}velocity)(.*)")) {
				Matcher m12aa = p12aa.matcher(check1);
				while (m12aa.find()) {
					check2 = check2 + "^^ " + m12aa.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m12aa.group();
				}
				Matcher m13aa = p13aa.matcher(check2);
				List<String> matchstring13aa = new ArrayList<String>();
				while (m13aa.find()) {
					calculate_z(matchstring13aa, m13aa);
				}
				if (matchstring13aa != null && !matchstring13aa.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring13aa.toString()+"@"+Integer.toString(matchstring13aa.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring13aa)+ ","+ Collections.min(matchstring13aa);
					excelData[i][startIndexCol + 3] = matchstring13aa.get(matchstring13aa.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 27);
			check2 = " ";
			// 28 ///////////////// Next Concept Start Here ///////////////
			// ///////////////////////////////////Find concept // (PV Mean
			// Velocity)/////////////
			if (check1.matches("(.*)(mean.{1,25}velocity)(.*)")) {
				Matcher m12ab = p12ab.matcher(check1);
				while (m12ab.find()) {
					check2 = check2 + "^^ " + m12ab.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m12ab.group();
				}
				Matcher m13ab = p13ab.matcher(check2);
				List<String> matchstring13ab = new ArrayList<String>();
				while (m13ab.find()) {
					calculate_z(matchstring13ab, m13ab);
				}
				if (matchstring13ab != null && !matchstring13ab.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring13ab.toString()+"@"+Integer.toString(matchstring13ab.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring13ab)+ ","+ Collections.min(matchstring13ab);
					excelData[i][startIndexCol + 3] = matchstring13ab.get(matchstring13ab.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 28);
			check2 = " ";
			// 29 ///////////////// Next Concept Start Here ///////////////
			// ///////////////////////////////////Find concept // (MR Mean
			// Velocity)/////////////
			if (check1.matches("(.*)(mean.{1,25}velocity)(.*)")) {
				Matcher m12ac = p12ac.matcher(check1);
				while (m12ac.find()) {
					check2 = check2 + "^^ " + m12ac.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m12ac.group();
				}
				Matcher m13ac = p13ac.matcher(check2);
				List<String> matchstring13ac = new ArrayList<String>();
				while (m13ac.find()) {
					calculate_z(matchstring13ac, m13ac);
				}
				if (matchstring13ac != null && !matchstring13ac.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring13ac.toString()+"@"+Integer.toString(matchstring13ac.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring13ac)+ ","+ Collections.min(matchstring13ac);
					excelData[i][startIndexCol + 3] = matchstring13ac.get(matchstring13ac.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 29);
			check2 = " ";
			// 30 ///////////////// Next Concept Start Here ///////////////
			// ///////////////////////////////////Find concept // (AV Mean
			// Velocity)/////////////
			if (check1.matches("(.*)(mean.{1,25}velocity)(.*)")) {
				Matcher m12ad = p12ad.matcher(check1);
				while (m12ad.find()) {
					check2 = check2 + "^^ " + m12ad.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m12ad.group();
				}
				Matcher m13ad = p13ad.matcher(check2);
				List<String> matchstring13ad = new ArrayList<String>();
				while (m13ad.find()) {
					calculate_z(matchstring13ad, m13ad);
				}
				if (matchstring13ad != null && !matchstring13ad.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring13ad.toString()+"@"+Integer.toString(matchstring13ad.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring13ad)+ ","+ Collections.min(matchstring13ad);
					excelData[i][startIndexCol + 3] = matchstring13ad.get(matchstring13ad.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 30);
			check2 = " ";
			// 31 ///////////////// Next Concept Start Here ///////////////
			// ///////////////////////////////////Find concept // (MV Mean
			// Velocity)/////////////
			if (check1.matches("(.*)(mean.{1,25}velocity)(.*)")) {
				Matcher m12ae = p12ae.matcher(check1);
				while (m12ae.find()) {
					check2 = check2 + "^^ " + m12ae.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m12ae.group();
				}
				Matcher m13ae = p13ae.matcher(check2);
				List<String> matchstring13ae = new ArrayList<String>();
				while (m13ae.find()) {
					calculate_z(matchstring13ae, m13ae);
				}
				if (matchstring13ae != null && !matchstring13ae.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring13ae.toString()+"@"+Integer.toString(matchstring13ae.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring13ae)+ ","+ Collections.min(matchstring13ae);
					excelData[i][startIndexCol + 3] = matchstring13ae.get(matchstring13ae.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 31);
			check2 = " ";
			// 32 ///////////////// Next Concept Start Here ///////////////
			// ///////////////////////////////////Find concept // (TV Mean
			// Velocity)/////////////
			if (check1.matches("(.*)(mean.{1,25}velocity)(.*)")) {
				Matcher m12af = p12af.matcher(check1);
				while (m12af.find()) {
					check2 = check2 + "^^ " + m12af.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m12af.group();
				}
				Matcher m13af = p13af.matcher(check2);
				List<String> matchstring13af = new ArrayList<String>();
				while (m13af.find()) {
					calculate_z(matchstring13af, m13af);
				}
				if (matchstring13af != null && !matchstring13af.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring13af.toString()+"@"+Integer.toString(matchstring13af.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring13af)+ ","+ Collections.min(matchstring13af);
					excelData[i][startIndexCol + 3] = matchstring13af.get(matchstring13af.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 32);
			check2 = " ";
			// 33 ///////////////// Next Concept Start Here ///////////////
			// //////////////// Find concept (Aortic Stenosis Severity)//
			// //////////////////
			if (check1.matches("(.*)((stenosis))(.*)")) {
				Matcher m14 = p14.matcher(check1);
				while (m14.find()) {
					check2 = check2 + "^^" + m14.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m14.group();
				}
				Matcher m15 = p15.matcher(check2);
				List<String> matchstring15 = new ArrayList<String>();
				while (m15.find()) {
					matchstring15.add(m15.group());
				}
				if (matchstring15 != null && !matchstring15.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring15.toString()+"@"+Integer.toString(matchstring15.size());
					excelData[i][startIndexCol + 3] = matchstring15.get(matchstring15.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 33);
			check2 = " ";
			// 34 ///////////////// Next Concept Start Here ///////////////
			// //////////////// Find concept (Mitral Stenosis Severity)//
			// //////////////////
			if (check1.matches("(.*)((stenosis))(.*)")) {
				Matcher m14a = p14a.matcher(check1);
				while (m14a.find()) {
					check2 = check2 + "^^ " + m14a.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m14a.group();
				}
				Matcher m15a = p15a.matcher(check2);
				List<String> matchstring15a = new ArrayList<String>();
				while (m15a.find()) {
					matchstring15a.add(m15a.group());
				}
				if (matchstring15a != null && !matchstring15a.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring15a.toString()+"@"+Integer.toString(matchstring15a.size());
					excelData[i][startIndexCol + 3] = matchstring15a.get(matchstring15a.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 34);
			check2 = " ";
			// 35 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (Dimensionless Index)//
			// //////////////////

			if (check1.matches("(.*)((dimensionless\\s*index))(.*)")) {
				Matcher m16 = p16.matcher(check1);
				while (m16.find()) {
					check2 = check2 + "^^ " + m16.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m16.group();
				}
				Matcher m17 = p17.matcher(check2);
				List<String> matchstring17 = new ArrayList<String>();
				while (m17.find()) {
					matchstring17.add(m17.group());
				}
				if (matchstring17 != null && !matchstring17.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring17.toString()+"@"+Integer.toString(matchstring17.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring17)+ ","+ Collections.min(matchstring17);
					excelData[i][startIndexCol + 3] = matchstring17.get(matchstring17.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 35);
			check2 = " ";
			// 36 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (MVA(P1/2T)) //////////////////
			if (check1.matches("(.*)(\\(p1\\/2t\\))(.*)")) {
				Matcher m18 = p18.matcher(check1);
				while (m18.find()) {
					check2 = check2 + "^^ " + m18.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m18.group();
				}
				Matcher m19 = p19.matcher(check2);
				List<String> matchstring19 = new ArrayList<String>();
				while (m19.find()) {
					matchstring19.add(m19.group());
				}
				if (matchstring19 != null && !matchstring19.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring19.toString()+"@"+Integer.toString(matchstring19.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring19)+ ","+ Collections.min(matchstring19);
					excelData[i][startIndexCol + 3] = matchstring19.get(matchstring19.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 36);
			check2 = " ";
			// 37 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (Pulmonary Artery Systolic pressure)//
			// //////////////////
			if (check1.matches("(.*)((systolic\\s*pressure))(.*)")) {
				Matcher m20 = p20.matcher(check1);
				while (m20.find()) {
					check2 = check2 + "^^ " + m20.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m20.group();
				}
				Matcher m21 = p21.matcher(check2);
				List<String> matchstring21 = new ArrayList<String>();
				while (m21.find()) {
					calculate(matchstring21, m21);
				}
				if (matchstring21 != null && !matchstring21.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring21.toString()+"@"+Integer.toString(matchstring21.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring21)+ ","+ Collections.min(matchstring21);
					excelData[i][startIndexCol + 3] = matchstring21.get(matchstring21.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 37);
			check2 = " ";
			// 38 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (Right Artery Pressure) //
			if (check1.matches("(.*)((ra\\s*pressure)|(atrial\\s*pressure))(.*)")) {
				Matcher m22 = p22.matcher(check1);
				while (m22.find()) {
					check2 = check2 + "^^ " + m22.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m22.group();
				}
				Matcher m23 = p23.matcher(check2);
				List<String> matchstring23 = new ArrayList<String>();
				while (m23.find()) {
					calculate(matchstring23, m23);
				}
				if (matchstring23 != null && !matchstring23.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring23.toString()+"@"+Integer.toString(matchstring23.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring23)+ ","+ Collections.min(matchstring23);
					excelData[i][startIndexCol + 3] = matchstring23.get(matchstring23.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 38);
			check2 = " ";
			// +++++++++++++++++++++++++++++++++++++++++++(REGURGITATION
			// GRADING)++++++++++++++++++++++++++++++++++++++//
			// 39 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (aortic regurgitation) //
			if (check1.matches("(.*)(aortic\\s*regurgitation)(.*)")) {
				Matcher m24 = p24.matcher(check1);
				while (m24.find()) {
					check2 = check2 + "^^ " + m24.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m24.group();
				}
				Matcher m25 = p25.matcher(check2);
				List<String> matchstring25 = new ArrayList<String>();
				while (m25.find()) {
					matchstring25.add(m25.group());
				}
				if (matchstring25 != null && !matchstring25.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring25.toString()+"@"+Integer.toString(matchstring25.size());
					excelData[i][startIndexCol + 3] = matchstring25.get(matchstring25.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 39);
			check2 = " ";
			// 40 ///// Next Concept Start Here ///////////////
			// ////////////////Find concept (mitral regurgitation) //
			if (check1.matches("(.*)(mitral\\s*regurgitation)(.*)")) {
				Matcher m24a = p24a.matcher(check1);
				while (m24a.find()) {
					check2 = check2 + "^^ " + m24a.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m24a.group();
				}
				Matcher m25a = p25a.matcher(check2);
				List<String> matchstring25a = new ArrayList<String>();
				while (m25a.find()) {
					matchstring25a.add(m25a.group());
				}
				if (matchstring25a != null && !matchstring25a.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring25a.toString()+"@"+Integer.toString(matchstring25a.size());
					excelData[i][startIndexCol + 3] = matchstring25a.get(matchstring25a.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 40);
			check2 = " ";
			// 41 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (vena contracta) //
			if (check1.matches("(.*)(vena\\s*contracta)(.*)")) {
				Matcher m26 = p26.matcher(check1);
				while (m26.find()) {
					check2 = check2 + "^^ " + m26.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m26.group();
				}
				Matcher m27 = p27.matcher(check2);
				List<String> matchstring27 = new ArrayList<String>();
				while (m27.find()) {
					if (m27.group().contains("mm") && !m27.group().contains("-") && !m27.group().contains("[")) {
						String[] items = m27.group().split("mm");
						Float f = new Float(items[0]);
						String ca = Float.toString(f / 10) + " cm";
						matchstring27.add(ca);
					} else {
						matchstring27.add(m27.group()); // TO DO include -
					}
				}
				if (matchstring27 != null && !matchstring27.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring27.toString()+"@"+Integer.toString(matchstring27.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring27)+ ","+ Collections.min(matchstring27);
					excelData[i][startIndexCol + 3] = matchstring27.get(matchstring27.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 41);
			check2 = " ";
			// 42 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (regurgitant fraction) //
			if (check1.matches("(.*)(regurgitant\\s*fraction)(.*)")) {
				Matcher m28 = p28.matcher(check1);
				while (m28.find()) {
					check2 = check2 + "^^ " + m28.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m28.group();
				}
				Matcher m29 = p29.matcher(check2);
				List<String> matchstring29 = new ArrayList<String>();
				while (m29.find()) {
					matchstring29.add(m29.group());
				}
				if (matchstring29 != null && !matchstring29.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring29.toString()+"@"+Integer.toString(matchstring29.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring29)+ ","+ Collections.min(matchstring29);
					excelData[i][startIndexCol + 3] = matchstring29.get(matchstring29.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 42);
			check2 = " ";
			// 43 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (regurgitant orifice area) //
			if (check1.matches("(.*)(orifice)(.*)")) {
				Matcher m30 = p30.matcher(check1);
				while (m30.find()) {
					check2 = check2 + "^^ " + m30.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m30.group();
				}
					Matcher m31 = p31.matcher(check2);
					List<String> matchstring31 = new ArrayList<String>();
					while (m31.find()) {
						if (m31.group().contains("mm") && !m31.group().contains("-") && !m31.group().contains("[")) {
							String[] items = m31.group().split("mm..*|sq\\smm|mm");
							Float f = new Float(items[0]);
							String ca = Float.toString(f / 100) + " cm2";
							matchstring31.add(ca);
						} else {
							matchstring31.add(m31.group());
						}
					}
					if (matchstring31 != null && !matchstring31.isEmpty()) {
						excelData[i][startIndexCol + 1] = matchstring31.toString()+"@"+Integer.toString(matchstring31.size());
						excelData[i][startIndexCol + 2] = Collections.max(matchstring31)+ ","+ Collections.min(matchstring31);
						excelData[i][startIndexCol + 3] = matchstring31.get(matchstring31.size() - 1);
					}
				}
				startIndexCol = IndexCol + (4 * 43);
				check2 = " ";
				// 44 ///////////////// Next Concept Start Here ///////////////
				// ////////////////Find concept (Pressure Half time) //
				if (check1.matches("(.*)(pressure\\s*half.{1,5}time)(.*)")) {
				Matcher m32 = p32.matcher(check1);
				while (m32.find()) {
					check2 = check2 + "^^ " + m32.group();
					
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m32.group();
				}
				Matcher m33 = p33.matcher(check2);
				List<String> matchstring33 = new ArrayList<String>();
				while (m33.find()) {
					matchstring33.add(m33.group());
				}
				if (matchstring33 != null && !matchstring33.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring33.toString()+"@"+Integer.toString(matchstring33.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring33)+ ","+ Collections.min(matchstring33);
					excelData[i][startIndexCol + 3] = matchstring33.get(matchstring33.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 44);
			check2 = " ";
			// 45 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (Aortic Flow Reversal) //
			if (check1.matches("(.*)((flow.{1,30}reversal)|(reversal\\s*of\\s*flow))(.*)")) {
				Matcher m34 = p34.matcher(check1);
				while (m34.find()) {
					check2 = check2 + "^^ " + m34.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m34.group();
				}
				Matcher m35 = p35.matcher(check2);
				List<String> matchstring35 = new ArrayList<String>();
				while (m35.find()) {
					matchstring35.add(m35.group());
				}	
				if (matchstring35 != null && !matchstring35.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring35.toString()+"@"+Integer.toString(matchstring35.size());
					excelData[i][startIndexCol + 3] = matchstring35.get(matchstring35.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 45);
			check2 = " ";
			// +++++++++++++++++++++++++++++++++++++++++++(LEFT
			// VENTRICLE)++++++++++++++++++++++++++++++++++++++//
			// 46 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (LVEF) //
			if (check1.matches("(.*)((lvef)|(ejection\\s*fraction)|(ef))(.*)")) {
				Matcher m36 = p36.matcher(check1);
				while (m36.find()) {
					check2 = check2 + "^^ " + m36.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m36.group();
				}
				Matcher m37 = p37.matcher(check2);
				List<String> matchstring37 = new ArrayList<String>();
				while (m37.find()) {
					calculate(matchstring37, m37);
				}
				if (matchstring37 != null && !matchstring37.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring37.toString()+"@"+Integer.toString(matchstring37.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring37)+ ","+ Collections.min(matchstring37);
					excelData[i][startIndexCol + 3] = matchstring37.get(matchstring37.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 46);
			check2 = " ";
			// 47 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (DIASTOLIC FUNCTION) //
			if (check1.matches("(.*)(diastolic\\s*(dysfunction|function))(.*)")) {
				Matcher m38 = p38.matcher(check1);
				while (m38.find()) {
					check2 = check2 + "^^ " + m38.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m38.group();
				}
				Matcher m39 = p39.matcher(check2);
				List<String> matchstring39 = new ArrayList<String>();
				while (m39.find()) {
					matchstring39.add(m39.group());
				}
				if (matchstring39 != null && !matchstring39.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring39.toString()+"@"+Integer.toString(matchstring39.size());
					excelData[i][startIndexCol + 3] = matchstring39.get(matchstring39.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 47);
			check2 = " ";
			// 48 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (MV E/A RATIO) //
			if (check1.matches("(.*)((e\\s*(\\/|\\:)\\s*a\\W)|(e\\s*to\\s*a\\s*(ratio)*))(.*)")) {
				Matcher m40 = p40.matcher(check1);
				while (m40.find()) {
					check2 = check2 + "^^ " + m40.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m40.group();
				}
				Matcher m41 = p41.matcher(check2);
				List<String> matchstring41 = new ArrayList<String>();
				while (m41.find()) {
					matchstring41.add(m41.group());
				}
				if (matchstring41 != null && !matchstring41.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring41.toString()+"@"+Integer.toString(matchstring41.size());
			excelData[i][startIndexCol + 2] = Collections.max(matchstring41)+ ","+ Collections.min(matchstring41);
			excelData[i][startIndexCol + 3] = matchstring41.get(matchstring41.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 48);
			check2 = " ";
			// 49 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (LV FILLING PRESSURE) //
			if (check1.matches("(.*)filling\\s*pressure(.*)")) {
				Matcher m42 = p42.matcher(check1);
				while (m42.find()) {
					check2 = check2 + "^^ " + m42.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m42.group();
				}
				Matcher m43 = p43.matcher(check2);
				List<String> matchstring43 = new ArrayList<String>();
				while (m43.find()) {
					matchstring43.add(m43.group());
				}
				if (matchstring43 != null && !matchstring43.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring43.toString()+"@"+Integer.toString(matchstring43.size());
					excelData[i][startIndexCol + 3] = matchstring43.get(matchstring43.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 49);
			check2 = " ";
			// 50 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (E/e' Ratio) //
			if (check1.matches("(.*)e\\s*(\\/|\\:)\\s*e\\s*\\'(.*)")) {
				Matcher m44 = p44.matcher(check1);
				while (m44.find()) {
					check2 = check2 + "^^ " + m44.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m44.group();
				}
				Matcher m45 = p45.matcher(check2);
				List<String> matchstring45 = new ArrayList<String>();
				while (m45.find()) {
					matchstring45.add(m45.group());
				}
				if (matchstring45 != null && !matchstring45.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring45.toString()+"@"+Integer.toString(matchstring45.size());
			excelData[i][startIndexCol + 2] = Collections.max(matchstring45)+ ","+ Collections.min(matchstring45);
			excelData[i][startIndexCol + 3] = matchstring45.get(matchstring45.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 50);
			check2 = " ";
			// 51 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (Chamber Size LVEDd) //
			if (check1.matches("(.*)lvedd(.*)")) {
				Matcher m46 = p46.matcher(check1);
				while (m46.find()) {
					check2 = check2 + "^^ " + m46.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m46.group();
				}
				Matcher m47 = p47.matcher(check2);
				List<String> matchstring47 = new ArrayList<String>();
				while (m47.find()) {
					if (m47.group().contains("mm") && !m47.group().contains("-") && !m47.group().contains("[")) {
						String[] items = m47.group().split("mm");
						Float f = new Float(items[0]);
						String ca = Float.toString(f / 10) + " cm";
						matchstring47.add(ca);
					} else {
						matchstring47.add(m47.group());
					}
				}
				if (matchstring47 != null && !matchstring47.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring47.toString()+"@"+Integer.toString(matchstring47.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring47)+ ","+ Collections.min(matchstring47);
					excelData[i][startIndexCol + 3] = matchstring47.get(matchstring47.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 51);
			check2 = " ";
			// 52 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (Chamber Size LVESD) //
			if (check1.matches("(.*)((lvesd)|(lvsd))(.*)")) {
				Matcher m48 = p48.matcher(check1);
				while (m48.find()) {
					check2 = check2 + "^^ " + m48.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m48.group();
				}
				Matcher m49 = p49.matcher(check2);
				List<String> matchstring49 = new ArrayList<String>();
				while (m49.find()) {
					matchstring49.add(m49.group());
				}
				if (matchstring49 != null && !matchstring49.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring49.toString()+"@"+Integer.toString(matchstring49.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring49)+ ","+ Collections.min(matchstring49);
					excelData[i][startIndexCol + 3] = matchstring49.get(matchstring49.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 52);
			check2 = " ";
			// 53 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (Chamber Thickness: Type:concentric)
			// //
			if (check1.matches("(.*)concentric.{1,15}((lv)|(left\\s*(ventricular|ventricle)))\\s*hypertrophy(.*)")) {
				Matcher m50 = p50.matcher(check1);
				while (m50.find()) {
					check2 = check2 + "^^ " + m50.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m50.group();
				}
				Matcher m51 = p51.matcher(check2);
				List<String> matchstring51 = new ArrayList<String>();
				while (m51.find()) {
					matchstring51.add(m51.group());
				}
				if (matchstring51 != null && !matchstring51.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring51.toString()+"@"+Integer.toString(matchstring51.size());
					excelData[i][startIndexCol + 3] = matchstring51.get(matchstring51.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 53);
			check2 = " ";
			// 54 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (Chamber Thickness: Type:basal
			// septal) //
			if (check1.matches("(.*)basal\\s*septal(.*)")) {
				Matcher m50a = p50a.matcher(check1);
				while (m50a.find()) {
					check2 = check2 + "^^ " + m50a.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m50a.group();
				}
				Matcher m51a = p51a.matcher(check2);
				List<String> matchstring51a = new ArrayList<String>();
				while (m51a.find()) {
					matchstring51a.add(m51a.group());
				}
				if (matchstring51a != null && !matchstring51a.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring51a.toString()+"@"+Integer.toString(matchstring51a.size());
					excelData[i][startIndexCol + 3] = matchstring51a.get(matchstring51a.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 54);
			check2 = " ";
			// 55 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (Degree Of lv Hypertrophy) //
			if (check1.matches("(.*)(((lv)|(left\\s*(ventricular|ventricle)))\\s*hypertrophy)(.*)")) {
				Matcher m52 = p52.matcher(check1);
				while (m52.find()) {
					check2 = check2 + "^^ " + m52.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m52.group();
				}
				Matcher m53 = p53.matcher(check2);
				List<String> matchstring53 = new ArrayList<String>();
				while (m53.find()) {
					matchstring53.add(m53.group());
				}
				if (matchstring53 != null && !matchstring53.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring53.toString()+"@"+Integer.toString(matchstring53.size());
					excelData[i][startIndexCol + 3] = matchstring53.get(matchstring53.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 55);
			check2 = " ";
			// 56 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (Degree Of basal septal Hypertrophy) //
			if (check1.matches("(.*)(septal\\s*hypertrophy)(.*)")) {
				Matcher m52a = p52a.matcher(check1);
				while (m52a.find()) {
					check2 = check2 + "^^ " + m52a.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m52a.group();
				}
				Matcher m53a = p53a.matcher(check2);
				List<String> matchstring53a = new ArrayList<String>();
				while (m53a.find()) {
					matchstring53a.add(m53a.group());
				}
				if (matchstring53a != null && !matchstring53a.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring53a.toString()+"@"+Integer.toString(matchstring53a.size());
					excelData[i][startIndexCol + 3] = matchstring53a.get(matchstring53a.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 56);
			check2 = " ";
			// 57 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (Degree of Septal thickness) //
			if (check1.matches("(.*)septal\\s*thickness(.*)")) {
				Matcher m54 = p54.matcher(check1);
				while (m54.find()) {
					check2 = check2 + "^^ " + m54.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m54.group();
				}
				Matcher m55 = p55.matcher(check2);
				List<String> matchstring55 = new ArrayList<String>();
				while (m55.find()) {
					if (m55.group().contains("mm") && !m55.group().contains("-") && !m55.group().contains("[")) {
						String[] items = m55.group().split("mm");
						Float f = new Float(items[0]);
						String ca = Float.toString(f / 10) + " cm";
						matchstring55.add(ca);
					} else {
						matchstring55.add(m55.group() + "cm");
					}
				}
				if (matchstring55 != null && !matchstring55.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring55.toString()+"@"+Integer.toString(matchstring55.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring55)+ ","+ Collections.min(matchstring55);
					excelData[i][startIndexCol + 3] = matchstring55.get(matchstring55.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 57);
			check2 = " ";
			// +++++++++++++++++++++++++++++++++++++++++++(LEFT
			// ATRIUM)++++++++++++++++++++++++++++++++++++++//
			// 58 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (Atrial Englargement) //
			if (check1.matches("(.*)atrial\\s*enlargement(.*)")) {
				Matcher m56 = p56.matcher(check1);
				while (m56.find()) {
					check2 = check2 + "^^ " + m56.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m56.group();
				}
				Matcher m57 = p57.matcher(check2);
				List<String> matchstring57 = new ArrayList<String>();
				while (m57.find()) {
					matchstring57.add(m57.group());
				}
				if (matchstring57 != null && !matchstring57.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring57.toString()+"@"+Integer.toString(matchstring57.size());
					excelData[i][startIndexCol + 3] = matchstring57.get(matchstring57.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 58);
			check2 = " ";
			// 59 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (LA Dimension) //
			if (check1.matches("(.*)la\\s*dimension(.*)")) {
				Matcher m58 = p58.matcher(check1);
				while (m58.find()) {
					check2 = check2 + "^^ " + m58.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m58.group();
				}
				Matcher m59 = p59.matcher(check2);
				List<String> matchstring59 = new ArrayList<String>();
				while (m59.find()) {
					matchstring59.add(m59.group() + " cm");
				}
				if (matchstring59 != null && !matchstring59.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring59.toString()+"@"+Integer.toString(matchstring59.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring59)+ ","+ Collections.min(matchstring59);
					excelData[i][startIndexCol + 3] = matchstring59.get(matchstring59.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 59);
			check2 = " ";
			// 60 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (Volumen Index) //
			if (check1.matches("(.*)volume\\s*index(.*)")) {
				Matcher m60 = p60.matcher(check1);
				while (m60.find()) {
					check2 = check2 + "^^" + m60.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m60.group();
				}
//				String result = check2.replaceAll("[a-z]", "");
				Matcher m61 = p61.matcher(check2);
				List<String> matchstring61 = new ArrayList<String>();
				while (m61.find()) {
					matchstring61.add(m61.group());
				}
				if (matchstring61 != null && !matchstring61.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring61.toString()+"@"+Integer.toString(matchstring61.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring61)+ ","+ Collections.min(matchstring61);
					excelData[i][startIndexCol + 3] = matchstring61.get(matchstring61.size() - 1);
				}
			}
			startIndexCol = IndexCol + (4 * 60);
			check2 = " ";
			// 61 ///////////////// Next Concept Start Here ///////////////
			// ////////////////Find concept (Body Surface Area) //
			if (check1.matches("(.*)bsa(.*)")) {
				System.out.println(i);
				Matcher m62 = p62.matcher(check1);
				while (m62.find()) {
					check2 = check2 + "  " + m62.group();
					excelData[i][startIndexCol] = excelData[i][startIndexCol]+ "\n" + "<OUTPUT>" + m62.group();
					// System.out.println(check2);
				}
				String result = check2.replaceAll("[a-z]", "");
				Matcher m63 = p63.matcher(check2);
				List<String> matchstring63 = new ArrayList<String>();
				while (m63.find()) {
					matchstring63.add(m63.group());
					// System.out.println(matchstring63);
				}
				if (matchstring63 != null && !matchstring63.isEmpty()) {
					excelData[i][startIndexCol + 1] = matchstring63.toString()+"@"+Integer.toString(matchstring63.size());
					excelData[i][startIndexCol + 2] = Collections.max(matchstring63)+ ","+ Collections.min(matchstring63);
					excelData[i][startIndexCol + 3] = matchstring63.get(matchstring63.size() - 1);
				}

			}
			startIndexCol = 4;
			check2 = " ";

			// ++++++++++++++++++++++++++++++++++++++++++++(WRITING EXCEL
			// SHEET)+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
			HSSFWorkbook myWorkBook = new HSSFWorkbook();
			HSSFSheet mySheet = myWorkBook.createSheet();
			HSSFRow myRow = null;
			HSSFCell myCell = null;

			for (int rowNum = 0; rowNum < numOfRows; rowNum++) {
				myRow = mySheet.createRow(rowNum);

				for (int cellNum = 0; cellNum < numOfOutputColumns; cellNum++) {
					myCell = myRow.createCell(cellNum);
					myCell.setCellValue(excelData[rowNum][cellNum]);
				}
			}
			try {
				FileOutputStream out = new FileOutputStream(WritefileName);
				myWorkBook.write(out);
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static boolean calculate(List<String> matchstring, Matcher match) {
		if ((match.group().contains("-") || match.group().contains("to"))
				&& !match.group().contains("[") && !match.group().contains("_")) {
			String[] items = match.group().split("to|-");
			Float f1 = new Float(items[0]);
			Float f2 = new Float(items[1]);
			String ca = Float.toString((f1 + f2) / 2);
			return matchstring.add(ca);
		} else {
			return matchstring.add(match.group());
		}
	}

	private static boolean calculate_z(List<String> matchstring_z, Matcher match_z) {
		if ((match_z.group().contains("cm/s") && !match_z.group().contains("-"))
				&& !match_z.group().contains("[")) {
			String[] items_z = match_z.group().split("cm\\/s");
			Float f = new Float(items_z[0]);
			String ca = Float.toString(f / 100) + " m/s";
			return matchstring_z.add(ca);
		} else {
			return matchstring_z.add(match_z.group());
		}
	}

}

// +++++++++++++++++++++++++++++++++++++++++++++++(PROGRAM ENDS
// HERE)++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
