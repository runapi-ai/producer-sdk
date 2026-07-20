    package ai.runapi.producer;

    import static org.junit.jupiter.api.Assertions.assertEquals;
    import static org.junit.jupiter.api.Assertions.assertNotNull;

    import ai.runapi.core.Constants;
    import ai.runapi.core.RequestOptions;
    import ai.runapi.core.json.Json;
    import ai.runapi.producer.types.CompletedTextToMusicResponse;
    import ai.runapi.producer.types.CompletedTextToMusicResponse;
import ai.runapi.producer.types.TextToMusicModel;
import ai.runapi.producer.types.TextToMusicParams;
import ai.runapi.producer.types.TextToMusicResponse;
    import com.fasterxml.jackson.databind.JsonNode;
    import com.sun.net.httpserver.HttpExchange;
    import com.sun.net.httpserver.HttpServer;
    import java.io.ByteArrayOutputStream;
    import java.io.IOException;
    import java.io.OutputStream;
    import java.net.InetSocketAddress;
    import java.nio.charset.StandardCharsets;
    import java.time.Duration;
    import java.util.ArrayList;
    import java.util.List;
    import org.junit.jupiter.api.AfterEach;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;

    class ProducerLocalApiSmokeTest {
      private HttpServer server;
      private final List<CapturedRequest> requests = new ArrayList<CapturedRequest>();

      @BeforeEach
      void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/api/v1/producer/text_to_music", this::handleRequest);
        server.start();
      }

      @AfterEach
      void stopServer() {
        server.stop(0);
      }

      @Test
      void runUsesApacheTransportAgainstLocalApi() throws Exception {
        try (ProducerClient client =
            ProducerClient.builder()
                .apiKey("sk-test")
                .baseUrl("http://127.0.0.1:" + server.getAddress().getPort())
                .build()) {
      CompletedTextToMusicResponse response =
          client.textToMusic().run(
              TextToMusicParams.builder()
                  .model(TextToMusicModel.FUZZ_2_0)
                  .vocalMode("exact_lyrics")
                  .prompt("A small red cube on a plain white table, studio product photo")
                  .lyrics("sample")
                  .build(),
              RequestOptions.builder()
                  .pollingInterval(Duration.ofMillis(1))
                  .pollingMaxWait(Duration.ofSeconds(2))
                  .maxRetries(0)
                  .build());

          assertEquals("completed", response.getStatus().value());
          assertNotNull(response.getAudios());
assertEquals("audio_1", response.getAudios().get(0).getId());
assertEquals("https://file.runapi.ai/generated.m4a", response.getAudios().get(0).getAudioUrl());
assertEquals("https://file.runapi.ai/cover.jpg", response.getAudios().get(0).getImageUrl());
assertEquals("fuzz-2.0", response.getAudios().get(0).getModelName());
assertEquals("Morning Light", response.getAudios().get(0).getTitle());
assertEquals(78.35, response.getAudios().get(0).getDurationSeconds());
assertEquals("Morning light", response.getAudios().get(0).getLyrics());
assertEquals("all_audios_ready", response.getGenerationStage());
          assertEquals("kept", response.extraFields().get("custom").asText());
        }

        assertEquals(2, requests.size());
        CapturedRequest create = requests.get(0);
        assertEquals("POST", create.method);
        assertEquals("/api/v1/producer/text_to_music", create.path);
        assertEquals("Bearer sk-test", create.header("Authorization"));
        assertEquals(Constants.SDK_USER_AGENT, create.header("User-Agent"));
        JsonNode body = Json.mapper().readTree(create.body);
        assertNotNull(body);
      }

      private void handleRequest(HttpExchange exchange) throws IOException {
        CapturedRequest captured = CapturedRequest.from(exchange);
        requests.add(captured);

        if ("POST".equals(captured.method)) {
          write(exchange, 200, "{\"id\":\"task_local_123\",\"status\":\"processing\"}");
          return;
        }

        assertEquals("/api/v1/producer/text_to_music/task_local_123", captured.path);
        write(exchange, 200, "{\"id\":\"task_local_123\",\"status\":\"completed\",\"audios\":[{\"id\":\"audio_1\",\"audio_url\":\"https://file.runapi.ai/generated.m4a\",\"image_url\":\"https://file.runapi.ai/cover.jpg\",\"model_name\":\"fuzz-2.0\",\"title\":\"Morning Light\",\"duration_seconds\":78.35,\"lyrics\":\"Morning light\"}],\"generation_stage\":\"all_audios_ready\",\"custom\":\"kept\"}");
      }

      private static void write(HttpExchange exchange, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream out = exchange.getResponseBody()) {
          out.write(bytes);
        }
      }

      private static final class CapturedRequest {
        private final String method;
        private final String path;
        private final com.sun.net.httpserver.Headers headers;
        private final String body;

        private CapturedRequest(String method, String path, com.sun.net.httpserver.Headers headers, String body) {
          this.method = method;
          this.path = path;
          this.headers = headers;
          this.body = body;
        }

        private static CapturedRequest from(HttpExchange exchange) throws IOException {
          return new CapturedRequest(
              exchange.getRequestMethod(),
              exchange.getRequestURI().getPath(),
              exchange.getRequestHeaders(),
              new String(readAll(exchange), StandardCharsets.UTF_8));
        }

        private String header(String name) {
          List<String> values = headers.get(name);
          if (values == null || values.isEmpty()) {
            return null;
          }
          return values.get(0);
        }

        private static byte[] readAll(HttpExchange exchange) throws IOException {
          ByteArrayOutputStream out = new ByteArrayOutputStream();
          byte[] buffer = new byte[1024];
          int read;
          while ((read = exchange.getRequestBody().read(buffer)) != -1) {
            out.write(buffer, 0, read);
          }
          return out.toByteArray();
        }
      }
    }
