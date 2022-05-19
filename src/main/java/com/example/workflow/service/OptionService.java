package com.example.workflow.service;


import com.example.workflow.model.Option;

import java.util.List;
import java.util.Map;

public interface OptionService {
    Option create(String description);
    Map<String,Option> createList(List<String> raws);

    Option save(Option option);
}
