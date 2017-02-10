package com.segaudi;

/**
 * Created by xuchenyou on 12/22/16.
 */
import com.segaudi.util.Utils;
import com.sun.tools.doclets.internal.toolkit.util.DocFinder;
import org.apache.commons.collections4.functors.NonePredicate;
import org.apache.commons.collections4.iterators.ArrayListIterator;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.management.DynamicMBean;
import java.io.*;
import java.nio.file.*;
import java.time.LocalTime;
import java.util.*;


public class RapportDB {
    static String cacheJsonPath = "WhereisCache";
    static int[] dyadNumbers = {1,2,3,4,7,8,9,10,13,14,15,16};
    private List<convDyad> dataDyads = new ArrayList<>();


    RapportDB() {
    }
    //DB data related methods
    public convDyad getDyad(int dyadNo){
        return dataDyads.get(dyadNo);
    }
    public int dyadSize(){
        return dataDyads.size();
    }

    //initialize DB from cache
    public void initRapportDB(){
        File cacheFile = new File(cacheJsonPath);
        if (!cacheFile.exists())
            initCache();
        else
            readCache();
    }
    private void readCache(){
        //from json cache file
        //dataDyads = Utils.fromJsonString(cacheJsonPath, convDyad);
    }

    //initialize cache from RawData
    private void initCache(){
        System.out.println("Initializing Cache...");
        dataDyads.clear();
        for (int dyadNum = 0; dyadNum < dyadNumbers.length; dyadNum++){
            convDyad newDyad = new convDyad(dyadNumbers[dyadNum]);
            newDyad.initDyad();
            dataDyads.add(newDyad);
        }
        //ToDO Utils.toJson(dataDyads);
    }
    private void xls2xlsx(String xlsReadingPath, String xlsxWritingPath) throws InvalidFormatException, IOException{
        File xlsFile = new File(xlsReadingPath);
        File xlsxFile = new File(xlsxWritingPath);
        if (xlsxFile.exists())
            xlsxFile.delete();
        /*
        System.out.println("Converting xls File " + xlsFile.getName() + " to xlsx File " + xlsxFile.getName() + " ...");
        InputStream in = new FileInputStream(xlsFile);
        try {
            Workbook wbIn = new HSSFWorkbook(in);
            Workbook wbOut = new XSSFWorkbook();

            int numberOfSheets = wbIn.getNumberOfSheets();
            if (numberOfSheets != 2){
                System.out.println("Sheet Number Other than 2");
                return;
            }
            Sheet sIn1 = wbIn.getSheetAt(0);
            Sheet sIn0 = wbIn.getSheetAt(1);

            List<Sheet> inSheetList = new ArrayList<Sheet>();
            inSheetList.add(sIn0);
            inSheetList.add(sIn1);

            Iterator<Sheet> sheetIterator = inSheetList.iterator();
            while (sheetIterator.hasNext()){
                Sheet inSheet = sheetIterator.next();
                Sheet outSheet = wbOut.createSheet(inSheet.getSheetName());

                Iterator<Row> rowIterator = inSheet.rowIterator();
                while (rowIterator.hasNext()){
                    Row inRow = rowIterator.next();
                    Row outRow = outSheet.createRow(inRow.getRowNum());

                    Iterator<Cell> cellIterator = inRow.cellIterator();
                    while (cellIterator.hasNext()){
                        Cell inCell = cellIterator.next();
                        Cell outCell = outRow.createCell(inCell.getColumnIndex(), inCell.getCellTypeEnum());
                        switch (inCell.getCellType()) {
                            case Cell.CELL_TYPE_BLANK:
                                break;

                            case Cell.CELL_TYPE_BOOLEAN:
                                outCell.setCellValue(inCell.getBooleanCellValue());
                                break;

                            case Cell.CELL_TYPE_ERROR:
                                outCell.setCellValue(inCell.getErrorCellValue());
                                break;

                            case Cell.CELL_TYPE_FORMULA:
                                outCell.setCellFormula(inCell.getCellFormula());
                                break;

                            case Cell.CELL_TYPE_NUMERIC:
                                outCell.setCellValue(inCell.getNumericCellValue());
                                System.out.println(inCell.getNumericCellValue());
                                break;

                            case Cell.CELL_TYPE_STRING:
                                outCell.setCellValue(inCell.getStringCellValue());
                                break;
                        }
                        {
                            CellStyle styleIn = inCell.getCellStyle();
                            CellStyle styleOut = outCell.getCellStyle();
                            styleOut.setDataFormat(styleIn.getDataFormat());
                        }
                        outCell.setCellComment(inCell.getCellComment());

                    }
                }
            }
            OutputStream out = new BufferedOutputStream(new FileOutputStream(xlsxFile));

            try{
                wbOut.write(out);
            } finally {
                out.close();
            }

        } finally {
            in.close();
        }*/
    }

