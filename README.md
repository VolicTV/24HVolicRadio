# 24 Hour Volic Radio

## Description

24 Hour Volic Radio is a serverless web application that streams old music mixes 24/7. Built with a Java backend running on Azure Functions and a React.js frontend, this project offers a unique listening experience for fans of classic tunes.

## Features

- 24/7 streaming of old music mixes
- Integration with YouTube for content sourcing
- Real-time "Now Playing" information
- Volume control and play/pause functionality
- Responsive web design for desktop and mobile devices

## Technology Stack

- Backend: Java 17, Azure Functions, Azure Media Services
- Frontend: React.js, Axios
- APIs: YouTube Data API
- Cloud: Microsoft Azure (Serverless)

## Project Structure
```
/24-hour-volic-radio
|-- /backend
| |-- /src
| | |-- /main
| | | |-- /java/com/volicradio
| | | | |-- RadioStationApplication.java
| | | | |-- /controllers
| | | | |-- /services
| | | | |-- /models
| | | | |-- /config
| | | |-- /resources
| |-- build.gradle
|-- /frontend
| |-- /public
| |-- /src
| | |-- /components
| | |-- /services
| | |-- App.js
| | |-- index.js
| |-- package.json
|-- /azure
| |-- host.json
| |-- /RadioStationFunction
|-- README.md
|-- gradlew
|-- gradlew.bat
|-- /gradle
| |-- /wrapper
| | |-- gradle-wrapper.jar
| | |-- gradle-wrapper.properties
```
## Setup and Installation

### Prerequisites

- Java 17
- Gradle
- Azure CLI
- Azure Functions Core Tools
- Azurite
- Node.js and npm


### Automated Dependency Installation

For your convenience, we've included scripts to automatically install all required dependencies:

- On Windows: Run `installDependencies.bat` as administrator (will prompt for admin if ran without admin)
- On Linux/macOS: Run `./installDependencies.sh` with sudo privileges

These scripts will install all necessary tools and packages, including:

- Node.js and npm
- Java 17
- Gradle
- Azure CLI
- Azure Functions Core Tools
- Azurite

If you prefer to install dependencies manually, please ensure you have all the above prerequisites installed before proceeding with the setup.

### Backend

1. Ensure you have Java 17 and Gradle installed.
2. Configure your local and Azure settings:
   - For local development, update `backend/src/main/resources/application-local.properties` with your local settings.
   - For Azure deployment, update `backend/src/main/resources/application-azure.properties` with your Azure settings.
3. Build the project:
   ```
   ./gradlew build
   ```

### Running Locally

To run the application locally:

1. Run `./gradlew runLocal` to start the backend.

This command will start the application using the local profile and configurations.

### Deploying to Azure

To deploy the application to Azure:

1. Ensure you have set up your Azure account and have the necessary credentials.
2. Update the Azure configuration in `build.gradle`:
   ```gradle
   azurefunctions {
       resourceGroup = 'your-resource-group'
       appName = '24-hour-volic-radio'
       pricingTier = 'Consumption'
       region = 'westus'
       runtime {
           os = 'java'
           javaVersion = '17'
       }
   }
   ```
3. Run the deployment command:
   ```
   ./gradlew deployToAzure
   ```

This command will build and deploy your application to Azure using the Azure profile and configurations.
### Frontend

1. Ensure you have Node.js and npm installed.
2. Navigate to the `frontend` directory.
3. Run `npm install` to install dependencies.
4. Create a `.env` file and add your API base URL:
   ```bash
   REACT_APP_API_BASE_URL=your_azure_function_url
   ```
5. Run `npm start` to start the development server.

## Usage

After setting up both backend and frontend, you can:

1. For local development:
   - Start the backend using `./gradlew runLocal`
   - Start the frontend using `npm start` in the `frontend` directory
2. For Azure deployment:
   - Deploy the backend using `./gradlew deployToAzure`
   - Deploy the frontend to your chosen hosting service (e.g., Azure Static Web Apps)
3. Open the web application in your browser.
4. Press the play button to start streaming.
5. Adjust volume as needed.
6. View the currently playing track information.

## Contributing

We welcome contributions to 24 Hour Volic Radio! Please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature.
3. Make your changes and commit them.
4. Push to your fork and submit a pull request.

## License

This project is licensed under the MIT License.

## Contact

For any queries, please reach out to [Your Contact Information].