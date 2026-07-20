package ai.runapi.producer.types;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** Media result URL. */
public final class Audio {
  @JsonProperty("id")
  private String id;

  @JsonProperty("audio_url")
  private String audioUrl;

  @JsonProperty("image_url")
  private String imageUrl;

  @JsonProperty("model_name")
  private String modelName;

  @JsonProperty("title")
  private String title;

  @JsonProperty("duration_seconds")
  private Double durationSeconds;

  @JsonProperty("lyrics")
  private String lyrics;

  private final Map<String, JsonNode> extraFields = new LinkedHashMap<String, JsonNode>();

  /** Returns the ID. */
  public String getId() {
    return id;
  }

  /** Returns the audio URL. */
  public String getAudioUrl() {
    return audioUrl;
  }

  /** Returns the image URL. */
  public String getImageUrl() {
    return imageUrl;
  }

  /** Returns the model name. */
  public String getModelName() {
    return modelName;
  }

  /** Returns the title. */
  public String getTitle() {
    return title;
  }

  /** Returns the duration in seconds. */
  public Double getDurationSeconds() {
    return durationSeconds;
  }

  /** Returns the lyrics. */
  public String getLyrics() {
    return lyrics;
  }

  /** Returns unrecognized response fields preserved from the API response. */
  @JsonAnyGetter
  public Map<String, JsonNode> extraFields() {
    return Collections.unmodifiableMap(extraFields);
  }

  @JsonAnySetter
  void putExtraField(String name, JsonNode value) {
    extraFields.put(name, value);
  }
}
