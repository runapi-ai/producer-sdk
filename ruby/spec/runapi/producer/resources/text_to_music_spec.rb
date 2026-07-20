# frozen_string_literal: true

require "spec_helper"

RSpec.describe RunApi::Producer::Resources::TextToMusic do
  let(:http) { instance_double(RunApi::Core::HttpClient) }
  let(:text_to_music) { described_class.new(http) }
  let(:endpoint) { "/api/v1/producer/text_to_music" }

  describe "#create" do
    it "POSTs flat exact-lyrics params" do
      params = {
        model: "fuzz-2.0",
        vocal_mode: "exact_lyrics",
        prompt: "Warm acoustic pop with clear vocals",
        lyrics: "[Verse] Morning light",
        title: "Morning Light"
      }
      expect(http).to receive(:request).with(:post, endpoint, body: params)
        .and_return("id" => "task-1", "status" => "processing")

      result = text_to_music.create(**params)

      expect(result).to be_a(RunApi::Producer::Types::TextToMusicResponse)
      expect(result.id).to eq("task-1")
    end

    it "rejects lyrics in instrumental mode" do
      expect do
        text_to_music.create(
          model: "fuzz-2.0",
          vocal_mode: "instrumental",
          prompt: "Cinematic ambient score",
          lyrics: "Should not be sent"
        )
      end.to raise_error(RunApi::Core::ValidationError, /lyrics is not allowed/)
    end
  end

  describe "#get" do
    it "GETs and decodes normalized audio results" do
      expect(http).to receive(:request).with(:get, "#{endpoint}/task-1")
        .and_return(
          "id" => "task-1",
          "status" => "completed",
          "audios" => [{
            "id" => "audio-1",
            "audio_url" => "https://media.runapi.ai/audio.m4a",
            "duration_seconds" => 78.35
          }]
        )

      result = text_to_music.get("task-1")

      expect(result).to be_a(RunApi::Producer::Types::TextToMusicResponse)
      expect(result.audios.first.id).to eq("audio-1")
      expect(result.audios.first.duration_seconds).to eq(78.35)
    end
  end
end
