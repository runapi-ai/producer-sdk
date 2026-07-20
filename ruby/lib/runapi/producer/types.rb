# frozen_string_literal: true

module RunApi
  module Producer
    module Types
      # A generated audio track.
      class Audio < RunApi::Core::BaseModel
        optional :id, String
        optional :audio_url, String
        optional :image_url, String
        optional :model_name, String
        optional :title, String
        optional :duration_seconds, Numeric
        optional :lyrics, String
      end

      # Async Producer text-to-music task result.
      class TextToMusicResponse < RunApi::Core::TaskResponse
        required :id, String
        optional :status, String, enum: -> { RunApi::Core::TaskResponse::Status::ALL }
        optional :audios, [-> { Audio }]
        optional :generation_stage, String
        optional :error, String
      end

      # Narrowed response returned by +run+ once polling confirms completion.
      class CompletedTextToMusicResponse < TextToMusicResponse
        required :audios, [-> { Audio }]
      end
    end
  end
end
