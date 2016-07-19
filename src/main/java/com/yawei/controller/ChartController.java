package com.yawei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;

@Controller
@RequestMapping("/chart")
public class ChartController {

    @Inject
    private ChartService chartService;
    @RequestMapping(method = RequestMethod.GET)
    public String chartHome(Model model, @RequestParam(required = false,defaultValue = "") String start,
                            @RequestParam(required = false,defaultValue = "") String end){
        Long newCustCount = chartService.findNewCustCount(start,end);
        Long salesCount = chartService.findSalesCount(start,end);
        Float salesPrice = chartService.findSalesPrice(start,end);

        model.addAttribute("newCustCount",newCustCount);
        model.addAttribute("salesCount",salesCount);
        model.addAttribute("salesPrice",salesPrice);
        return "chart/home";
    }
}
