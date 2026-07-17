package graphhelper

import (
	"context"
	"errors"
	"fmt"
	"os"

	"github.com/Azure/azure-sdk-for-go/sdk/azidentity"
	msgraphsdk "github.com/microsoftgraph/msgraph-sdk-go"

	"github.com/microsoftgraph/msgraph-sdk-go/models/odataerrors"
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

func (g *GraphHelper) InitializeGraphForUserAuth() error {
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

func (g *GraphHelper) ShowSiteDrives(siteId string) error {

	drives, err := g.userClient.Sites().BySiteId(siteId).Drives().Get(context.Background(), nil)
	if err != nil {
		printGraphError(err)
	}

	for _, drive := range drives.GetValue() {
		fmt.Printf("Drive : %v\n", drive)
	}

	return nil
}

func printGraphError(err error) {
	var odataErr *odataerrors.ODataError
	if errors.As(err, &odataErr) {
		if odataErr.GetErrorEscaped() != nil {
			e := odataErr.GetErrorEscaped()
			fmt.Println("code:", ptrToString(e.GetCode()))
			fmt.Println("message:", ptrToString(e.GetMessage()))
		}
		return
	}

	fmt.Printf("error: %+v\n", err)
}

func ptrToString(p *string) string {
	if p == nil {
		return ""
	}
	return *p
}