    void sheet2Sheet(Sheet sheetIn, Sheet sheetOut){
        Iterator<Row> rowIterator = sheetIn.rowIterator();
        Row rowIn;
        Row rowOut;
        Cell cellIn;
        Cell cellOut;

        while (rowIterator.hasNext()){
            rowIn = rowIterator.next();
            rowOut = sheetOut.createRow(rowIn.getRowNum());

            Iterator<Cell> cellIterator = rowIn.cellIterator();
            while (cellIterator.hasNext()){
                cellIn = cellIterator.next();
                cellOut = rowOut.createCell(cellIn.getColumnIndex());
                System.out.println(1);
                System.out.println(cellIn.getCellTypeEnum());
            }
        }
    }
    void writeXlsx(String pairname) throws Exception {
        FileOutputStream fileOut = new FileOutputStream("workbook.xlsx");
        Workbook wb = new XSSFWorkbook();
        Sheet s = wb.createSheet();
        wb.setSheetName(0, "\u0422\u0435\u0441\u0442\u043E\u0432\u0430\u044F " +
                "\u0421\u0442\u0440\u0430\u043D\u0438\u0447\u043A\u0430" );
        Row r = null;
        Cell c = null;
        wb.write(fileOut);
        fileOut.close();
    }
    void readXlsx(String filename) throws Exception {
        InputStream inp = new FileInputStream("workbook.xlsx");
        Workbook wb = WorkbookFactory.create(inp);
        Sheet sheet = wb.getSheetAt(0);
        System.out.println(sheet.getSheetName());
        Row row = sheet.getRow(2);
        if (row == null)
            row = sheet.createRow(2);
        Cell cell = row.getCell(3);

        if (cell == null)
            cell = row.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("a test");
        FileOutputStream fileOut = new FileOutputStream("workbook.xlsx");
        wb.write(fileOut);
        fileOut.close();
    }

    Workbook readExcelFile(File myfile) throws Exception{
        InputStream inp = new FileInputStream(myfile);
        Workbook wb = WorkbookFactory.create(inp);
        return wb;
    }

    String[] returnFileName(String strategyType){
        String[] returnedFileHeadnEnd = new String[2];

        // Should have been included in the configure file
        String generalName = "Dyad";
        int[] fileNumbers = {1,2,3,4,7,8,9,10,13,14,15,16};
        String data_path = Config.data_path;

        returnedFileHeadnEnd[1] = ".xlsx";

        switch (strategyType){
            case "BC":
                returnedFileHeadnEnd[0] = data_path + "1Backchannel" + "/Dyad%02d";
                break;
            case "SD":
                returnedFileHeadnEnd[0] = data_path + "2Self disclosure" + "/Dyad%02d";
                //1. Deeper path; 2. More than one file for each (right ones are called DXSX_final codes);
                //3. D7S4 D8S3 got nothing
                break;
            case "SE":
                returnedFileHeadnEnd[0] = data_path + "3Shared experience" + "/Dyad%02d";
                break;
            case "VSN":
                returnedFileHeadnEnd[0] = data_path + "4Social Norm Violations" + "/Dyad%02d";
                break;
            case "PR":
                returnedFileHeadnEnd[0] = data_path + "5Praise" + "/Dyad%02d"; //27 in 60 ends with xls
                break;
            case "RE":
                returnedFileHeadnEnd[0] = data_path + "6Reciprocity" + "/Dyad%02d";
                break;
            default:
                break;
        }

        return returnedFileHeadnEnd;
    }

    // from xls or xlsx file to csv
    void fileModifier(){
        String[] fileHeadnEnd = new String[2];
        List<String> fileList;
        // Should have been included in configuration
        String[] strategyTypes = {"VSN", "SE", "BC", "PR", "SD", "RE"};
        int[] dyadNumbers = {1,2,3,4,7,8,9,10,13,14,15,16};

        for (int numStrategy = 0; numStrategy<=strategyTypes.length-1; numStrategy++) {
            fileList = new ArrayList<String>();
            fileHeadnEnd = returnFileName(strategyTypes[numStrategy]);

            /*
            for (int i = 0; i <= fileHeadnEnd.length - 1; i++) {
                System.out.println(fileHeadnEnd[i]);
            }
            */

            for (int i = 0; i <= dyadNumbers.length - 1; i++) {
                File f = new File(String.format(fileHeadnEnd[0], dyadNumbers[i]));
                File[] f_list = f.listFiles();
                for (int numOfFiles = 0; numOfFiles <= f_list.length - 1; numOfFiles++) {
                    String fileBeingReviewed = f_list[numOfFiles].getName();
                    for (int j = 1; j <= 5; j++) {
                        if ((fileBeingReviewed.endsWith(fileHeadnEnd[1]) || fileBeingReviewed.endsWith(".xlsx")) && fileBeingReviewed.startsWith(String.format("D%dS%d", dyadNumbers[i], j))) {
                            fileList.add(fileBeingReviewed);
                            //System.out.println(fileBeingReviewed);
                        }
                    }
                }

            }

            System.out.println(strategyTypes[numStrategy] + fileList.size());
        }
    }



