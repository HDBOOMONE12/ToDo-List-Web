package ru.Artem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.Artem.entity.Record;
import ru.Artem.entity.RecordStatus;
import ru.Artem.service.RecordService;

import java.util.List;

@Controller

public class CommonController {
    private final RecordService recordService;

    @Autowired
    public CommonController(RecordService recordService) {
        this.recordService = recordService;
    }

    @RequestMapping("/home")
    public String getMainPage(Model model){
        List<Record> records =  recordService.findAllRecords();
        int numberOfDoneRecords =(int) records.stream().filter(record->record.getStatus() == RecordStatus.DONE).count();
        int numberOfActiveRecords =(int) records.stream().filter(record->record.getStatus() == RecordStatus.ACTIVE).count();
        model.addAttribute("numberOfDoneRecords", numberOfDoneRecords);
        model.addAttribute("numberOfActiveRecords", numberOfActiveRecords);
        model.addAttribute("records", records);
        return "main-page";
    }
}
