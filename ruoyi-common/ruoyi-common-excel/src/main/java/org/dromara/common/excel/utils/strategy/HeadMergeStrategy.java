package org.dromara.common.excel.utils.strategy;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * 表头合并策略
 *
 * @author
 */
@Data
public class HeadMergeStrategy implements CellWriteHandler {

    private Integer headSize;

    public HeadMergeStrategy(Integer headSize) {
        this.headSize = headSize;
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        mergerHead(cell);
    }

    /**
     * 头部单元格合并 规则：先垂直合并，再水平合并
     *
     * @param cell
     */
    private void mergerHead(Cell cell) {
        //当前表格行数
        int currentRow = cell.getRowIndex();
        if (currentRow > headSize - 1) {
            return;
        }
        //当前表格列数
        int currentCol = cell.getColumnIndex();
        Sheet sheet = cell.getSheet();
        //第一行只左右合并
        //当前表格数据
        Object curData = cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : cell.getNumericCellValue();
        if (currentRow == 0 && currentCol != 0) {
            //获取当前单元格的左边一个单元格中的数据
            Cell leftCell = sheet.getRow(currentRow).getCell(currentCol - 1);
            Object leftData = leftCell.getCellType() == CellType.STRING ? leftCell.getStringCellValue() : leftCell.getNumericCellValue();
            if (curData.equals(leftData)) {
                CellRangeAddress cellRangeAddress = new CellRangeAddress(currentRow, currentRow, currentCol - 1, currentCol);
                sheet.addMergedRegionUnsafe(cellRangeAddress);
            }
        } else {
            //先上下合并
            //垂直上一表格数据
            int currentUpPreRow = currentRow - 1;
            if (currentUpPreRow < 0) {
                return;
            }
            Cell upCell = sheet.getRow(currentUpPreRow).getCell(currentCol);
            Object upData = upCell.getCellType() == CellType.STRING ? upCell.getStringCellValue() : upCell.getNumericCellValue();
            if (curData.equals(upData)) {//上下合并
                CellRangeAddress cellRangeAddress = new CellRangeAddress(currentUpPreRow, currentRow, currentCol, currentCol);
                sheet.addMergedRegionUnsafe(cellRangeAddress);
            }
            //左右合并时  需要判断上一级
            //获取当前单元格的左边一个单元格中的数据
            int currentLeftCol = currentCol - 1;
            if (currentLeftCol < 0) {
                return;
            }
            Cell leftCell = sheet.getRow(currentRow).getCell(currentLeftCol);
            Object leftData = leftCell.getCellType() == CellType.STRING ? leftCell.getStringCellValue() : leftCell.getNumericCellValue();
            //左上表格数据
            Cell leftUpCell = sheet.getRow(currentUpPreRow).getCell(currentLeftCol);
            Object leftUpData = leftUpCell.getCellType() == CellType.STRING ? leftUpCell.getStringCellValue() : leftUpCell.getNumericCellValue();
            if (upData.equals(leftUpData)) {
                if (curData.equals(leftData)) {
                    CellRangeAddress cellRangeAddress = new CellRangeAddress(currentRow, currentRow, currentLeftCol, currentCol);
                    sheet.addMergedRegionUnsafe(cellRangeAddress);
                }
            }
        }
    }
}
