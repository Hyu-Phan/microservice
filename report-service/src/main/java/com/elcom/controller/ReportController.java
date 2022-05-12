package com.elcom.controller;

import com.elcom.message.ResponseMessage;
import com.elcom.model.Book;
import com.elcom.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ReportController extends BaseController{
    @Autowired
    private ReportService reportService;

    public ResponseMessage addBook(Map<String, String> headerParam, Map<String, Object> bodyParam) {
        ResponseMessage response = null;
        response = authenToken(headerParam);
        if(response != null && response.getStatus() == HttpStatus.OK.value()) {
            List<Object> bookList = (List<Object>) bodyParam.get("books");
            System.out.println(bookList.size());
        }
        return response;
    }
}
