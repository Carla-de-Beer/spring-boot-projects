package com.cadebe.github_reader.controller;

import com.cadebe.github_reader.model.GitHubRepository;
import com.cadebe.github_reader.model.User;
import com.cadebe.github_reader.service.ReaderService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(ReaderController.BASE_URL)
public class ReaderController {

    static final String BASE_URL = "/";
    private static ReaderService readerService;

    @Autowired
    public ReaderController(ReaderService readerService) {
        ReaderController.readerService = readerService;
    }

    @GetMapping("/")
    public String greetingForm(Model model) {
        model.addAttribute("gitHubName", "");
        return "index";
    }

    @PostMapping("/")
    public String greetingSubmit(@RequestParam String gitHubName, Model model) {
        String userName = gitHubName.trim().replace(" ", "-");

        JsonArray jsonArrayRepos = ReaderController.readerService.getJsonArrayRepos(userName);
        JsonArray jsonArraySubscriptions = ReaderController.readerService.getJsonArraySubscriptions(userName);

        List<JsonElement> list = ReaderController.readerService.buildDistinctCombinedRepoList(jsonArrayRepos, jsonArraySubscriptions);
        List<GitHubRepository> repoList = ReaderController.readerService.getAllRepositories(list, userName);

        int repoCount = ReaderController.readerService.countAllRepositories(repoList);
        int reposWithLanguages = ReaderController.readerService.countAllRepositoriesWithLanguages(repoList);

        User user = ReaderController.readerService.getUser(userName);

        Map<String, Integer> langMap = ReaderController.readerService.getAllLanguages(repoList);
        Map<String, Double> freqMap = ReaderController.readerService.getLanguageFrequencies(langMap, reposWithLanguages);

        model.addAttribute("user", user);
        model.addAttribute("repoCount", repoCount);
        model.addAttribute("repoList", repoList);
        model.addAttribute("frequencies", freqMap);
        return "displayInfo";
    }
}