package com.example.workflow.service.impl;

import com.example.workflow.model.Option;
import com.example.workflow.repository.OptionRepository;
import com.example.workflow.service.OptionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OptionServiceImpl implements OptionService {
    public OptionServiceImpl(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    private final OptionRepository optionRepository;
    @Override
    public Option create(String description) {
        return new Option(description, new ArrayList<>());
    }

    @Override
    public Map<String,Option> createList(List<String> raws) {
        Map<String, Option> options = new HashMap<>();

        raws.forEach(o->options.put(o, create(o)));
        return options;
    }

    @Override
    public Option save(Option option) {
        return optionRepository.save(option);
    }
}
