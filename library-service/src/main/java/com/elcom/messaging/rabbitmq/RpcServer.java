package com.elcom.messaging.rabbitmq;

import com.elcom.controller.AuthorController;
import com.elcom.controller.BookController;
import com.elcom.controller.CategoryController;
import com.elcom.message.RequestMessage;
import com.elcom.message.ResponseMessage;
import com.elcom.utils.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class RpcServer {

    @Autowired
    private BookController bookController;

    @Autowired
    private AuthorController authorController;

    @Autowired
    private CategoryController categoryController;

    @RabbitListener(queues = "${library.rpc.queue}")
    public String processService(String json) {
        System.out.println(json);
        try {
            ObjectMapper mapper = new ObjectMapper();
            RequestMessage request = mapper.readValue(json, RequestMessage.class);
            System.out.println(request);
            ResponseMessage response = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
            if(request != null) {
                String requestPath = request.getRequestPath();
                String urlParam = request.getUrlParam();
                String pathParam = request.getPathParam();
                Map<String, String> headerParam = request.getHeaderParam();
                Map<String, Object> bodyParam = request.getBodyParam();
                switch (request.getRequestMethod()){
                    case "GET":
                        if("/library/book".equalsIgnoreCase(requestPath)) {
                            response = bookController.getAllBook(headerParam, requestPath, urlParam);
                        }
                        else if("/library/author".equalsIgnoreCase(requestPath)) {
                            response = authorController.getAuthors(headerParam);
                        }
                        else if("/library/category".equalsIgnoreCase(requestPath)) {
                            response = categoryController.getCategories(headerParam);
                        }
                        break;
                    case "POST":
                        if("/library/book".equalsIgnoreCase(requestPath)) {
                            response = bookController.addBook(requestPath, urlParam, headerParam, bodyParam);
                        }
                        else if("/library/author".equalsIgnoreCase(requestPath)) {
                            response = authorController.addAuthor(headerParam, bodyParam);
                        }
                        else if("/library/category".equalsIgnoreCase(requestPath)) {
                            response = categoryController.addCategory(headerParam, bodyParam);
                        }
                        break;
                    case "PUT":
                        if("/library/book".equalsIgnoreCase(requestPath)) {
                            response = bookController.editBook(requestPath, urlParam, headerParam, bodyParam);
                        }
                        else if("/library/author".equalsIgnoreCase(requestPath)) {
                            response = authorController.editAuthor(headerParam, bodyParam);
                        }
                        else if("/library/category".equalsIgnoreCase(requestPath)) {
                            response = categoryController.editCategory(headerParam, bodyParam);
                        }
                        break;
                    case "DELETE":
                        if("/library/book/delete".equalsIgnoreCase(requestPath)){
                            response = bookController.deleteBook(headerParam, pathParam);
                        }
                        else if("/library/author/delete".equalsIgnoreCase(requestPath)){
                            response = authorController.deleteAuthorById(headerParam, pathParam);
                        }
                        else if("/library/category/delete".equalsIgnoreCase(requestPath)){
                            response = categoryController.deleteAuthorById(headerParam, pathParam);
                        }
                        break;
                }
            }
            return response != null ? response.toJsonString() : null;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
