    package ai.runapi.producer;

    import static org.junit.jupiter.api.Assertions.assertEquals;
    import static org.junit.jupiter.api.Assertions.assertNotNull;
    import static org.junit.jupiter.api.Assumptions.assumeTrue;

        import ai.runapi.core.errors.TaskFailedException;
    import ai.runapi.core.RequestOptions;
    import ai.runapi.core.json.Json;
    import ai.runapi.producer.types.CompletedTextToMusicResponse;
    import ai.runapi.producer.types.CompletedTextToMusicResponse;
import ai.runapi.producer.types.TextToMusicModel;
import ai.runapi.producer.types.TextToMusicParams;
import ai.runapi.producer.types.TextToMusicResponse;
    import com.fasterxml.jackson.databind.node.ObjectNode;
    import java.nio.charset.StandardCharsets;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.time.Duration;
    import org.junit.jupiter.api.Test;

    class ProducerLiveApiSmokeTest {
      @Test
      void primaryResourceRunAgainstLiveRunApi() throws Exception {
        assumeTrue("true".equals(System.getenv("RUNAPI_JAVA_LIVE_PRODUCER_SMOKE")));

        String baseUrl = requireEnv("RUNAPI_BASE_URL");
        String apiKey = requireEnv("RUNAPI_API_KEY");
        String callbackUrl = callbackUrl("producer");
        Path outputPath = Paths.get(System.getenv().getOrDefault("RUNAPI_JAVA_LIVE_PRODUCER_OUTPUT", "build/live-producer-smoke-result.json"));
        Files.createDirectories(outputPath.getParent());
        try (ProducerClient client = ProducerClient.builder().apiKey(apiKey).baseUrl(baseUrl).build()) {
          ObjectNode result = Json.mapper().createObjectNode();
          result.put("action", "producer/text-to-music");
          result.put("result_field", "audios");
          result.put("callback_url", callbackUrl);
          try {
      CompletedTextToMusicResponse response =
          client.textToMusic().run(
              TextToMusicParams.builder()
                  .model(TextToMusicModel.FUZZ_2_0)
                  .vocalMode("exact_lyrics")
                  .prompt("A small red cube on a plain white table, studio product photo")
                  .lyrics("sample")
                  .callbackUrl(callbackUrl)
                  .build(),
              RequestOptions.builder()
                  .pollingInterval(Duration.ofSeconds(10))
                  .pollingMaxWait(Duration.ofMinutes(15))
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
            result.put("id", response.getId());
            result.put("status", response.getStatus().value());
            Files.write(outputPath, Json.mapper().writerWithDefaultPrettyPrinter().writeValueAsString(result).getBytes(StandardCharsets.UTF_8));
          } catch (TaskFailedException failure) {
            result.put("status", "failed");
            result.put("exception", failure.getClass().getSimpleName());
            result.put("message", failure.getMessage());
            Object taskResponse = failure.getTaskResponse();
            if (taskResponse instanceof TextToMusicResponse) {
              result.put("id", ((TextToMusicResponse) taskResponse).getId());
              result.put("status", ((TextToMusicResponse) taskResponse).getStatus().value());
              result.put("error", ((TextToMusicResponse) taskResponse).getError());
            }
            Files.write(outputPath, Json.mapper().writerWithDefaultPrettyPrinter().writeValueAsString(result).getBytes(StandardCharsets.UTF_8));
            throw failure;
          } catch (RuntimeException failure) {
            result.put("status", "error");
            result.put("exception", failure.getClass().getSimpleName());
            result.put("message", failure.getMessage());
            Files.write(outputPath, Json.mapper().writerWithDefaultPrettyPrinter().writeValueAsString(result).getBytes(StandardCharsets.UTF_8));
            throw failure;
          }
        }
      }

      private static String callbackUrl(String modelSlug) {
        String base = requireEnv("RUNAPI_CALLBACK_URL");
        String normalized = base.endsWith("/") ? base.substring(0, base.length() - 1) : base;
        return normalized + "/java-live-smoke/" + modelSlug + "/" + System.currentTimeMillis();
      }

      private static String requireEnv(String name) {
        String value = System.getenv(name);
        if (value == null || value.trim().isEmpty()) {
          throw new IllegalStateException(name + " is required");
        }
        return value;
      }
    }
