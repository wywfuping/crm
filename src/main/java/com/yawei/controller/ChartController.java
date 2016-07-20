package com.yawei.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yawei.service.ChartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

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

    /**
     * 获取用户的销售价格
     * @return
     */
    @RequestMapping(value = "/user/price",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> userSalesPrice(@RequestParam(required = false,defaultValue = "") String start,
                                             @RequestParam(required = false,defaultValue = "") String end){
        List<Map<String,Object>> data = chartService.userSalesPrice(start,end);
        List<String> names = Lists.newArrayList();
        List<Object> values = Lists.newArrayList();

        for(Map<String,Object> map:data){
            for(Map.Entry<String,Object> entry : map.entrySet()){
                if(entry.getKey().equals("realname")){
                    names.add(entry.getValue().toString());
                }else if(entry.getKey().equals("price")){
                    values.add(entry.getValue().toString());
                }
            }
        }

        Map<String,Object> result = Maps.newHashMap();
        result.put("names",names);
        result.put("values",values);

        return result;
    }

    /**
     * 获取销售进度
     * @return
     */
    @RequestMapping(value = "/progress/data",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> progressData(@RequestParam(required = false,defaultValue = "") String start,
                                           @RequestParam(required = false,defaultValue = "") String end){
        return chartService.progressData(start,end);
    }
}
