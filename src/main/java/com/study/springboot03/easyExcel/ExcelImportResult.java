package com.study.springboot03.easyExcel;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * excel数据导入结果
 *
 * @author yangyanbin
 * @date 2023/03/09
 * @since 20230309
 */
@Data
public class ExcelImportResult {
    private List<ExcelImportSucObjectDto> successDtos;

    private List<ExcelImportErrObjectDto> errDtos;

    public ExcelImportResult(List<ExcelImportSucObjectDto> successDtos,List<ExcelImportErrObjectDto> errDtos){
        this.successDtos =successDtos;
        this.errDtos = errDtos;
    }

    public ExcelImportResult(List<ExcelImportErrObjectDto> errDtos){
        this.successDtos =new ArrayList<>();
        this.errDtos = errDtos;
    }
}
