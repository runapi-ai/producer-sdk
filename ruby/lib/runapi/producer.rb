# frozen_string_literal: true

require "runapi/core"
require_relative "producer/types"
require_relative "producer/contract_gen"
require_relative "producer/resources/text_to_music"
require_relative "producer/client"

module RunApi
  module Producer
    AuthenticationError = RunApi::Core::AuthenticationError
    RateLimitError = RunApi::Core::RateLimitError
    InsufficientCreditsError = RunApi::Core::InsufficientCreditsError
    NotFoundError = RunApi::Core::NotFoundError
    ValidationError = RunApi::Core::ValidationError
    TaskFailedError = RunApi::Core::TaskFailedError
    TaskTimeoutError = RunApi::Core::TaskTimeoutError
  end
end