    // This part is deserted for the moment
    void convertVSN(String filename) throws Exception{
        String[] fileFolders = new String[12];
        String generalName = "Dyad";
        int[] fileNumbers = {1,2,3,4,7,8,9,10,13,14,15,16};
        String fileHead;
        String fileEnd;
        for (int i = 0; i<fileNumbers.length; i++){
            fileFolders[i] = Config.data_path + "/" + "4Social Norm Violations" + "/" + generalName+ String.format("%02d",fileNumbers[i]);
            File f = new File(fileFolders[i]);
            File[] f_list = f.listFiles();
            String S1SVN = new String();
            String S2SVN = new String();

            for (int k = 0; k < f_list.length; k++){
                for (int j = 1; j<=5; j++){
                    fileHead = new String(String.format("D%dS%d",fileNumbers[i],j));
                    fileEnd = new String("xlsx");
                    if (f_list[k].getName().startsWith(fileHead) && f_list[k].getName().endsWith(fileEnd)){

                        Workbook wb = readExcelFile(f_list[k]);
                        Sheet sheet = wb.getSheetAt(0);
                        /*
                        Row row;
                        Cell cell;

                        Iterator<Row> rowIterator = sheet.iterator();
                        rowIterator.next();
                        while(rowIterator.hasNext()){
                            row = rowIterator.next();
                            Iterator<Cell> cellIterator = row.iterator();
                            while(cellIterator.hasNext()){
                                cell = cellIterator.next();

                            }
                        }
                        */
                        System.out.println(sheet.getSheetName());
                        System.out.println(sheet.getLastRowNum());
                        for (int numRow = 1; numRow<sheet.getLastRowNum(); numRow++){
                            Row row = sheet.getRow(numRow);
                            System.out.println(row.getCell(0).getNumericCellValue());
                            System.out.println(row.getCell(1).getNumericCellValue());
                            S1SVN = " ";
                            S2SVN = " ";
                            try{
                                S1SVN = row.getCell(5).getStringCellValue();
                                S2SVN = row.getCell(6).getStringCellValue();
                            } catch (NullPointerException e){
                            }
                            System.out.println(f_list[k].getName()+" "+numRow);
                            System.out.println(S1SVN==null?" ":S1SVN);
                            System.out.println(S2SVN==null?" ":S2SVN);
                        }
                    }
                }
            }

        }
    }


}


class convDyad{
    private int dyadNum;
    private List<convSession> sessionList = new ArrayList<>();

    public convDyad(int dyadNum){
        this.dyadNum = dyadNum;
    }
    public void initDyad(){
        sessionList.clear();
        for (int sessionNum = 1; sessionNum<=5; sessionNum++){
            convSession newSession = new convSession(sessionNum, this.dyadNum);
            newSession.initSession();
            sessionList.add(newSession);
        }
    }
    public convSession getSession(int i){
        return sessionList.get(i);
    }
    public int dyadNum(){
        return dyadNum;
    }
    public int size(){
        return sessionList.size();
    }
}

class convSession{
    private int sessionNum;
    private int dyadNum;
    private Map<String, Queue<convEntry>> verbalMap = new HashMap<>();
    private Map<String, Queue<convEntry>> nonverbalMap = new HashMap<>();
    private List<convEntry> mergedList = new ArrayList<>();

    convSession(int sessionNum, int dyadNum){
        this.sessionNum = sessionNum;
        this.dyadNum = dyadNum;
    }

    public void initSession(){
        verbalMap.clear();
        Iterator<String> verbalIterator = new ArrayList<>(Config.verbalNames).iterator();
        while(verbalIterator.hasNext()){
            String verbalName = verbalIterator.next();
            this.verbalMap.put(verbalName, initializeCS(this.sessionNum, this.dyadNum, verbalName));
        }
        nonverbalMap.clear();
        Iterator<String> nonverbalIterator = new ArrayList<>(Config.nonverbalNames).iterator();
        while(nonverbalIterator.hasNext()){
            String nonverbalName = nonverbalIterator.next();
            this.nonverbalMap.put(nonverbalName, initializeCS(this.dyadNum, this.sessionNum, nonverbalName));
        }
        merge1();
    }

