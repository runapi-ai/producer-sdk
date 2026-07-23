package ai.runapi.producer.types;

import com.fasterxml.jackson.annotation.JsonCreator;

/** Model slug for text to music operations. */
public final class TextToMusicModel extends ProducerValue {
  /** fuzz-2.0 model slug. */
  public static final TextToMusicModel FUZZ_2_0 = new TextToMusicModel("fuzz-2.0");
  /** fuzz-2.0-pro model slug. */
  public static final TextToMusicModel FUZZ_2_0_PRO = new TextToMusicModel("fuzz-2.0-pro");
  /** fuzz-2.0-raw model slug. */
  public static final TextToMusicModel FUZZ_2_0_RAW = new TextToMusicModel("fuzz-2.0-raw");
  /** fuzz-1.1-pro model slug. */
  public static final TextToMusicModel FUZZ_1_1_PRO = new TextToMusicModel("fuzz-1.1-pro");
  /** fuzz-1.0-pro model slug. */
  public static final TextToMusicModel FUZZ_1_0_PRO = new TextToMusicModel("fuzz-1.0-pro");
  /** fuzz-1.0 model slug. */
  public static final TextToMusicModel FUZZ_1_0 = new TextToMusicModel("fuzz-1.0");
  /** fuzz-1.1 model slug. */
  public static final TextToMusicModel FUZZ_1_1 = new TextToMusicModel("fuzz-1.1");
  /** fuzz-0.8 model slug. */
  public static final TextToMusicModel FUZZ_0_8 = new TextToMusicModel("fuzz-0.8");

  /** Creates a model value from a literal model slug. */
  @JsonCreator
  public TextToMusicModel(String value) {
    super(value);
  }
}
