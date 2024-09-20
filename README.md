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

- Backend: Java 11, Azure Functions, Azure Media Services
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
| |-- pom.xml
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
```
## Setup and Installation

### Backend

1. Ensure you have Java 11 and Gradle installed.
2. Navigate to the `backend` directory.
3. Run `./gradlew build` to build the project.
4. Set up your Azure account and configure Azure Functions.
5. Deploy the backend to Azure Functions using `./gradlew azureFunctionsDeploy`.

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

1. Open the web application in your browser.
2. Press the play button to start streaming.
3. Adjust volume as needed.
4. View the currently playing track information.

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