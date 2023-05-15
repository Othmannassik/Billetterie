package ma.emsi.billetterie.services;

import ma.emsi.billetterie.dao.IBillet;
import ma.emsi.billetterie.dao.IBilletImpl;
import ma.emsi.billetterie.dao.IMatch;
import ma.emsi.billetterie.dao.IMatchImpl;
import ma.emsi.billetterie.entities.Billet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

public class BilletService {
    private IBillet billetDao = new IBilletImpl();
    private IMatch matchDao = new IMatchImpl();
    public List<Billet> findAll() {
        return billetDao.findAll();
    }
    public Billet find(int id){
        return billetDao.find(id);
    }
    public void save(Billet billet) {
        billetDao.insert(billet);
    }
    public void update(Billet billet) {
        billetDao.update(billet);
    }
    public void remove(Billet billet) {
        billetDao.delete(billet.getId());
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
                    Billet billet = new Billet();
                    billet.setId((int) row.getCell(0).getNumericCellValue());
                    billet.setPrix(row.getCell(1).getNumericCellValue());
                    billet.setMatch(matchDao.find((int) row.getCell(2).getNumericCellValue()));

                    if (this.find(billet.getId()) instanceof Billet)
                        this.update(billet);
                    else
                        this.save(billet);
                }
                i=1;
            }
            workbook.close();
            inputStream.close();
            System.out.println("Billets Data Imported Successfully From "+ file);
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
        header.createCell(0).setCellValue("Billet ID");
        header.createCell(1).setCellValue("Prix");
        header.createCell(2).setCellValue("Match ID");

        int rowNumber=1;
        for (Billet billet : this.findAll()){
            XSSFRow row = spreadsheet.createRow(rowNumber++);
            row.createCell(0).setCellValue(billet.getId());
            row.createCell(1).setCellValue(billet.getPrix());
            row.createCell(2).setCellValue(billet.getMatch().getId());
        }

        try (FileOutputStream out = new FileOutputStream(new File(file))) {
            workbook.write(out);
            System.out.println("Billets Data Exported Successfully To "+ file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
