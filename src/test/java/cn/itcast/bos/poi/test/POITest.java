package cn.itcast.bos.poi.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

/**
 * 解析 Excel 和 生成 Excel
 * 
 * @author Mr_Jc
 * 
 */
public class POITest {
	@Test
	// 解析info.xls 文件
	public void demo1() throws Exception {
		// 1、 获得 HSSFWorkbook (针对 xls格式文件 )
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream("info.xls"));

		// 2、获得要解析sheet
		HSSFSheet sheet = hssfWorkbook.getSheet("Sheet1"); // 通过名称获得Sheet
		HSSFSheet sheet2 = hssfWorkbook.getSheetAt(0);// 获得第一个Sheet，通过下标获得

		// 3、解析Sheet中每一行
		for (Row row : sheet) {
			// 遍历 sheet中每一行
			// 4、 打印行 中单元格数据
			for (Cell cell : row) {
				// 判断单元格数据类型
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					System.out.println(cell.getStringCellValue());
				} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					System.out.println(cell.getNumericCellValue());
				}
			}

			// 打印指定单元格的值
			System.out.println(row.getCell(1).getStringCellValue());// 打印每行第二个单元格的内容
		}

	}

	@Test
	// 向c盘根目录，生成一个Excel文件
	public void demo2() throws FileNotFoundException, IOException {
		// 1、 创建 一个空的工作薄
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();

		// 2、 在工作薄建立 Sheet
		HSSFSheet sheet = hssfWorkbook.createSheet("数据信息");

		// 3、 向sheet写入行数据
		HSSFRow row = sheet.createRow(0);// 创建第一行数据

		// 4、 向row中单元格进行数据输出
		row.createCell(0).setCellValue("产品");
		row.createCell(1).setCellValue("价格");

		// 5、 将Excel 数据输出到硬盘上
		hssfWorkbook.write(new FileOutputStream("c:/test.xls"));
	}

}
