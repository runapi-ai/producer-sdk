// Package producer provides the Producer FUZZ music generation API client.
package producer

import (
	"context"

	"github.com/runapi-ai/core-sdk/go/base"
	"github.com/runapi-ai/core-sdk/go/core"
	"github.com/runapi-ai/core-sdk/go/option"
)

const textToMusicPath = "/api/v1/producer/text_to_music"

type Client struct {
	base.Base
	TextToMusic *TextToMusic
}

func NewClient(opts ...option.ClientOption) (*Client, error) {
	resolved, err := option.ResolveClientOptions(opts...)
	if err != nil {
		return nil, err
	}
	httpClient, err := core.NewHTTPClient(resolved)
	if err != nil {
		return nil, err
	}
	return NewClientWithHTTP(httpClient), nil
}

func NewClientWithHTTP(httpClient core.HTTPClient) *Client {
	return &Client{Base: base.New(httpClient), TextToMusic: &TextToMusic{http: httpClient}}
}

type TextToMusic struct{ http core.HTTPClient }

func (r *TextToMusic) Create(ctx context.Context, params TextToMusicParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	body := core.CompactParams(params)
	if err := core.ValidateParams(contractSchema["text-to-music"], body); err != nil {
		return nil, err
	}
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, textToMusicPath, body, requestOptions)
}

func (r *TextToMusic) Get(ctx context.Context, id string, opts ...option.RequestOption) (*TextToMusicResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[TextToMusicResponse](ctx, r.http, core.ResourcePath(textToMusicPath, id), requestOptions)
}

func (r *TextToMusic) Run(ctx context.Context, params TextToMusicParams, opts ...option.RequestOption) (*TextToMusicResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) {
		return r.Create(ctx, params, opts...)
	}, func(ctx context.Context, id string) (*TextToMusicResponse, error) {
		return r.Get(ctx, id, opts...)
	}, pollingOptions)
}
