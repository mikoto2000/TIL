type alias Id = Int
type alias LoginName = String
type alias DisplayName = String
type User = Regular Id LoginName DisplayName | Guest DisplayName

mikoto2000 = Regular 0 "mikoto2000" "mikoto"
guest000 = Guest "Guest User 000"

isRegularUser user =\
  case user of\
    Regular _ _ _ ->\
      True\
    Guest _ ->\
      False

welcomeMessage user =\
  case user of\
    Regular _ _ displayName ->\
      "Welcome " ++ displayName ++ "!"\
    Guest displayName ->\
      "Welcome " ++ displayName ++ "!"

Debug.toString mikoto2000

isRegularUser mikoto2000

welcomeMessage mikoto2000


isRegularUser guest000

Debug.toString guest000

welcomeMessage guest000

