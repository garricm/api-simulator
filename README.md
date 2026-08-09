# API Simulator

A lightweight Spring Boot service for simulating REST, JSON, and XML API responses from a file on disk.

The simulator is useful when a client application needs a predictable endpoint during development or integration testing. It does not generate responses dynamically: each request returns the current contents of the configured response file.

## Requirements

- Java 8 or newer
- Maven 3.x

## Getting Started

Clone the repository and start the application with Maven:

```bash
mvn spring-boot:run
```

On Windows, use the equivalent command in Command Prompt or PowerShell:

```bat
mvn spring-boot:run
```

The repository contains Maven Wrapper scripts, but the wrapper metadata directory is not currently included. Use a local Maven installation unless `.mvn/wrapper/maven-wrapper.properties` is restored.

The application starts on port `6083` by default.

## Response File

The response file is configured with `api.simulator.response.fileName`:

```properties
api.simulator.response.fileName=C:\\API-SIMULATOR\\Response.txt
```

When an endpoint is called:

1. The parent directory is created if necessary.
2. The response file is created if it does not exist.
3. A placeholder response is written to a newly created file.
4. The file contents are returned as the HTTP response body.

For a Unix-like environment, override the default Windows path at runtime:

```bash
mvn spring-boot:run \
  -Dspring-boot.run.arguments="--api.simulator.response.fileName=/tmp/api-simulator/Response.txt"
```

You can also create `src/main/resources/application-local.properties` and run with the `local` profile:

```properties
api.simulator.response.fileName=/tmp/api-simulator/Response.txt
server.port=6083
```

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

Edit the response file while the application is running. The next request reads the latest contents.

## Endpoints

All routes are under `/simulator` and support both `GET` and `POST`.

| Method | Path | Content type | Description |
| --- | --- | --- | --- |
| `GET`, `POST` | `/simulator/api` | Any | Returns the response file contents. |
| `GET`, `POST` | `/simulator/api/json` | `application/json` | Returns the response file as JSON. The request must use `Content-Type: application/json`. |
| `GET`, `POST` | `/simulator/api/xml` | `application/xml` | Returns the response file as XML. The request must use `Content-Type: application/xml`. |

The JSON and XML endpoints return the file contents without transforming or validating them. Ensure that the file contains valid content for the endpoint you are calling.

### Examples

Plain response:

```bash
curl http://localhost:6083/simulator/api
```

JSON response:

```bash
curl -X GET \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  http://localhost:6083/simulator/api/json
```

XML response:

```bash
curl -X GET \
  -H "Content-Type: application/xml" \
  -H "Accept: application/xml" \
  http://localhost:6083/simulator/api/xml
```

POST requests use the same response file and do not process the request body:

```bash
curl -X POST \
  -H "Content-Type: application/json" \
  http://localhost:6083/simulator/api/json
```

## Configuration

The default settings are in `src/main/resources/application.properties`:

| Property | Default | Description |
| --- | --- | --- |
| `server.port` | `6083` | Port used by the embedded web server. |
| `api.simulator.response.fileName` | `C:\\API-SIMULATOR\\Response.txt` | Absolute or relative path to the response file. |

Override a property when starting the application:

```bash
mvn spring-boot:run \
  -Dspring-boot.run.arguments="--server.port=8080 --api.simulator.response.fileName=/tmp/Response.txt"
```

## Build and Test

Create the executable JAR:

```bash
mvn clean package
```

Run the test suite:

```bash
mvn test
```

Run the packaged application:

```bash
java -jar target/API-SIMULATOR.jar
```

## Project Structure

```text
src/main/java/com/simulator/api/
├── ApiSimulatorApplication.java       # Spring Boot entry point
└── controllers/
    └── APISimulatorController.java   # Simulator endpoints and file handling

src/main/resources/
├── application.properties             # Runtime configuration
└── banner.txt                         # Startup banner
```

## Notes

- The service is intended for local development and testing, not production use.
- Requests are served from one shared response file, so concurrent callers receive the same current contents.
- The controller returns a plain-text error message if response-file processing fails.
- No authentication, request validation, persistence, or response templating is included.

## License

No license file is currently included in this repository.
