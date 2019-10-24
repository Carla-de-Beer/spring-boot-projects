package com.cadebe.github_reader.service

import com.cadebe.github_reader.model.GitHubRepository
import com.cadebe.github_reader.model.User
import com.google.gson.JsonArray
import org.json.JSONArray
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class ReaderServiceTest extends Specification {

    @Shared
    def mockUserName = "octocat"

    @Shared
    def language1 = "language1"

    @Shared
    def language2 = "language2"

    @Shared
    def numLanguages = 2

    @Shared
    List<GitHubRepository> repoList

    def setup() {
        repoList = []
        repoList.add(GitHubRepository.builder()
                .repoName("repoName1")
                .urlLink("urlLink1")
                .description("description1")
                .language(language1)
                .createdYear("2012")
                .stargazersCount(0)
                .build())
        repoList.add(GitHubRepository.builder()
                .repoName("repoName2")
                .description("description2")
                .language(language2)
                .build())
        repoList.add(GitHubRepository.builder()
                .repoName("repoName3")
                .urlLink("urlLink3")
                .language(language1)
                .build())
        repoList.add(GitHubRepository.builder()
                .repoName("repoName4")
                .urlLink("urlLink4")
                .description("description4")
                .stargazersCount(3)
                .build())
    }

    @Subject
    def readerService = new ReaderService()

    def "ReaderService: getJsonArrayRepos()"() {
        given: "A name"

        when: "calling getJsonArrayRepos()"
        JSONArray result = readerService.getJsonArrayRepos(mockUserName)

        then: "getJsonArrayRepos() successfully called"
        result != null
        result.getClass() == JsonArray
        result.size() > 1
    }

    def "ReaderService: getJsonArraySubscriptions()"() {
        given: "A name"

        when: "calling getJsonArraySubscriptions()"
        JsonArray result = readerService.getJsonArraySubscriptions(mockUserName)

        then: "getJsonArraySubscriptions() successfully called"
        result != null
        result.getClass() == JsonArray
        result.size() > 1
    }

    def "ReaderService: getUser()"() {
        given: "A list of GitHubRepositories"

        when: "calling getUser()"
        User result = readerService.getUser(mockUserName)

        then: "getUser() successfully called"
        result != null
        result.getClass() == User
        result.getUserName() == "The Octocat"
        result.getHtmlUrl() == "https://github.com/octocat"
        result.getAvatarUrl() == "https://avatars3.githubusercontent.com/u/583231?v=4"
        result.getYearCreated() == "2011"
        result.getNumFollowers() == 30
    }

    def "ReaderService: countAllRepositories()"() {
        given: "A list of GitHubRepositories"

        when: "calling countAllRepositories()"
        int result = readerService.countAllRepositories(repoList)

        then: "countAllRepositories() successfully called"
        result == repoList.size()
    }

    def "ReaderService: countAllRepositoriesWithLanguages()"() {
        given: "A list of GitHubRepositories"

        when: "calling countAllRepositoriesWithLanguages()"
        int result = readerService.countAllRepositoriesWithLanguages(repoList)

        then: "countAllRepositoriesWithLanguages() successfully called"
        result == 3
    }

    def "ReaderService: getAllLanguages()"() {
        given: "A list of GitHubRepositories"

        when: "calling getAllLanguages()"
        Map<String, Integer> result = readerService.getAllLanguages(repoList)

        then: "getAllLanguages() successfully called"
        result != null
        result.get(language1) == 2
        result.get(language2) == 1
        result.size() == numLanguages
    }

    def "ReaderService: getLanguageFrequencies()"() {
        given: "A language map and a language count"
        Map<String, Integer> langMap = new HashMap<>()
        langMap.put(givenLanguage, frequency)

        when: "calling getLanguageFrequencies()"
        Map<String, Double> result = readerService.getLanguageFrequencies(langMap, 4)

        then: "getLanguageFrequencies() successfully called"
        result != null
        result.get(givenLanguage) == percentage

        where:
        givenLanguage | frequency | percentage
        language1     | 2         | 50.0
        language2     | 1         | 25.0
    }
}