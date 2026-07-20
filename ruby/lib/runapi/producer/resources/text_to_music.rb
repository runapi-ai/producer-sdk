# frozen_string_literal: true

module RunApi
  module Producer
    module Resources
      # Generates music from exact lyrics or an instrumental brief.
      class TextToMusic
        include RunApi::Core::ResourceHelpers

        ENDPOINT = "/api/v1/producer/text_to_music"

        RESPONSE_CLASS = Types::TextToMusicResponse
        COMPLETED_RESPONSE_CLASS = Types::CompletedTextToMusicResponse

        def initialize(http)
          @http = http
        end

        def run(options: nil, **params)
          task = create(options: options, **params)
          poll_until_complete { get(task.id, options: options) }
        end

        def create(options: nil, **params)
          params = compact_params(params)
          validate_contract!(CONTRACT["text-to-music"], params)
          request(:post, ENDPOINT, body: params, options: options)
        end

        def get(id, options: nil)
          request(:get, "#{ENDPOINT}/#{id}", options: options)
        end
      end
    end
  end
end
