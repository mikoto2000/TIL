package graphhelper

import (
	"os"

	"github.com/Azure/azure-sdk-for-go/sdk/azidentity"
	msgraphsdk "github.com/microsoftgraph/msgraph-sdk-go"
)

type GraphHelper struct {
	deviceCodeCredential *azidentity.DeviceCodeCredential
	userClient           *msgraphsdk.GraphServiceClient
	graphUserScopes      []string
}

func NewGraphHelper() *GraphHelper {
	g := &GraphHelper{}
	return g
}

// </GraphHelperSnippet>

func (g *GraphHelper) InitializeGraphForClientAuth() error {
	clientId := os.Getenv("CLIENT_ID")
	tenantId := os.Getenv("TENANT_ID")
	clientSecret := os.Getenv("CLIENT_SECRET")
	scopes := []string{
		"https://graph.microsoft.com/.default",
	}

	g.graphUserScopes = scopes

	credential, err := azidentity.NewClientSecretCredential(
		tenantId,
		clientId,
		clientSecret,
		nil,
	)
	if err != nil {
		return err
	}

	client, err := msgraphsdk.NewGraphServiceClientWithCredentials(
		credential,
		scopes,
	)
	if err != nil {
		return err
	}

	g.userClient = client

	return nil
}

