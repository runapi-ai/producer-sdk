package ai.runapi.producer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ai.runapi.core.RequestOptions;
import ai.runapi.core.errors.ValidationException;
import ai.runapi.core.http.HttpRequest;
import ai.runapi.core.http.HttpResponse;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.core.http.JsonRequestBody;
import ai.runapi.core.json.Json;
import ai.runapi.producer.types.CompletedTextToMusicResponse;
import ai.runapi.producer.types.TextToMusicResponse;
import ai.runapi.producer.types.CompletedTextToMusicResponse;
import ai.runapi.producer.types.TextToMusicModel;
import ai.runapi.producer.types.TextToMusicParams;
import ai.runapi.producer.types.TextToMusicResponse;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class ProducerClientTest {
  @Test
  void builderCreatesClientAndUniversalResources() {
    ProducerClient client = ProducerClient.builder().apiKey("sk-test").build();

    assertNotNull(client.textToMusic());
    assertNotNull(client.files());
    assertNotNull(client.account());
  }

  @Test
  void openValueClassesSerializeAsScalarStrings() throws Exception {
    String json = Json.mapper().writeValueAsString(new TextToMusicModel("fuzz-2.0"));

    assertEquals("\"fuzz-2.0\"", json);
    assertEquals(new TextToMusicModel("fuzz-2.0"), Json.mapper().readValue(json, TextToMusicModel.class));
    assertEquals("fuzz-2.0-pro", TextToMusicModel.FUZZ_2_0_PRO.value());
    assertEquals("fuzz-2.0-raw", TextToMusicModel.FUZZ_2_0_RAW.value());
    assertEquals("fuzz-1.1-pro", TextToMusicModel.FUZZ_1_1_PRO.value());
    assertEquals("fuzz-1.0-pro", TextToMusicModel.FUZZ_1_0_PRO.value());
    assertEquals("fuzz-1.0", TextToMusicModel.FUZZ_1_0.value());
    assertEquals("fuzz-1.1", TextToMusicModel.FUZZ_1_1.value());
    assertEquals("fuzz-0.8", TextToMusicModel.FUZZ_0_8.value());
  }

  @Test
  void createSendsExpectedRequestShape() throws Exception {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"task_123\",\"status\":\"processing\"}");
    ProducerClient client = ProducerClient.builder().apiKey("sk-test").transport(transport).build();

    client.textToMusic().create(
        TextToMusicParams.builder()
            .model(TextToMusicModel.FUZZ_2_0)
            .vocalMode("exact_lyrics")
            .prompt("A small red cube on a plain white table, studio product photo")
            .lyrics("sample")
            .build()
    );

    assertEquals("POST", transport.request.getMethod().name());
    assertEquals("/api/v1/producer/text_to_music", transport.request.getPath());
    JsonNode body = bodyJson(transport.request);
    assertNotNull(body);
  }

  @Test
  void getDecodesTaskResponseAndExtraFields() {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"task_456\",\"status\":\"completed\",\"audios\":[{\"id\":\"audio_1\",\"audio_url\":\"https://file.runapi.ai/generated.m4a\",\"image_url\":\"https://file.runapi.ai/cover.jpg\",\"model_name\":\"fuzz-2.0\",\"title\":\"Morning Light\",\"duration_seconds\":78.35,\"lyrics\":\"Morning light\"}],\"generation_stage\":\"all_audios_ready\",\"custom\":\"kept\"}");
    ProducerClient client = ProducerClient.builder().apiKey("sk-test").transport(transport).build();

    TextToMusicResponse response = client.textToMusic().get("task_456");

    assertEquals("GET", transport.request.getMethod().name());
    assertEquals("/api/v1/producer/text_to_music/task_456", transport.request.getPath());
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

  @Test
  void runPollsUntilCompletedAndKeepsExtraFields() {
    SequenceTransport transport = new SequenceTransport(
        "{\"id\":\"task_789\",\"status\":\"processing\"}",
        "{\"id\":\"task_789\",\"status\":\"completed\",\"audios\":[{\"id\":\"audio_1\",\"audio_url\":\"https://file.runapi.ai/generated.m4a\",\"image_url\":\"https://file.runapi.ai/cover.jpg\",\"model_name\":\"fuzz-2.0\",\"title\":\"Morning Light\",\"duration_seconds\":78.35,\"lyrics\":\"Morning light\"}],\"generation_stage\":\"all_audios_ready\",\"custom\":\"kept\"}");
    ProducerClient client = ProducerClient.builder().apiKey("sk-test").transport(transport).build();

    CompletedTextToMusicResponse response = client.textToMusic().run(
        TextToMusicParams.builder()
            .model(TextToMusicModel.FUZZ_2_0)
            .vocalMode("exact_lyrics")
            .prompt("A small red cube on a plain white table, studio product photo")
            .lyrics("sample")
            .build(),
        RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build());

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
    assertEquals(2, transport.calls);
  }

  @Test
  void runRejectsCompletedResponseMissingResultField() {
    SequenceTransport transport = new SequenceTransport(
        "{\"id\":\"task_missing\",\"status\":\"processing\"}",
        "{\"id\":\"task_missing\",\"status\":\"completed\"}");
    ProducerClient client = ProducerClient.builder().apiKey("sk-test").transport(transport).build();

    assertThrows(
        ValidationException.class,
        () -> client.textToMusic().run(
                TextToMusicParams.builder()
                    .model(TextToMusicModel.FUZZ_2_0)
                    .vocalMode("exact_lyrics")
                    .prompt("A small red cube on a plain white table, studio product photo")
                    .lyrics("sample")
                    .build(),
            RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build()));
  }

    @Test
    void coversTexttomusicResourceMethods() {
      CapturingTransport createTransport = new CapturingTransport("{\"id\":\"task_text_to_music\",\"status\":\"processing\"}");
      ProducerClient createClient = ProducerClient.builder().apiKey("sk-test").transport(createTransport).build();
      assertNotNull(createClient.textToMusic().create(
              TextToMusicParams.builder()
                  .model(TextToMusicModel.FUZZ_2_0)
                  .vocalMode("exact_lyrics")
                  .prompt("A small red cube on a plain white table, studio product photo")
                  .lyrics("sample")
                  .build()
      ));

      CapturingTransport createWithOptionsTransport = new CapturingTransport("{\"id\":\"task_text_to_music_options\",\"status\":\"processing\"}");
      ProducerClient createWithOptionsClient = ProducerClient.builder().apiKey("sk-test").transport(createWithOptionsTransport).build();
      assertNotNull(createWithOptionsClient.textToMusic().create(
              TextToMusicParams.builder()
                  .model(TextToMusicModel.FUZZ_2_0)
                  .vocalMode("exact_lyrics")
                  .prompt("A small red cube on a plain white table, studio product photo")
                  .lyrics("sample")
                  .build(),
          RequestOptions.none()));

      CapturingTransport getTransport = new CapturingTransport("{\"id\":\"task_text_to_music\",\"status\":\"completed\",\"audios\":[{\"id\":\"audio_1\",\"audio_url\":\"https://file.runapi.ai/generated.m4a\",\"image_url\":\"https://file.runapi.ai/cover.jpg\",\"model_name\":\"fuzz-2.0\",\"title\":\"Morning Light\",\"duration_seconds\":78.35,\"lyrics\":\"Morning light\"}],\"generation_stage\":\"all_audios_ready\"}");
      ProducerClient getClient = ProducerClient.builder().apiKey("sk-test").transport(getTransport).build();
      assertNotNull(getClient.textToMusic().get("task_text_to_music"));

      CapturingTransport getWithOptionsTransport = new CapturingTransport("{\"id\":\"task_text_to_music_options\",\"status\":\"completed\",\"audios\":[{\"id\":\"audio_1\",\"audio_url\":\"https://file.runapi.ai/generated.m4a\",\"image_url\":\"https://file.runapi.ai/cover.jpg\",\"model_name\":\"fuzz-2.0\",\"title\":\"Morning Light\",\"duration_seconds\":78.35,\"lyrics\":\"Morning light\"}],\"generation_stage\":\"all_audios_ready\"}");
      ProducerClient getWithOptionsClient = ProducerClient.builder().apiKey("sk-test").transport(getWithOptionsTransport).build();
      assertNotNull(getWithOptionsClient.textToMusic().get("task_text_to_music_options", RequestOptions.none()));

      SequenceTransport runTransport = new SequenceTransport(
          "{\"id\":\"task_text_to_music_run\",\"status\":\"processing\"}",
          "{\"id\":\"task_text_to_music_run\",\"status\":\"completed\",\"audios\":[{\"id\":\"audio_1\",\"audio_url\":\"https://file.runapi.ai/generated.m4a\",\"image_url\":\"https://file.runapi.ai/cover.jpg\",\"model_name\":\"fuzz-2.0\",\"title\":\"Morning Light\",\"duration_seconds\":78.35,\"lyrics\":\"Morning light\"}],\"generation_stage\":\"all_audios_ready\"}");
      ProducerClient runClient = ProducerClient.builder().apiKey("sk-test").transport(runTransport).build();
      CompletedTextToMusicResponse runResponse = runClient.textToMusic().run(
              TextToMusicParams.builder()
                  .model(TextToMusicModel.FUZZ_2_0)
                  .vocalMode("exact_lyrics")
                  .prompt("A small red cube on a plain white table, studio product photo")
                  .lyrics("sample")
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build());
      assertNotNull(runResponse);

      SequenceTransport runWithOptionsTransport = new SequenceTransport(
          "{\"id\":\"task_text_to_music_run_options\",\"status\":\"processing\"}",
          "{\"id\":\"task_text_to_music_run_options\",\"status\":\"completed\",\"audios\":[{\"id\":\"audio_1\",\"audio_url\":\"https://file.runapi.ai/generated.m4a\",\"image_url\":\"https://file.runapi.ai/cover.jpg\",\"model_name\":\"fuzz-2.0\",\"title\":\"Morning Light\",\"duration_seconds\":78.35,\"lyrics\":\"Morning light\"}],\"generation_stage\":\"all_audios_ready\"}");
      ProducerClient runWithOptionsClient = ProducerClient.builder().apiKey("sk-test").transport(runWithOptionsTransport).build();
      assertNotNull(runWithOptionsClient.textToMusic().run(
              TextToMusicParams.builder()
                  .model(TextToMusicModel.FUZZ_2_0)
                  .vocalMode("exact_lyrics")
                  .prompt("A small red cube on a plain white table, studio product photo")
                  .lyrics("sample")
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build()));
    }

  private static JsonNode bodyJson(HttpRequest request) throws Exception {
    JsonRequestBody body = (JsonRequestBody) request.getBody();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    body.writeTo(out);
    return Json.mapper().readTree(out.toByteArray());
  }

  private static final class CapturingTransport implements HttpTransport {
    private final String body;
    private HttpRequest request;

    private CapturingTransport(String body) {
      this.body = body;
    }

    public HttpResponse send(HttpRequest request) {
      this.request = request;
      return new HttpResponse(200, body, Collections.<String, java.util.List<String>>emptyMap());
    }

    public void close() {}
  }

  private static final class SequenceTransport implements HttpTransport {
    private final String[] responses;
    private int calls;

    private SequenceTransport(String... responses) {
      this.responses = responses;
    }

    public HttpResponse send(HttpRequest request) {
      String response = responses[Math.min(calls, responses.length - 1)];
      calls++;
      return new HttpResponse(200, response, Collections.<String, java.util.List<String>>emptyMap());
    }

    public void close() {}
  }
}
