package com.bocsh.base.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bocsh.base.domain.Serve;
import com.bocsh.base.domain.ServeRepository;
import com.bocsh.base.dto.ServeFindDto;
import com.bocsh.base.dto.ServeQueryDto;
import com.bocsh.base.dto.ServeSaveDto;
import com.bocsh.base.utils.DateUtils;
import com.bocsh.base.utils.DtoUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

@Service
public class ServeService {
	
	@Autowired
    ServeRepository serveRepository;

	@Autowired
	Validator validator;
	
	public void add(ServeSaveDto servicedto) throws Exception{
    	
    	Serve service = DtoUtils.convertNew(servicedto,Serve.class);
    	service.setCreateDate(DateUtils.getCurrentDateString("yyyyMMdd"));
    	
    	serveRepository.save(service);
    }
	
    public boolean exist(Long id) throws Exception{
    	
    	if(!serveRepository.existsById(id)){
    		return false;
    	}
    	else { 
    		return true;
    	}
    }
    
    public void update(ServeSaveDto servicedto,Long id) throws Exception{
    	
    	Serve service = serveRepository.findById(id).get();
    	service = DtoUtils.convertUpdate(servicedto,service);
    	service.setUpdateDate(DateUtils.getCurrentDateString("yyyyMMdd"));
    	serveRepository.save(service);
    }
    
    public ServeFindDto get(Long id) throws Exception{
    	
    	Serve service = serveRepository.findById(id).get();
    	return DtoUtils.convertNew(service,ServeFindDto.class);
    }
    
    public void delete(Long id) throws Exception{
    	
    	serveRepository.deleteById(id);
    }
    
    public Page<ServeFindDto> queryList(Pageable pageable,ServeQueryDto querydto) throws Exception{
    	
        Serve service = DtoUtils.convertNew(querydto,Serve.class);
    	
    	ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("pm", ExampleMatcher.GenericPropertyMatchers.contains())  //设置需要模糊匹配的字段规则
                .withMatcher("serviceId", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("serviceName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("id");

    	Example<Serve> example = Example.of(service,matcher);
    	return DtoUtils.convertPageToDto(serveRepository.findAll(example,pageable),ServeFindDto.class,pageable);
    }

	public List<ServeFindDto> queryListAll(ServeQueryDto querydto) throws Exception{

		Serve service = DtoUtils.convertNew(querydto,Serve.class);

		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("pm", ExampleMatcher.GenericPropertyMatchers.contains())  //设置需要模糊匹配的字段规则
				.withMatcher("serviceId", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("serviceName", ExampleMatcher.GenericPropertyMatchers.contains())
				.withIgnorePaths("id");

		Example<Serve> example = Example.of(service,matcher);
		List<Serve> serveList = serveRepository.findAll(example);

		List<ServeFindDto> findlist = new ArrayList<ServeFindDto>();

		for(Serve serve:serveList){
			ServeFindDto finddto = DtoUtils.convertNew(serve,ServeFindDto.class);
			findlist.add(finddto);
		}

		return findlist;
	}

	public XSSFWorkbook createServiceListExcel(ServeQueryDto querydto) throws Exception {

		List<ServeFindDto> serveList = this.queryListAll(querydto);

		//声明一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		//生成一个表格
		XSSFSheet sheet = workbook.createSheet("Sheet1");
		//设置表格默认列宽度为18个字节
		sheet.setDefaultColumnWidth((short) 18);

		XSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("服务编号");
		header.createCell(1).setCellValue("服务名称");
		header.createCell(2).setCellValue("前端开发人员");
		header.createCell(3).setCellValue("后端开发人员");
		header.createCell(4).setCellValue("信科负责人");
		header.createCell(5).setCellValue("服务描述");

		int rownum=1;

		for (ServeFindDto serve : serveList) {

			XSSFRow row = sheet.createRow(rownum);

			row.createCell(0).setCellValue(serve.getServiceId());
			row.createCell(1).setCellValue(serve.getServiceName());
			row.createCell(2).setCellValue(serve.getFrontDev());
			row.createCell(3).setCellValue(serve.getBackendDev());
			row.createCell(4).setCellValue(serve.getPm());
			row.createCell(5).setCellValue(serve.getServiceDesc());

			rownum++;

		}

		return workbook;
	}

	public void parseServiceListExcel(XSSFWorkbook workbook) throws Exception {

		XSSFSheet xssfSheet = workbook.getSheetAt(0);
		int rownum = xssfSheet.getLastRowNum();
		List<Serve> serveList = new ArrayList<>();
		for (int i = 1; i <= rownum; i++) {

			Row row = xssfSheet.getRow(i);

			ServeSaveDto serveSaveDto = new ServeSaveDto();

			serveSaveDto.setServiceId(row.getCell(0).getStringCellValue());
			serveSaveDto.setServiceName(row.getCell(1).getStringCellValue());
			serveSaveDto.setFrontDev(row.getCell(2).getStringCellValue());
			serveSaveDto.setBackendDev(row.getCell(3).getStringCellValue());
			serveSaveDto.setPm(row.getCell(4).getStringCellValue());
			serveSaveDto.setServiceDesc(row.getCell(5).getStringCellValue());

			String message = "";

			//对每行数据进行validation校验
			Set<ConstraintViolation<ServeSaveDto>> set =
					Validation.buildDefaultValidatorFactory().getValidator().validate(serveSaveDto);

			for(ConstraintViolation<ServeSaveDto> constraintViolation : set) {

				message = message + constraintViolation.getPropertyPath().toString() +
						   "(" + constraintViolation.getInvalidValue() + ")" +
						    constraintViolation.getMessage() + ",";
			}

			if(!message.equals("")){
				throw new IllegalArgumentException("第" + (i + 1) + "行数据校验错误：" + message.substring(0,message.length()-1));
			}

			Serve service = DtoUtils.convertNew(serveSaveDto,Serve.class);
			service.setCreateDate(DateUtils.getCurrentDateString("yyyyMMdd"));

			serveList.add(service);
		}

		serveRepository.saveAll(serveList);

	}

}
