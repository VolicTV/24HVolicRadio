package com.volicradio;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;
import com.azure.storage.blob.*;
import com.azure.identity.*;
import com.azure.core.credential.TokenCredential;
import com.azure.resourcemanager.mediaservices.models.*;
import com.volicradio.services.YoutubeService;
import com.volicradio.models.Track;
import com.google.gson.Gson;

import java.util.*;

public class RadioStationFunction {
    private static final String YOUTUBE_API_KEY = System.getenv("YOUTUBE_API_KEY");
    private static final Gson gson = new Gson();

    @FunctionName("StreamRadio")
    public HttpResponseMessage streamRadio(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS) 
            HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        try {
            // Initialize Azure clients
            TokenCredential credential = new DefaultAzureCredentialBuilder().build();
            MediaServicesManager manager = MediaServicesManager
                .authenticate(credential, "your-subscription-id");

            // Get your Media Services account
            MediaServicesAccount account = manager.mediaServicesAccounts()
                .getByResourceGroup("your-resource-group", "your-media-services-account");

            // Get or create a streaming locator
            StreamingLocator locator = getOrCreateStreamingLocator(account);

            // Get streaming URLs
            List<String> urls = getStreamingUrls(account, locator);

            // For simplicity, we'll just return the first URL
            String streamUrl = urls.get(0);

            return request.createResponseBuilder(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body("{\"streamUrl\": \"" + streamUrl + "\"}")
                    .build();

        } catch (Exception e) {
            context.getLogger().severe("Error in streaming: " + e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while trying to stream.")
                    .build();
        }
    }

    @FunctionName("SearchYoutube")
    public HttpResponseMessage searchYoutube(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS) 
            HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("YouTube search function processed a request.");

        // Add CORS headers
        HttpResponseMessage.Builder responseBuilder = request.createResponseBuilder(HttpStatus.OK)
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
            .header("Access-Control-Allow-Headers", "Content-Type");

        String query = request.getQueryParameters().get("q");
        if (query == null) {
            return responseBuilder
                .status(HttpStatus.BAD_REQUEST)
                .body("Please provide a search query using the 'q' parameter.")
                .build();
        }

        try {
            YoutubeService youtubeService = YoutubeService.getInstance(YOUTUBE_API_KEY);
            List<Track> tracks = youtubeService.searchTracks(query, 10);
            
            return responseBuilder
                .header("Content-Type", "application/json")
                .body(gson.toJson(tracks))
                .build();
        } catch (Exception e) {
            context.getLogger().severe("Error in YouTube search: " + e.getMessage());
            return responseBuilder
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred while searching YouTube.")
                .build();
        }
    }

    private StreamingLocator getOrCreateStreamingLocator(MediaServicesAccount account) {
        // Check if a streaming locator already exists
        // If not, create a new one
        // This is a simplified example - you'd need to implement the actual logic
        return account.streamingLocators().define("locator-name")
            .withAssetName("your-asset-name")
            .withStreamingPolicyName("Predefined_ClearStreamingOnly")
            .create();
    }

    private List<String> getStreamingUrls(MediaServicesAccount account, StreamingLocator locator) {
        // Get the streaming endpoints
        StreamingEndpoint streamingEndpoint = account.streamingEndpoints().getByName("default");

        // Build the streaming URLs
        List<String> urls = new ArrayList<>();
        for (String path : locator.paths()) {
            urls.add("https://" + streamingEndpoint.hostName() + path);
        }

        return urls;
    }
}
