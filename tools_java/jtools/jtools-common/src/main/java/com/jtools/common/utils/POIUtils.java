package com.jtools.common.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class POIUtils
  implements Serializable
{
  public static synchronized String createExcel(String rootPath, String filename, List datas, String[] columns, String[] fields)
  {
    SXSSFWorkbook wb = null;

    if (filename.endsWith(".xls"))
    {
      filename = filename + "x";
    }

    wb = new SXSSFWorkbook(-1);
    wb.setCompressTempFiles(true);

    Sheet sh = wb.createSheet();
    CellStyle cs = wb.createCellStyle();
    Font f = wb.createFont();
    f.setColor(IndexedColors.BLACK.getIndex());
    f.setBoldweight((short)700);
    cs.setFont(f);
    cs.setAlignment((short)2);
    Row row = null;
    Cell cell = null;
    Object rowObj = null;
    Object propertyObj = null;
    String[] fieldsArray = new String[0];
    for (int rownum = 0; rownum < datas.size(); rownum++) {
      if (rownum == 0) {
        row = sh.createRow(rownum);
        for (int cellnum = 0; cellnum < columns.length; cellnum++) {
          cell = row.createCell(cellnum);
          cell.setCellStyle(cs);
          cell.setCellValue(columns[cellnum]);
          sh.autoSizeColumn(cellnum);
        }
      }
      row = sh.createRow(rownum + 1);
      File fileTmp = null;
      String valueTmp = "";
      for (int cellnum = 0; cellnum < fields.length; cellnum++) {
        cell = row.createCell(cellnum);
        rowObj = datas.get(rownum);
        if (fields[cellnum].startsWith("file:"))
        {
          try {
            propertyObj = PropertyUtils.getProperty(rowObj, fields[cellnum].substring(5));
            List files = new ArrayList();
            if (propertyObj.getClass().equals(File.class)) {
              files.add((File)propertyObj);
            }
            if (propertyObj.getClass().equals(ArrayList.class)) {
              files.addAll((List)propertyObj);
            }
            for (int i = 0; i < files.size(); i++) {
              fileTmp = (File)files.get(i);
              if ((fileTmp == null) || (!fileTmp.exists()) || (!fileTmp.canRead())) {
                continue;
              }
              InputStream is = new FileInputStream(fileTmp);
              byte[] bytes = IOUtils.toByteArray(is);
              int pictureIdx = wb.addPicture(bytes, 5);
              is.close();

              ClientAnchor anchor = wb.getCreationHelper().createClientAnchor();

              anchor.setCol1(cellnum + i);
              anchor.setRow1(rownum + 1);
              Picture pict = sh.createDrawingPatriarch().createPicture(anchor, pictureIdx);

              pict.resize(0.1D);
            }
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
        } else {
          try {
            fieldsArray = fields[cellnum].split("\\.");
            if (fieldsArray.length > 1)
              valueTmp = BeanUtils.getNestedProperty(rowObj, fields[cellnum]);
            else
              valueTmp = BeanUtils.getProperty(rowObj, fields[cellnum]);
          }
          catch (IllegalAccessException e)
          {
            e.printStackTrace();
          } catch (InvocationTargetException e) {
            e.printStackTrace();
          } catch (NoSuchMethodException e) {
            e.printStackTrace();
          }
          cell.setCellValue(valueTmp);
        }

        if ((rownum == 0) || (rownum % 500 != 0))
          continue;
        try {
          ((SXSSFSheet)sh).flushRows(500);
        } catch (IOException e) {
          e.printStackTrace();
        }

      }

      try
      {
        ((SXSSFSheet)sh).flushRows();
      } catch (IOException e) {
        e.printStackTrace();
      }

      for (int i = 0; i < columns.length; i++) {
        sh.autoSizeColumn(i);
      }

      int curColWidth = 0;

      for (int i = 0; i < columns.length; i++) {
        curColWidth = sh.getColumnWidth(i);
        if (curColWidth < 4000) {
          sh.setColumnWidth(i, 4000);
        }
      }
    }

    if (!new File(rootPath).exists()) {
      new File(rootPath).mkdirs();
    }
    filename = filename.substring(0, filename.lastIndexOf(".")) + "-" + DateUtils2.getNowTime("yyyyMMddHHmmss") + filename.substring(filename.lastIndexOf("."));
    try {
      FileOutputStream out = new FileOutputStream(rootPath + File.separator + filename);
      wb.write(out);
      out.close();
      wb.dispose();
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    return filename;
  }

  public static void main(String[] args)
    throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
  {
    System.out.println("file:orderFile".substring(5));
  }
}