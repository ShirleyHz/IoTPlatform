package com.example.iotplatform.RuleEngine.Service.impl;

import java.time.LocalDateTime;
import java.util.*;
import java.io.*;

import com.alibaba.fastjson.JSONObject;
import com.example.iotplatform.DeviceManager.DAO.DeviceShadowDAO;
import com.example.iotplatform.DeviceManager.Service.RuleEngineCOMM;
import com.example.iotplatform.RuleEngine.Model.Rule;
import com.example.iotplatform.RuleEngine.Repository.RuleRepository;
import com.example.iotplatform.RuleEngine.Service.RuleEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleEngineImpl implements RuleEngine {

    @Autowired
    DeviceShadowDAO deviceShadowDAO;

    @Autowired
    RuleEngineCOMM ruleEngineCOMM;

    @Autowired
    RuleRepository ruleRepository;

//    public RuleEngineImpl(DeviceShadowDAO deviceShadowDAO, RuleEngineCOMM ruleEngineCOMM,
//            RuleRepository ruleRepository) {
//        this.deviceShadowDAO = deviceShadowDAO;
//        this.ruleEngineCOMM = ruleEngineCOMM;
//        this.ruleRepository = ruleRepository;
//    }

    public void ruleMatch(){
        List<Rule> rules = ruleRepository.findAll();
        ArrayList<Rule> curRules = new ArrayList<>();
        LocalDateTime nowTime = LocalDateTime.now();
        for(Rule rule:rules){
//            System.out.println(rule.getExpires_date());
//            System.out.println(nowTime);
            if(rule.isEnabled() && nowTime.isAfter(rule.getEffective_date()) && nowTime.isBefore(rule.getExpires_date())){
                curRules.add(rule);
            }
        }
        Map<Integer,String> desireMap = new HashMap<>();
        for(Rule rule:curRules){
            // id attr operator operand&&id attr operator operand||id attr operator operand
            String conditions = rule.getForeWare();
            String [] ors = conditions.split("\\|\\|");
            Boolean match = false;
            for(String or:ors){
                String[] ands = or.split("&&");
                System.out.println(Arrays.toString(ands));
                match = true;
                for(String and:ands){
                    String[] cond = and.split(" ");
                    if(!deviceShadowDAO.contain(Integer.parseInt(cond[0]))){
                        match = false;
                        continue;
                    }
                    String jsonString = deviceShadowDAO.getJson(Integer.parseInt(cond[0]));
                    JSONObject jsonObject = JSONObject.parseObject(jsonString);
                    String state = jsonObject.getString("State");
                    jsonObject = JSONObject.parseObject(state);
                    String reported = jsonObject.getString("reported");
                    jsonObject = JSONObject.parseObject(reported);
                    Integer attr = jsonObject.getInteger(cond[1]);
                    Integer operand = Integer.parseInt(cond[3]);
                    switch(cond[2]){
                        case ">":
                            if(attr <= operand)
                                match = false;
                                break;
                        case "<":
                            if(attr >= operand)
                            match = false;
                            break;
                        case "=":
                            if(attr != operand)
                            match = false;
                            break;
                        case ">=":
                            if(attr < operand)
                            match = false;
                            break;
                        case "<=":
                            if(attr > operand)
                            match = false;
                            break;
                        case "!=":
                            if(attr == operand)
                            match = false;
                            break;
                    }
                }
                if(match)
                    break;
            }
            
            if(match){
                if(rule.getSalience()==0){
                    // id attr operand&&id attr operand
                    String actions = rule.getHindWare();
                    for(String action:actions.split("&&")){
                        String[] ops = action.split(" ");
                        Integer id = Integer.parseInt(ops[0]);
                        String payload = desireMap.get(id);
                        if(payload==null){
                            payload = "\""+ops[1]+"\":"+ops[2];
                        }else{
                            payload += ",\""+ops[1]+"\":"+ops[2];
                        }
                        desireMap.put(id,payload);
                    }
                }
                else if(rule.getSalience()==1){
                    // id attr&&id attr
                    String records = rule.getHindWare();
                    for(String record:records.split("&&")){
                        String[] ops = record.split(" ");
                        String jsonString = deviceShadowDAO.getJson(Integer.parseInt(ops[0]));
                        JSONObject jsonObject = JSONObject.parseObject(jsonString);
                        String state = jsonObject.getString("State");
                        jsonObject = JSONObject.parseObject(state);
                        String reported = jsonObject.getString("reported");
                        jsonObject = JSONObject.parseObject(reported);
                        Integer attr = jsonObject.getInteger(ops[1]);
                        LocalDateTime now = LocalDateTime.now();
                        try {
                            BufferedWriter out = new BufferedWriter(new FileWriter("data.txt",true));
                            out.write(now.toString()+'-'+ops[0]+'-'+ops[1]+'-'+String.valueOf(attr)+'\n');
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }   
        }
        System.out.println(desireMap.size());
        for(Integer id:desireMap.keySet()){
            if(!deviceShadowDAO.contain(id)) continue;
            ruleEngineCOMM.control(id, "{"+desireMap.get(id)+"}");
        }
    }
}
