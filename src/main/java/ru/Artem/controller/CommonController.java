package ru.Artem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.Artem.entity.Record;
import ru.Artem.entity.RecordStatus;
import ru.Artem.entity.dto.RecordsContainerDto;
import ru.Artem.service.RecordService;

import java.util.List;

@Controller

public class CommonController {
    private final RecordService recordService;

    @Autowired
    public CommonController(RecordService recordService) {
        this.recordService = recordService;
    }

    @RequestMapping("/")
    public String redirectHomePage() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String getMainPage(Model model, @RequestParam(name = "filter", required = false) String filterMode) {
        RecordsContainerDto container = recordService.findAllRecords(filterMode);
        model.addAttribute("numberOfDoneRecords", container.getNumberOfDoneRecords());
        model.addAttribute("numberOfActiveRecords", container.getNumberOfActiveRecords());
        model.addAttribute("records", container.getRecords());
        return "main-page";
    }

    @RequestMapping(value = "/add-record", method = RequestMethod.POST)
    public String addRecord(@RequestParam String title) {
        recordService.saveRecord(title);
        return "redirect:/home";
    }

    @RequestMapping(value = "/make-record-done", method = RequestMethod.POST)
    public String makeRecordDone(@RequestParam int id) {
        recordService.updateRecordStatus(id, RecordStatus.DONE);
        return "redirect:/home";
    }

    @RequestMapping(value = "/delete-record", method = RequestMethod.POST)
    public String deleteRecord(@RequestParam int id) {
        recordService.deleteRecordStatus(id);
        return "redirect:/home";
    }
}
