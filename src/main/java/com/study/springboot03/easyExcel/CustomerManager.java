package com.study.springboot03.easyExcel;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author yangyanbin
 * @since 20230309
 **/
@Service
public class CustomerManager implements ExcelCheckManager<CustomerExcelDto>{


    /**
     * 检查导入excel
     *
     * @param excelDtoList 对象
     * @return {@link ExcelImportResult}
     */
    @Override
    public ExcelImportResult checkImportExcel(List<CustomerExcelDto> excelDtoList) {
        // 做一些业务校验或者入库的操作
        List<CustomerExcelDto> excelListDis = excelDtoList.stream().distinct().collect(Collectors.toList());
        List<ExcelImportSucObjectDto> successList = new ArrayList<>();
        for (CustomerExcelDto excelDto : excelListDis) {
            ExcelImportSucObjectDto excelImportSucObjectDto = new ExcelImportSucObjectDto();
            excelImportSucObjectDto.setObject(excelDto);
            successList.add(excelImportSucObjectDto);
        }

        if(CollectionUtils.isNotEmpty(excelListDis)){
            excelDtoList.remove(excelListDis);
        }
        List<ExcelImportErrObjectDto> failList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(excelListDis)){
            ExcelImportErrObjectDto excelImportErrObjectDto = new ExcelImportErrObjectDto(excelDtoList, "存在重复数据");
            failList.add(excelImportErrObjectDto);
        }
        return new ExcelImportResult(successList,failList);

    }


}
