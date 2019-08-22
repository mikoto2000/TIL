module Main exposing (Model, Msg(..), init, main, update, view)

import Browser
import Html exposing (Html, div, text)
import Html.Events exposing (onClick)


main =
    Browser.sandbox { init = init, update = update, view = view }



-- MODEL


type alias Model =
    String


init : Model
init =
    "Initial Message."



-- UPDATE


type Msg
    = Click


update : Msg -> Model -> Model
update msg model =
    case msg of
        Click ->
            "Message Updated!!!"



-- VIEW


view : Model -> Html Msg
view model =
    div [ onClick Click ] [ text model ]
