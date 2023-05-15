package ma.emsi.billetterie.services;

import ma.emsi.billetterie.dao.IMatch;
import ma.emsi.billetterie.dao.IMatchImpl;
import ma.emsi.billetterie.dao.IStade;
import ma.emsi.billetterie.dao.IStadeImpl;
import ma.emsi.billetterie.entities.Competition;
import ma.emsi.billetterie.entities.Match;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

public class MatchService {
    private IMatch matchDao = new IMatchImpl();
    private IStade stadeDao = new IStadeImpl();
    public List<Match> findAll() {
        return matchDao.findAll();
    }
    public Match find(int id){
        return matchDao.find(id);
    }
    public void save(Match match) {
        matchDao.insert(match);
    }
    public void update(Match match) {
        matchDao.update(match);
    }
    public void remove(Match match) {
        matchDao.delete(match.getId());
    }
    public void exportToTxtFile(String file){
        try( FileOutputStream outputFile = new FileOutputStream(file))
        {
            for(Match match : this.findAll()){
                outputFile.write(match.toString().getBytes());
                outputFile.write('\n');
            }
            System.out.println("Matches Data Exported To "+ file +" Successfully");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importFromExcelFile (String file) {

        try {
            FileInputStream inputStream = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            int i=0;
            for (Row row : sheet) {
                if (i != 0)
                {
                    Match match = new Match();
                    match.setId((int) row.getCell(0).getNumericCellValue());
                    match.setDate(row.getCell(1).getDateCellValue());
                    match.setNbPlace((int) row.getCell(2).getNumericCellValue());
                    match.setEquipe1(row.getCell(3).getStringCellValue());
                    match.setEquipe2(row.getCell(4).getStringCellValue());
                    match.setCompetition(Competition.valueOf(row.getCell(5).getStringCellValue()));
                    match.setStade(stadeDao.find((int) row.getCell(6).getNumericCellValue()));

                    if (this.find(match.getId()) instanceof Match)
                        this.update(match);
                    else
                        this.save(match);
                }
                i=1;
            }
            workbook.close();
            inputStream.close();
            System.out.println("Matches Data Imported Successfully From "+ file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportToExcelFile(String file) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("Sheet");

        XSSFRow header = spreadsheet.createRow(0);
        header.createCell(0).setCellValue("Match ID");
        header.createCell(1).setCellValue("Date");
        header.createCell(2).setCellValue("Nb Places");
        header.createCell(3).setCellValue("Equipe 1");
        header.createCell(4).setCellValue("Equipe 2");
        header.createCell(5).setCellValue("Competition");
        header.createCell(6).setCellValue("Stade ID");

        int rowNumber=1;
        for (Match match : this.findAll()){
            XSSFRow row = spreadsheet.createRow(rowNumber++);
            row.createCell(0).setCellValue(match.getId());
            row.createCell(1).setCellValue(match.getDate());
            row.createCell(2).setCellValue(match.getNbPlace());
            row.createCell(3).setCellValue(match.getEquipe1());
            row.createCell(4).setCellValue(match.getEquipe2());
            row.createCell(5).setCellValue(match.getCompetition().name());
            row.createCell(6).setCellValue(match.getStade().getId());
        }

        try (FileOutputStream out = new FileOutputStream(new File(file))) {
            workbook.write(out);
            System.out.println("Matches Data Exported Successfully To "+ file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
