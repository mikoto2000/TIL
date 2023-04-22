Rails.application.config.middleware.use OmniAuth::Builder do
  provider :openid_connect, {
    discovery: true,
    issuer: "https://cognito-idp.#{ENV['AWS_REGION']}.amazonaws.com/#{ENV['AWS_COGNITO_USERPOOL_ID']}",
    client_auth_method: 'jwks',
    client_options: {
      identifier: ENV['AWS_COGNITO_CLIENT_ID'],
      redirect_uri: ENV['AWS_COGNITO_REDIRECT_URL'],
      end_session_endpoint: ENV['AWS_COGNITO_END_SESSION_ENDPOINT']
    }
  }
end