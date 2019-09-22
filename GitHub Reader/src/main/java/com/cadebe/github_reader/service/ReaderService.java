package com.cadebe.github_reader.service;

import com.cadebe.github_reader.model.GitHubRepository;
import com.cadebe.github_reader.model.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReaderService {

    private static final String GITHUB_PREFIX = "https://api.github.com/users/";
    private static final String GITHUB_SUBSCRIPTIONS_SUFFIX = "/subscriptions";
    private static final String GITHUB_REPOS_SUFFIX = "/repos";
    private static final String GITHUB_FOLLOWERS_SUFFIX = "/followers";

    public JsonArray getJsonArrayRepos(String userName) {
        String sURL = GITHUB_PREFIX + userName + GITHUB_REPOS_SUFFIX;
        JsonElement root = ReaderService.readURL(sURL);
        assert root != null;
        return root.getAsJsonArray();
    }

    public JsonArray getJsonArraySubscriptions(String userName) {
        String sURL = GITHUB_PREFIX + userName + GITHUB_SUBSCRIPTIONS_SUFFIX;
        JsonElement root = ReaderService.readURL(sURL);
        assert root != null;
        return root.getAsJsonArray();
    }

    public List<JsonElement> buildDistinctCombinedRepoList(JsonArray array1, JsonArray array2) {
        Map<JsonElement, JsonElement> map = new HashMap<>();

        for (int i = 0; i < array1.size(); ++i) {
            map.put(array1.get(i).getAsJsonObject().get("name"), array1.get(i));
        }

        for (int i = 0; i < array2.size(); ++i) {
            map.put(array2.get(i).getAsJsonObject().get("name"), array2.get(i));
        }
        return new ArrayList<>(map.values());
    }

    public List<GitHubRepository> getAllRepositories(List<JsonElement> list, String userName) {
        List<GitHubRepository> repositories = new ArrayList<>();

        for (JsonElement element : list) {
            JsonElement nameElement = element.getAsJsonObject().get("name");
            JsonElement htmlUrlElement = element.getAsJsonObject().get("html_url");
            JsonElement descriptionElement = element.getAsJsonObject().get("description");
            JsonElement languageElement = element.getAsJsonObject().get("language");
            JsonElement forkElement = element.getAsJsonObject().get("fork");
            JsonElement createdElement = element.getAsJsonObject().get("created_at");
            JsonElement updatedElement = element.getAsJsonObject().get("updated_at");
            JsonElement ownerElement = element.getAsJsonObject().get("owner");
            JsonElement starElement = element.getAsJsonObject().get("stargazers_count");

            String repoName = nameElement instanceof JsonNull ? "" : nameElement.getAsString();
            String htmlUrl = htmlUrlElement instanceof JsonNull ? "" : htmlUrlElement.getAsString();
            String description = descriptionElement instanceof JsonNull ? "" : descriptionElement.getAsString();
            String language = languageElement instanceof JsonNull ? "" : languageElement.getAsString();
            String createdYear = languageElement instanceof JsonNull ? "" : createdElement.getAsString();
            String updatedYear = languageElement instanceof JsonNull ? "" : updatedElement.getAsString();
            int stargazersCount = languageElement instanceof JsonNull ? 0 : starElement.getAsInt();
            boolean isForked = !(forkElement instanceof JsonNull) && forkElement.getAsBoolean();
            boolean isOwner = !(ownerElement instanceof JsonNull) &&
                    ownerElement.getAsJsonObject().get("login").getAsString().equals(userName);

            GitHubRepository repo = GitHubRepository.builder()
                    .repoName(repoName)
                    .urlLink(htmlUrl)
                    .description(description)
                    .language(language)
                    .createdYear(createdYear.length() > 4 ? createdYear.substring(0, 4) : "")
                    .updatedYear(updatedYear.length() > 4 ? updatedYear.substring(0, 4) : "")
                    .isFork(isForked)
                    .isOwner(isOwner)
                    .stargazersCount(stargazersCount)
                    .build();

            repositories.add(repo);
        }

        return repositories
                .stream()
                .sorted(Comparator.comparing(GitHubRepository::getRepoName)).collect(Collectors.toList());
    }

    public User getUser(String userNameInput) {
        String sURL = GITHUB_PREFIX + userNameInput;
        JsonElement element = ReaderService.readURL(sURL);

        String userName = "";
        String htmlUrl = "";
        String avatarUrl = "";
        String yearCreated = "";

        JsonElement owner = element.getAsJsonObject().get("owner");
        JsonElement nameElement = element.getAsJsonObject().get("name");
        JsonElement htmlUrlElement = element.getAsJsonObject().get("html_url");
        JsonElement avatarUrlElement = element.getAsJsonObject().get("avatar_url");
        JsonElement yearCreatedElement = element.getAsJsonObject().get("created_at");

        if (!(owner instanceof JsonNull) && !(nameElement instanceof JsonNull)) {
            userName = nameElement.getAsString();
            if (!(htmlUrlElement instanceof JsonNull)) {
                htmlUrl = htmlUrlElement.getAsString();
            }
            if (!(avatarUrlElement instanceof JsonNull)) {
                avatarUrl = avatarUrlElement.getAsString();
            }
            if (!(yearCreatedElement instanceof JsonNull)) {
                yearCreated = yearCreatedElement.getAsString();
            }
        }

        return User.builder()
                .userName(userName)
                .htmlUrl(htmlUrl)
                .avatarUrl(avatarUrl)
                .yearCreated(yearCreated.length() > 4 ? yearCreated.substring(0, 4) : "")
                .numFollowers(ReaderService.getNumFollowers(userNameInput))
                .build();
    }

    public int countAllRepositories(List<GitHubRepository> repositories) {
        return repositories.size();
    }

    public int countAllRepositoriesWithLanguages(List<GitHubRepository> repositories) {
        int count = 0;
        for (GitHubRepository repo : repositories) {
            if (repo.getLanguage() != null && !repo.getLanguage().isEmpty()) {
                count++;
            }
        }
        return count;
    }

    public Map<String, Integer> getAllLanguages(List<GitHubRepository> repositories) {
        List<String> languages = new ArrayList<>();
        for (GitHubRepository repository : repositories) {
            if (repository.getLanguage() != null && !repository.getLanguage().isEmpty()) {
                languages.add(repository.getLanguage());
            }
        }

        Map<String, Integer> map = new HashMap<>();
        for (String i : languages) {
            Integer j = map.get(i);
            map.put(i, (j == null) ? 1 : j + 1);
        }
        return map;
    }

    public Map<String, Double> getLanguageFrequencies(Map<String, Integer> langMap, int langCount) {
        Map<String, Double> map = new HashMap<>();

        for (String i : langMap.keySet()) {
            double a = ((double) langMap.get(i) / langCount) * 100;
            a = Math.round(a * 10.0) / 10.0;
            map.put(i, a);
        }

        return map.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new));
    }

    private static JsonElement readURL(String sURL) {
        JsonElement root = null;
        try {
            // Connect to the URL using Java's native library
            URL url = new URL(sURL);
            URLConnection request = url.openConnection();
            request.connect();

            // Convert to a JSON object to print data with gson
            JsonParser jp = new JsonParser();
            root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        } catch (IOException e) {
            log.error("IOException caught. Could not link to GitHub account:'{}'.", e.getMessage());
            throw new RuntimeException(e);
        }
        return root;
    }

    private static int getNumFollowers(String userName) {
        String sURL = GITHUB_PREFIX + userName + GITHUB_FOLLOWERS_SUFFIX;
        JsonElement root = ReaderService.readURL(sURL);
        assert root != null;
        return root.getAsJsonArray().size();
    }
}

