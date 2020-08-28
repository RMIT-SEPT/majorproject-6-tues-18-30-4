package com.yuesun.base.security.menu.controller;

import com.yuesun.base.security.menu.entity.Menu;
import com.yuesun.base.security.menu.service.MenuService;
import com.yuesun.core.controller.AdminController;
import com.yuesun.core.entity.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController extends AdminController {
	
	@Autowired
	private MenuService menuService;

	@PostMapping("list")
	public Object list(ServletRequest request){
		List<Menu> menuList = menuService.findAll();
		return menuList;
	}

	@PostMapping("save")
	public HttpResult save(@Valid Menu menu, BindingResult bindingResult){
        menuService.save(menu);
        return returnSuccess("save "+menu.getName()+" success");
	}
	
	@PostMapping("delete/{id}")
	public HttpResult delete(@PathVariable(required = true) long id){
		menuService.delete(id);
		return returnSuccess("success");
	}
}
