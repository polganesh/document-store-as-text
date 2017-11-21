/**
 * 
 */
package com.gp.textstore.launcher;

import com.gp.common.io.document.parser.DocumentParseOperationTemplate;
import com.gp.common.io.document.parser.IDocumentParseOperation;
import com.gp.common.io.document.parser.ResourceLocationsEnum;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * @author gpol
 *
 */
public class DocumentToTextStoreLauncher {

	/**
	 * 
	 */
	public DocumentToTextStoreLauncher() {
		// TODO Auto-generated constructor stub
	}

	public static void parseDocumentsToTextAsCSV(String documentLocation,
			String csvLocation)throws IOException {
		File inputDirectory = new File(documentLocation);
		File[] childDirectoryArray=inputDirectory.listFiles();
		String content=getContentToWriteAsCSV(childDirectoryArray);
		FileUtils.writeStringToFile(new File(csvLocation), content);
	}
	
	private static String getContentToWriteAsCSV(File[] childDirectoryArray) {
		StringBuilder contentBuilder=new StringBuilder();
		String content="";
		IDocumentParseOperation parseOperation=null;
		for(File childDirectory:childDirectoryArray){
			if(childDirectory.isDirectory()){
				File[] filesInFolder=childDirectory.listFiles();
				for(File file:filesInFolder){
					if(!file.isHidden()){
						parseOperation=new DocumentParseOperationTemplate();
						content=parseOperation.parseDocument(file.getAbsolutePath(), ResourceLocationsEnum.LOCAL_FILE_SYSTEM);
						content=content.replaceAll(",", "");
						content=content.replaceAll("(\\r|\\n|\\r\\n)+", " ");
						contentBuilder.append(content);
						contentBuilder.append(",");
						contentBuilder.append(file.getName());
						contentBuilder.append(",");
						contentBuilder.append(childDirectory.getName());
						contentBuilder.append(System.getProperty("line.separator"));
					}else{
						System.out.println("hidden------ "+file.getName());
					}
				}
			}
		}
		return contentBuilder.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)throws IOException {
		String inputFolderLocation = "C:\\input-files";
		//String inputFolderLocation = "C:\\inputs";
		String outputFolderLocation = "C:\\data-set-output\\content.csv";
		DocumentToTextStoreLauncher.parseDocumentsToTextAsCSV(inputFolderLocation,
				outputFolderLocation);

	}

}
