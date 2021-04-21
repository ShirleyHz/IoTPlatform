package com.example.iotplatform.RuleEngine.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.iotplatform.RuleEngine.Domian.RulePO;
import com.example.iotplatform.RuleEngine.Model.Rule;
import com.example.iotplatform.RuleEngine.Repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class RuleController {

    @Autowired
    RuleRepository ruleRepository;

    //Device-3 temperature > 29-1 mode 1&&1 temperature 27
    //Forward-2 mode = 0-5 luminance&&6 db
    @RequestMapping(value = "/addRule",method = RequestMethod.POST)
    public String addRule(@RequestBody String jsonStr){
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        String rule = jsonObj.getString("rule");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime effective_date = LocalDateTime.parse(jsonObj.getString("effective_date"),df);
        LocalDateTime expires_date = LocalDateTime.parse(jsonObj.getString("expires_date"),df);
        Boolean enabled = jsonObj.getBoolean("enabled");
        String[] parts = rule.split("-");
        int salience = 0;
        if(parts[0]=="Forward"){
            salience = 1;
        }
        ruleRepository.insert(salience,effective_date,expires_date,enabled,parts[1],parts[2]);
        return "Rule Add Success!!!";
    }
    
    @RequestMapping(value = "/getRule",method = RequestMethod.GET)
    public String getRule(){
        List<Rule> rules = ruleRepository.findAll();
        List<RulePO> rulePOs = new ArrayList<>();
        for(Rule rule:rules){
            RulePO rulePO = new RulePO();
            rulePO.setId(rule.getId());
            rulePO.setEnabled(rule.isEnabled());
            rulePO.setEffective_date(rule.getEffective_date());
            rulePO.setExpires_date(rule.getExpires_date());
            String str = rule.getSalience()==0?"Device":"Forward";
            rulePO.setRule(str+'-'+rule.getForeWare()+'-'+rule.getHindWare());
            rulePOs.add(rulePO);
        }
        return JSON.toJSONString(rulePOs);
    }

    @RequestMapping(value = "/deleteRule",method = RequestMethod.POST)
    public String delRule(@RequestBody String jsonStr){
        Integer id = JSON.parseObject(jsonStr).getIntValue("id");
        ruleRepository.delete(id);
        return "Rule Delete Success!!!";
    }
}
