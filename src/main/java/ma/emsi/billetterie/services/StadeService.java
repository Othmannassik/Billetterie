package ma.emsi.billetterie.services;

import ma.emsi.billetterie.dao.IStade;
import ma.emsi.billetterie.dao.IStadeImpl;
import ma.emsi.billetterie.entities.Stade;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

public class StadeService {
    private IStade stadeDao = new IStadeImpl();
    public List<Stade> findAll() {
        return stadeDao.findAll();
    }
    public Stade find(int id){
        return stadeDao.find(id);
    }
    public void save(Stade stade) {
        stadeDao.insert(stade);
    }
    public void update(Stade stade) {
        stadeDao.update(stade);
    }
    public void remove(Stade stade) {
        stadeDao.delete(stade.getId());
    }

    public void importFromTxtFile(String file){
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            Stade stade = null;
            String readLine = br.readLine();

            while(readLine != null){

                String [] std  = readLine.split("\\|");

                stade = new Stade();
                stade.setName(std[0].trim());
                stade.setLieu(std[1].trim());
                stade.setMaxPlace(Integer.parseInt(std[2].trim()));

                save(stade);
                readLine = br.readLine();
            }
            System.out.println("Stade Data Imported Successfully From "+ file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
                    Stade stade = new Stade();
                    stade.setId((int) row.getCell(0).getNumericCellValue());
                    stade.setName(row.getCell(1).getStringCellValue());
                    stade.setLieu(row.getCell(2).getStringCellValue());
                    stade.setMaxPlace((int) row.getCell(3).getNumericCellValue());

                    if (this.find(stade.getId()) instanceof Stade)
                        this.update(stade);
                    else
                        this.save(stade);
                }
                i=1;
            }
            workbook.close();
            inputStream.close();
            System.out.println("Stades Data Imported Successfully From "+ file);
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
        header.createCell(0).setCellValue("Stade ID");
        header.createCell(1).setCellValue("Nom");
        header.createCell(2).setCellValue("Lieu");
        header.createCell(3).setCellValue("Max Places");

        int rowNumber=1;
        for (Stade stade : this.findAll()){
            XSSFRow row = spreadsheet.createRow(rowNumber++);
            row.createCell(0).setCellValue(stade.getId());
            row.createCell(1).setCellValue(stade.getName());
            row.createCell(2).setCellValue(stade.getLieu());
            row.createCell(3).setCellValue(stade.getMaxPlace());
        }

        try (FileOutputStream out = new FileOutputStream(new File(file))) {
            workbook.write(out);
            System.out.println("Stades Data Exported Successfully To "+ file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
