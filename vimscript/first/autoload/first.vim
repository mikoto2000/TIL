" path の絶対パスを求めて返却する。
function! first#abs(path)
    return fnamemodify(a:path, ":p")
endfunc

" path がルートディレクトリかどうかを判定する
" TODO: windows 対応
function! first#is_root_directory(path)
    let l:target = fnamemodify(a:path, ":p")
    " 最後に '/' があるかないかで '/' or '/../' になるので、
    " どちらかだったらルートディレクトリと判断する。
    if target == "/" || target == "/../"
        return 1
    else
        return 0
    endif
endfunc
