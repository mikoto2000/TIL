Rails.application.routes.draw do
  resources :accounts
  resources :roles
  # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html

  # ログイン時は、OmniAuth がここを叩く
  get 'auth/:provider/callback', to: 'session#create'

  # ログアウト form で、ここを叩くようにする
  get 'auth/logout', to: 'session#delete'

  # Defines the root path route ("/")
  root "application#index"
end
