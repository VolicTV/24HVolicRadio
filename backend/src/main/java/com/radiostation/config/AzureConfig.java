package com.radiostation.config;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.core.credential.TokenCredential;
import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.mediaservices.models.MediaServicesAccount;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureConfig {

    @Value("${azure.storage.connection-string}")
    private String storageConnectionString;

    @Value("${azure.media-services.subscription-id}")
    private String subscriptionId;

    @Value("${azure.media-services.resource-group}")
    private String resourceGroup;

    @Value("${azure.media-services.account-name}")
    private String accountName;

    @Bean
    public BlobServiceClient blobServiceClient() {
        return new BlobServiceClientBuilder()
                .connectionString(storageConnectionString)
                .buildClient();
    }

    @Bean
    public TokenCredential tokenCredential() {
        return new DefaultAzureCredentialBuilder().build();
    }

    @Bean
    public AzureResourceManager azureResourceManager(TokenCredential credential) {
        return AzureResourceManager
                .authenticate(credential)
                .withSubscription(subscriptionId);
    }

    @Bean
    public MediaServicesAccount mediaServicesAccount(AzureResourceManager azureResourceManager) {
        return azureResourceManager.mediaServices().manager()
                .serviceClient()
                .getMediaServices()
                .getByResourceGroup(resourceGroup, accountName);
    }
}
