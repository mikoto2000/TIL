module Main exposing (Model, Msg(..), init, main, update, view)

import Browser
import Html exposing (..)
import Html.Attributes exposing (..)
import Html.Events exposing (onClick, onInput)



-- MAIN


main =
    Browser.sandbox { init = init, update = update, view = view }



-- MODEL


type alias Model =
    { inputting : String
    , submitted : String
    }


init : Model
init =
    Model "" ""



-- UPDATE


type Msg
    = UpdateText String
    | SubmitText


update : Msg -> Model -> Model
update msg model =
    case msg of
        UpdateText text ->
            { model | inputting = text }

        SubmitText ->
            { model | submitted = model.inputting }



-- VIEW


view : Model -> Html Msg
view model =
    div []
        [ div [] [ text "編集中テキスト: ", text model.inputting ]
        , div [] [ text "送信済みテキスト: ", text model.submitted ]
        , div []
            [ input
                [ placeholder "Input any text."
                , value model.inputting
                , onInput UpdateText
                ]
                []
            , button [ onClick SubmitText ] [ text "submit" ]
            ]
        ]

