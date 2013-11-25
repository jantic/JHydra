package jhydra.core.excel;

import jhydra.core.lexicon.ILexicon;
import jhydra.core.logging.ILog;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Author: jantic
 * Date: 11/24/13
 */

//TODO:  Properly implement this
public class ExcelWorkbook {
    private final ILog log;
    //private final Object _m = Type.Missing;
    private final String fileName;
    private final String name;
    private final ILexicon lexicon;
    private final HashMap<String, ExcelWorksheet> worksheets = new HashMap<>();
    private Workbook rawWorkbook;

    public ExcelWorkbook(String fileName, ILog logger, ILexicon lexicon) throws IOException {
        this.log = logger;
        this.lexicon = lexicon;
        this.fileName = fileName;
        final Boolean xlsx = FilenameUtils.getExtension(fileName).equalsIgnoreCase("xlsx");
        this.name = FilenameUtils.removeExtension(fileName);
        if(!xlsx) {
            this.rawWorkbook = new HSSFWorkbook(new FileInputStream(fileName));
        }
        else {
            this.rawWorkbook = new XSSFWorkbook(new FileInputStream(fileName));
        }

        loadWorksheets();
        recalculateAll();
    }

    public void recalculateAll(){

    }

    private void loadWorksheets(){

    }
}
