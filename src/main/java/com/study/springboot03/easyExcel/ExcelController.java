package com.study.springboot03.easyExcel;

import com.alibaba.excel.EasyExcel;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.Globals;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yangyanbin
 * @since 20230309
 **/
@RestController
public class ExcelController {
    @Resource
    private CustomerManager customerManager;

    /**
     * // 在读的时候只需要new easyExcelListener 监听器传入就行了
     * EasyExcel.read(fileName, DemoData.class, new EasyExcelListener())
     *     // 需要读取批注 默认不读取
     *     .extraRead(CellExtraTypeEnum.COMMENT)
     *     // 需要读取超链接 默认不读取
     *     .extraRead(CellExtraTypeEnum.HYPERLINK)
     *     // 需要读取合并单元格信息 默认不读取
     *     .extraRead(CellExtraTypeEnum.MERGE).sheet().doRead();
     *
     * @param response
     * @param file
     * @throws IOException
     */
    @PostMapping(value = "importExcel")
    @ApiOperation(value = "客户信息导入")
    //@SysServiceLog(operateContent = "客户信息导入", operateType = Globals.Log_Type_UPLOAD)
    public void importExcel(HttpServletResponse response, @RequestParam MultipartFile file) throws IOException {
        EasyExcelListener easyExcelListener = new EasyExcelListener(customerManager,CustomerExcelDto.class);
        EasyExcel.read(file.getInputStream(),CustomerExcelDto.class,easyExcelListener).sheet().doRead();
        //错误结果集
        List<ExcelImportErrObjectDto> errList = easyExcelListener.getErrList();
        System.out.println("errList = " + errList);
    /*    if (errList.size() > 0){
            List<CustomerCompleteDtoImprotResult> completeDtoImprotResults = errList.stream().map(excelImportErrObjectDto -> {
                CustomerCompleteDtoImprotResult customerCompleteDtoImprotResult = BeanUtils.convert(excelImportErrObjectDto.getObject(), CustomerCompleteDtoImprotResult.class);
                customerCompleteDtoImprotResult.setErrMsg(excelImportErrObjectDto.getErrMsg());
                return customerCompleteDtoImprotResult;
            }).collect(Collectors.toList());
            //导出excel
            EasyExcelUtils.webWriteExcel(response,completeDtoImprotResults,CustomerCompleteDtoImprotResult.class,"客户信息");
        }
        return addSucResult();*/
    }
}