    private Queue<convEntry> initializeCS(int numSession, int numDyad, String strategyName){
        //System.out.println("Initializing " + strategyName + " for Dyad " + numDyad + " Session " + numSession + "...");
        Queue<convEntry> entryQueue = new ArrayDeque<>();
        FileResolver fileResolver = new FileResolver(strategyName, numSession, numDyad);
        File enclosedFile = new File(fileResolver.getEnclosedPath());
        String[] fname_list = enclosedFile.list(fileResolver.getMyFileFilter());
        switch (fname_list.length){
            case 0:
                //System.out.println(0);
                return entryQueue;
            case 1:
                fileResolver.setFilePath(fname_list[0]);
                //System.out.println(fileResolver.getFilePath());
                return fileResolver.parseFile();
            default:
                System.out.println("More than one file found");
                System.exit(0);
                return entryQueue;
        }
    }

    private void merge1(){
        double currentTime = 0;
        List<String> keyList = new ArrayList<>();
        keyList.addAll(verbalMap.keySet());

        for (int i = 0; i<keyList.size(); i++){
            String thisKey = keyList.get(i);
            Queue<convEntry> entryQueue = verbalMap.get(thisKey);
            if (entryQueue.size() == 0)
                verbalMap.remove(thisKey);
        }
        mergedList.clear();

        while (!verbalMap.isEmpty()){
            keyList.clear();
            keyList.addAll(verbalMap.keySet());
            String minKey = "";
            double minValue = 10000;
            for (int i = 0; i<keyList.size(); i++){
                String thisKey = keyList.get(i);
                double thisValue = verbalMap.get(thisKey).peek().getBeginTime();
                if (thisValue <= minValue) {
                    minValue = thisValue;
                    minKey = thisKey;
                }
            }
            //System.out.println(minKey + minValue);
            mergedList.add(verbalMap.get(minKey).poll());
            if (verbalMap.get(minKey).size() == 0)
                verbalMap.remove(minKey);
        }
    }


    public List<String> verbalStrategy(){
        return (List<String>) this.verbalMap.keySet();
    }
    public List<String> nonVerbalStrategy(){
        return (List<String>) this.nonverbalMap.keySet();
    }
    public int size(){
        return verbalMap.size() + nonverbalMap.size();
    }
    public List<Integer> sizes(){
        List<Integer> sizeList = new ArrayList<>();
        Iterator<Queue<convEntry>> queueIterator = verbalMap.values().iterator();
        while (queueIterator.hasNext()){
            sizeList.add(queueIterator.next().size());
        }

        return sizeList;
    }
    public List<convEntry> getMergedList(){
        return mergedList;
    }

}

class convEntry{
    private xlsTime beginTime;
    private xlsTime endTime;
    private int person;
    private String extraInfo = null;

    convEntry(double rawBeginTime, double rawEndTime, int person){
       this.beginTime = new xlsTime(rawBeginTime);
       this.endTime = new xlsTime(rawEndTime);
       this.person = person;
    }
    convEntry(double rawBeginTime, double rawEndTime, int person, String extraInfo){
        this.beginTime = new xlsTime(rawBeginTime);
        this.endTime = new xlsTime(rawEndTime);
        this.person = person;
        this.extraInfo = extraInfo;
    }

    public String getStrategyName(){
        return extraInfo;
    }
    public int getPerson(){
        return person;
    }
    public boolean samePerson(convEntry AnotherEntry){
        return (person==AnotherEntry.getPerson());
    }
    public double getBeginTime(){
        return this.beginTime.currentTime;
    }
    public double getEndTime(){
        return this.endTime.currentTime;
    }
    static class xlsTime{
        private double currentTime;

        xlsTime(double rawTime){
            this.currentTime = rawTime*60.0*24.0;
        }

        public double getCurrentTime(){
            return this.currentTime;
        }
        public double earlierTime(xlsTime Anothertime){
            return (Anothertime.getCurrentTime() - this.currentTime);
        }
    }
}


class FileResolver{
    private int numSession;
    private int numDyad;
    private String strategyName;
    private String enclosedPath;
    private String sheetName = "Codes";
    private xlsFilter myFileFilter;

    private String filePath;

