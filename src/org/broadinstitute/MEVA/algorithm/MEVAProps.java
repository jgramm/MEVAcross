package org.broadinstitute.MEVA.algorithm;

import java.util.List;
import java.util.ArrayList;

public class MEVAProps {

	public enum RunType {ZSCORE, FOLDCHANGE, PVALUE}
	
	
	public static int iterations = 1000;
	public static RunType runType = RunType.ZSCORE;
	
	public static List<String> pwfs = new ArrayList<String>();
	
	public static String fileName;
	public static int HMDBCol = -1;
	public static int nameCol = -1;
	public static int startCol = -1;
	public static int stopCol = -1;
	
	
	public static String analysisDir;
	
	
}
