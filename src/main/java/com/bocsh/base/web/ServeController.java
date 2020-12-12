package com.bocsh.base.web;

import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import com.bocsh.base.auth.Authorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.bocsh.base.dto.ServeFindDto;
import com.bocsh.base.dto.ServeQueryDto;
import com.bocsh.base.dto.ServeSaveDto;
import com.bocsh.base.service.ServeService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags="微服务服务定义")
@RequestMapping("/services")
public class ServeController extends AbstractController{

	
	@Autowired
	private ServeService serveService;

    @ApiOperation(value="获取微服务定义列表", notes="获取微服务定义列表") 
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "int", paramType = "query", value = "当前页数"),
        @ApiImplicitParam(name = "size", dataType = "int", paramType = "query", value = "每页条数"),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "排序字段")
    })
    @PostMapping("/search")
    @Authorize({"MSA_USER","MSA_ADMIN"})
    public Page<ServeFindDto> getService(Pageable pageable,@RequestBody ServeQueryDto querydto) throws Exception {

		return serveService.queryList(pageable, querydto);

    }
    
    @ApiOperation(value="新增微服务定义", notes="新增微服务定义") 
    @PostMapping
	@Authorize({"MSA_ADMIN"})
    @ResponseStatus(code=HttpStatus.CREATED)
    public void addService(@Validated @RequestBody ServeSaveDto servicedto,HttpServletResponse response) throws Exception {

    	serveService.add(servicedto);
    }
    
    @ApiOperation(value="获取单个微服务定义", notes="获取单个微服务定义") 
    @GetMapping("/{id}")
	@Authorize({"MSA_USER","MSA_ADMIN"})
    public ServeFindDto getService(@PathVariable Long id,HttpServletResponse response) throws Exception {
    	
    	if(!serveService.exist(id)){
    		response.sendError(HttpStatus.NOT_FOUND.value());
    		return null;
    	}
    	
    	return serveService.get(id);
    	
    }
    
    @ApiOperation(value="修改微服务定义", notes="修改微服务定义") 
    @PutMapping("/{id}")
	@Authorize({"MSA_ADMIN"})
    @ResponseStatus(code=HttpStatus.CREATED)
    public void updateService(@PathVariable Long id,
    		                  @Validated @RequestBody ServeSaveDto servicedto,
    		                  HttpServletResponse response) throws Exception {
    	
    	if(!serveService.exist(id)){
    		response.sendError(HttpStatus.NOT_FOUND.value());
    		return;
    	}
    	
    	serveService.update(servicedto,id);
        
    }
    
    
    @ApiOperation(value="删除微服务定义", notes="删除微服务定义") 
    @DeleteMapping("/{id}")
	@Authorize({"MSA_ADMIN"})
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteService(@PathVariable Long id,HttpServletResponse response) throws Exception {
    	
    	if(!serveService.exist(id)){
    		response.sendError(HttpStatus.NOT_FOUND.value());
    		return;
    	}
    	
    	serveService.delete(id);
    
    }

	@ApiOperation(value="批量下载微服务列表", notes="批量下载微服务列表")
	@PostMapping("/download")
	@Authorize({"MSA_USER","MSA_ADMIN"})
	public void download(@RequestBody ServeQueryDto querydto,HttpServletResponse response) throws Exception {

		//声明一个工作薄
		XSSFWorkbook workbook = serveService.createServiceListExcel(querydto);

		response.setContentType("application/octet-stream");
		//默认Excel名称
		response.setHeader("Content-Disposition", "attachment;filename=services.xlsx");
		response.flushBuffer();

		workbook.write(response.getOutputStream());

	}

	@ApiOperation(value="批量上传微服务列表", notes="批量上传微服务列表")
	@PostMapping("/upload")
	@Authorize({"MSA_ADMIN"})
	@ResponseStatus(code=HttpStatus.CREATED)
	public void upload(@RequestParam("img")MultipartFile file, HttpServletResponse response) throws Exception {

		String filename = file.getOriginalFilename();
		log.info(filename);

		if (filename.endsWith(".xlsx")) {

			// 开始解析文本
			InputStream ins = file.getInputStream();
			if (ins != null) {

				XSSFWorkbook workbook = new XSSFWorkbook(ins);
				serveService.parseServiceListExcel(workbook);
			}
		}
		else{

			response.sendError(HttpStatus.BAD_REQUEST.value(),"上传文件必须为xlsx格式");
			return;

		}
	}
    
}