    FileResolver(String strategyName, int numSession, int numDyad){
        this.strategyName = strategyName;
        this.numSession = numSession;
        this.numDyad = numDyad;
        String dyadSpecific = String.format("Dyad%02d", numDyad);
        String sessionSpecific = String.format("D%dS%d", numDyad, numSession);
        switch (strategyName){
            case "BC":
                this.enclosedPath = Config.data_path + "1Backchannel" + "/" + dyadSpecific;
                break;
            case "SD":
                this.enclosedPath = Config.data_path + "2Self disclosure" + "/" + dyadSpecific + "/" + sessionSpecific;
                //1. Deeper path; 2. More than one file for each (right ones are called DXSX_final codes);
                //3. D7S4 D8S3 got nothing
                break;
            case "SE":
                this.enclosedPath = Config.data_path + "3Shared experience" + "/" + dyadSpecific;
                break;
            case "VSN":
                this.enclosedPath = Config.data_path + "4Social Norm Violations" + "/" + dyadSpecific;
                break;
            case "PR":
                this.enclosedPath = Config.data_path + "5Praise" + "/" + dyadSpecific; //27 in 60 ends with xls
                this.sheetName = "Sheet1";
                break;
            case "RE":
                this.enclosedPath = Config.data_path + "6Reciprocity" + "/" + dyadSpecific;
                break;
            default:
                break;
        }
        String fileHead;
        switch (strategyName){
            case "SD":
                fileHead = sessionSpecific + "_final";
                break;
            default:
                fileHead = sessionSpecific;
        }
        this.myFileFilter = new xlsFilter(fileHead);
    }
    public String getEnclosedPath() {
        return enclosedPath;
    }
    public FilenameFilter getMyFileFilter(){
        return myFileFilter;
    }
    public String getFilePath(){
        if (filePath != null) {
            return filePath;
        }
        return new String();
    }
    public void setFilePath(String fileName){
        this.filePath = this.enclosedPath + "/" + fileName;
    }
    public boolean isXlsx(){
        return this.filePath.endsWith(".xlsx");
    }

    public Queue<convEntry> parseFile(){
        Queue<convEntry> newQueue = new ArrayDeque<>();
        newQueue.clear();
        try{
            if (0 == 0){
                InputStream inp = new FileInputStream(this.getFilePath());
                Workbook wb = WorkbookFactory.create(inp);
                if (wb.getNumberOfSheets() == 2) {
                    Sheet sheet = wb.getSheet(this.sheetName);
                    //if (sheet == null){
                    //    System.out.println(this.getFilePath());
                    //}
                    //if (sheet.getLastRowNum() == 1 && this.sheetName == "Sheet1"){
                    //    System.out.println(this.getFilePath());
                    //}
                    Iterator<Row> rowIterator = sheet.iterator();
                    rowIterator.next();
                    while (rowIterator.hasNext()){
                        int person = 1;
                        double rawBeginTime;
                        double rawEndTime;

                        Row row = rowIterator.next();
                        Cell firstCell = row.getCell(0);
                        if (firstCell == null)
                            break;
                        if (firstCell.getCellType() == 3)
                            break;
                        if (firstCell.getCellType() == 1)
                            continue;
                        //System.out.println(row.getCell(0).getNumericCellValue()*60*24 + " " + row.getCell(1).getNumericCellValue()*60*24);
                        Cell personOne = row.getCell(3);
                        Cell personTwo = row.getCell(4);
                        boolean boolA = personOne == null;
                        boolean boolB = personTwo == null;
                        if (boolA == boolB) {
                            if (boolA == true)
                                continue;
                            if (boolA == false) {
                                if (personOne.getCellType() + personTwo.getCellType() != 4) {
                                    continue;
                                }
                                if (personOne.getCellType() == 1){
                                    person = 0;
                                }
                            }
                        } else{
                            if (boolA == false)
                                person = 0;
                        }
                        rawBeginTime = row.getCell(0).getNumericCellValue();
                        rawEndTime = row.getCell(1).getNumericCellValue();
                        convEntry newEntry = new convEntry(rawBeginTime,rawEndTime,person,this.strategyName);
                        newQueue.add(newEntry);
                    }
                }
                wb.close();
                inp.close();
            }
            else{ //in case xls does not support xlsx

            }
            return newQueue;
        } catch (Exception e){
            e.printStackTrace();
            return newQueue;
        }
    }
    static class xlsFilter implements FilenameFilter{
        private String fileHead;
        xlsFilter(String fileHead){
            this.fileHead = fileHead;
        }
        public boolean accept(File dir, String name){
            return ((name.endsWith(".xlsx")||name.endsWith(".xls")) && (name.startsWith(fileHead)));
        }
    }
}

