package main

import (
	"context"
	"log"

	"github.com/bluesky-social/indigo/api/atproto"
	"github.com/bluesky-social/indigo/xrpc"
)

func main() {
	cli := &xrpc.Client{
		Host: "https://bsky.social",
	}

	input := &atproto.ServerCreateSession_Input{
		Identifier: "xxxxxxxxxxxxxxxxxxxxxx",
		Password:   "xxxxxxxxxxxxxxxxxxx",
	}
	output, err := atproto.ServerCreateSession(context.TODO(), cli, input)
	if err != nil {
		log.Fatal(err)
	}
	cli.Auth = &xrpc.AuthInfo{
		AccessJwt:  output.AccessJwt,
		RefreshJwt: output.RefreshJwt,
		Handle:     output.Handle,
		Did:        output.Did,
	}
	_ = cli
}
