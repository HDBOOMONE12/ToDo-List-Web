package ru.Artem.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import ru.Artem.entity.Record;
import ru.Artem.entity.RecordStatus;
import ru.Artem.entity.dto.RecordsContainerDto;
import ru.Artem.service.RecordService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommonControllerTest {

    @Mock
    private RecordService recordService;

    @Mock
    private Model model;

    @InjectMocks
    private CommonController commonController;

    private Record activeRecord;
    private Record doneRecord;
    private List<Record> records;
    private RecordsContainerDto containerDto;

    @BeforeEach
    void setUp() {
        activeRecord = new Record("Test Active Task");
        activeRecord.setStatus(RecordStatus.ACTIVE);
        
        doneRecord = new Record("Test Done Task");
        doneRecord.setStatus(RecordStatus.DONE);
        
        records = Arrays.asList(activeRecord, doneRecord);
        containerDto = new RecordsContainerDto(records, 1, 1);
    }

    @Test
    void redirectHomePage_ShouldReturnRedirectToHome() {
        String result = commonController.redirectHomePage();

        assertEquals("redirect:/home", result);
    }

    @Test
    void getMainPage_WithNullFilter_ShouldReturnMainPage() {
        when(recordService.findAllRecords(null)).thenReturn(containerDto);

        String result = commonController.getMainPage(model, null);

        assertEquals("main-page", result);
        verify(recordService, times(1)).findAllRecords(null);
        verify(model, times(1)).addAttribute("numberOfDoneRecords", 1);
        verify(model, times(1)).addAttribute("numberOfActiveRecords", 1);
        verify(model, times(1)).addAttribute("records", records);
    }

    @Test
    void getMainPage_WithEmptyFilter_ShouldReturnMainPage() {
        when(recordService.findAllRecords("")).thenReturn(containerDto);

        String result = commonController.getMainPage(model, "");

        assertEquals("main-page", result);
        verify(recordService, times(1)).findAllRecords("");
        verify(model, times(1)).addAttribute("numberOfDoneRecords", 1);
        verify(model, times(1)).addAttribute("numberOfActiveRecords", 1);
        verify(model, times(1)).addAttribute("records", records);
    }

    @Test
    void getMainPage_WithActiveFilter_ShouldReturnMainPage() {
        when(recordService.findAllRecords("ACTIVE")).thenReturn(containerDto);

        String result = commonController.getMainPage(model, "ACTIVE");

        assertEquals("main-page", result);
        verify(recordService, times(1)).findAllRecords("ACTIVE");
        verify(model, times(1)).addAttribute("numberOfDoneRecords", 1);
        verify(model, times(1)).addAttribute("numberOfActiveRecords", 1);
        verify(model, times(1)).addAttribute("records", records);
    }

    @Test
    void getMainPage_WithDoneFilter_ShouldReturnMainPage() {
        when(recordService.findAllRecords("DONE")).thenReturn(containerDto);

        String result = commonController.getMainPage(model, "DONE");

        assertEquals("main-page", result);
        verify(recordService, times(1)).findAllRecords("DONE");
        verify(model, times(1)).addAttribute("numberOfDoneRecords", 1);
        verify(model, times(1)).addAttribute("numberOfActiveRecords", 1);
        verify(model, times(1)).addAttribute("records", records);
    }

    @Test
    void getMainPage_WithEmptyRecords_ShouldReturnMainPage() {
        RecordsContainerDto emptyContainer = new RecordsContainerDto(Collections.emptyList(), 0, 0);
        when(recordService.findAllRecords(null)).thenReturn(emptyContainer);

        String result = commonController.getMainPage(model, null);

        assertEquals("main-page", result);
        verify(recordService, times(1)).findAllRecords(null);
        verify(model, times(1)).addAttribute("numberOfDoneRecords", 0);
        verify(model, times(1)).addAttribute("numberOfActiveRecords", 0);
        verify(model, times(1)).addAttribute("records", Collections.emptyList());
    }

    @Test
    void addRecord_WithValidTitle_ShouldRedirectToHome() {
        String title = "New Task";

        String result = commonController.addRecord(title);

        assertEquals("redirect:/home", result);
        verify(recordService, times(1)).saveRecord(title);
    }

    @Test
    void addRecord_WithNullTitle_ShouldRedirectToHome() {
        String result = commonController.addRecord(null);

        assertEquals("redirect:/home", result);
        verify(recordService, times(1)).saveRecord(null);
    }

    @Test
    void addRecord_WithEmptyTitle_ShouldRedirectToHome() {
        String result = commonController.addRecord("");

        assertEquals("redirect:/home", result);
        verify(recordService, times(1)).saveRecord("");
    }

    @Test
    void makeRecordDone_WithNullFilter_ShouldRedirectToHome() {
        int id = 1;

        String result = commonController.makeRecordDone(id, null);

        assertEquals("redirect:/home", result);
        verify(recordService, times(1)).updateRecordStatus(id, RecordStatus.DONE);
    }

    @Test
    void makeRecordDone_WithEmptyFilter_ShouldRedirectToHome() {
        int id = 1;

        String result = commonController.makeRecordDone(id, "");

        assertEquals("redirect:/home", result);
        verify(recordService, times(1)).updateRecordStatus(id, RecordStatus.DONE);
    }

    @Test
    void makeRecordDone_WithActiveFilter_ShouldRedirectToHomeWithFilter() {
        int id = 1;
        String filter = "ACTIVE";

        String result = commonController.makeRecordDone(id, filter);

        assertEquals("redirect:/home?filter=ACTIVE", result);
        verify(recordService, times(1)).updateRecordStatus(id, RecordStatus.DONE);
    }

    @Test
    void makeRecordDone_WithDoneFilter_ShouldRedirectToHomeWithFilter() {
        int id = 1;
        String filter = "DONE";

        String result = commonController.makeRecordDone(id, filter);

        assertEquals("redirect:/home?filter=DONE", result);
        verify(recordService, times(1)).updateRecordStatus(id, RecordStatus.DONE);
    }

    @Test
    void makeRecordDone_WithWhitespaceFilter_ShouldRedirectToHome() {
        int id = 1;
        String filter = "   ";

        String result = commonController.makeRecordDone(id, filter);

        assertEquals("redirect:/home", result);
        verify(recordService, times(1)).updateRecordStatus(id, RecordStatus.DONE);
    }

    @Test
    void deleteRecord_WithNullFilter_ShouldRedirectToHome() {
        int id = 1;

        String result = commonController.deleteRecord(id, null);

        assertEquals("redirect:/home", result);
        verify(recordService, times(1)).deleteRecordStatus(id);
    }

    @Test
    void deleteRecord_WithEmptyFilter_ShouldRedirectToHome() {
        int id = 1;

        String result = commonController.deleteRecord(id, "");

        assertEquals("redirect:/home", result);
        verify(recordService, times(1)).deleteRecordStatus(id);
    }

    @Test
    void deleteRecord_WithActiveFilter_ShouldRedirectToHomeWithFilter() {
        int id = 1;
        String filter = "ACTIVE";

        String result = commonController.deleteRecord(id, filter);

        assertEquals("redirect:/home?filter=ACTIVE", result);
        verify(recordService, times(1)).deleteRecordStatus(id);
    }

    @Test
    void deleteRecord_WithDoneFilter_ShouldRedirectToHomeWithFilter() {
        int id = 1;
        String filter = "DONE";

        String result = commonController.deleteRecord(id, filter);

        assertEquals("redirect:/home?filter=DONE", result);
        verify(recordService, times(1)).deleteRecordStatus(id);
    }

    @Test
    void deleteRecord_WithWhitespaceFilter_ShouldRedirectToHome() {
        int id = 1;
        String filter = "   ";

        String result = commonController.deleteRecord(id, filter);

        assertEquals("redirect:/home", result);
        verify(recordService, times(1)).deleteRecordStatus(id);
    }
}
