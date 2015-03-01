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

" dir とその親ディレクトリを巡っていって、 fileName を探す。
" ルートディレクトリまで巡っても見つからなかったら諦める。
" TODO: windows 対応
function! first#find(dir, fileName)
    " ディレクトリ末尾にセパレータがあった場合、削除
    let l:d = substitute(first#abs(a:dir), "/$", "", "g")

    " ファイルの絶対パスを取得
    let l:filePath = first#abs(l:d . "/" . a:fileName)

    if filereadable(filePath) == 1
        " ファイルが見つかったらファイルパスを返却する
        return l:filePath
    elseif first#is_root_directory(a:dir) == 1
        " ファイルが見つからない、かつ、
        " ここがルートディレクトリならば 0 を返却する
        return 0
    else
        " ファイルが見つからない、かつ、
        " ここがルートディレクトリ出ないならば、
        " 再帰的に親ディレクトリを探す。
        let l:parentDir = fnamemodify(l:d, ":h")
        return first#find(l:parentDir, a:fileName)
    endif
endfunc

