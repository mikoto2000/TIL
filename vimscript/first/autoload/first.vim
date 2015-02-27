" path の絶対パスを求めて返却する。
function! first#abs(path)
    return fnamemodify(a:path, ":p")
endfunc
